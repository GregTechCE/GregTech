package gregtech.common.render;

import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.TransformUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.GTValues;
import gregtech.api.cover.ICoverable;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ModCompatibility;
import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.pipelike.fluidpipe.ItemBlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class FluidPipeRenderer implements ICCBlockRenderer, IItemRenderer {

    public static ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(new ResourceLocation(GTValues.MODID, "fluid_pipe"), "normal");
    public static FluidPipeRenderer INSTANCE = new FluidPipeRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;
    private Map<FluidPipeType, PipeTextureInfo> pipeTextures = new HashMap<>();
    private Map<FluidPipeType, PipeModelInfo> pipeModels = new HashMap<>();

    private static class PipeTextureInfo {
        public final TextureAtlasSprite inTexture;
        public final TextureAtlasSprite sideTexture;

        public PipeTextureInfo(TextureAtlasSprite inTexture, TextureAtlasSprite sideTexture) {
            this.inTexture = inTexture;
            this.sideTexture = sideTexture;
        }
    }

    private static class PipeModelInfo {
        public final CCModel[] connectionModels;
        public final CCModel[] fullBlockModels;
        public final CCModel[] cornerModels;

        public PipeModelInfo(CCModel[] connectionModels, CCModel[] fullBlockModels, CCModel[] cornerModels) {
            this.connectionModels = connectionModels; // TODO Remove this field when finished
            this.fullBlockModels = fullBlockModels;
            this.cornerModels = cornerModels;
        }
    }

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("gt_fluid_pipe");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        TextureUtils.addIconRegister(INSTANCE::registerIcons);
    }

    public void registerIcons(TextureMap map) {
        for (FluidPipeType fluidPipeType : FluidPipeType.values()) {
            ResourceLocation inLocation = new ResourceLocation(GTValues.MODID, String.format("blocks/pipe/pipe_%s_in", fluidPipeType.name));
            ResourceLocation sideLocation = new ResourceLocation(GTValues.MODID, String.format("blocks/pipe/pipe_%s_side", fluidPipeType.name));

            TextureAtlasSprite inTexture = map.registerSprite(inLocation);
            TextureAtlasSprite sideTexture = map.registerSprite(sideLocation);
            this.pipeTextures.put(fluidPipeType, new PipeTextureInfo(inTexture, sideTexture));
        }

        for (FluidPipeType fluidPipeType : FluidPipeType.values()) {
            float thickness = fluidPipeType.getThickness();
            double height = (1.0f - thickness) / 2.0f;
            int angles = 5 + fluidPipeType.ordinal();
            CCModel model = ShapeModelGenerator.generateModel(angles, height, thickness / 3.0f, height);
            CCModel fullBlockModel = ShapeModelGenerator.generateModel(angles, 1.0f, thickness / 3.0f, height);

            CCModel[] rotatedVariants = ShapeModelGenerator.generateRotatedVariants(model); // TODO Remove this when finished
            CCModel[] fullBlockVariants = ShapeModelGenerator.generateFullBlockVariants(fullBlockModel);
            CCModel[] cornerVariants = ShapeModelGenerator.generateCornerVariants(fullBlockModel);
            this.pipeModels.put(fluidPipeType, new PipeModelInfo(rotatedVariants, fullBlockVariants, cornerVariants));
        }
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        event.getModelRegistry().putObject(MODEL_LOCATION, this);
    }

    @Override
    public void renderItem(ItemStack rawItemStack, TransformType transformType) {
        ItemStack stack = ModCompatibility.getRealItemStack(rawItemStack);
        if (!(stack.getItem() instanceof ItemBlockFluidPipe)) {
            return;
        }
        CCRenderState renderState = CCRenderState.instance();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        renderState.reset();
        renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        BlockFluidPipe blockFluidPipe = (BlockFluidPipe) ((ItemBlockFluidPipe) stack.getItem()).getBlock();
        FluidPipeType pipeType = blockFluidPipe.getItemPipeType(stack);
        Material material = blockFluidPipe.getItemMaterial(stack);
        if (pipeType != null && material != null) {
            renderPipeBlock(material, pipeType, IPipeTile.DEFAULT_INSULATION_COLOR, renderState, new IVertexOperation[0], 0b001100);
        }
        renderState.draw();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.setBrightness(world, pos);

        BlockFluidPipe blockPipe = ((BlockFluidPipe) state.getBlock());
        TileEntityFluidPipe tileEntityPipe = (TileEntityFluidPipe) blockPipe.getPipeTileEntity(world, pos);

        if (tileEntityPipe == null) {
            return false;
        }

        FluidPipeType fluidPipeType = tileEntityPipe.getPipeType();
        Material pipeMaterial = tileEntityPipe.getPipeMaterial();
        int paintingColor = tileEntityPipe.getInsulationColor();

        if (fluidPipeType != null && pipeMaterial != null) {
            BlockRenderLayer renderLayer = MinecraftForgeClient.getRenderLayer();

            if (renderLayer == BlockRenderLayer.CUTOUT) {
                int connectedSidesMask = blockPipe.getActualConnections(tileEntityPipe, world);
                IVertexOperation[] pipeline = new IVertexOperation[] {new Translation(pos)};
                renderPipeBlock(pipeMaterial, fluidPipeType, paintingColor, renderState, pipeline, connectedSidesMask);
            }

            ICoverable coverable = tileEntityPipe.getCoverableImplementation();
            coverable.renderCovers(renderState, new Matrix4().translate(pos.getX(), pos.getY(), pos.getZ()), renderLayer);
        }
        return true;
    }

    private int getPipeColor(Material material, int insulationColor) {
        if(insulationColor == IPipeTile.DEFAULT_INSULATION_COLOR) {
            return material.materialRGB;
        } else return insulationColor;
    }

    public boolean renderPipeBlock(Material material, FluidPipeType pipeType, int insulationColor, CCRenderState state, IVertexOperation[] pipeline, int connectMask) {
        int pipeColor = GTUtility.convertRGBtoOpaqueRGBA_CL(getPipeColor(material, insulationColor));
        ColourMultiplier multiplier = new ColourMultiplier(pipeColor);

        PipeTextureInfo textureInfo = this.pipeTextures.get(pipeType);
        PipeModelInfo modelInfo = this.pipeModels.get(pipeType);

        IVertexOperation[] openingTexture = ArrayUtils.addAll(pipeline, new IconTransformation(textureInfo.inTexture), multiplier);
        IVertexOperation[] sideTexture = ArrayUtils.addAll(pipeline, new IconTransformation(textureInfo.sideTexture), multiplier);

        int sidedConnMask = connectMask & 0b111111;
        CCModel fullBlockModel = null;
        if (sidedConnMask == 0b000011) {
            fullBlockModel = modelInfo.fullBlockModels[0];
        } else if (sidedConnMask == 0b001100) {
            fullBlockModel = modelInfo.fullBlockModels[1];
        } else if (sidedConnMask == 0b110000) {
            fullBlockModel = modelInfo.fullBlockModels[2];
        } else if (sidedConnMask == 0b111111) { // TODO eventually replace this with general "else" (might need to handle edge models before it) except for connection models
            fullBlockModel = modelInfo.cornerModels[63];
        } else if (sidedConnMask == 0b001111) {
            fullBlockModel = modelInfo.cornerModels[0b001111];
        } else if (sidedConnMask == 0b110011) {
            fullBlockModel = modelInfo.cornerModels[0b110011];
        } else if (sidedConnMask == 0b111100) {
            fullBlockModel = modelInfo.cornerModels[0b111100];
        } else if (sidedConnMask == 0b010011) {
            fullBlockModel = modelInfo.cornerModels[0b010011];
        }
        if (fullBlockModel != null) {
            state.setPipeline(fullBlockModel, 0, fullBlockModel.verts.length, sideTexture);
            state.render();
            return true;
        }

        /* Lines below here to be removed */
        Cuboid6 centerCuboid = BlockFluidPipe.getSideBox(null, pipeType.getThickness());
        state.setPipeline(openingTexture);
        BlockRenderer.renderCuboid(state, centerCuboid, 0);

        for (EnumFacing side : EnumFacing.VALUES) {
            if ((connectMask & 1 << side.getIndex()) > 0) {
                CCModel model = modelInfo.connectionModels[side.getIndex()];
                state.setPipeline(model, 0, model.verts.length, sideTexture);
                state.render();
            }
        }
        return true;
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
        return TransformUtils.DEFAULT_BLOCK;
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

    public Pair<TextureAtlasSprite, Integer> getParticleTexture(IPipeTile<FluidPipeType, FluidPipeProperties> tileEntity) {
        if (tileEntity == null) {
            return Pair.of(TextureUtils.getMissingSprite(), 0xFFFFFF);
        }
        FluidPipeType fluidPipeType = tileEntity.getPipeType();
        Material material = ((TileEntityFluidPipe) tileEntity).getPipeMaterial();
        if (fluidPipeType == null || material == null) {
            return Pair.of(TextureUtils.getMissingSprite(), 0xFFFFFF);
        }
        TextureAtlasSprite atlasSprite = pipeTextures.get(fluidPipeType).sideTexture;
        int pipeColor = getPipeColor(material, tileEntity.getInsulationColor());
        return Pair.of(atlasSprite, pipeColor);
    }
}
