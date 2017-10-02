package gregtech.common.blocks.models;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.BlockCompressed;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;

public class BlockCompressedFactory extends AbstractBlockModelFactory {

    private static final String VARIANT_DEFINITION =
            "\"$MATERIAL$\": {\n" +
                "        \"textures\": {\n" +
                "          \"all\": \"$TEXTURE$\",\n" +
                "          \"particle\": \"$TEXTURE$\"\n" +
                "        }\n" +
                "      }";

    private static final IBlockColor BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        state.getValue(((BlockCompressed) state.getBlock()).variantProperty).materialRGB;

    private static final IItemColor ITEM_COLOR = (stack, tintIndex) -> {
        BlockCompressed block = (BlockCompressed) ((ItemBlock) stack.getItem()).getBlock();
        IBlockState state = block.getStateFromMeta(stack.getItemDamage());
        return state.getValue(block.variantProperty).materialRGB;
    };

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
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(BLOCK_COLOR, block);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ITEM_COLOR, block);

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
