package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.ITexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class GT_CopiedBlockTexture implements ITexture {
    private final Block mBlock;
    private final byte mSide, mMeta;
    private final boolean mAllowAlpha;
    /**
     * DO NOT MANIPULATE THE VALUES INSIDE THIS ARRAY!!!
     * <p/>
     * Just set this variable to another different Array instead.
     * Otherwise some colored things will get Problems.
     */
    public short[] mRGBa;

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta, short[] aRGBa, boolean aAllowAlpha) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_CopiedBlockTexture");
        mBlock = aBlock;
        mRGBa = aRGBa;
        mSide = (byte) aSide;
        mMeta = (byte) aMeta;
        mAllowAlpha = aAllowAlpha;
    }

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta, short[] aRGBa) {
        this(aBlock, aSide, aMeta, aRGBa, true);
    }

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta) {
        this(aBlock, aSide, aMeta, Dyes._NULL.mRGBa);
    }

    private final IIcon getIcon(int aSide) {
        if (mSide == 6) return mBlock.getIcon(aSide, mMeta);
        return mBlock.getIcon(mSide, mMeta);
    }

    @Override
    public void renderXPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        IIcon aIcon = getIcon(5);
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.6F), (int) (mRGBa[1] * 0.6F), (int) (mRGBa[2] * 0.6F), mAllowAlpha ? 255 - mRGBa[3] : 255);
//		aRenderer.flipTexture = !aRenderer.flipTexture;
        aRenderer.renderFaceXPos(aBlock, aX, aY, aZ, aIcon);
//		aRenderer.flipTexture = !aRenderer.flipTexture;
    }

    @Override
    public void renderXNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        IIcon aIcon = getIcon(4);
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.6F), (int) (mRGBa[1] * 0.6F), (int) (mRGBa[2] * 0.6F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceXNeg(aBlock, aX, aY, aZ, aIcon);
    }

    @Override
    public void renderYPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        IIcon aIcon = getIcon(1);
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 1.0F), (int) (mRGBa[1] * 1.0F), (int) (mRGBa[2] * 1.0F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceYPos(aBlock, aX, aY, aZ, aIcon);
    }

    @Override
    public void renderYNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        IIcon aIcon = getIcon(0);
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.5F), (int) (mRGBa[1] * 0.5F), (int) (mRGBa[2] * 0.5F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceYNeg(aBlock, aX, aY, aZ, aIcon);
    }

    @Override
    public void renderZPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        IIcon aIcon = getIcon(3);
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.8F), (int) (mRGBa[1] * 0.8F), (int) (mRGBa[2] * 0.8F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceZPos(aBlock, aX, aY, aZ, aIcon);
    }

    @Override
    public void renderZNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        IIcon aIcon = getIcon(2);
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.8F), (int) (mRGBa[1] * 0.8F), (int) (mRGBa[2] * 0.8F), mAllowAlpha ? 255 - mRGBa[3] : 255);
//		aRenderer.flipTexture = !aRenderer.flipTexture;
        aRenderer.renderFaceZNeg(aBlock, aX, aY, aZ, aIcon);
//		aRenderer.flipTexture = !aRenderer.flipTexture;
    }

    @Override
    public boolean isValidTexture() {
        return mBlock != null;
    }
}