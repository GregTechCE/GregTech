package gregtech.common.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class RenderGeneratedOres implements ICCBlockRenderer {

    public static RenderGeneratedOres INSTANCE = new RenderGeneratedOres();

    public EnumBlockRenderType renderType;

    public void init() {
        renderType = BlockRenderingRegistry.createRenderType("GT_GENERATED_ORES");
        BlockRenderingRegistry.registerRenderer(renderType, INSTANCE);
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, VertexBuffer buffer) {
        RenderBlocks.INSTANCE.handleRenderBlockDamage(world, pos, state, sprite, buffer);
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer) {
        RenderBlocks.INSTANCE.renderOresBlock(world, pos, state, buffer);
        return true;
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {
    }

    public void renderBlockAsItem(CCRenderState ccrs, ItemStack stack) {
        RenderBlocks.INSTANCE.renderOresAsItem(ccrs, stack);
    }

    @Override
    public void registerTextures(TextureMap map) {

    }
}
