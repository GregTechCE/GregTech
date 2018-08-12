package gregtech.api.render;

import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.render.particle.CustomParticleHandler;
import codechicken.lib.render.particle.DigIconParticle;
import codechicken.lib.render.particle.IModelParticleProvider;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gregtech.api.GTValues;
import gregtech.api.pipelike.BlockPipeLike;
import gregtech.api.pipelike.IBaseProperty;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.pipelike.PipeFactory;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.type.Material;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static gregtech.api.pipelike.PipeFactory.MASK_FORMAL_CONNECTION;
import static gregtech.api.pipelike.PipeFactory.MASK_RENDER_SIDE;
import static gregtech.api.render.MetaTileEntityRenderer.BLOCK_TRANSFORMS;

@SideOnly(Side.CLIENT)
@SuppressWarnings({"unchecked"})
public abstract class PipeLikeRenderer<Q extends Enum<Q> & IBaseProperty & IStringSerializable> implements ICCBlockRenderer, IItemRenderer, IModelParticleProvider {

    private static final Map<PipeFactory, PipeLikeRenderer> RENDERERS = Maps.newHashMap();

    public static <Q extends Enum<Q> & IBaseProperty & IStringSerializable> PipeLikeRenderer<Q> getRenderer(PipeFactory<Q, ?, ?> factory) {
        return RENDERERS.get(factory);
    }
    public ModelResourceLocation MODEL_LOCATION;
    public EnumBlockRenderType BLOCK_RENDER_TYPE;

    protected Set<MaterialIconSet> generatedSets = Sets.newHashSet();

    protected PipeFactory<Q, ?, ?> factory;

    protected PipeLikeRenderer(PipeFactory<Q, ?, ?> factory) {
        this.factory = factory;
        MODEL_LOCATION = new ModelResourceLocation(new ResourceLocation(GTValues.MODID, factory.name), "normal");
        RENDERERS.put(factory, this);
    }

    private static final int ITEM_RENDER_MASK = (MASK_RENDER_SIDE | MASK_FORMAL_CONNECTION) << EnumFacing.SOUTH.getIndex()
        | (MASK_RENDER_SIDE | MASK_FORMAL_CONNECTION) << EnumFacing.NORTH.getIndex();

    private static Set<TextureAtlasSprite> ignoredParticleSprites = null;
    private static Set<TextureAtlasSprite> getIgnoredParticleSprites() {
        if (ignoredParticleSprites == null) {
            try {
                Field field = CustomParticleHandler.class.getDeclaredField("ignoredParticleSprites");
                field.setAccessible(true);
                ignoredParticleSprites = (Set<TextureAtlasSprite>) field.get(null);
            } catch (Exception e) {
                ignoredParticleSprites = Sets.newHashSet();
            }
        }
        return ignoredParticleSprites;
    }

    /**
     * {@link Block#addHitEffects}
     * {@link CustomParticleHandler#handleDestroyEffects(World, BlockPos, ParticleManager)} with specified block state
     * Provided the model bound is an instance of IModelParticleProvider, you will have landing particles just handled for you.
     * Use the default PerspectiveModel implementations inside CCL, Destroy effects will just be handled for you.
     *
     * @param world   The world.
     * @param state   Fake block state for render
     * @param pos     The position of the block.
     * @param manager The ParticleManager.
     * @return True if particles were added, basically just return the result of this method inside {@link Block#addHitEffects}
     */
    @SideOnly(Side.CLIENT)
    public static boolean handleDestroyEffects(World world, IBlockState state, BlockPos pos, ParticleManager manager) {
        BlockModelShapes modelProvider = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
        try {
            state = state.getActualState(world, pos);
        } catch (Throwable ignored) {
        }
        IBakedModel model = modelProvider.getModelForState(state);
        state = state.getBlock().getExtendedState(state, world, pos);
        if (model instanceof IModelParticleProvider) {
            Cuboid6 bounds = new Cuboid6(state.getBoundingBox(world, pos));
            Set<TextureAtlasSprite> destroySprites = ((IModelParticleProvider) model).getDestroyEffects(state, world, pos);
            int color = 0x999999;
            if (model instanceof PipeLikeRenderer) color = ((PipeLikeRenderer) model).getDestoryEffectColor(state, world, pos);
            List<TextureAtlasSprite> sprites = destroySprites.stream().filter(sprite -> !getIgnoredParticleSprites().contains(sprite)).collect(Collectors.toList());
            addBlockDestroyEffects(world, bounds.add(pos), sprites, manager, color);
            return true;
        }
        return false;
    }

    /**
     * {@link CustomParticleHandler#addBlockDestroyEffects(World, Cuboid6, List, ParticleManager)} with color
     */
    @SideOnly (Side.CLIENT)
    public static void addBlockDestroyEffects(World world, Cuboid6 bounds, List<TextureAtlasSprite> icons, ParticleManager particleManager, int color) {
        Vector3 diff = bounds.max.copy().subtract(bounds.min);
        Vector3 center = bounds.min.copy().add(bounds.max).multiply(0.5);
        Vector3 density = diff.copy().multiply(4).ceil();

        for (int i = 0; i < density.x; ++i) {
            for (int j = 0; j < density.y; ++j) {
                for (int k = 0; k < density.z; ++k) {
                    double x = bounds.min.x + (i + 0.5) * diff.x / density.x;
                    double y = bounds.min.y + (j + 0.5) * diff.y / density.y;
                    double z = bounds.min.z + (k + 0.5) * diff.z / density.z;
                    particleManager.addEffect(new ColoredDigIconParticle(world, x, y, z, x - center.x, y - center.y, z - center.z, icons.get(world.rand.nextInt(icons.size())), color));
                }
            }
        }
    }

    private static class ColoredDigIconParticle extends DigIconParticle {
        ColoredDigIconParticle(World world, double x, double y, double z, double dx, double dy, double dz, TextureAtlasSprite icon, int color) {
            super(world, x, y, z, dx, dy, dz, icon);
            this.particleRed = (float) (color >> 16 & 0xFF) / 255.0F;
            this.particleGreen = (float) (color >> 8 & 0xFF) / 255.0F;
            this.particleBlue = (float) (color & 0xFF) / 255.0F;
        }
    }

    protected abstract int getDestoryEffectColor(IBlockState state, World world, BlockPos pos);

    @Override
    public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {
        GlStateManager.enableBlend();
        CCRenderState renderState = CCRenderState.instance();
        GlStateManager.enableBlend();
        renderState.reset();
        renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        BlockPipeLike<Q, ?, ?> block = (BlockPipeLike<Q, ?, ?>) ((ItemBlock) stack.getItem()).getBlock();
        Q baseProperty = block.getBaseProperties(stack);
        Material material = block.material;
        renderBlock(material, baseProperty, factory.getDefaultColor(), renderState, new IVertexOperation[0], ITEM_RENDER_MASK);
        renderState.draw();
        GlStateManager.disableBlend();
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        IVertexOperation[] pipeline = {new Translation(pos)};
        renderState.setBrightness(world, pos);

        BlockPipeLike<Q, ?, ?> block = getBlock(state);
        ITilePipeLike<Q, ?> tile = factory.getTile(world, pos);
        if(tile == null) return false;
        int paintingColor = tile.getColor();
        int connectedSidesMask = tile.getRenderMask();

        Q baseProperty = state.getValue(block.getBaseProperty());
        Material material = block.material;

        renderBlock(material, baseProperty, paintingColor, renderState, pipeline, connectedSidesMask);
        return true;
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {
        renderItem(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), ItemCameraTransforms.TransformType.FIXED);
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.setPipeline(new Vector3(new Vec3d(pos)).translation(), new IconTransformation(sprite));
        ITilePipeLike<Q, ?> tile = factory.getTile(world, pos);
        if(tile == null) return;
        float thickness = tile.getBaseProperty().getThickness();
        int connectedSidesMask = factory.getRenderMask(tile, world, pos);
        Cuboid6 baseBox = PipeFactory.getSideBox(null, thickness);
        BlockRenderer.renderCuboid(renderState, baseBox, 0);
        for(EnumFacing renderSide : EnumFacing.VALUES) {
            if((connectedSidesMask & (MASK_FORMAL_CONNECTION << renderSide.getIndex())) > 0) {
                Cuboid6 sideBox = PipeFactory.getSideBox(renderSide, thickness);
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
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        if(BLOCK_TRANSFORMS.containsKey(cameraTransformType)) {
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

    @Override
    public Set<TextureAtlasSprite> getHitEffects(@Nonnull RayTraceResult traceResult, IBlockState state, IBlockAccess world, BlockPos pos) {
        return getDestroyEffects(state, world, pos);
    }

    public abstract void renderBlock(Material material, Q baseProperty, int tileColor, CCRenderState state, IVertexOperation[] pipeline, int renderMask);

    private static ThreadLocal<BlockRenderer.BlockFace> blockFaces = ThreadLocal.withInitial(BlockRenderer.BlockFace::new);
    protected static void renderSide(CCRenderState renderState, IVertexOperation[] pipeline, EnumFacing side, Cuboid6 cuboid6) {
        if (pipeline == null) return;
        BlockRenderer.BlockFace blockFace = blockFaces.get();
        blockFace.loadCuboidFace(cuboid6, side.getIndex());
        renderState.setPipeline(blockFace, 0, blockFace.verts.length, pipeline);
        renderState.render();
    }

    public EnumBlockRenderType getRenderType() {
        return BLOCK_RENDER_TYPE;
    }

    public void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("gt_" + factory.name);
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, this);
        MinecraftForge.EVENT_BUS.register(this);
        TextureUtils.addIconRegister(this::registerIcons);
        for(Material material : factory.getBlockMap().keySet()) {
            MaterialIconSet iconSet = material.materialIconSet;
            this.generatedSets.add(iconSet);
        }
    }

    protected abstract void registerIcons(TextureMap map);

    public ModelResourceLocation getModelLocation() {
        return MODEL_LOCATION;
    }

    protected BlockPipeLike<Q, ?, ?> getBlock(IBlockState state) {
        return (BlockPipeLike<Q, ?, ?>) state.getBlock();
    }

}
