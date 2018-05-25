package gregtech.common.blocks.modelfactories;

import com.google.common.base.Joiner;
import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.common.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.stream.Collectors;

import static net.minecraft.util.EnumFacing.*;

@SideOnly(Side.CLIENT)
public class BlockOreFactory extends AbstractBlockModelFactory {

    private static final String VARIANT_DEFINITION =
            "      \"$STONE_TYPE$\": {\n" +
            "        \"textures\": {\n" +
            "          \"base_down\": \"$BASE_TEXTURE_DOWN$\",\n" +
            "          \"base_up\": \"$BASE_TEXTURE_UP$\",\n" +
            "          \"base_north\": \"$BASE_TEXTURE_NORTH$\",\n" +
            "          \"base_south\": \"$BASE_TEXTURE_SOUTH$\",\n" +
            "          \"base_west\": \"$BASE_TEXTURE_WEST$\",\n" +
            "          \"base_east\": \"$BASE_TEXTURE_EAST$\",\n" +
            "          \"particle\": \"$PARTICLE$\"\n" +
            "        }\n"+
            "      }";
    private static final Joiner COMMA_JOINER = Joiner.on(",\n");

    public static void init() {
        BlockOreFactory factory = new BlockOreFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockOreFactory() {
        super("ore_block", "ore_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        return blockStateSample
                .replace("$STONE_TYPES$", COMMA_JOINER.join(((BlockOre) block).STONE_TYPE.getAllowedValues().stream()
                        .map(stoneType -> VARIANT_DEFINITION
                                .replace("$STONE_TYPE$", stoneType.name)
                                .replace("$BASE_TEXTURE_DOWN$", stoneType.baseTexture.getOrDefault(DOWN, "missingno"))
                                .replace("$BASE_TEXTURE_UP$", stoneType.baseTexture.getOrDefault(UP, "missingno"))
                                .replace("$BASE_TEXTURE_NORTH$", stoneType.baseTexture.getOrDefault(NORTH, "missingno"))
                                .replace("$BASE_TEXTURE_SOUTH$", stoneType.baseTexture.getOrDefault(SOUTH, "missingno"))
                                .replace("$BASE_TEXTURE_WEST$", stoneType.baseTexture.getOrDefault(WEST, "missingno"))
                                .replace("$BASE_TEXTURE_EAST$", stoneType.baseTexture.getOrDefault(EAST, "missingno"))
                                .replace("$PARTICLE$", stoneType.particleTexture))
                        .collect(Collectors.toList())))
                .replace("$MATERIAL_TEXTURE_NORMAL$", MaterialIconType.ore.getBlockPath(((BlockOre) block).material.materialIconSet).toString());
    }

}
