package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.IColorModulationContainer;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class GT_SidedTexture implements ITexture, IColorModulationContainer {
    private final IIconContainer[] mIconContainer;
    private final boolean mAllowAlpha;
    /**
     * DO NOT MANIPULATE THE VALUES INSIDE THIS ARRAY!!!
     * <p/>
     * Just set this variable to another different Array instead.
     * Otherwise some colored things will get Problems.
     */
    public short[] mRGBa;

    public GT_SidedTexture(IIconContainer aIcon0, IIconContainer aIcon1, IIconContainer aIcon2, IIconContainer aIcon3, IIconContainer aIcon4, IIconContainer aIcon5, short[] aRGBa, boolean aAllowAlpha) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_RenderedTexture");
        mIconContainer = new IIconContainer[]{aIcon0, aIcon1, aIcon2, aIcon3, aIcon4, aIcon5};
        mAllowAlpha = aAllowAlpha;
        mRGBa = aRGBa;
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
    public void renderXPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.6F), (int) (mRGBa[1] * 0.6F), (int) (mRGBa[2] * 0.6F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceXPos(aBlock, aX, aY, aZ, mIconContainer[5].getIcon());
        if (mIconContainer[5].getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(153, 153, 153, 255);
            aRenderer.renderFaceXPos(aBlock, aX, aY, aZ, mIconContainer[5].getOverlayIcon());
        }
    }

    @Override
    public void renderXNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.6F), (int) (mRGBa[1] * 0.6F), (int) (mRGBa[2] * 0.6F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceXNeg(aBlock, aX, aY, aZ, mIconContainer[4].getIcon());
        if (mIconContainer[4].getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(153, 153, 153, 255);
            aRenderer.renderFaceXNeg(aBlock, aX, aY, aZ, mIconContainer[4].getOverlayIcon());
        }
    }

    @Override
    public void renderYPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 1.0F), (int) (mRGBa[1] * 1.0F), (int) (mRGBa[2] * 1.0F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceYPos(aBlock, aX, aY, aZ, mIconContainer[1].getIcon());
        if (mIconContainer[1].getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(255, 255, 255, 255);
            aRenderer.renderFaceYPos(aBlock, aX, aY, aZ, mIconContainer[1].getOverlayIcon());
        }
    }

    @Override
    public void renderYNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.5F), (int) (mRGBa[1] * 0.5F), (int) (mRGBa[2] * 0.5F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        IIcon aIcon = mIconContainer[0].getIcon();

        double d3 = aIcon.getInterpolatedU(aRenderer.renderMaxX * 16.0D);
        double d4 = aIcon.getInterpolatedU(aRenderer.renderMinX * 16.0D);
        double d5 = aIcon.getInterpolatedV(aRenderer.renderMinZ * 16.0D);
        double d6 = aIcon.getInterpolatedV(aRenderer.renderMaxZ * 16.0D);

        if (aRenderer.renderMinX < 0.0D || aRenderer.renderMaxX > 1.0D) {
            d3 = aIcon.getMaxU();
            d4 = aIcon.getMinU();
        }

        if (aRenderer.renderMinZ < 0.0D || aRenderer.renderMaxZ > 1.0D) {
            d5 = aIcon.getMinV();
            d6 = aIcon.getMaxV();
        }

        double d11 = aX + aRenderer.renderMinX;
        double d12 = aX + aRenderer.renderMaxX;
        double d13 = aY + aRenderer.renderMinY;
        double d14 = aZ + aRenderer.renderMinZ;
        double d15 = aZ + aRenderer.renderMaxZ;

        Tessellator.instance.addVertexWithUV(d11, d13, d15, d3, d6);
        Tessellator.instance.addVertexWithUV(d11, d13, d14, d3, d5);
        Tessellator.instance.addVertexWithUV(d12, d13, d14, d4, d5);
        Tessellator.instance.addVertexWithUV(d12, d13, d15, d4, d6);

        if ((aIcon = mIconContainer[0].getOverlayIcon()) != null) {
            Tessellator.instance.setColorRGBA(128, 128, 128, 255);

            d3 = aIcon.getInterpolatedU(aRenderer.renderMaxX * 16.0D);
            d4 = aIcon.getInterpolatedU(aRenderer.renderMinX * 16.0D);
            d5 = aIcon.getInterpolatedV(aRenderer.renderMinZ * 16.0D);
            d6 = aIcon.getInterpolatedV(aRenderer.renderMaxZ * 16.0D);

            if (aRenderer.renderMinX < 0.0D || aRenderer.renderMaxX > 1.0D) {
                d3 = aIcon.getMaxU();
                d4 = aIcon.getMinU();
            }

            if (aRenderer.renderMinZ < 0.0D || aRenderer.renderMaxZ > 1.0D) {
                d5 = aIcon.getMinV();
                d6 = aIcon.getMaxV();
            }

            d11 = aX + aRenderer.renderMinX;
            d12 = aX + aRenderer.renderMaxX;
            d13 = aY + aRenderer.renderMinY;
            d14 = aZ + aRenderer.renderMinZ;
            d15 = aZ + aRenderer.renderMaxZ;

            Tessellator.instance.addVertexWithUV(d11, d13, d15, d3, d6);
            Tessellator.instance.addVertexWithUV(d11, d13, d14, d3, d5);
            Tessellator.instance.addVertexWithUV(d12, d13, d14, d4, d5);
            Tessellator.instance.addVertexWithUV(d12, d13, d15, d4, d6);
        }
    }

    @Override
    public void renderZPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.8F), (int) (mRGBa[1] * 0.8F), (int) (mRGBa[2] * 0.8F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceZPos(aBlock, aX, aY, aZ, mIconContainer[3].getIcon());
        if (mIconContainer[3].getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(204, 204, 204, 255);
            aRenderer.renderFaceZPos(aBlock, aX, aY, aZ, mIconContainer[3].getOverlayIcon());
        }
    }

    @Override
    public void renderZNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.8F), (int) (mRGBa[1] * 0.8F), (int) (mRGBa[2] * 0.8F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceZNeg(aBlock, aX, aY, aZ, mIconContainer[2].getIcon());
        if (mIconContainer[2].getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(204, 204, 204, 255);
            aRenderer.renderFaceZNeg(aBlock, aX, aY, aZ, mIconContainer[2].getOverlayIcon());
        }
    }

    @Override
    public short[] getRGBA() {
        return mRGBa;
    }

    @Override
    public boolean isValidTexture() {
        return mIconContainer != null && mIconContainer[0] != null && mIconContainer[1] != null && mIconContainer[2] != null && mIconContainer[3] != null && mIconContainer[4] != null && mIconContainer[5] != null;
    }
}