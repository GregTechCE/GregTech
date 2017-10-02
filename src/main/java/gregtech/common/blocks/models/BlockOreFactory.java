package gregtech.common.blocks.models;

import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.common.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockOreFactory extends AbstractBlockModelFactory {

    private static final IBlockColor BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        tintIndex == 1 ? ((BlockOre) state.getBlock()).material.materialRGB : 0xFFFFFF;

    private static final IItemColor ITEM_COLOR = (stack, tintIndex) ->
        tintIndex == 1 ? ((BlockOre) ((ItemBlock) stack.getItem()).getBlock()).material.materialRGB : 0xFFFFFF;

    public static void init() {
        BlockOreFactory factory = new BlockOreFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockOreFactory() {
        super("ore_block", "ore_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(BLOCK_COLOR, block);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ITEM_COLOR, block);

        MaterialIconSet iconSet = ((BlockOre) block).material.materialIconSet;
        return blockStateSample
                .replace("$MATERIAL_TEXTURE_NORMAL$", MaterialIconType.ore.getBlockPath(iconSet).toString())
                .replace("$MATERIAL_TEXTURE_SMALL$", MaterialIconType.oreSmall.getBlockPath(iconSet).toString());
    }

}
