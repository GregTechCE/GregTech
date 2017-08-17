package gregtech.common.blocks.models;

import com.google.common.collect.ImmutableList;
import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.BlockCompressed;
import net.minecraft.block.Block;

public class BlockCompressedFactory extends AbstractBlockModelFactory {

    public BlockCompressedFactory() {
        super("compressed_block", "block_compressed_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        ImmutableList<Material> allowedValues = ((BlockCompressed) block).variantProperty.getAllowedValues();
        for(int i = 0; i < allowedValues.size(); i++) {
            blockStateSample = blockStateSample.replace("{M" + i + "}", allowedValues.get(i).toString());
        }
        return blockStateSample;
    }

}
