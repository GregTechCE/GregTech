package gregtech.api.render;

import gregtech.api.gui.resources.RenderUtil;
import gregtech.api.util.world.DummyWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WorldSceneRenderer {

    private final List<BlockPos> renderedBlocks = new ArrayList<>();
    private final World world = new DummyWorld();
    private final Tessellator tessellator = new Tessellator(2097152);

    public WorldSceneRenderer(Map<BlockPos, Tuple<IBlockState, TileEntity>> renderedBlocks) {
        for(Entry<BlockPos, Tuple<IBlockState, TileEntity>> renderEntry : renderedBlocks.entrySet()) {
            BlockPos renderPos = renderEntry.getKey();
            this.renderedBlocks.add(renderPos);
            IBlockState blockState = renderEntry.getValue().getFirst();
            TileEntity tileEntity = renderEntry.getValue().getSecond();
            this.world.setBlockState(renderPos, blockState);
            //noinspection ConstantConditions -- intellij seems to think that tileEntity can't be null
            if(blockState.getBlock().hasTileEntity(blockState) && tileEntity != null) {
                this.world.setTileEntity(renderPos, tileEntity);
            }
        }
    }

    public void render(int x, int y, int width, int height) {
        setupCamera(x, y, width, height, 0xFFFFFF);

        Minecraft minecraft = Minecraft.getMinecraft();
        GlStateManager.enableBlend();
        minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        GlStateManager.rotate(minecraft.world.getTotalWorldTime() % 360, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0, -3.0, 0.0);
        BlockRendererDispatcher dispatcher = minecraft.getBlockRendererDispatcher();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        for(BlockPos renderPos : renderedBlocks) {
            IBlockState blockState = world.getBlockState(renderPos);
            dispatcher.renderBlock(blockState, renderPos, world, bufferBuilder);
        }
        tessellator.draw();
    }

    public static void setupCamera(int x, int y, int width, int height, int skyColor) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution resolution = new ScaledResolution(mc);

        mc.entityRenderer.disableLightmap();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();

        //translate gui coordinates to window ones (y is inverted)
        int windowX = (int) (x / (resolution.getScaledWidth() * 1.0) * mc.displayWidth);
        int windowWidth = (int) (width / (resolution.getScaledWidth() * 1.0) * mc.displayWidth);
        int windowHeight = (int) (height / (resolution.getScaledHeight() * 1.0) * mc.displayHeight);
        int windowY = mc.displayHeight - (int) (y / (resolution.getScaledHeight() * 1.0) * mc.displayHeight) - windowHeight;
        //setup viewport and clear GL buffers
        GlStateManager.viewport(windowX, windowY, windowWidth, windowHeight);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(windowX, windowY, windowWidth, windowHeight);
        RenderUtil.setGlClearColorFromInt(skyColor, 255);
        GlStateManager.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        //setup projection matrix to perspective
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        float aspectRatio = width / (height * 1.0f);
        GLU.gluPerspective(60.0f, aspectRatio, 0.1f, 10000.0f);

        //setup modelview matrix
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GLU.gluLookAt(0.0f, 0.0f, -10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

}
