package gregtech.api.render;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.GTValues;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.block.machines.MachineItemBlock;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Matrix4f;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MetaTileEntityRenderer implements ICCBlockRenderer, IItemRenderer {

    public static ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(new ResourceLocation(GTValues.MODID, "machine"), "normal");
    public static MetaTileEntityRenderer INSTANCE = new MetaTileEntityRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;
    public static Map<TransformType, TRSRTransformation> BLOCK_TRANSFORMS = new HashMap<>();

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("meta_tile_entity");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        TextureUtils.addIconRegister(Textures::register);
    }

    @SuppressWarnings("deprecation")
    public static void postInit() {
        try {

            try (IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("models/block/block.json"))) {
                InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                ModelBlock modelBlock = ModelBlock.deserialize(reader);
                for (TransformType transformType : TransformType.values()) {
                    ItemTransformVec3f vec3f = modelBlock.getAllTransforms().getTransform(transformType);
                    BLOCK_TRANSFORMS.put(transformType, new TRSRTransformation(vec3f));
                }
            }
        } catch (IOException exception) {
            GTLog.logger.error("Failed to load default block transforms", exception);
        }
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        GTLog.logger.info("Injected MetaTileEntity render model");
        event.getModelRegistry().putObject(MODEL_LOCATION, this);
    }

    @Override
    public void renderItem(ItemStack stack, TransformType transformType) {
        MetaTileEntity metaTileEntity = MachineItemBlock.getMetaTileEntity(stack);
        if (metaTileEntity == null) {
            return;
        }
        GlStateManager.enableBlend();
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        metaTileEntity.setRenderContextStack(stack);
        metaTileEntity.renderMetaTileEntity(renderState, new Matrix4(), new IVertexOperation[0]);
        if (metaTileEntity.requiresDynamicRendering()) {
            metaTileEntity.renderMetaTileEntityDynamic(renderState, new Matrix4(), new IVertexOperation[0], 0.0f);
        }
        metaTileEntity.setRenderContextStack(null);
        renderState.draw();
        GlStateManager.disableBlend();
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        MetaTileEntity metaTileEntity = BlockMachine.getMetaTileEntity(world, pos);
        if (metaTileEntity == null) {
            return false;
        }
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        IVertexOperation[] pipeline = new IVertexOperation[]{renderState.lightMatrix};
        Matrix4 translation = new Matrix4().translate(pos.getX(), pos.getY(), pos.getZ());
        renderState.lightMatrix.locate(world, pos);
        metaTileEntity.renderMetaTileEntity(renderState, translation.copy(), pipeline);
        metaTileEntity.renderCovers(renderState, translation);
        return true;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
        if (BLOCK_TRANSFORMS.containsKey(cameraTransformType)) {
            return Pair.of(this, BLOCK_TRANSFORMS.get(cameraTransformType).getMatrix());
        }
        return Pair.of(this, null);
    }

    @Override
    public IModelState getTransforms() {
        return TRSRTransformation.identity();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, BufferBuilder buffer) {
        MetaTileEntity metaTileEntity = BlockMachine.getMetaTileEntity(world, pos);
        ArrayList<IndexedCuboid6> boundingBox = new ArrayList<>();
        if (metaTileEntity != null) {
            metaTileEntity.addCollisionBoundingBox(boundingBox);
            metaTileEntity.addCoverCollisionBoundingBox(boundingBox, false);
        }
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.setPipeline(new Vector3(new Vec3d(pos)).translation(), new IconTransformation(sprite));
        for (Cuboid6 cuboid : boundingBox) {
            BlockRenderer.renderCuboid(renderState, cuboid, 0);
        }
    }

    public TextureAtlasSprite getParticleTexture(IBlockAccess world, BlockPos pos) {
        MetaTileEntity metaTileEntity = BlockMachine.getMetaTileEntity(world, pos);
        if (metaTileEntity == null) {
            return TextureUtils.getMissingSprite();
        } else {
            return metaTileEntity.getParticleTexture();
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return TextureUtils.getMissingSprite();
    }

    @Override
    public void registerTextures(TextureMap map) {
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
