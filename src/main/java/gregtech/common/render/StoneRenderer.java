package gregtech.common.render;

import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.surfacerock.BlockSurfaceRock;
import gregtech.common.blocks.surfacerock.BlockSurfaceRockFlooded;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.opengl.GL11;

public class StoneRenderer implements ICCBlockRenderer {

    private static final StoneRenderer INSTANCE = new StoneRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;
    private BlockFluidRenderer waterRenderer;
    private BlockColors blockColors;

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("gt_stone");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
    }

    private void lazyInitializeRenderers() {
        if(waterRenderer == null) {
            Minecraft minecraft = Minecraft.getMinecraft();
            BlockRendererDispatcher dispatcher = minecraft.getBlockRendererDispatcher();
            this.blockColors = minecraft.getBlockColors();
            this.waterRenderer = ObfuscationReflectionHelper.getPrivateValue(BlockRendererDispatcher.class, dispatcher, 3); //fluidRenderer
        }
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.setPipeline(new Vector3(new Vec3d(pos)).translation(), new IconTransformation(sprite));
        Cuboid6 baseBox = BlockSurfaceRock.getShapeFromBlockPos(pos);
        BlockRenderer.renderCuboid(renderState, baseBox, 0);
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        //render water in dedicated translucent rendering layer
        lazyInitializeRenderers();
        if(state.getBlock() instanceof BlockSurfaceRockFlooded &&
            MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
            waterRenderer.renderFluid(world, state, pos, buffer);
            return true;
        }
        //otherwise, we are in solid rendering layer and render primary stone
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        Matrix4 translation = new Matrix4();
        translation.translate(pos.getX(), pos.getY(), pos.getZ());
        IVertexOperation[] operations = new IVertexOperation[1];
        operations[0] = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(
            blockColors.colorMultiplier(state, world, pos, 1)));
        if(world != null) {
            renderState.setBrightness(world, pos);
        }
        TextureAtlasSprite stoneSprite = TextureUtils.getBlockTexture("stone");
        Cuboid6 baseBox = BlockSurfaceRock.getShapeFromBlockPos(pos);
        for(EnumFacing renderSide : EnumFacing.VALUES) {
            Textures.renderFace(renderState, translation, operations, renderSide, baseBox, stoneSprite);
        }
        return true;
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        renderBlock(null, BlockPos.ORIGIN, state, tessellator.getBuffer());
        tessellator.draw();
    }

    @Override
    public void registerTextures(TextureMap map) {
    }
}
