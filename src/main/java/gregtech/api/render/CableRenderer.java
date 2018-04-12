package gregtech.api.render;

import codechicken.lib.render.BlockRenderer.BlockFace;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.GTValues;
import gregtech.api.block.machines.MachineItemBlock;
import gregtech.api.cable.BlockCable;
import gregtech.api.cable.Insulation;
import gregtech.api.cable.WireProperties;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.type.MetalMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Matrix4f;

import static gregtech.api.render.MetaTileEntityRenderer.BLOCK_TRANSFORMS;

public class CableRenderer implements ICCBlockRenderer, IItemRenderer {

    public static ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(new ResourceLocation(GTValues.MODID, "cable"), "normal");
    public static CableRenderer INSTANCE = new CableRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;
    private static ThreadLocal<BlockFace> blockFaces = ThreadLocal.withInitial(BlockFace::new);

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("meta_tile_entity");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    @Override
    public void renderItem(ItemStack stack, TransformType transformType) {
        GlStateManager.enableBlend();
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        Insulation insulation = BlockCable.getInsulation(stack);
        MetalMaterial material = ((BlockCable) ((ItemBlock) stack.getItem()).getBlock()).baseProps.material;
        renderCableBlock(material, insulation, renderState, new IVertexOperation[0], 1 << 2 | 1 << 3);
        renderState.draw();
        GlStateManager.disableBlend();
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.lightMatrix.locate(world, pos);
        IVertexOperation[] pipeline = new IVertexOperation[2];
        pipeline[0] = new Translation(pos);
        pipeline[1] = renderState.lightMatrix;
        int connectedSidesMask = 0;
        for(EnumFacing enumFacing : EnumFacing.VALUES) {
            BlockPos offsetPos = pos.offset(enumFacing);
            IBlockState blockState = world.getBlockState(offsetPos);
            if(blockState.getBlock() instanceof BlockCable) {
                connectedSidesMask |= 1 << enumFacing.getIndex();
            } else if(blockState.getBlock().hasTileEntity(blockState)) {
                TileEntity tileEntity = world.getTileEntity(offsetPos);
                if(tileEntity.hasCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER,
                    enumFacing.getOpposite()))
                    connectedSidesMask |= 1 << enumFacing.getIndex();
            }
        }
        Insulation insulation = state.getValue(BlockCable.INSULATION);
        MetalMaterial material = ((BlockCable) state.getBlock()).baseProps.material;
        renderCableBlock(material, insulation, renderState, pipeline, connectedSidesMask);
        return true;
    }

    public void renderCableBlock(MetalMaterial material, Insulation insulation, CCRenderState renderState, IVertexOperation[] pipeline, int connectedSidesMask) {
        TextureAtlasSprite atlasSprite = TextureUtils.getMissingSprite();
        IVertexOperation[] insulationPipeline = ArrayUtils.add(pipeline, new IconTransformation(atlasSprite));
        IVertexOperation[] wirePipeline = ArrayUtils.add(pipeline, new IconTransformation(atlasSprite));
        BlockFace blockFace = blockFaces.get();
        float thickness = insulation.thickness;
        float min = (1.0f - thickness) / 2.0f;
        float max = min + thickness;
        Cuboid6 centerBounds = new Cuboid6(min, min, min, max, max, max);
        Cuboid6 wireBounds = new Cuboid6(min, 0.0f, min, max, min, max);
        for(EnumFacing facing : EnumFacing.VALUES) {
            //only render center side on sides who aren't connected
            if((connectedSidesMask & 1 << facing.getIndex()) == 0) {
                blockFace.loadCuboidFace(centerBounds, facing.getIndex());
                renderState.setPipeline(blockFace, 0, blockFace.verts.length, insulationPipeline);
                renderState.render();
            } else {
                for(EnumFacing cableFacing : EnumFacing.VALUES) {
                    if(cableFacing == facing.getOpposite())
                        return; //do not render inside cable
                    blockFace.loadCuboidFace(wireBounds, cableFacing.getIndex());
                    IVertexOperation[] operations = ArrayUtils.add(cableFacing == facing ? wirePipeline : insulationPipeline,
                        Rotation.sideRotations[cableFacing.getIndex()]);
                    renderState.setPipeline(blockFace, 0, blockFace.verts.length, operations);
                    renderState.render();
                }
            }
        }
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {
        renderItem(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), TransformType.FIXED);
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, BufferBuilder buffer) {
        //TODO implement properly
    }

    @Override
    public IModelState getTransforms() {
        return TRSRTransformation.identity();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
        if(BLOCK_TRANSFORMS.containsKey(cameraTransformType)) {
            return Pair.of(this, BLOCK_TRANSFORMS.get(cameraTransformType).getMatrix());
        }
        return Pair.of(this, null);
    }

    @Override
    public void registerTextures(TextureMap map) {
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }
}
