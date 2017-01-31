package gregtech.api.objects;

import codechicken.lib.colour.ColourARGB;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.IColorModulationContainer;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_RenderedTexture implements ITexture, IColorModulationContainer {
    public final IIconContainer mIconContainer;
    public final boolean mAllowAlpha;
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
    public void renderXPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean item, CCRenderState ccrs) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            if (aFullBlock) {
                aRenderer.renderFace(ccrs, EnumFacing.EAST, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.EAST, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                }
            } else {
                ccrs.setBrightness(aWorld, aPos);
                aRenderer.renderFace(ccrs, EnumFacing.EAST, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.EAST, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
                }
            }
        } else {
            aRenderer.renderFace(ccrs, EnumFacing.EAST, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderFace(ccrs, EnumFacing.EAST, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderXNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean item, CCRenderState ccrs) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);

        if(!item) {
            if (aFullBlock) {
                aRenderer.renderFace(ccrs, EnumFacing.WEST, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.WEST, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                }
            } else {
                ccrs.setBrightness(aWorld, aPos);
                aRenderer.renderFace(ccrs, EnumFacing.WEST, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.WEST, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
                }
            }
        } else {
            aRenderer.renderFace(ccrs, EnumFacing.WEST, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderFace(ccrs, EnumFacing.WEST, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean item, CCRenderState ccrs) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            if (aFullBlock) {
                aRenderer.renderFace(ccrs, EnumFacing.SOUTH, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.SOUTH, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                }
            } else {
                ccrs.setBrightness(aWorld, aPos);
                aRenderer.renderFace(ccrs, EnumFacing.SOUTH, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.SOUTH, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
                }
            }
        } else {
            aRenderer.renderFace(ccrs, EnumFacing.SOUTH, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderFace(ccrs, EnumFacing.SOUTH, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderZNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean item, CCRenderState ccrs) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            if (aFullBlock) {
                aRenderer.renderFace(ccrs, EnumFacing.NORTH, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.NORTH, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                }
            } else {
                ccrs.setBrightness(aWorld, aPos);
                aRenderer.renderFace(ccrs, EnumFacing.NORTH, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.NORTH, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
                }
            }
        } else {
            aRenderer.renderFace(ccrs, EnumFacing.NORTH, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderFace(ccrs, EnumFacing.NORTH, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYPos(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean item, CCRenderState ccrs) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            if (aFullBlock) {
                aRenderer.renderFace(ccrs, EnumFacing.UP, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.UP, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                }
            } else {
                ccrs.setBrightness(aWorld, aPos);
                aRenderer.renderFace(ccrs, EnumFacing.UP, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.UP, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
                }
            }
        } else {
            aRenderer.renderFace(ccrs, EnumFacing.UP, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderFace(ccrs, EnumFacing.UP, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderYNeg(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, boolean aFullBlock, boolean item, CCRenderState ccrs) {
        if(mIconContainer.getIcon() == null) {
            System.out.println("Container with null icon " + mIconContainer);
            return;
        }
        int color = ITexture.color(mRGBa, mAllowAlpha);
        if(!item) {
            if (aFullBlock) {
                aRenderer.renderFace(ccrs, EnumFacing.DOWN, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.DOWN, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()), ccrs.lightMatrix);
                }
            } else {
                ccrs.setBrightness(aWorld, aPos);
                aRenderer.renderFace(ccrs, EnumFacing.DOWN, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
                if (mIconContainer.getOverlayIcon() != null) {
                    aRenderer.renderFace(ccrs, EnumFacing.DOWN, new Translation(aPos.getX(), aPos.getY(), aPos.getZ()), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
                }
            }
        } else {
            aRenderer.renderFace(ccrs, EnumFacing.DOWN, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(mIconContainer.getIcon()));
            if (mIconContainer.getOverlayIcon() != null) {
                aRenderer.renderFace(ccrs, EnumFacing.DOWN, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourRGBA(153, 153, 153, 255).rgba()), new IconTransformation(mIconContainer.getIcon()));
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