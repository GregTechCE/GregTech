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
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import scala.util.control.Exception.By;

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

    private static final boolean DEBUG_PICKING = false;
    private static final IntBuffer VIEWPORT_BUFFER = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
    private static final IntBuffer SELECTION_BUFFER = ByteBuffer.allocateDirect(32 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();

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

    /**
     * Renders scene on given coordinates with given width and height, and RGB background color
     * Note that this will ignore any transformations applied currently to projection/view matrix,
     * so specified coordinates are scaled MC gui coordinates.
     * It will return matrices of projection and view in previous state after rendering
     */
    public void render(int x, int y, int width, int height, int backgroundColor) {
        renderInternal(x, y, width, height, backgroundColor, false);
    }

    /**
     * Attempts to pick block on the mouse cursor, returning block position on
     * successful hit and null if no hit was found
     */
    public BlockPos select(int x, int y, int width, int height) {
        int hitRecords = renderInternal(x, y, width, height, 0xFFFFFF, true);
        SELECTION_BUFFER.rewind();
        int lastHitRecord = -1;
        for(int i = 0; i < hitRecords; i++) {
            if(DEBUG_PICKING) System.out.println("Hit record " + i);
            int namesCount = SELECTION_BUFFER.get();
            if(DEBUG_PICKING) System.out.println("Names amount: " + namesCount);
            int minDepthValue = SELECTION_BUFFER.get();
            int maxDepthValue = SELECTION_BUFFER.get();
            if(DEBUG_PICKING) System.out.println("Depth values: " + minDepthValue + "-" + maxDepthValue);
            for(int nameIndex = 0; nameIndex < namesCount; nameIndex++) {
                int name = SELECTION_BUFFER.get();
                if(DEBUG_PICKING) System.out.println("Hit name: " + name);
                lastHitRecord = name;
            }
        }
        return lastHitRecord == -1 ? null : renderedBlocks.get(lastHitRecord);
    }

    private int renderInternal(int x, int y, int width, int height, int backgroundColor, boolean isPickingMode) {
        setupCamera(x, y, width, height, backgroundColor, isPickingMode);
        if(renderCallback != null) {
            renderCallback.preRenderScene(this);
        }
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BlockRendererDispatcher dispatcher = minecraft.getBlockRendererDispatcher();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        if(!isPickingMode) {
            //if we're not in picking mode, buffer all draw calls into one
            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        }

        for(int index = 0; index < renderedBlocks.size(); index++) {
            BlockPos pos = renderedBlocks.get(index);
            if(renderFilter != null && !renderFilter.test(pos))
                continue; //do not render if position is skipped
            IBlockState blockState = world.getBlockState(pos);
            if(isPickingMode) {
                //if we're in picking mode, every block should get it's own draw call
                //with it's name set to block position index
                bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                GL11.glPushName(index);
            }
            dispatcher.renderBlock(blockState, pos, world, bufferBuilder);
            if(isPickingMode) {
                //if we're in picking mode, every block should get it's own draw call
                //with it's name set to block position index
                tessellator.draw();
                GL11.glPopName();
            }

        }
        if(!isPickingMode) {
            //if we're not in picking mode, buffer all draw calls into one
            tessellator.draw();
        }
        return resetCamera(isPickingMode);
    }

    public static void setupCamera(int x, int y, int width, int height, int skyColor, boolean isPickingMode) {
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

        if(!isPickingMode) {
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
        //if we're picking, apply pick matrix before perspective
        if (isPickingMode) {
            VIEWPORT_BUFFER.rewind();
            VIEWPORT_BUFFER.put(windowX);
            VIEWPORT_BUFFER.put(windowY);
            VIEWPORT_BUFFER.put(windowWidth);
            VIEWPORT_BUFFER.put(windowHeight);
            VIEWPORT_BUFFER.rewind();
            int mouseX = Mouse.getX();
            int mouseY = Mouse.getY();
            GLU.gluPickMatrix(mouseX, mouseY, 2.0f, 2.0f, VIEWPORT_BUFFER);
        }

        float aspectRatio = width / (height * 1.0f);
        GLU.gluPerspective(60.0f, aspectRatio, 0.1f, 10000.0f);

        //setup modelview matrix
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GLU.gluLookAt(0.0f, 0.0f, -10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

        //if we're picking, bind selection buffer and set render mode to select
        if (isPickingMode) {
            GL11.glSelectBuffer(SELECTION_BUFFER);
            GL11.glRenderMode(GL11.GL_SELECT);
        }
    }

    public static int resetCamera(boolean isPickingMode) {
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

        //if we're resetting from picking, reset render mode to render
        if(isPickingMode) {
            int pickedNames = GL11.glRenderMode(GL11.GL_RENDER);
            if(DEBUG_PICKING) System.out.println("Hits found: " + pickedNames);
            return pickedNames;
        }
        return 0;
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
