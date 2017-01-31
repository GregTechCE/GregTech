package gregtech.api.interfaces;

import codechicken.lib.render.CCRenderState;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITexture {

    @SideOnly(Side.CLIENT)
    public void renderXPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs);

    @SideOnly(Side.CLIENT)
    public void renderXNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs);

    @SideOnly(Side.CLIENT)
    public void renderYPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs);

    @SideOnly(Side.CLIENT)
    public void renderYNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs);

    @SideOnly(Side.CLIENT)
    public void renderZPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs);

    @SideOnly(Side.CLIENT)
    public void renderZNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean aItem, CCRenderState ccrs);

    public boolean isValidTexture();

    static int color(short[] mRGBa, boolean mAllowAlpha) {
        int r = (int) (mRGBa[0]);
        int g = (int) (mRGBa[1]);
        int b = (int) (mRGBa[2]);
        int a = mAllowAlpha ? 255 - mRGBa[3] : 255;
        return (r << 16) | (g << 8) | (b) | (a << 24);
    }
}