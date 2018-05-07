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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
                String materialName;
                boolean isSmallOre;
                if(stringDeclaration.startsWith("small_ore:")) {
                    materialName = stringDeclaration.substring(10);
                    isSmallOre = true;
                } else {
                    materialName = stringDeclaration.substring(4);
                    isSmallOre = false;
                }
                DustMaterial material = getMaterialByName(materialName);
                Map<StoneType, IBlockState> blockStateMap = getOreForMaterial(material, isSmallOre);


            }

        }

        return null;
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
