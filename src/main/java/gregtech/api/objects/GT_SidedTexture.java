package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_SidedTexture implements ITexture {

    public final GT_RenderedTexture[] mSides;

    public GT_SidedTexture(IIconContainer aIcon0, IIconContainer aIcon1, IIconContainer aIcon2, IIconContainer aIcon3, IIconContainer aIcon4, IIconContainer aIcon5, short[] aRGBa, boolean aAllowAlpha) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_RenderedTexture");
        IIconContainer[] mIconContainer = new IIconContainer[]{aIcon0, aIcon1, aIcon2, aIcon3, aIcon4, aIcon5};
        mSides = new GT_RenderedTexture[6];
        for(int i = 0; i < 6; i++) {
            mSides[i] = new GT_RenderedTexture(mIconContainer[i], aRGBa);
        }
    }

    public GT_SidedTexture(IIconContainer aIcon0, IIconContainer aIcon1, IIconContainer aIcon2, IIconContainer aIcon3, IIconContainer aIcon4, IIconContainer aIcon5, short[] aRGBa) {
        this(aIcon0, aIcon1, aIcon2, aIcon3, aIcon4, aIcon5, aRGBa, true);
    }

    public GT_SidedTexture(IIconContainer aIcon0, IIconContainer aIcon1, IIconContainer aIcon2, IIconContainer aIcon3, IIconContainer aIcon4, IIconContainer aIcon5) {
        this(aIcon0, aIcon1, aIcon2, aIcon3, aIcon4, aIcon5, Dyes._NULL.mRGBa);
    }

    public GT_SidedTexture(IIconContainer aBottom, IIconContainer aTop, IIconContainer aSides, short[] aRGBa) {
        this(aBottom, aTop, aSides, aSides, aSides, aSides, aRGBa);
    }

    public GT_SidedTexture(IIconContainer aBottom, IIconContainer aTop, IIconContainer aSides) {
        this(aBottom, aTop, aSides, Dyes._NULL.mRGBa);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderXPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        mSides[5].renderXPos(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderXNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        mSides[4].renderXNeg(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        mSides[1].renderYPos(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        mSides[0].renderYNeg(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        mSides[3].renderZPos(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean aItem, VertexBuffer buf) {
        mSides[2].renderZNeg(aRenderer, aState, aPos, lightning, aItem, buf);
    }

    @Override
    public boolean isValidTexture() {
        return true;
    }


}