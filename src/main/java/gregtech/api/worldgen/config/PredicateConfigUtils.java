package gregtech.api.worldgen.config;

import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gregtech.api.unification.ore.StoneType;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PredicateConfigUtils {

    public static IBlockState parseBlockStateDefinition(JsonObject object) {
        Block block = OreConfigUtils.getBlockByName(object.get("block").getAsString());
        IBlockState blockState = block.getDefaultState();

        for(IProperty<?> property : block.getBlockState().getProperties()) {
            JsonElement valueElement = object.get(property.getName());
            if(valueElement != null && valueElement.isJsonPrimitive()) {
                String stringValue = valueElement.getAsString();
                Optional<?> parsedValue = property.parseValue(stringValue);
                if(!parsedValue.isPresent()) {
                    throw new IllegalArgumentException("Couldn't parse property " + property.getName() + " value " + valueElement);
                }
                //fuck java
                @SuppressWarnings("UnnecessaryLocalVariable")
                IProperty areYouFuckingSerious = property;
                Comparable fuckJava = (Comparable) parsedValue.get();
                //noinspection unchecked
                blockState = blockState.withProperty(areYouFuckingSerious, fuckJava);
            }
        }
        return blockState;
    }

    private static Predicate<IBlockState>  createSimpleStatePredicate(String stringDeclaration) {
        if(stringDeclaration.equals("any")) {
            return state -> true;
        } else if(stringDeclaration.equals("stone_type")) {
            return state -> StoneType.computeStoneType(state) != null;

        } else if(stringDeclaration.startsWith("stone_type:")) {
            String typeName = stringDeclaration.substring(11);
            return state -> {
                StoneType stoneType = StoneType.computeStoneType(state);
                return stoneType != null && stoneType.name.equalsIgnoreCase(typeName);
            };
        } else if(stringDeclaration.startsWith("block:")) {
            Block block = OreConfigUtils.getBlockByName(stringDeclaration.substring(6));
            return state -> state.getBlock() == block;
        } else if(stringDeclaration.startsWith("ore_dict:")) {
            String oreDictName = stringDeclaration.substring(9);
            List<IBlockState> allMatching = OreConfigUtils.getOreDictBlocks(oreDictName);
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
        Block block = OreConfigUtils.getBlockByName(object.get("block").getAsString());
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
}
