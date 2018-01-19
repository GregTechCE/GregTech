package gregtech.common.blocks.modelfactories;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Joiner;

import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.ore.StoneType;
import gregtech.common.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockOreFactory extends AbstractBlockModelFactory {

    private static final String VARIANT_DEFINITION_NORMAL =
            "      \"$STONE_TYPE$\": {\n" +
            "        \"textures\": {\n" +
            "          \"base\": \"$BASE_TEXTURE$\",\n" +
            "          \"particle\": \"$PARTICLE$\"\n" +
            "        }\n"+
            "      }";
    private static final String VARIANT_DEFINITION_SIDED =
            "      \"$STONE_TYPE$\": {\n" +
            "        \"model\": \"gregtech:overlay_model_all_overlay\",\n" +
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
        ArrayList<String> variants = new ArrayList<>();
        for (StoneType stoneType : ((BlockOre) block).STONE_TYPE.getAllowedValues()) {
            switch (stoneType.modelType) {
            case SIDED:
                variants.add(VARIANT_DEFINITION_SIDED
                        .replace("$STONE_TYPE$", stoneType.name)
                        .replace("$BASE_TEXTURE_DOWN$", stoneType.baseTexture[0])
                        .replace("$BASE_TEXTURE_UP$", stoneType.baseTexture[1])
                        .replace("$BASE_TEXTURE_NORTH$", stoneType.baseTexture[2])
                        .replace("$BASE_TEXTURE_SOUTH$", stoneType.baseTexture[3])
                        .replace("$BASE_TEXTURE_WEST$", stoneType.baseTexture[4])
                        .replace("$BASE_TEXTURE_EAST$", stoneType.baseTexture[5])
                        .replace("$PARTICLE$", stoneType.particleTexture));
                break;
            default:
                variants.add(VARIANT_DEFINITION_NORMAL
                        .replace("$STONE_TYPE$", stoneType.name)
                        .replace("$BASE_TEXTURE$", stoneType.baseTexture[0])
                        .replace("$PARTICLE$", stoneType.particleTexture));
                break;
            }
        }
        MaterialIconSet iconSet = ((BlockOre) block).material.materialIconSet;
        String r = blockStateSample
                .replace("$STONE_TYPES$", COMMA_JOINER.join(variants))
                .replace("$MATERIAL_TEXTURE_NORMAL$", MaterialIconType.ore.getBlockPath(iconSet).toString())
                .replace("$MATERIAL_TEXTURE_SMALL$", MaterialIconType.oreSmall.getBlockPath(iconSet).toString());
        return r;
    }

}
