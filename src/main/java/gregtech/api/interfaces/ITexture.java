package gregtech.api.interfaces;

import gregtech.common.render.RenderBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITexture {

    @SideOnly(Side.CLIENT)
    public void renderXPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer vertexBuffer);

    @SideOnly(Side.CLIENT)
    public void renderXNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning,  boolean aItem, VertexBuffer vertexBuffer);

    @SideOnly(Side.CLIENT)
    public void renderYPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning,  boolean aItem, VertexBuffer vertexBuffer);

    @SideOnly(Side.CLIENT)
    public void renderYNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning,  boolean aItem, VertexBuffer vertexBuffer);

    @SideOnly(Side.CLIENT)
    public void renderZPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning,  boolean aItem, VertexBuffer vertexBuffer);

    @SideOnly(Side.CLIENT)
    public void renderZNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer vertexBuffer);

    public boolean isValidTexture();

    static int color(short[] mRGBa, boolean mAllowAlpha) {
        int r = (int) (mRGBa[0] * 0.6F);
        int g = (int) (mRGBa[1] * 0.6F);
        int b = (int) (mRGBa[2] * 0.6F);
        int a = mAllowAlpha ? 255 - mRGBa[3] : 255;
        return (r << 16) | (g << 8) | (b) | (a << 24);
    }
}