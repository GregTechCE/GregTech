package gregtech.api.objects;

import gregtech.api.interfaces.ITexture;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.math.BlockPos;
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
    public void renderXPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderXPos(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderXNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderXNeg(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderYPos(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderYNeg(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderZPos(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        for (ITexture tTexture : mTextures)
            if (tTexture != null && tTexture.isValidTexture()) tTexture.renderZNeg(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    public boolean isValidTexture() {
        return true;
    }
}