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
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockSurfaceRock;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class StoneRenderer implements ICCBlockRenderer {

    private static BlockFluidRenderer waterRenderer;
    private static final StoneRenderer INSTANCE = new StoneRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("gt_stone");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
    }

    private BlockFluidRenderer getWaterRenderer() {
        //if(waterRenderer == null) {
            BlockColors blockColors = new BlockColors() {
                @Override
                public int colorMultiplier(IBlockState state, @Nullable IBlockAccess blockAccess, @Nullable BlockPos pos, int renderPass) {
                    return BiomeColorHelper.getWaterColorAtPos(blockAccess, pos);
                }
            };
            waterRenderer = new BlockFluidRenderer(blockColors);
       // }
        return waterRenderer;
    }

    private boolean shouldRenderWater(IBlockAccess world, BlockPos pos) {
        boolean renderWater = false;
        for(EnumFacing facingValue : EnumFacing.VALUES) {
            IBlockState blockState = world.getBlockState(pos.offset(facingValue));
            renderWater |= blockState.getMaterial() == net.minecraft.block.material.Material.WATER;
        }
        return renderWater;
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
        if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
            boolean renderWater = world != null && shouldRenderWater(world, pos);
            if(renderWater) {
                //render water if next to it (because otherwise we will get invisible sides)
                BlockFluidRenderer fluidRenderer = getWaterRenderer();
                IBlockState liquidState = Blocks.WATER.getDefaultState().withProperty(BlockLiquid.LEVEL, state.getValue(BlockLiquid.LEVEL));
                fluidRenderer.renderFluid(world, liquidState, pos, buffer);
            }
            return renderWater;
        }

        //otherwise, we are in solid rendering layer and render primary stone
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        Matrix4 translation = new Matrix4();
        translation.translate(pos.getX(), pos.getY(), pos.getZ());
        IVertexOperation[] operations = new IVertexOperation[1];
        Material material = state.getValue(((BlockSurfaceRock) state.getBlock()).materialProperty);
        operations[0] = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(material.materialRGB));
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
