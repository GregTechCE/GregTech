package gregtech.common.render;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

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
import gregtech.api.pipenet.block.ItemBlockPipe;
import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ModCompatibility;
import gregtech.common.pipelike.inventory.BlockInventoryPipe;
import gregtech.common.pipelike.inventory.InventoryPipeType;
import gregtech.common.pipelike.inventory.tile.TileEntityInventoryPipe;
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

// Review: Temporary renderer to reuse the fluid pipe assets (but with aluminium colouring), haven't tried the original InvPipeRenderer
public class InventoryPipeRenderer implements ICCBlockRenderer, IItemRenderer {

    public static ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(new ResourceLocation(GTValues.MODID, "inventory_pipe"), "normal");
    public static InventoryPipeRenderer INSTANCE = new InventoryPipeRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;
    private PipeTextureInfo pipeTexture;
    private PipeModelInfo pipeModel;

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

        public PipeModelInfo(CCModel[] connectionModels, CCModel[] fullBlockModels) {
            this.connectionModels = connectionModels;
            this.fullBlockModels = fullBlockModels;
        }
    }

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("gtaddons_inventory_pipe");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        TextureUtils.addIconRegister(INSTANCE::registerIcons);
    }

    public void registerIcons(TextureMap map) {
        ResourceLocation inLocation = new ResourceLocation(GTValues.MODID, "blocks/pipe/pipe_large_in");
        ResourceLocation sideLocation = new ResourceLocation(GTValues.MODID, "blocks/pipe/pipe_large_side");

        TextureAtlasSprite inTexture = map.registerSprite(inLocation);
        TextureAtlasSprite sideTexture = map.registerSprite(sideLocation);
        this.pipeTexture = new PipeTextureInfo(inTexture, sideTexture);

        float thickness = InventoryPipeType.NORMAL.getThickness();
        double height = (1.0f - thickness) / 2.0f;
        int angles = 5 + 3;
        CCModel model = ShapeModelGenerator.generateModel(angles, height, thickness / 3.0f, height);
        CCModel fullBlockModel = ShapeModelGenerator.generateModel(angles, 1.0f, thickness / 3.0f, height);

        CCModel[] rotatedVariants = ShapeModelGenerator.generateRotatedVariants(model);
        CCModel[] fullBlockVariants = ShapeModelGenerator.generateFullBlockVariants(fullBlockModel);
        this.pipeModel = new PipeModelInfo(rotatedVariants, fullBlockVariants);
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        event.getModelRegistry().putObject(MODEL_LOCATION, this);
    }

    @Override
    public void renderItem(ItemStack rawItemStack, TransformType transformType) {
        ItemStack stack = ModCompatibility.getRealItemStack(rawItemStack);
        if (!(stack.getItem() instanceof ItemBlockPipe)) {
            return;
        }
        CCRenderState renderState = CCRenderState.instance();
        GlStateManager.enableBlend();
        renderState.reset();
        renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        InventoryPipeType pipeType = InventoryPipeType.NORMAL;
        Material material = Materials.Aluminium;
        if (pipeType != null && material != null) {
            renderPipeBlock(material, pipeType, IPipeTile.DEFAULT_INSULATION_COLOR, renderState, new IVertexOperation[0], 0);
        }
        renderState.draw();
        GlStateManager.disableBlend();
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.setBrightness(world, pos);

        BlockInventoryPipe blockPipe = ((BlockInventoryPipe) state.getBlock());
        TileEntityInventoryPipe tileEntityPipe = (TileEntityInventoryPipe) blockPipe.getPipeTileEntity(world, pos);

        if (tileEntityPipe == null) {
            return false;
        }

        InventoryPipeType pipeType = tileEntityPipe.getPipeType();
        Material pipeMaterial = Materials.Aluminium;
        int paintingColor = tileEntityPipe.getInsulationColor();

        if (pipeType != null && pipeMaterial != null) {
            BlockRenderLayer renderLayer = MinecraftForgeClient.getRenderLayer();

            if (renderLayer == BlockRenderLayer.CUTOUT) {
                int connectedSidesMask = blockPipe.getActualConnections(tileEntityPipe, world);
                IVertexOperation[] pipeline = new IVertexOperation[] {new Translation(pos)};
                renderPipeBlock(pipeMaterial, pipeType, paintingColor, renderState, pipeline, connectedSidesMask);
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

    public boolean renderPipeBlock(Material material, InventoryPipeType pipeType, int insulationColor, CCRenderState state, IVertexOperation[] pipeline, int connectMask) {
        int pipeColor = GTUtility.convertRGBtoOpaqueRGBA_CL(getPipeColor(material, insulationColor));
        ColourMultiplier multiplier = new ColourMultiplier(pipeColor);

        PipeTextureInfo textureInfo = this.pipeTexture;
        PipeModelInfo modelInfo = this.pipeModel;

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
        }
        if (fullBlockModel != null) {
            state.setPipeline(fullBlockModel, 0, fullBlockModel.verts.length, sideTexture);
            state.render();
            return true;
        }

        Cuboid6 centerCuboid = BlockInventoryPipe.getSideBox(null, pipeType.getThickness());
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
        BlockInventoryPipe blockInventoryPipe = (BlockInventoryPipe) state.getBlock();
        IPipeTile<InventoryPipeType, EmptyNodeData> tileEntityPipe = blockInventoryPipe.getPipeTileEntity(world, pos);
        if (tileEntityPipe == null) {
            return;
        }
        InventoryPipeType pipeType = tileEntityPipe.getPipeType();
        if (pipeType == null) {
            return;
        }
        float thickness = pipeType.getThickness();
        int connectedSidesMask = blockInventoryPipe.getActualConnections(tileEntityPipe, world);
        Cuboid6 baseBox = BlockInventoryPipe.getSideBox(null, thickness);
        BlockRenderer.renderCuboid(renderState, baseBox, 0);
        for (EnumFacing renderSide : EnumFacing.VALUES) {
            if ((connectedSidesMask & (1 << renderSide.getIndex())) > 0) {
                Cuboid6 sideBox = BlockInventoryPipe.getSideBox(renderSide, thickness);
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

    public Pair<TextureAtlasSprite, Integer> getParticleTexture(IPipeTile<InventoryPipeType, EmptyNodeData> tileEntity) {
        if (tileEntity == null) {
            return Pair.of(TextureUtils.getMissingSprite(), 0xFFFFFF);
        }
        InventoryPipeType pipeType = tileEntity.getPipeType();
        Material material = Materials.Aluminium;
        if (pipeType == null || material == null) {
            return Pair.of(TextureUtils.getMissingSprite(), 0xFFFFFF);
        }
        TextureAtlasSprite atlasSprite = this.pipeTexture.sideTexture;
        int pipeColor = getPipeColor(material, tileEntity.getInsulationColor());
        return Pair.of(atlasSprite, pipeColor);
    }
}
