package gregtech.common.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IPipeRenderedTileEntity;
import gregtech.api.interfaces.tileentity.ITexturedTileEntity;
import gregtech.common.blocks.GT_Block_Machines;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class GT_Renderer_Block
        implements ISimpleBlockRenderingHandler {
    public static GT_Renderer_Block INSTANCE;
    public final int mRenderID;

    public GT_Renderer_Block() {
        this.mRenderID = RenderingRegistry.getNextAvailableRenderId();
        INSTANCE = this;
        RenderingRegistry.registerBlockHandler(this);
    }

    private static void renderNormalInventoryMetaTileEntity(Block aBlock, int aMeta, RenderBlocks aRenderer) {
        if ((aMeta <= 0) || (aMeta >= GregTech_API.METATILEENTITIES.length)) {
            return;
        }
        IMetaTileEntity tMetaTileEntity = GregTech_API.METATILEENTITIES[aMeta];
        if (tMetaTileEntity == null) {
            return;
        }
        aBlock.setBlockBoundsForItemRender();
        aRenderer.setRenderBoundsFromBlock(aBlock);

        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        if ((tMetaTileEntity.getBaseMetaTileEntity() instanceof IPipeRenderedTileEntity)) {
            float tThickness = ((IPipeRenderedTileEntity) tMetaTileEntity.getBaseMetaTileEntity()).getThickNess();
            float sp = (1.0F - tThickness) / 2.0F;

            aBlock.setBlockBounds(0.0F, sp, sp, 1.0F, sp + tThickness, sp + tThickness);
            aRenderer.setRenderBoundsFromBlock(aBlock);

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, -1.0F, 0.0F);
            renderNegativeYFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 0, (byte) 9, (byte) -1, false, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 1.0F, 0.0F);
            renderPositiveYFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 1, (byte) 9, (byte) -1, false, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 0.0F, -1.0F);
            renderNegativeZFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 2, (byte) 9, (byte) -1, false, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 0.0F, 1.0F);
            renderPositiveZFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 3, (byte) 9, (byte) -1, false, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(-1.0F, 0.0F, 0.0F);
            renderNegativeXFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 4, (byte) 9, (byte) -1, true, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(1.0F, 0.0F, 0.0F);
            renderPositiveXFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 5, (byte) 9, (byte) -1, true, false), true);
            Tessellator.instance.draw();
        } else {
            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, -1.0F, 0.0F);
            renderNegativeYFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 0, (byte) 4, (byte) -1, true, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 1.0F, 0.0F);
            renderPositiveYFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 1, (byte) 4, (byte) -1, true, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 0.0F, -1.0F);
            renderNegativeZFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 2, (byte) 4, (byte) -1, true, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 0.0F, 1.0F);
            renderPositiveZFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 3, (byte) 4, (byte) -1, true, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(-1.0F, 0.0F, 0.0F);
            renderNegativeXFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 4, (byte) 4, (byte) -1, true, false), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(1.0F, 0.0F, 0.0F);
            renderPositiveXFacing(null, aRenderer, aBlock, 0, 0, 0, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 5, (byte) 4, (byte) -1, true, false), true);
            Tessellator.instance.draw();
        }
        aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        aRenderer.setRenderBoundsFromBlock(aBlock);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public static boolean renderStandardBlock(IBlockAccess aWorld, int aX, int aY, int aZ, Block aBlock, RenderBlocks aRenderer) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof ITexturedTileEntity)) {
            return renderStandardBlock(aWorld, aX, aY, aZ, aBlock, aRenderer, new ITexture[][]{((ITexturedTileEntity) tTileEntity).getTexture(aBlock, (byte) 0), ((ITexturedTileEntity) tTileEntity).getTexture(aBlock, (byte) 1), ((ITexturedTileEntity) tTileEntity).getTexture(aBlock, (byte) 2), ((ITexturedTileEntity) tTileEntity).getTexture(aBlock, (byte) 3), ((ITexturedTileEntity) tTileEntity).getTexture(aBlock, (byte) 4), ((ITexturedTileEntity) tTileEntity).getTexture(aBlock, (byte) 5)});
        }
        return false;
    }

    public static boolean renderStandardBlock(IBlockAccess aWorld, int aX, int aY, int aZ, Block aBlock, RenderBlocks aRenderer, ITexture[][] aTextures) {
        aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        aRenderer.setRenderBoundsFromBlock(aBlock);

        renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[0], true);
        renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[1], true);
        renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[2], true);
        renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[3], true);
        renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[4], true);
        renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[5], true);
        return true;
    }

    public static boolean renderPipeBlock(IBlockAccess aWorld, int aX, int aY, int aZ, Block aBlock, IPipeRenderedTileEntity aTileEntity, RenderBlocks aRenderer) {
        byte aConnections = aTileEntity.getConnections();
        if ((aConnections & 0xC0) != 0) {
            return renderStandardBlock(aWorld, aX, aY, aZ, aBlock, aRenderer);
        }
        float tThickness = aTileEntity.getThickNess();
        if (tThickness >= 0.99F) {
            return renderStandardBlock(aWorld, aX, aY, aZ, aBlock, aRenderer);
        }
        float sp = (1.0F - tThickness) / 2.0F;

        byte tConnections = 0;
        for (byte i = 0; i < 6; i = (byte) (i + 1)) {
            if ((aConnections & 1 << i) != 0) {
                tConnections = (byte) (tConnections | 1 << (i + 2) % 6);
            }
        }
        boolean[] tIsCovered = new boolean[6];
        for (byte i = 0; i < 6; i = (byte) (i + 1)) {
            tIsCovered[i] = (aTileEntity.getCoverIDAtSide(i) != 0 ? true : false);
        }
        if ((tIsCovered[0]) && (tIsCovered[1]) && (tIsCovered[2]) && (tIsCovered[3]) && (tIsCovered[4]) && (tIsCovered[5])) {
            return renderStandardBlock(aWorld, aX, aY, aZ, aBlock, aRenderer);
        }
        ITexture[][] tIcons = new ITexture[6][];
        ITexture[][] tCovers = new ITexture[6][];
        for (byte i = 0; i < 6; i = (byte) (i + 1)) {
            tCovers[i] = aTileEntity.getTexture(aBlock, i);
            tIcons[i] = aTileEntity.getTextureUncovered(i);
        }
        if (tConnections == 0) {
            aBlock.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
            renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
            renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
            renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
            renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
            renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
        } else if (tConnections == 3) {
            aBlock.setBlockBounds(0.0F, sp, sp, 1.0F, sp + tThickness, sp + tThickness);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
            renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
            renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
            renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
            }
        } else if (tConnections == 12) {
            aBlock.setBlockBounds(sp, 0.0F, sp, sp + tThickness, 1.0F, sp + tThickness);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
            renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
            renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
            renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
            }
        } else if (tConnections == 48) {
            aBlock.setBlockBounds(sp, sp, 0.0F, sp + tThickness, sp + tThickness, 1.0F);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
            renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
            renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
            renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
            }
        } else {
            if ((tConnections & 0x1) == 0) {
                aBlock.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
            } else {
                aBlock.setBlockBounds(0.0F, sp, sp, sp, sp + tThickness, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
                if (!tIsCovered[4]) {
                    renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
                }
            }
            if ((tConnections & 0x2) == 0) {
                aBlock.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
            } else {
                aBlock.setBlockBounds(sp + tThickness, sp, sp, 1.0F, sp + tThickness, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
                if (!tIsCovered[5]) {
                    renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
                }
            }
            if ((tConnections & 0x4) == 0) {
                aBlock.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
            } else {
                aBlock.setBlockBounds(sp, 0.0F, sp, sp + tThickness, sp, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
                if (!tIsCovered[0]) {
                    renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
                }
            }
            if ((tConnections & 0x8) == 0) {
                aBlock.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
            } else {
                aBlock.setBlockBounds(sp, sp + tThickness, sp, sp + tThickness, 1.0F, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
                if (!tIsCovered[1]) {
                    renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
                }
            }
            if ((tConnections & 0x10) == 0) {
                aBlock.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
            } else {
                aBlock.setBlockBounds(sp, sp, 0.0F, sp + tThickness, sp + tThickness, sp);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
                if (!tIsCovered[2]) {
                    renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[2], false);
                }
            }
            if ((tConnections & 0x20) == 0) {
                aBlock.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
            } else {
                aBlock.setBlockBounds(sp, sp, sp + tThickness, sp + tThickness, sp + tThickness, 1.0F);
                aRenderer.setRenderBoundsFromBlock(aBlock);
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[0], false);
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[1], false);
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[4], false);
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[5], false);
                if (!tIsCovered[3]) {
                    renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tIcons[3], false);
                }
            }
        }
        if (tIsCovered[0]) {
            aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[0], false);
            renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[0], false);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[0], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[0], false);
            }
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[0], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[0], false);
            }
        }
        if (tIsCovered[1]) {
            aBlock.setBlockBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[1], false);
            renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[1], false);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[1], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[1], false);
            }
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[1], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[1], false);
            }
        }
        if (tIsCovered[2]) {
            aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[2], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[2], false);
            }
            renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[2], false);
            renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[2], false);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[2], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[2], false);
            }
        }
        if (tIsCovered[3]) {
            aBlock.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[3], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[3], false);
            }
            renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[3], false);
            renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[3], false);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[3], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[3], false);
            }
        }
        if (tIsCovered[4]) {
            aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[4], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[4], false);
            }
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[4], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[4], false);
            }
            renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[4], false);
            renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[4], false);
        }
        if (tIsCovered[5]) {
            aBlock.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            aRenderer.setRenderBoundsFromBlock(aBlock);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[5], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[5], false);
            }
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[5], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[5], false);
            }
            renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[5], false);
            renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, tCovers[5], false);
        }
        aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        aRenderer.setRenderBoundsFromBlock(aBlock);

        return true;
    }

    public static void renderNegativeYFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX, aY - 1, aZ, 0))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aX, aFullBlock ? aY - 1 : aY, aZ));
        }
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderYNeg(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderPositiveYFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX, aY + 1, aZ, 1))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aX, aFullBlock ? aY + 1 : aY, aZ));
        }
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderYPos(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderNegativeZFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX, aY, aZ - 1, 2))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aX, aY, aFullBlock ? aZ - 1 : aZ));
        }
        aRenderer.flipTexture = (!aFullBlock);
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderZNeg(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderPositiveZFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX, aY, aZ + 1, 3))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aX, aY, aFullBlock ? aZ + 1 : aZ));
        }
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderZPos(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderNegativeXFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX - 1, aY, aZ, 4))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aFullBlock ? aX - 1 : aX, aY, aZ));
        }
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderXNeg(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderPositiveXFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX + 1, aY, aZ, 5))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aFullBlock ? aX + 1 : aX, aY, aZ));
        }
        aRenderer.flipTexture = (!aFullBlock);
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderXPos(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public void renderInventoryBlock(Block aBlock, int aMeta, int aModelID, RenderBlocks aRenderer) {
        if ((aBlock instanceof GT_Block_Machines)) {
            if ((aMeta > 0) && (aMeta < GregTech_API.METATILEENTITIES.length) && (GregTech_API.METATILEENTITIES[aMeta] != null) &&
                    (!GregTech_API.METATILEENTITIES[aMeta].renderInInventory(aBlock, aMeta, aRenderer))) {
                renderNormalInventoryMetaTileEntity(aBlock, aMeta, aRenderer);
            }
        } else if ((aBlock instanceof GT_Block_Ores_Abstract)) {
            GT_TileEntity_Ores tTileEntity = new GT_TileEntity_Ores();
            tTileEntity.mMetaData = ((short) aMeta);

            aBlock.setBlockBoundsForItemRender();
            aRenderer.setRenderBoundsFromBlock(aBlock);

            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, -1.0F, 0.0F);
            renderNegativeYFacing(null, aRenderer, aBlock, 0, 0, 0, tTileEntity.getTexture(aBlock, (byte) 0), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 1.0F, 0.0F);
            renderPositiveYFacing(null, aRenderer, aBlock, 0, 0, 0, tTileEntity.getTexture(aBlock, (byte) 1), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 0.0F, -1.0F);
            renderNegativeZFacing(null, aRenderer, aBlock, 0, 0, 0, tTileEntity.getTexture(aBlock, (byte) 2), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0.0F, 0.0F, 1.0F);
            renderPositiveZFacing(null, aRenderer, aBlock, 0, 0, 0, tTileEntity.getTexture(aBlock, (byte) 3), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(-1.0F, 0.0F, 0.0F);
            renderNegativeXFacing(null, aRenderer, aBlock, 0, 0, 0, tTileEntity.getTexture(aBlock, (byte) 4), true);
            Tessellator.instance.draw();

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(1.0F, 0.0F, 0.0F);
            renderPositiveXFacing(null, aRenderer, aBlock, 0, 0, 0, tTileEntity.getTexture(aBlock, (byte) 5), true);
            Tessellator.instance.draw();
        }
        aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        aRenderer.setRenderBoundsFromBlock(aBlock);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public boolean renderWorldBlock(IBlockAccess aWorld, int aX, int aY, int aZ, Block aBlock, int aModelID, RenderBlocks aRenderer) {
        TileEntity aTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (aTileEntity == null) {
            return false;
        }
        if (((aTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) aTileEntity).getMetaTileEntity() != null) && (((IGregTechTileEntity) aTileEntity).getMetaTileEntity().renderInWorld(aWorld, aX, aY, aZ, aBlock, aRenderer))) {
            return true;
        }
        if ((aTileEntity instanceof IPipeRenderedTileEntity)) {
            return renderPipeBlock(aWorld, aX, aY, aZ, aBlock, (IPipeRenderedTileEntity) aTileEntity, aRenderer);
        }
        return renderStandardBlock(aWorld, aX, aY, aZ, aBlock, aRenderer);
    }

    public boolean shouldRender3DInInventory(int aModel) {
        return true;
    }

    public int getRenderId() {
        return this.mRenderID;
    }
}
