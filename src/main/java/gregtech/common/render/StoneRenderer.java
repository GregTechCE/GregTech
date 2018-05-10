package gregtech.common.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockSurfaceRock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class StoneRenderer implements ICCBlockRenderer {

    private static final StoneRenderer INSTANCE = new StoneRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("gt_stone");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        Cuboid6 boundCuboid = BlockSurfaceRock.getShapeFromBlockPos(pos);
        Matrix4 identity = new Matrix4();
        identity.translate(pos.getX(), pos.getY(), pos.getZ());
        IVertexOperation[] noOperations = new IVertexOperation[0];
        for(EnumFacing renderSide : EnumFacing.VALUES) {
            Textures.renderFace(renderState, identity, noOperations, renderSide, boundCuboid, sprite);
        }
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        Matrix4 translation = new Matrix4();
        translation.translate(pos.getX(), pos.getY(), pos.getZ());
        IVertexOperation[] operations = new IVertexOperation[2];
        Material material = state.getValue(((BlockSurfaceRock) state.getBlock()).materialProperty);
        operations[1] = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA(material.materialRGB));
        if(world != null) {
            renderState.lightMatrix.locate(world, pos);
            operations[0] = renderState.lightMatrix;
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
