package gregtech.api.render.scene;

import gregtech.api.gui.resources.RenderUtil;
import gregtech.api.util.BlockInfo;
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
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector3f;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class WorldSceneRenderer {

    private static final FloatBuffer MODELVIEW_MATRIX_BUFFER = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    private static final FloatBuffer PROJECTION_MATRIX_BUFFER = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    private static final IntBuffer VIEWPORT_BUFFER = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
    private static final FloatBuffer POSITION_NEAR_BUFFER = ByteBuffer.allocateDirect(3 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    private static final FloatBuffer POSITION_FAR_BUFFER = ByteBuffer.allocateDirect(3 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

    public final TrackedDummyWorld world = new TrackedDummyWorld();
    private final List<BlockPos> renderedBlocks = new ArrayList<>();
    private SceneRenderCallback renderCallback;
    private Predicate<BlockPos> renderFilter;

    public WorldSceneRenderer(Map<BlockPos, BlockInfo> renderedBlocks) {
        for(Entry<BlockPos, BlockInfo> renderEntry : renderedBlocks.entrySet()) {
            BlockPos pos = renderEntry.getKey();
            BlockInfo blockInfo = renderEntry.getValue();
            if(blockInfo.getBlockState().getBlock() == Blocks.AIR)
                continue; //do not render air blocks
            this.renderedBlocks.add(pos);
            blockInfo.apply(world, pos);
        }
    }

    public void setRenderCallback(SceneRenderCallback callback) {
        this.renderCallback = callback;
    }
    
    public void setRenderFilter(Predicate<BlockPos> filter) {
        this.renderFilter = filter;
    }

    public Vector3f getSize() {
        return world.getSize();
    }

    public RayTraceResult rayTraceFromMouse(int x, int y, int width, int height) {
        float windowMouseX = Mouse.getX();
        float windowMouseY = Minecraft.getMinecraft().displayHeight - Mouse.getY();
        setupCamera(x, y, width, height, -1);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, PROJECTION_MATRIX_BUFFER);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, MODELVIEW_MATRIX_BUFFER);
        GL11.glGetInteger(GL11.GL_VIEWPORT, VIEWPORT_BUFFER);
        VIEWPORT_BUFFER.rewind();
        PROJECTION_MATRIX_BUFFER.rewind();
        MODELVIEW_MATRIX_BUFFER.rewind();

        POSITION_NEAR_BUFFER.rewind();
        POSITION_FAR_BUFFER.rewind();
        GLU.gluUnProject(windowMouseX, windowMouseY, 0.0f, MODELVIEW_MATRIX_BUFFER, PROJECTION_MATRIX_BUFFER, VIEWPORT_BUFFER, POSITION_NEAR_BUFFER);
        GLU.gluUnProject(windowMouseX, windowMouseY, 1.0f, MODELVIEW_MATRIX_BUFFER, PROJECTION_MATRIX_BUFFER, VIEWPORT_BUFFER, POSITION_FAR_BUFFER);
        POSITION_NEAR_BUFFER.rewind();
        POSITION_FAR_BUFFER.rewind();
        Vec3d nearPosition = new Vec3d(POSITION_NEAR_BUFFER.get(), POSITION_NEAR_BUFFER.get(), POSITION_NEAR_BUFFER.get());
        Vec3d farPosition = new Vec3d(POSITION_FAR_BUFFER.get(), POSITION_FAR_BUFFER.get(), POSITION_FAR_BUFFER.get());
        resetCamera();
        return world.rayTraceBlocks(nearPosition, farPosition);
    }


    /**
     * Renders scene on given coordinates with given width and height, and RGB background color
     * Note that this will ignore any transformations applied currently to projection/view matrix,
     * so specified coordinates are scaled MC gui coordinates.
     * It will return matrices of projection and view in previous state after rendering
     */
    public void render(int x, int y, int width, int height, int backgroundColor) {
        setupCamera(x, y, width, height, backgroundColor);
        if(renderCallback != null) {
            renderCallback.preRenderScene(this);
        }
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BlockRendererDispatcher dispatcher = minecraft.getBlockRendererDispatcher();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        for (BlockPos pos : renderedBlocks) {
            if (renderFilter != null && !renderFilter.test(pos))
                continue; //do not render if position is skipped
            IBlockState blockState = world.getBlockState(pos);
            dispatcher.renderBlock(blockState, pos, world, bufferBuilder);
        }
        tessellator.draw();

        resetCamera();
    }

    public static void setupCamera(int x, int y, int width, int height, int skyColor) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution resolution = new ScaledResolution(mc);

        GlStateManager.pushAttrib();
        mc.entityRenderer.disableLightmap();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();

        //translate gui coordinates to window ones (y is inverted)
        int windowX = (int) (x / (resolution.getScaledWidth() * 1.0) * mc.displayWidth);
        int windowWidth = (int) (width / (resolution.getScaledWidth() * 1.0) * mc.displayWidth);
        int windowHeight = (int) (height / (resolution.getScaledHeight() * 1.0) * mc.displayHeight);
        int windowY = mc.displayHeight - (int) (y / (resolution.getScaledHeight() * 1.0) * mc.displayHeight) - windowHeight;
        //setup viewport and clear GL buffers
        GlStateManager.viewport(windowX, windowY, windowWidth, windowHeight);

        if(skyColor >= 0) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(windowX, windowY, windowWidth, windowHeight);
            RenderUtil.setGlClearColorFromInt(skyColor, 255);
            GlStateManager.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        //setup projection matrix to perspective
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();

        float aspectRatio = width / (height * 1.0f);
        GLU.gluPerspective(60.0f, aspectRatio, 0.1f, 10000.0f);

        //setup modelview matrix
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GLU.gluLookAt(0.0f, 0.0f, -10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    public static void resetCamera() {
        //reset viewport
        Minecraft minecraft = Minecraft.getMinecraft();
        GlStateManager.viewport(0, 0, minecraft.displayWidth, minecraft.displayHeight);

        //reset projection matrix
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.popMatrix();

        //reset modelview matrix
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();

        //reset attributes
        GlStateManager.popAttrib();
    }
    
    public class TrackedDummyWorld extends DummyWorld {

        private final Vector3f minPos = new Vector3f();
        private final Vector3f maxPos = new Vector3f();
        
        @Override
        public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
            if(newState.getBlock() == Blocks.AIR) {
                renderedBlocks.remove(pos);
            } else {
                renderedBlocks.add(pos);
            }
            minPos.setX(Math.min(minPos.getX(), pos.getX()));
            minPos.setY(Math.min(minPos.getY(), pos.getY()));
            minPos.setZ(Math.min(minPos.getZ(), pos.getZ()));
            maxPos.setX(Math.max(maxPos.getX(), pos.getX()));
            maxPos.setY(Math.max(maxPos.getY(), pos.getY()));
            maxPos.setZ(Math.max(maxPos.getZ(), pos.getZ()));
            return super.setBlockState(pos, newState, flags);
        }

        @Override
        public IBlockState getBlockState(BlockPos pos) {
            if(renderFilter != null && !renderFilter.test(pos))
                return Blocks.AIR.getDefaultState(); //return air if not rendering this block
            return super.getBlockState(pos);
        }

        public Vector3f getSize() {
            Vector3f result = new Vector3f();
            result.setX(maxPos.getX() - minPos.getX());
            result.setY(maxPos.getY() - minPos.getY());
            result.setZ(maxPos.getZ() - minPos.getZ());
            return result;
        }

        public Vector3f getMinPos() {
            return minPos;
        }

        public Vector3f getMaxPos() {
            return maxPos;
        }
    }

}
