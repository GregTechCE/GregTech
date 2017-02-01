package gregtech.api.objects;

import codechicken.lib.render.CCRenderState;
import gregtech.api.interfaces.ITexture;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Lets Multiple ITextures Render overlay over each other.
 * <p/>
 * I should have done this much earlier...
 */
public class GT_MultiTexture implements ITexture {
    private final ITexture[] mTextures;

    public GT_MultiTexture(ITexture... aTextures) {
        mTextures = aTextures;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderXPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderXPos(aWorld, aRenderer, aState, aPos, aFullBlock, aItem, ccrs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderXNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderXNeg(aWorld, aRenderer, aState, aPos, aFullBlock, aItem, ccrs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderYPos(aWorld, aRenderer, aState, aPos, aFullBlock, aItem, ccrs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderYNeg(aWorld, aRenderer, aState, aPos, aFullBlock, aItem, ccrs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderZPos(aWorld, aRenderer, aState, aPos, aFullBlock, aItem, ccrs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderZNeg(aWorld, aRenderer, aState, aPos, aFullBlock, aItem, ccrs);
    }

    @Override
    public boolean isValidTexture() {
        return true;
    }
}