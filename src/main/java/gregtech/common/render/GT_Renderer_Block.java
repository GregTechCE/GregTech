package gregtech.common.render;

import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IPipeRenderedTileEntity;
import gregtech.api.interfaces.tileentity.ITexturedTileEntity;
import gregtech.api.items.GT_Generic_Block;
import gregtech.common.blocks.GT_Block_Machines;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class GT_Renderer_Block implements ICCBlockRenderer {

    public static GT_Renderer_Block INSTANCE = new GT_Renderer_Block();

    public EnumBlockRenderType renderType;
    private static BlockPos.MutableBlockPos t = new BlockPos.MutableBlockPos();


    public void init() {
        renderType = BlockRenderingRegistry.createRenderType("GT_TILE_BLOCK");
        BlockRenderingRegistry.registerRenderer(renderType, INSTANCE);
    }

    private static void renderNormalInventoryMetaTileEntity(IBlockState aState, int aMeta, RenderBlocks aRenderer) {
        GT_Generic_Block aBlock = (GT_Generic_Block) aState.getBlock();
        if ((aMeta <= 0) || (aMeta >= GregTech_API.METATILEENTITIES.length)) {
            return;
        }
        IMetaTileEntity tMetaTileEntity = GregTech_API.METATILEENTITIES[aMeta];
        if (tMetaTileEntity == null) {
            return;
        }
        aBlock.setBlockBoundsForItemRender();
        aRenderer.setRenderBoundsFromBlock1(aBlock);

        Tessellator tes = Tessellator.getInstance();
        VertexBuffer buff = tes.getBuffer();

        //GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        //GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        if ((tMetaTileEntity.getBaseMetaTileEntity() instanceof IPipeRenderedTileEntity)) {
            float tThickness = ((IPipeRenderedTileEntity) tMetaTileEntity.getBaseMetaTileEntity()).getThickNess();
            float sp = (1.0F - tThickness) / 2.0F;

            aRenderer.setBlockBounds(0.0F, sp, sp, 1.0F, sp + tThickness, sp + tThickness);

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderNegativeYFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 0, (byte) 9, (byte) -1, false, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderPositiveYFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 1, (byte) 9, (byte) -1, false, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderNegativeZFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 2, (byte) 9, (byte) -1, false, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderPositiveZFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 3, (byte) 9, (byte) -1, false, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderNegativeXFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 4, (byte) 9, (byte) -1, true, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderPositiveXFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 5, (byte) 9, (byte) -1, true, false), true, buff);
            tes.draw();
        } else {
            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderNegativeYFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 0, (byte) 2, (byte) -1, true, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderPositiveYFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 1, (byte) 2, (byte) -1, true, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderNegativeZFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 2, (byte) 2, (byte) -1, true, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderPositiveZFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 3, (byte) 2, (byte) -1, true, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderNegativeXFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 4, (byte) 2, (byte) -1, true, false), true, buff);
            tes.draw();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            renderPositiveXFacing(null, aRenderer, aState, null, tMetaTileEntity.getTexture(tMetaTileEntity.getBaseMetaTileEntity(), (byte) 5, (byte) 2, (byte) -1, true, false), true, buff);
            tes.draw();
        }
        aRenderer.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

  
    
    public static boolean renderStandardBlock(IBlockAccess aWorld, BlockPos aPos, IBlockState aState, RenderBlocks aRenderer, ITexture[][] aTextures, VertexBuffer buf) {
        aRenderer.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        renderNegativeYFacing(aWorld, aRenderer, aState, aPos, aTextures[0], true, buf);
        renderPositiveYFacing(aWorld, aRenderer, aState, aPos, aTextures[1], true, buf);
        renderNegativeZFacing(aWorld, aRenderer, aState, aPos, aTextures[2], true, buf);
        renderPositiveZFacing(aWorld, aRenderer, aState, aPos, aTextures[3], true, buf);
        renderNegativeXFacing(aWorld, aRenderer, aState, aPos, aTextures[4], true, buf);
        renderPositiveXFacing(aWorld, aRenderer, aState, aPos, aTextures[5], true, buf);
        return true;
    }

    public boolean renderPipeBlock(IBlockAccess aWorld, BlockPos aPos, IBlockState aState, IPipeRenderedTileEntity aTileEntity, RenderBlocks aRenderer, VertexBuffer buf) {
        GT_Generic_Block aBlock = (GT_Generic_Block) aState.getBlock();
        byte aConnections = aTileEntity.getConnections();
        if ((aConnections & 0xC0) != 0) {
            return renderStandardBlock(aWorld, aPos, aState, aRenderer, aTileEntity, buf);
        }
        float tThickness = aTileEntity.getThickNess();
        if (tThickness >= 0.99F) {
            return renderStandardBlock(aWorld, aPos, aState, aRenderer, aTileEntity, buf);
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
            tIsCovered[i] = aTileEntity.getCoverIDAtSide(i) != 0;
        }
        if ((tIsCovered[0]) && (tIsCovered[1]) && (tIsCovered[2]) && (tIsCovered[3]) && (tIsCovered[4]) && (tIsCovered[5])) {
            return renderStandardBlock(aWorld, aPos, aState, aRenderer, aTileEntity, buf);
        }
        ITexture[][] tIcons = new ITexture[6][];
        ITexture[][] tCovers = new ITexture[6][];
        for (byte i = 0; i < 6; i = (byte) (i + 1)) {
            tCovers[i] = aTileEntity.getTexture(aBlock, i);
            tIcons[i] = aTileEntity.getTextureUncovered(i);
        }
        if (tConnections == 0) {
            aRenderer.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
            renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
            renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
            renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
            renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
            renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
            renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
        } else if (tConnections == 3) {
            aRenderer.setBlockBounds(0.0F, sp, sp, 1.0F, sp + tThickness, sp + tThickness);
            renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
            renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
            renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
            renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
            }
        } else if (tConnections == 12) {
            aRenderer.setBlockBounds(sp, 0.0F, sp, sp + tThickness, 1.0F, sp + tThickness);
            renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
            renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
            renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
            renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
            }
        } else if (tConnections == 48) {
            aRenderer.setBlockBounds(sp, sp, 0.0F, sp + tThickness, sp + tThickness, 1.0F);
            renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
            renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
            renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
            renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
            }
        } else {
            if ((tConnections & 0x1) == 0) {
                aRenderer.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
            } else {
                aRenderer.setBlockBounds(0.0F, sp, sp, sp, sp + tThickness, sp + tThickness);
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
                if (!tIsCovered[4]) {
                    renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
                }
            }
            if ((tConnections & 0x2) == 0) {
                aRenderer.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
            } else {
                aRenderer.setBlockBounds(sp + tThickness, sp, sp, 1.0F, sp + tThickness, sp + tThickness);
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
                if (!tIsCovered[5]) {
                    renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
                }
            }
            if ((tConnections & 0x4) == 0) {
                aRenderer.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
            } else {
                aRenderer.setBlockBounds(sp, 0.0F, sp, sp + tThickness, sp, sp + tThickness);
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
                if (!tIsCovered[0]) {
                    renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
                }
            }
            if ((tConnections & 0x8) == 0) {
                aRenderer.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
            } else {
                aRenderer.setBlockBounds(sp, sp + tThickness, sp, sp + tThickness, 1.0F, sp + tThickness);
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
                if (!tIsCovered[1]) {
                    renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
                }
            }
            if ((tConnections & 0x10) == 0) {
                aRenderer.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
            } else {
                aRenderer.setBlockBounds(sp, sp, 0.0F, sp + tThickness, sp + tThickness, sp);
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
                if (!tIsCovered[2]) {
                    renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tIcons[2], false, buf);
                }
            }
            if ((tConnections & 0x20) == 0) {
                aRenderer.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
            } else {
                aRenderer.setBlockBounds(sp, sp, sp + tThickness, sp + tThickness, sp + tThickness, 1.0F);
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tIcons[0], false, buf);
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tIcons[1], false, buf);
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tIcons[4], false, buf);
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tIcons[5], false, buf);
                if (!tIsCovered[3]) {
                    renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tIcons[3], false, buf);
                }
            }
        }
        if (tIsCovered[0]) {
            aRenderer.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
            renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tCovers[0], false, buf);
            renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tCovers[0], false, buf);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tCovers[0], false, buf);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tCovers[0], false, buf);
            }
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tCovers[0], false, buf);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tCovers[0], false, buf);
            }
        }
        if (tIsCovered[1]) {
            aRenderer.setBlockBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tCovers[1], false, buf);
            renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tCovers[1], false, buf);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tCovers[1], false, buf);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tCovers[1], false, buf);
            }
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tCovers[1], false, buf);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tCovers[1], false, buf);
            }
        }
        if (tIsCovered[2]) {
            aRenderer.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tCovers[2], false, buf);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tCovers[2], false, buf);
            }
            renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tCovers[2], false, buf);
            renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tCovers[2], false, buf);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tCovers[2], false, buf);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tCovers[2], false, buf);
            }
        }
        if (tIsCovered[3]) {
            aRenderer.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tCovers[3], false, buf);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tCovers[3], false, buf);
            }
            renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tCovers[3], false, buf);
            renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tCovers[3], false, buf);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tCovers[3], false, buf);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tCovers[3], false, buf);
            }
        }
        if (tIsCovered[4]) {
            aRenderer.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tCovers[4], false, buf);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tCovers[4], false, buf);
            }
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tCovers[4], false, buf);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tCovers[4], false, buf);
            }
            renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tCovers[4], false, buf);
            renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tCovers[4], false, buf);
        }
        if (tIsCovered[5]) {
            aRenderer.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(aWorld, aRenderer, aState, aPos, tCovers[5], false, buf);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(aWorld, aRenderer, aState, aPos, tCovers[5], false, buf);
            }
            if (!tIsCovered[2]) {
                renderNegativeZFacing(aWorld, aRenderer, aState, aPos, tCovers[5], false, buf);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(aWorld, aRenderer, aState, aPos, tCovers[5], false, buf);
            }
            renderNegativeXFacing(aWorld, aRenderer, aState, aPos, tCovers[5], false, buf);
            renderPositiveXFacing(aWorld, aRenderer, aState, aPos, tCovers[5], false, buf);
        }
        aRenderer.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        return true;
    }

    public static void renderNegativeYFacing(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, ITexture[] aIcon, boolean aFullBlock, VertexBuffer vertexBuffer) {
        if (aIcon != null) {
            int lightning = 0;
            boolean item = true;
            if (aWorld != null) {
                if (aFullBlock && !aState.shouldSideBeRendered(aWorld, aPos, EnumFacing.DOWN)) {
                    return;
                }
                item = false;
                t.setPos(aPos.getX(), aPos.getY() - 1, aPos.getZ());
                lightning = aWorld.getBlockState(t).getPackedLightmapCoords(aWorld, t);
            }
            for (ITexture anAIcon : aIcon) {
                if (anAIcon != null) {
                    anAIcon.renderYNeg(aRenderer, aState, aPos, lightning, item, vertexBuffer);
                }
            }
        }
    }

    public static void renderPositiveYFacing(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, ITexture[] aIcon, boolean aFullBlock, VertexBuffer vertexBuffer) {
        if (aIcon != null) {
            int lightning = 0;
            boolean item = true;
            if (aWorld != null) {
                if (aFullBlock && !aState.shouldSideBeRendered(aWorld, aPos, EnumFacing.UP)) {
                    return;
                }
                item = false;
                t.setPos(aPos.getX(), aPos.getY() + 1, aPos.getZ());
                lightning = aWorld.getBlockState(t).getPackedLightmapCoords(aWorld, t);
            }
            for (ITexture anAIcon : aIcon) {
                if (anAIcon != null) {
                    anAIcon.renderYPos(aRenderer, aState, aPos, lightning, item, vertexBuffer);
                }
            }
        }
    }

    public static void renderNegativeZFacing(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, ITexture[] aIcon, boolean aFullBlock, VertexBuffer vertexBuffer) {
        if (aIcon != null) {
            int lightning = 0;
            boolean item = true;
            if (aWorld != null) {
                if (aFullBlock && !aState.shouldSideBeRendered(aWorld, aPos, EnumFacing.NORTH)) {
                    return;
                }
                item = false;
                t.setPos(aPos.getX(), aPos.getY(), aPos.getZ() - 1);
                lightning = aWorld.getBlockState(t).getPackedLightmapCoords(aWorld, t);
            }
            for (ITexture anAIcon : aIcon) {
                if (anAIcon != null) {
                    anAIcon.renderZNeg(aRenderer, aState, aPos, lightning, item, vertexBuffer);
                }
            }
        }
    }

    public static void renderPositiveZFacing(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, ITexture[] aIcon, boolean aFullBlock, VertexBuffer vertexBuffer) {
        if (aIcon != null) {
            int lightning = 0;
            boolean item = true;
            if (aWorld != null) {
                if (aFullBlock && !aState.shouldSideBeRendered(aWorld, aPos, EnumFacing.SOUTH)) {
                    return;
                }
                item = false;
                t.setPos(aPos.getX(), aPos.getY(), aPos.getZ() + 1);
                lightning = aWorld.getBlockState(t).getPackedLightmapCoords(aWorld, t);
            }
            for (ITexture anAIcon : aIcon) {
                if (anAIcon != null) {
                    anAIcon.renderZPos(aRenderer, aState, aPos, lightning, item, vertexBuffer);
                }
            }
        }
    }

    public static void renderNegativeXFacing(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, ITexture[] aIcon, boolean aFullBlock, VertexBuffer vertexBuffer) {
        if (aIcon != null) {
            int lightning = 0;
            boolean item = true;
            if (aWorld != null) {
                if (aFullBlock && !aState.shouldSideBeRendered(aWorld, aPos, EnumFacing.WEST)) {
                    return;
                }
                item = false;
                t.setPos(aPos.getX() - 1, aPos.getY(), aPos.getZ());
                lightning = aWorld.getBlockState(t).getPackedLightmapCoords(aWorld, t);
            }
            for (ITexture anAIcon : aIcon) {
                if (anAIcon != null) {
                    anAIcon.renderXNeg(aRenderer, aState, aPos, lightning, item, vertexBuffer);
                }
            }
        }
    }

    public static void renderPositiveXFacing(IBlockAccess aWorld, RenderBlocks aRenderer, IBlockState aState, BlockPos aPos, ITexture[] aIcon, boolean aFullBlock, VertexBuffer vertexBuffer) {
        if (aIcon != null) {
            int lightning = 0;
            boolean item = true;
            if (aWorld != null) {
                if (aFullBlock && !aState.shouldSideBeRendered(aWorld, aPos, EnumFacing.EAST)) {
                    return;
                }
                item = false;
                t.setPos(aPos.getX() + 1, aPos.getY(), aPos.getZ());
                lightning = aWorld.getBlockState(t).getPackedLightmapCoords(aWorld, t);
            }
            for (ITexture anAIcon : aIcon) {
                if (anAIcon != null) {
                    anAIcon.renderXPos(aRenderer, aState, aPos, lightning, item, vertexBuffer);
                }
            }
        }
    }

    public void renderInventoryBlock(ItemStack itemStack) {
        INSTANCE.renderInventoryBlock(((ItemBlock) itemStack.getItem()).block.getDefaultState(), itemStack.getItemDamage(), RenderBlocks.INSTANCE);
    }


    public void renderInventoryBlock(IBlockState aState, int aMeta, RenderBlocks aRenderer) {
        GT_Generic_Block aBlock = (GT_Generic_Block) aState.getBlock();
        if (aBlock instanceof GT_Block_Machines) {

            if (aMeta > 0 && aMeta < GregTech_API.METATILEENTITIES.length && GregTech_API.METATILEENTITIES[aMeta] != null)
                renderNormalInventoryMetaTileEntity(aState, aMeta, aRenderer);

        }
        aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        aRenderer.setRenderBoundsFromBlock1(aBlock);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public boolean renderWorldBlock(IBlockAccess aWorld, BlockPos aPos, IBlockState aState, RenderBlocks aRenderer, VertexBuffer buf) {
        TileEntity aTileEntity = aWorld.getTileEntity(aPos);

        if (aTileEntity == null) {
            return false;
        }

        if ((aTileEntity instanceof IPipeRenderedTileEntity)) {
            return renderPipeBlock(aWorld, aPos, aState, (IPipeRenderedTileEntity) aTileEntity, aRenderer, buf);
        }

        if ((aTileEntity instanceof ITexturedTileEntity)) {
            ITexturedTileEntity tTileEntity = (ITexturedTileEntity) aTileEntity;
            return renderStandardBlock(aWorld, aPos, aState, aRenderer, tTileEntity, buf);
        }

        return false;
    }
    
    public boolean renderStandardBlock(IBlockAccess aWorld, BlockPos aPos, IBlockState aState, RenderBlocks aRenderer, ITexturedTileEntity aTileEntity, VertexBuffer buf) {
        GT_Generic_Block aBlock = (GT_Generic_Block) aState.getBlock();
        return renderStandardBlock(aWorld, aPos, aState, aRenderer, new ITexture[][]{aTileEntity.getTexture(aBlock, (byte) 0), aTileEntity.getTexture(aBlock, (byte) 1), aTileEntity.getTexture(aBlock, (byte) 2), aTileEntity.getTexture(aBlock, (byte) 3), aTileEntity.getTexture(aBlock, (byte) 4), aTileEntity.getTexture(aBlock, (byte) 5)}, buf);
    } 


    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, VertexBuffer buffer) {
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer) {
        return renderWorldBlock(world, pos, state, RenderBlocks.INSTANCE, buffer);
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {

    }

    @Override
    public void registerTextures(TextureMap map) {}

}
