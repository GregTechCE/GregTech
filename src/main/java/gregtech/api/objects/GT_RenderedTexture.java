package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.IColorModulationContainer;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    @SideOnly(Side.CLIENT)
    public void renderXPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean item, VertexBuffer buf) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            aRenderer.renderXPos(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), color, lightning, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderXPos(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), 0xFF999999, lightning, buf);
            }
        } else {
            aRenderer.renderXPosItem(mIconContainer.getIcon(), 0f, 0f, 0f, color, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderXPosItem(mIconContainer.getIcon(), 0f, 0f, 0f, 0xFF999999, buf);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderXNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean item, VertexBuffer buf) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            aRenderer.renderXNeg(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), color, lightning, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderXNeg(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), 0xFF999999, lightning, buf);
            }
        } else {
            aRenderer.renderXNegItem(mIconContainer.getIcon(), 0f, 0f, 0f, color, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderXNegItem(mIconContainer.getIcon(), 0f, 0f, 0f, 0xFF999999, buf);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean item, VertexBuffer buf) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            aRenderer.renderZPos(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), color, lightning, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderZPos(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), 0xFF999999, lightning, buf);
            }
        } else {
            aRenderer.renderZPosItem(mIconContainer.getIcon(), 0f, 0f, 0f, color, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderZPosItem(mIconContainer.getIcon(), 0f, 0f, 0f, 0xFF999999, buf);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean item, VertexBuffer buf) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            aRenderer.renderZNeg(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), color, lightning, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderZNeg(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), 0xFF999999, lightning, buf);
            }
        } else {
            aRenderer.renderZNegItem(mIconContainer.getIcon(), 0f, 0f, 0f, color, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderZNegItem(mIconContainer.getIcon(), 0f, 0f, 0f, 0xFF999999, buf);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYPos(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean item, VertexBuffer buf) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            aRenderer.renderYPos(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), color, lightning, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderYPos(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), 0xFF999999, lightning, buf);
            }
        } else {
            aRenderer.renderYPosItem(mIconContainer.getIcon(), 0f, 0f, 0f, color, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderYPosItem(mIconContainer.getIcon(), 0f, 0f, 0f, 0xFF999999, buf);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYNeg(RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, int lightning, boolean item, VertexBuffer buf) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            aRenderer.renderYNeg(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), color, lightning, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderYNeg(mIconContainer.getIcon(), aPos.getX(), aPos.getY(), aPos.getZ(), 0xFF999999, lightning, buf);
            }
        } else {
            aRenderer.renderYNegItem(mIconContainer.getIcon(), 0f, 0f, 0f, color, buf);
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderYNegItem(mIconContainer.getIcon(), 0f, 0f, 0f, 0xFF999999, buf);
            }
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