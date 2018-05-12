package gregtech.api.worldgen.config;

import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.api.util.GTUtility;
import gregtech.api.util.XSTR;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OreConfigUtils {

    public static Predicate<WorldProvider> createWorldPredicate(JsonElement element) {
        if(!element.isJsonArray())
            throw new IllegalArgumentException("World filter should be array!");
        JsonArray predicateArray = element.getAsJsonArray();
        ArrayList<Predicate<WorldProvider>> allPredicates = new ArrayList<>();
        for(JsonElement worldPredicate : predicateArray) {
            String stringValue = worldPredicate.getAsString();
            if(stringValue.equals("is_surface_world")) {
                allPredicates.add(WorldProvider::isSurfaceWorld);
                continue;
            } else if(stringValue.equals("is_nether")) {
                allPredicates.add(WorldProvider::isNether);
                continue;
            }
            Function<WorldProvider, String> stringSupplier;
            if(stringValue.startsWith("name:")) {
                stringSupplier = provider -> provider.getDimensionType().getName();
                stringValue = stringValue.substring(5);
            } else if(stringValue.startsWith("provider_class:")) {
                stringSupplier = provider -> provider.getClass().getSimpleName();
                stringValue = stringValue.substring(15);
            } else throw new IllegalArgumentException("Unknown world predicate: " + stringValue);
            if(stringValue.startsWith("*")) {
                Pattern pattern = Pattern.compile(stringValue.substring(1));
                return provider -> pattern.matcher(stringSupplier.apply(provider)).matches();
            } else {
                String finalStringValue = stringValue;
                return provider -> finalStringValue.equalsIgnoreCase(stringSupplier.apply(provider));
            }
        }

        return provider -> allPredicates.stream().anyMatch(p -> p.test(provider));
    }

    public static Function<Biome, Integer> createBiomeWeightModifier(JsonElement element) {
        if (!element.isJsonObject())
            throw new IllegalArgumentException("Biome weight modifier should be object!");
        JsonObject object = element.getAsJsonObject();
        String influenceType = object.get("type").getAsString();

        if(influenceType.equals("biome_map")) {
            HashMap<Biome, Integer> backedMap = new HashMap<>();
            for(Entry<String, JsonElement> elementEntry : object.entrySet()) {
                if (elementEntry.getKey().equals("type")) continue; //skip type
                ResourceLocation biomeName = new ResourceLocation(elementEntry.getKey());
                Biome biome = GameRegistry.findRegistry(Biome.class).getValue(biomeName);
                if (biome == null)
                    throw new IllegalArgumentException("Couldn't find biome with name " + biomeName);
                backedMap.put(biome, elementEntry.getValue().getAsInt());
            }
            return biome -> backedMap.getOrDefault(biome, 0);

        } else if(influenceType.equals("biome_dictionary")) {
            HashMap<BiomeDictionary.Type, Integer> backedMap = new HashMap<>();
            for(Entry<String, JsonElement> elementEntry : object.entrySet()) {
                if (elementEntry.getKey().equals("type")) continue; //skip type
                BiomeDictionary.Type type = GTUtility.getBiomeTypeTagByName(elementEntry.getKey());
                backedMap.put(type, elementEntry.getValue().getAsInt());
            }
            return biome -> {
                int totalModifier = 0;
                for(Type type : backedMap.keySet()) {
                    if(BiomeDictionary.hasType(biome, type)) {
                        totalModifier += backedMap.get(type);
                    }
                }
                return totalModifier;
            };
        } else {
            throw new IllegalArgumentException("Unknown biome influence type: " + influenceType);
        }
    }

    public static Function<IBlockState, IBlockState> createBlockStateFiller(JsonElement element) {
        if(element instanceof JsonPrimitive) {
            String stringDeclaration = element.getAsString();
            return createSimpleFiller(stringDeclaration);
        } else if(element instanceof JsonObject) {
            JsonObject object = element.getAsJsonObject();
            if(object.has("block")) {
                IBlockState stateDefinition = parseBlockStateDefinition(object);
                return state -> stateDefinition;
            }
            if(!object.has("type")) {
                throw new IllegalArgumentException("Missing required type for block state predicate");
            }
            String predicateType = object.get("type").getAsString();
            if(predicateType.equals("random")) {
                return createRandomStateFiller(object);
            } else if(predicateType.equals("weight_random")) {
                return createWeightRandomStateFiller(object);
            } else if(predicateType.equals("stone_type_match")) {
                return createStoneTypeMatchFiller(object);
            } else if(predicateType.equals("state_match")) {
                return createBlockStateFiller(object);
            } else {
                throw new IllegalArgumentException("Unknown filler match type: " + predicateType);
            }
        } else {
            throw new IllegalArgumentException("Unknown block state type " + element);
        }
    }

    private static Function<IBlockState, IBlockState> createSimpleFiller(String stringDeclaration) {
        if(stringDeclaration.startsWith("block:")) {
            Block block = getBlockByName(stringDeclaration.substring(6));
            return state -> block.getDefaultState();
        } else if(stringDeclaration.startsWith("ore:")) {
            Map<StoneType, IBlockState> blockStateMap = getOreStateMap(stringDeclaration);
            return stoneState -> {
                StoneType stoneType = StoneType.computeStoneType(stoneState);
                if(stoneType == StoneTypes._NULL)
                    stoneType = StoneTypes.STONE; //use stone as fallback stone type for ore type defining
                return blockStateMap.get(stoneType);
            };
        } else if(stringDeclaration.startsWith("ore_dict:")) {
            String oreDictName = stringDeclaration.substring(9);
            IBlockState firstBlock = getOreDictBlocks(oreDictName).get(0);
            return state -> firstBlock;
        } else {
            throw new IllegalArgumentException("Unknown string block state declaration: " + stringDeclaration);
        }
    }

    private static Function<IBlockState, IBlockState> createStateMatchFiller(JsonObject object) {
        JsonArray valuesArray = object.get("values").getAsJsonArray();
        ArrayList<Entry<Predicate<IBlockState>, Function<IBlockState, IBlockState>>> matchers = new ArrayList<>();
        for(JsonElement valueDefinition : valuesArray) {
            if(!valueDefinition.isJsonObject())
                throw new IllegalArgumentException("Found invalid value definition: " + valueDefinition);
            JsonObject valueObject = valueDefinition.getAsJsonObject();
            Predicate<IBlockState> predicate = createBlockStatePredicate(valueObject.get("predicate"));
            Function<IBlockState, IBlockState> filler = createBlockStateFiller(valueObject.get("value"));
            matchers.add(new SimpleEntry<>(predicate, filler));
        }
        JsonElement defaultElement = object.get("default");
        if(!defaultElement.isJsonNull()) {
            Function<IBlockState, IBlockState> filler = createBlockStateFiller(defaultElement);
            matchers.add(new SimpleEntry<>(state -> true, filler));
        } else {
            //add default element if it isn't defined, to avoid issues
            matchers.add(new SimpleEntry<>(state -> true, state -> Blocks.AIR.getDefaultState()));
        }
        return state -> {
            for(Entry<Predicate<IBlockState>, Function<IBlockState, IBlockState>> matchEntry : matchers) {
                Predicate<IBlockState> predicate = matchEntry.getKey();
                Function<IBlockState, IBlockState> filler = matchEntry.getValue();
                if(predicate.test(state))
                    return filler.apply(state);
            }
            //if this will happen somehow (even if it's impossible, call latest filler)
            return matchers.get(matchers.size() - 1).getValue().apply(state);
        };
    }

    private static Function<IBlockState, IBlockState> createStoneTypeMatchFiller(JsonObject object) {
        HashMap<StoneType, Function<IBlockState, IBlockState>> stateByStoneType = new HashMap<>();
        for(StoneType stoneType : StoneType.STONE_TYPE_REGISTRY.getObjectsWithIds()) {
            JsonElement stoneTypeDefinition = object.get(stoneType.name);
            if(stoneTypeDefinition.isJsonNull()) continue;
            stateByStoneType.put(stoneType, createBlockStateFiller(stoneTypeDefinition));
        }
        JsonElement defaultElement = object.get("default");
        if(!defaultElement.isJsonNull()) {
            Function<IBlockState, IBlockState> defaultFiller = createBlockStateFiller(defaultElement);
            stateByStoneType.put(StoneTypes._NULL, defaultFiller);
        } else {
            //if no default element is defined, define it as air to avoid issues
            stateByStoneType.put(StoneTypes._NULL, state -> Blocks.AIR.getDefaultState());
        }
        return state -> {
            StoneType stoneType = StoneType.computeStoneType(state);
            if(!stateByStoneType.containsKey(stoneType))
                stoneType = StoneTypes._NULL;
            return stateByStoneType.get(stoneType).apply(state);
        };
    }

    private static final Random blockStateRandom = new XSTR();

    private static Function<IBlockState, IBlockState> createWeightRandomStateFiller(JsonObject object) {
        JsonArray values = object.get("values").getAsJsonArray();
        ArrayList<Entry<Integer, Function<IBlockState, IBlockState>>> randomList = new ArrayList<>();
        for (JsonElement randomElement : values) {
            JsonObject randomObject = randomElement.getAsJsonObject();
            int weight = randomObject.get("weight").getAsInt();
            if (weight <= 0) {
                throw new IllegalArgumentException("Invalid weight: " + weight);
            }
            Function<IBlockState, IBlockState> filler = createBlockStateFiller(randomObject.get("value"));
            randomList.add(new SimpleEntry<>(weight, filler));
        }
        return state -> {
            int functionIndex = GTUtility.getRandomItem(blockStateRandom, randomList, randomList.size());
            Function<IBlockState, IBlockState> randomFunction = randomList.get(functionIndex).getValue();
            return randomFunction.apply(state);
        };
    }

    private static Function<IBlockState, IBlockState> createRandomStateFiller(JsonObject object) {
        JsonArray values = object.get("values").getAsJsonArray();
        ArrayList<Function<IBlockState, IBlockState>> randomList = new ArrayList<>();
        for (JsonElement randomElement : values) {
            randomList.add(createBlockStateFiller(randomElement));
        }
        return state -> {
            Function<IBlockState, IBlockState> randomFunction = randomList.get(blockStateRandom.nextInt(randomList.size()));
            return randomFunction.apply(state);
        };
    }

    public static IBlockState parseBlockStateDefinition(JsonObject object) {
        Block block = getBlockByName(object.get("block").getAsString());
        IBlockState blockState = block.getDefaultState();

        for(IProperty<?> property : block.getBlockState().getProperties()) {
            JsonElement valueElement = object.get(property.getName());
            if(valueElement.isJsonPrimitive()) {
                String stringValue = valueElement.getAsString();
                Optional<?> parsedValue = property.parseValue(stringValue);
                if(!parsedValue.isPresent()) {
                    throw new IllegalArgumentException("Couldn't parse property " + property.getName() + " value " + valueElement);
                }
                //fuck java
                @SuppressWarnings("UnnecessaryLocalVariable")
                IProperty areYouFuckingSerious = property;
                Comparable fuckJava = (Comparable) parsedValue.get();
                blockState = blockState.withProperty(areYouFuckingSerious, fuckJava);
            }
        }

        return blockState;
    }

    private static Predicate<IBlockState> createSimpleStatePredicate(String stringDeclaration) {
        if(stringDeclaration.startsWith("block:")) {
            Block block = getBlockByName(stringDeclaration.substring(6));
            return state -> state.getBlock() == block;
        } else if(stringDeclaration.startsWith("ore_dict:")) {
            String oreDictName = stringDeclaration.substring(9);
            List<IBlockState> allMatching = getOreDictBlocks(oreDictName);
            return allMatching::contains;
        } else {
            throw new IllegalArgumentException("Unknown string block state predicate: " + stringDeclaration);
        }
    }

    public static Predicate<IBlockState> createBlockStatePredicate(JsonElement element) {
        if(element instanceof JsonPrimitive) {
            String stringDeclaration = element.getAsString();
            return createSimpleStatePredicate(stringDeclaration);
        } else if(element instanceof JsonObject) {
            JsonObject object = element.getAsJsonObject();
            if(!object.has("block"))
                throw new IllegalArgumentException("Block state predicate missing required block key!");
            return parseBlockStatePropertyPredicate(object);
        } else if(element instanceof JsonArray) {
            JsonArray array = element.getAsJsonArray();
            ArrayList<Predicate<IBlockState>> allPredicates = new ArrayList<>();
            for(JsonElement arrayElement : array) {
                allPredicates.add(createBlockStatePredicate(arrayElement));
            }
            return state -> allPredicates.stream().anyMatch(p -> p.test(state));
        } else {
            throw new IllegalArgumentException("Unsupported block state variant predicate type: " + element);
        }
    }

    private static Predicate<IBlockState> parseBlockStatePropertyPredicate(JsonObject object) {
        Block block = getBlockByName(object.get("block").getAsString());
        Map<IProperty<?>, List<Object>> allowedValues = new HashMap<>();

        for(IProperty<?> property : block.getBlockState().getProperties()) {
            JsonElement valueElement = object.get(property.getName());
            if(valueElement.isJsonPrimitive()) {
                JsonElement singleValue = valueElement;
                valueElement = new JsonArray();
                valueElement.getAsJsonArray().add(singleValue);
            }
            if(valueElement.isJsonArray()) {
                ArrayList<Object> allValues = new ArrayList<>();
                JsonArray valuesArray = valueElement.getAsJsonArray();
                boolean isBlacklist = false;
                for(JsonElement allowedValue : valuesArray) {
                    String elementValue = allowedValue.getAsString();
                    if(elementValue.startsWith("!")) {
                        elementValue = elementValue.substring(1);
                        isBlacklist = true;
                    }
                    Optional<?> parsedValue = property.parseValue(elementValue);
                    if(!parsedValue.isPresent()) {
                        throw new IllegalArgumentException("Couldn't parse property " + property.getName() + " value " + valueElement);
                    }
                    allValues.add(parsedValue.get());
                }
                if(isBlacklist) {
                    ArrayList<Object> blacklistValues = allValues;
                    allValues = new ArrayList<>(property.getAllowedValues());
                    allValues.removeAll(blacklistValues);
                }
                allowedValues.put(property, allValues);
            }
        }

        return blockState -> {
            for(IProperty<?> property : blockState.getPropertyKeys()) {
                if(!allowedValues.containsKey(property))
                    continue; //do not check unspecified properties
                Object propertyValue = blockState.getValue(property);
                if(!allowedValues.get(property).contains(propertyValue))
                    return false;
            }
            return true;
        };
    }

    @SuppressWarnings("deprecation")
    private static List<IBlockState> getOreDictBlocks(String oreDictName) {
        List<ItemStack> allOres = OreDictionary.getOres(oreDictName);
        ArrayList<IBlockState> allBlocks = new ArrayList<>();
        for(ItemStack oreStack : allOres) {
            Block itemStackBlock = Block.getBlockFromItem(oreStack.getItem());
            if(itemStackBlock == null)
                continue;
            int placementMetadata = oreStack.getItem().getMetadata(oreStack.getMetadata());
            IBlockState placementState = itemStackBlock.getStateFromMeta(placementMetadata);
            allBlocks.add(placementState);
        }
        if(allBlocks.isEmpty()) {
            throw new IllegalArgumentException("Couldn't find any blocks matching " + oreDictName + " oredict tag");
        }
        return allBlocks;
    }

    private static Map<StoneType, IBlockState> getOreStateMap(String stringDeclaration) {
        String materialName;
        if(stringDeclaration.startsWith("ore:")) {
            materialName = stringDeclaration.substring(4);
        } else {
            throw new IllegalArgumentException("Invalid string ore decl: " + stringDeclaration);
        }
        DustMaterial material = getMaterialByName(materialName);
        return getOreForMaterial(material);
    }

    public static Map<StoneType, IBlockState> getOreForMaterial(DustMaterial material) {
        List<BlockOre> oreBlocks = MetaBlocks.ORES.stream()
            .filter(ore -> ore.material == material)
            .collect(Collectors.toList());
        HashMap<StoneType, IBlockState> stoneTypeMap = new HashMap<>();
        for(BlockOre blockOre : oreBlocks) {
            for(StoneType stoneType : blockOre.STONE_TYPE.getAllowedValues()) {
                IBlockState blockState = blockOre.getOreBlock(stoneType);
                stoneTypeMap.put(stoneType, blockState);
            }
        }
        return stoneTypeMap;
    }

    public static DustMaterial getMaterialByName(String name) {
        Material material = Material.MATERIAL_REGISTRY.getObject(name);
        if(!(material instanceof DustMaterial))
            throw new IllegalArgumentException("Material with name " + name + " not found!");
        return (DustMaterial) material;
    }

    public static Block getBlockByName(String name) {
        ResourceLocation blockName = new ResourceLocation(name);
        Block block = GameRegistry.findRegistry(Block.class).getValue(blockName);
        if(block == null)
            throw new IllegalArgumentException("Block with identifier " + blockName + " not found!");
        return block;
    }

}
