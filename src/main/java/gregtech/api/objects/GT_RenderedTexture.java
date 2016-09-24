package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.IColorModulationContainer;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class GT_RenderedTexture implements ITexture, IColorModulationContainer {
    private final IIconContainer mIconContainer;
    private final boolean mAllowAlpha;
    /**
     * DO NOT MANIPULATE THE VALUES INSIDE THIS ARRAY!!!
     * <p/>
     * Just set this variable to another different Array instead.
     * Otherwise some colored things will get Problems.
     */
    public short[] mRGBa;

    public GT_RenderedTexture(IIconContainer aIcon, short[] aRGBa, boolean aAllowAlpha) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_RenderedTexture");
        mIconContainer = aIcon;
        mAllowAlpha = aAllowAlpha;
        mRGBa = aRGBa;
    }

    public GT_RenderedTexture(IIconContainer aIcon, short[] aRGBa) {
        this(aIcon, aRGBa, true);
    }

    public GT_RenderedTexture(IIconContainer aIcon) {
        this(aIcon, Dyes._NULL.mRGBa);
    }

    @Override
    public void renderXPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.6F), (int) (mRGBa[1] * 0.6F), (int) (mRGBa[2] * 0.6F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceXPos(aBlock, aX, aY, aZ, mIconContainer.getIcon());
        if (mIconContainer.getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(153, 153, 153, 255);
            aRenderer.renderFaceXPos(aBlock, aX, aY, aZ, mIconContainer.getOverlayIcon());
        }
    }

    @Override
    public void renderXNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.6F), (int) (mRGBa[1] * 0.6F), (int) (mRGBa[2] * 0.6F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceXNeg(aBlock, aX, aY, aZ, mIconContainer.getIcon());
        if (mIconContainer.getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(153, 153, 153, 255);
            aRenderer.renderFaceXNeg(aBlock, aX, aY, aZ, mIconContainer.getOverlayIcon());
        }
    }

    @Override
    public void renderYPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 1.0F), (int) (mRGBa[1] * 1.0F), (int) (mRGBa[2] * 1.0F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceYPos(aBlock, aX, aY, aZ, mIconContainer.getIcon());
        if (mIconContainer.getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(255, 255, 255, 255);
            aRenderer.renderFaceYPos(aBlock, aX, aY, aZ, mIconContainer.getOverlayIcon());
        }
    }

    @Override
    public void renderYNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.5F), (int) (mRGBa[1] * 0.5F), (int) (mRGBa[2] * 0.5F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        IIcon aIcon = mIconContainer.getIcon();

        float d_16 = 16.0F;
        float d3 = (float)aIcon.getInterpolatedU(aRenderer.renderMaxX * d_16);
        float d4 = (float)aIcon.getInterpolatedU(aRenderer.renderMinX * d_16);
        float d5 = (float)aIcon.getInterpolatedV(aRenderer.renderMinZ * d_16);
        float d6 = (float)aIcon.getInterpolatedV(aRenderer.renderMaxZ * d_16);

        if (aRenderer.renderMinX < 0.0D || aRenderer.renderMaxX > 1.0D) {
            d3 = aIcon.getMaxU();
            d4 = aIcon.getMinU();
        }

        if (aRenderer.renderMinZ < 0.0D || aRenderer.renderMaxZ > 1.0D) {
            d5 = aIcon.getMinV();
            d6 = aIcon.getMaxV();
        }

        float d11 = aX + (float)aRenderer.renderMinX;
        float d12 = aX + (float)aRenderer.renderMaxX;
        float d13 = aY + (float)aRenderer.renderMinY;
        float d14 = aZ + (float)aRenderer.renderMinZ;
        float d15 = aZ + (float)aRenderer.renderMaxZ;

        Tessellator.instance.addVertexWithUV((double)d11, (double)d13, (double)d15, (double)d3, (double)d6);
        Tessellator.instance.addVertexWithUV((double)d11, (double)d13, (double)d14, (double)d3, (double)d5);
        Tessellator.instance.addVertexWithUV((double)d12, (double)d13, (double)d14, (double)d4, (double)d5);
        Tessellator.instance.addVertexWithUV((double)d12, (double)d13, (double)d15, (double)d4, (double)d6);

        if ((aIcon = mIconContainer.getOverlayIcon()) != null) {
            Tessellator.instance.setColorRGBA(128, 128, 128, 255);

            Tessellator.instance.addVertexWithUV((double)d11, (double)d13, (double)d15, (double)d3, (double)d6);
            Tessellator.instance.addVertexWithUV((double)d11, (double)d13, (double)d14, (double)d3, (double)d5);
            Tessellator.instance.addVertexWithUV((double)d12, (double)d13, (double)d14, (double)d4, (double)d5);
            Tessellator.instance.addVertexWithUV((double)d12, (double)d13, (double)d15, (double)d4, (double)d6);
        }
    }

    @Override
    public void renderZPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.8F), (int) (mRGBa[1] * 0.8F), (int) (mRGBa[2] * 0.8F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceZPos(aBlock, aX, aY, aZ, mIconContainer.getIcon());
        if (mIconContainer.getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(204, 204, 204, 255);
            aRenderer.renderFaceZPos(aBlock, aX, aY, aZ, mIconContainer.getOverlayIcon());
        }
    }

    @Override
    public void renderZNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.8F), (int) (mRGBa[1] * 0.8F), (int) (mRGBa[2] * 0.8F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceZNeg(aBlock, aX, aY, aZ, mIconContainer.getIcon());
        if (mIconContainer.getOverlayIcon() != null) {
            Tessellator.instance.setColorRGBA(204, 204, 204, 255);
            aRenderer.renderFaceZNeg(aBlock, aX, aY, aZ, mIconContainer.getOverlayIcon());
        }
    }

    @Override
    public short[] getRGBA() {
        return mRGBa;
    }

    @Override
    public boolean isValidTexture() {
        return mIconContainer != null;
    }
}