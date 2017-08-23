package gregtech.common.blocks.models;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.BlockCompressed;
import net.minecraft.block.Block;

import java.util.ArrayList;

public class BlockCompressedFactory extends AbstractBlockModelFactory {

    private static final String VARIANT_DEFINITION =
            "\"$MATERIAL$\": {\n" +
            "        \"textures\": {\n" +
            "          \"all\": \"$TEXTURE$\"\n" +
            "        }\n" +
            "      }\n";

    private static final Joiner COMMA_JOINER = Joiner.on(',');

    public static void init() {
        BlockCompressedFactory factory = new BlockCompressedFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockCompressedFactory() {
        super("compressed_block", "compressed_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        ImmutableList<Material> allowedValues = ((BlockCompressed) block).variantProperty.getAllowedValues();
        ArrayList<String> variants = new ArrayList<>();
        for(Material material : allowedValues) {
            variants.add(VARIANT_DEFINITION
                    .replace("$MATERIAL$", material.toString())
                    .replace("$TEXTURE$", MaterialIconType.block.getBlockPath(material.materialIconSet).toString())
            );
        }
        return blockStateSample.replace("$VARIANTS$", COMMA_JOINER.join(variants));
    }


}
