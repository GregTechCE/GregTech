package gregtech.api.worldgen.filler;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.StoneType;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OreUtils {

    public static Function<IBlockState, IBlockState> parseBlockState(JsonElement element) {
        if(element instanceof JsonPrimitive) {
            String stringDeclaration = element.getAsString();
            if(stringDeclaration.startsWith("block:")) {
                Block block = getBlockByName(stringDeclaration.substring(6));
                return state -> block.getDefaultState();
            } else if(stringDeclaration.startsWith("ore:") || stringDeclaration.startsWith("small_ore:")) {
                Map<StoneType, IBlockState> blockStateMap = getOreStateMap(stringDeclaration);
                return stoneState -> {
                    StoneType stoneType = StoneType.computeStoneType(stoneState);
                    return blockStateMap.get(stoneType);
                };
            } else if(stringDeclaration.startsWith("ore_dict:")) {
                String oreDictName = stringDeclaration.substring(9);
                List<IBlockState> allBlocks = getOreDictBlocks(oreDictName);



            }

        }

        return null;
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
        boolean isSmallOre;
        if(stringDeclaration.startsWith("small_ore:")) {
            materialName = stringDeclaration.substring(10);
            isSmallOre = true;
        } else if(stringDeclaration.startsWith("ore:")) {
            materialName = stringDeclaration.substring(4);
            isSmallOre = false;
        } else {
            throw new IllegalArgumentException("Invalid string ore decl: " + stringDeclaration);
        }
        DustMaterial material = getMaterialByName(materialName);
        return getOreForMaterial(material, isSmallOre);
    }

    public static Map<StoneType, IBlockState> getOreForMaterial(DustMaterial material, boolean small) {
        List<BlockOre> oreBlocks = MetaBlocks.ORES.stream()
            .filter(ore -> ore.material == material)
            .collect(Collectors.toList());
        HashMap<StoneType, IBlockState> stoneTypeMap = new HashMap<>();
        for(BlockOre blockOre : oreBlocks) {
            for(StoneType stoneType : blockOre.STONE_TYPE.getAllowedValues()) {
                IBlockState blockState = blockOre.getOreBlock(stoneType, small);
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
