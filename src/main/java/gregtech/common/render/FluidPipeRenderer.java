package gregtech.common.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.BlockRenderer.BlockFace;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.GTValues;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.pipelike.fluidpipe.ItemBlockFluidPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Matrix4f;
import java.util.HashMap;
import java.util.Map;

import static gregtech.api.render.MetaTileEntityRenderer.BLOCK_TRANSFORMS;

public class FluidPipeRenderer implements ICCBlockRenderer, IItemRenderer {

    public static ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(new ResourceLocation(GTValues.MODID, "fluid_pipe"), "normal");
    public static FluidPipeRenderer INSTANCE = new FluidPipeRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;
    private static ThreadLocal<BlockFace> blockFaces = ThreadLocal.withInitial(BlockFace::new);
    private Map<FluidPipeType, PipeTextureInfo> pipeTextures = new HashMap<>();

    private static class PipeTextureInfo {
        public final TextureAtlasSprite inTexture;
        public final TextureAtlasSprite sideTexture;

        public PipeTextureInfo(TextureAtlasSprite inTexture, TextureAtlasSprite sideTexture) {
            this.inTexture = inTexture;
            this.sideTexture = sideTexture;
        }
    }

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("gt_fluid_pipe");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        TextureUtils.addIconRegister(INSTANCE::registerIcons);
    }

    public void registerIcons(TextureMap map) {
        GTLog.logger.info("Registering fluid pipe textures.");

        for (FluidPipeType fluidPipeType : FluidPipeType.values()) {
            ResourceLocation inLocation = new ResourceLocation(GTValues.MODID, String.format("blocks/pipe/pipe_%s_in", fluidPipeType.name));
            ResourceLocation sideLocation = new ResourceLocation(GTValues.MODID, String.format("blocks/pipe/pipe_%s_side", fluidPipeType.name));
            TextureAtlasSprite inTexture = map.registerSprite(inLocation);
            TextureAtlasSprite sideTexture = map.registerSprite(sideLocation);
            pipeTextures.put(fluidPipeType, new PipeTextureInfo(inTexture, sideTexture));
        }
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        GTLog.logger.info("Injected fluid pipe render model");
        event.getModelRegistry().putObject(MODEL_LOCATION, this);
    }

    @Override
    public void renderItem(ItemStack stack, TransformType transformType) {
        GlStateManager.enableBlend();
        CCRenderState renderState = CCRenderState.instance();
        GlStateManager.enableBlend();
        renderState.reset();
        renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        BlockFluidPipe blockFluidPipe = (BlockFluidPipe) ((ItemBlockFluidPipe) stack.getItem()).getBlock();
        FluidPipeType pipeType = blockFluidPipe.getItemPipeType(stack);
        Material material = blockFluidPipe.getItemMaterial(stack);
        if (pipeType != null && material != null) {
            renderPipeBlock(material, pipeType, IPipeTile.DEFAULT_INSULATION_COLOR, renderState, new IVertexOperation[0],
                1 << EnumFacing.SOUTH.getIndex() | 1 << EnumFacing.NORTH.getIndex() |
                    1 << (6 + EnumFacing.SOUTH.getIndex()) | 1 << (6 + EnumFacing.NORTH.getIndex()));
        }
        renderState.draw();
        GlStateManager.disableBlend();
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        IVertexOperation[] pipeline = {new Translation(pos)};

        int brightness = world.getBlockState(pos).getPackedLightmapCoords(world, pos);
        BlockFluidPipe blockFluidPipe = (BlockFluidPipe) state.getBlock();
        IPipeTile<FluidPipeType, FluidPipeProperties> tileEntityCable = blockFluidPipe.getPipeTileEntity(world, pos);

        if (tileEntityCable == null) {
            return false;
        }

        int paintingColor = tileEntityCable.getInsulationColor();
        int connectedSidesMask = blockFluidPipe.getActualConnections(tileEntityCable, world);
        FluidPipeType fluidPipeType = tileEntityCable.getPipeType();
        Material material = tileEntityCable.getPipeMaterial();

        if (fluidPipeType != null && material != null) {
            renderState.brightness = brightness;
            renderPipeBlock(material, fluidPipeType, paintingColor, renderState, pipeline, connectedSidesMask);
            renderState.brightness = brightness;
            renderState.lightMatrix.locate(world, pos);
            tileEntityCable.getCoverableImplementation().renderCovers(renderState, new Matrix4().translate(pos.getX(), pos.getY(), pos.getZ()), renderState.lightMatrix, brightness);
        }
        return true;
    }

    public void renderPipeBlock(Material material, FluidPipeType pipeType, int insulationColor, CCRenderState state, IVertexOperation[] pipeline, int connectMask) {
        int pipeColor;
        if (insulationColor == IPipeTile.DEFAULT_INSULATION_COLOR) {
            pipeColor = ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(material.materialRGB),
                GTUtility.convertRGBtoOpaqueRGBA_CL(insulationColor));
        } else {
            pipeColor = GTUtility.convertRGBtoOpaqueRGBA_CL(material.materialRGB);
        }
        float thickness = pipeType.getThickness();
        ColourMultiplier multiplier = new ColourMultiplier(pipeColor);
        PipeTextureInfo textureInfo = pipeTextures.get(pipeType);
        IVertexOperation[] pipeConnectSide = ArrayUtils.addAll(pipeline, new IconTransformation(textureInfo.inTexture), multiplier);
        IVertexOperation[] pipeSide = ArrayUtils.addAll(pipeline, new IconTransformation(textureInfo.sideTexture), multiplier);


        Cuboid6 cuboid6 = BlockFluidPipe.getSideBox(null, thickness);
        for (EnumFacing renderedSide : EnumFacing.VALUES) {
            if ((connectMask & 1 << renderedSide.getIndex()) == 0) {
                int oppositeIndex = renderedSide.getOpposite().getIndex();
                if ((connectMask & 1 << oppositeIndex) > 0 && (connectMask & ~(1 << oppositeIndex)) == 0) {
                    renderPipeSide(state, pipeConnectSide, renderedSide, cuboid6);
                } else {
                    renderPipeSide(state, pipeSide, renderedSide, cuboid6);
                }
            }
        }

        renderPipeCube(connectMask, state, pipeSide, pipeConnectSide, EnumFacing.DOWN, thickness);
        renderPipeCube(connectMask, state, pipeSide, pipeConnectSide, EnumFacing.UP, thickness);
        renderPipeCube(connectMask, state, pipeSide, pipeConnectSide, EnumFacing.WEST, thickness);
        renderPipeCube(connectMask, state, pipeSide, pipeConnectSide, EnumFacing.EAST, thickness);
        renderPipeCube(connectMask, state, pipeSide, pipeConnectSide, EnumFacing.NORTH, thickness);
        renderPipeCube(connectMask, state, pipeSide, pipeConnectSide, EnumFacing.SOUTH, thickness);
    }

    private static void renderPipeCube(int connections, CCRenderState renderState, IVertexOperation[] pipeline, IVertexOperation[] pipeConnectSide, EnumFacing side, float thickness) {
        if ((connections & 1 << side.getIndex()) > 0) {
            boolean renderFrontSide = (connections & 1 << (6 + side.getIndex())) > 0;
            Cuboid6 cuboid6 = BlockFluidPipe.getSideBox(side, thickness);
            for (EnumFacing renderedSide : EnumFacing.VALUES) {
                if (renderedSide == side) {
                    if (renderFrontSide) {
                        renderPipeSide(renderState, pipeConnectSide, renderedSide, cuboid6);
                    }
                } else if (renderedSide != side.getOpposite()) {
                    renderPipeSide(renderState, pipeline, renderedSide, cuboid6);
                }
            }
        }
    }

    private static void renderPipeSide(CCRenderState renderState, IVertexOperation[] pipeline, EnumFacing side, Cuboid6 cuboid6) {
        BlockFace blockFace = blockFaces.get();
        blockFace.loadCuboidFace(cuboid6, side.getIndex());
        renderState.setPipeline(blockFace, 0, blockFace.verts.length, pipeline);
        renderState.render();
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.setPipeline(new Vector3(new Vec3d(pos)).translation(), new IconTransformation(sprite));
        BlockFluidPipe blockFluidPipe = (BlockFluidPipe) state.getBlock();
        IPipeTile<FluidPipeType, FluidPipeProperties> tileEntityPipe = blockFluidPipe.getPipeTileEntity(world, pos);
        if (tileEntityPipe == null) {
            return;
        }
        FluidPipeType fluidPipeType = tileEntityPipe.getPipeType();
        if (fluidPipeType == null) {
            return;
        }
        float thickness = fluidPipeType.getThickness();
        int connectedSidesMask = blockFluidPipe.getActualConnections(tileEntityPipe, world);
        Cuboid6 baseBox = BlockFluidPipe.getSideBox(null, thickness);
        BlockRenderer.renderCuboid(renderState, baseBox, 0);
        for (EnumFacing renderSide : EnumFacing.VALUES) {
            if ((connectedSidesMask & (1 << renderSide.getIndex())) > 0) {
                Cuboid6 sideBox = BlockFluidPipe.getSideBox(renderSide, thickness);
                BlockRenderer.renderCuboid(renderState, sideBox, 0);
            }
        }
    }

    @Override
    public void registerTextures(TextureMap map) {
    }

    @Override
    public IModelState getTransforms() {
        return TRSRTransformation.identity();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
        if (BLOCK_TRANSFORMS.containsKey(cameraTransformType)) {
            return Pair.of(this, BLOCK_TRANSFORMS.get(cameraTransformType).getMatrix());
        }
        return Pair.of(this, null);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return TextureUtils.getMissingSprite();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    public TextureAtlasSprite getParticleTexture(IPipeTile<FluidPipeType, FluidPipeProperties> tileEntity) {
        if (tileEntity == null) {
            return TextureUtils.getMissingSprite();
        }
        FluidPipeType fluidPipeType = tileEntity.getPipeType();
        if (fluidPipeType == null) {
            return TextureUtils.getMissingSprite();
        }
        return pipeTextures.get(fluidPipeType).sideTexture;
    }
}
