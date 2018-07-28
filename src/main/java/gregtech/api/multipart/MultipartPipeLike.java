package gregtech.api.multipart;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.particle.CustomParticleHandler;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.multipart.*;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.pipelike.*;
import gregtech.api.render.PipeLikeRenderer;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.world.DummyWorld;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MultipartPipeLike<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C> extends TMultiPart implements TNormalOcclusionPart, TPartialOcclusionPart, INeighborTileChangePart, ITilePipeLike<Q, P> {

    private PipeFactory<Q, P, C> factory;

    private IBlockState blockState;
    private BlockPipeLike<Q, P, C> block;
    private Q baseProperty;
    private P tileProperty;
    private int color;
    private int internalConnections = 0;
    private int renderMask = 0;

    private Cuboid6 centerBox;
    private List<Cuboid6> sideBoxes = new ArrayList<>();

    public MultipartPipeLike(PipeFactory<Q, P, C> factory) {
        this.factory = factory;
    }

    @SuppressWarnings("unchecked")
    public MultipartPipeLike(PipeFactory<Q, P, C> factory, TileEntityPipeLike<Q, P, C> tile, IBlockState state) {
        this.factory = factory;
        block = (BlockPipeLike<Q, P, C>) state.getBlock();
        baseProperty = state.getValue(block.getBaseProperty());
        tileProperty = block.getActualProperty(baseProperty);
        color = tile != null ? tile.getColor() : factory.getDefaultColor();
        reinitShape();
    }

    @Override
    public PipeFactory<Q, P, C> getFactory() {
        return factory;
    }

    /////////////////////////////// BASE PROPERTIES ////////////////////////////////////////

    @Override
    public void save(NBTTagCompound tag) {
        tag.setString("Material", block.material.toString());
        tag.setInteger("BaseProperty", baseProperty.ordinal());
        tag.setInteger("Color", color);
        tag.setInteger("InternalConnections", internalConnections);
        tag.setInteger("RenderMask", renderMask);
    }

    @Override
    public void load(NBTTagCompound tag) {
        block = factory.getBlock(Material.MATERIAL_REGISTRY.getObject(tag.getString("Material")));
        baseProperty = factory.getBaseProperty(tag.getInteger("BaseProperty"));
        tileProperty = block.getActualProperty(baseProperty);
        color = tag.getInteger("Color");
        internalConnections = tag.getInteger("InternalConnections");
        renderMask = tag.getInteger("RenderMask");
        reinitShape();
    }

    @Override
    public ResourceLocation getType() {
        return factory.multipartType;
    }

    @Override
    public World getTileWorld() {
        return world();
    }

    @Override
    public BlockPos getTilePos() {
        return pos();
    }

    @Override
    public Material getMaterial() {
        return block.material;
    }

    @Override
    public Q getBaseProperty() {
        return baseProperty;
    }

    @Override
    public P getTileProperty() {
        return tileProperty;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        updateRenderMask(false);
        if (!world().isRemote) sendDescUpdate();
        updateNode();
        notifyTile();
    }

    @Override
    public int getInternalConnections() {
        return internalConnections;
    }

    @Override
    public int getRenderMask() {
        return renderMask;
    }

    public IBlockState getBlockState() {
        return blockState == null ? blockState = block.getDefaultState().withProperty(factory.baseProperty, baseProperty) : blockState;
    }

    ///////////////////////////////// ITEM DROPPED /////////////////////////////////////////

    protected ItemStack getItem() {
        return block.getItem(baseProperty);
    }

    /**
     * Modified version of {@link net.minecraftforge.common.ForgeHooks#blockStrength(IBlockState, EntityPlayer, World, BlockPos)}
     */
    @Override
    public float getStrength(EntityPlayer player, CuboidRayTraceResult hit) {
        IBlockState state = getBlockState();
        World world = world();
        BlockPos pos = pos();
        float hardness = state.getBlockHardness(world, pos);
        if (hardness < 0.0F) return 0.0F;
        return player.getDigSpeed(state, pos) / hardness / (canHarvestPart(player) ? 30F : 100F);
    }

    /**
     * Modified version of {@link net.minecraftforge.common.ForgeHooks#canHarvestBlock(Block, EntityPlayer, IBlockAccess, BlockPos)}
     */
    protected boolean canHarvestPart(EntityPlayer player) {
        IBlockState state = getBlockState();
        if (state.getMaterial().isToolNotRequired()) return true;

        ItemStack stack = player.getHeldItemMainhand();
        String tool = block.getHarvestTool(state);
        if (stack.isEmpty() || tool == null) return player.canHarvestBlock(state);

        int toolLevel = stack.getItem().getHarvestLevel(stack, tool, player, state);
        if (toolLevel < 0) return player.canHarvestBlock(state);

        return toolLevel >= block.getHarvestLevel(state);
    }

    @Override
    public void harvest(EntityPlayer player, CuboidRayTraceResult hit) {
        if (!player.capabilities.isCreativeMode) {
            if (canHarvestPart(player))
                tile().dropItems(Collections.singleton(getItem()));//TODO Covers?
        }
        tile().remPart(this);
    }

    @Override
    public ItemStack pickItem(CuboidRayTraceResult hit) {
        return getItem();
    }

    ////////////////////////////////// CONNECTIONS /////////////////////////////////////////

    @Override
    public void updateInternalConnection() {
        if (tile() == null || tile().isInvalid()) return;

        float thickness = baseProperty.getThickness();
        int lastValue = internalConnections;
        internalConnections = 0;
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (!tile().canReplacePart(this, new NormallyOccludedPart(PipeFactory.getSideBox(facing, thickness)))) {
                internalConnections |= (MASK_BLOCKED | MASK_INPUT_DISABLED | MASK_OUTPUT_DISABLED) << facing.getIndex();//TODO Covers
            }
        }

        lastValue ^= internalConnections;
        if ((lastValue & 0b111111_111111_000000_000000) != 0) updateRenderMask();
        if ((lastValue & 0b111111_000000_111111_111111) != 0) updateNode();
        if ((lastValue & 0b111111_000000_000000_000000) != 0) notifyTile();
    }

    protected void updateRenderMask() {
        updateRenderMask(true);
    }

    protected void updateRenderMask(boolean sync) {
        if (tile() == null || tile().isInvalid() || world() == null) return;

        int lastValue = renderMask;
        renderMask = factory.getRenderMask(this, world(), pos());
        lastValue ^= renderMask;

        if ((lastValue & 0b000000_111111) != 0) reinitSides();
        if (sync && lastValue != 0 && world() != null && !world().isRemote) sendDescUpdate();
    }

    protected void updateNode() {
        World world = world();
        if (world != null && !world.isRemote) {
            factory.updateNode(world, pos(), this);
        }
    }

    protected void notifyTile() {
        World world = world();
        if (world != null && !world.isRemote && tile() != null) {
            tile().notifyPartChange(this);
        }
    }

    @Override
    public void onAdded() {
        updateInternalConnection();
        updateRenderMask(false);
        if (!world().isRemote) {
            factory.addToPipeNet(world(), pos(), this);
        }
    }

    @Override
    public void onRemoved() {
        factory.removeFromPipeNet(world(), pos());
    }

    /////////////////////////////// COLLISION BOXES ////////////////////////////////////////

    protected void reinitShape() {
        centerBox = PipeFactory.getSideBox(null, baseProperty.getThickness());
        reinitSides();
    }

    protected void reinitSides() {
        sideBoxes.clear();
        float thickness = baseProperty.getThickness();
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (0 != (renderMask & PipeLikeRenderer.MASK_FORMAL_CONNECTION << facing.getIndex())) {
                sideBoxes.add(PipeFactory.getSideBox(facing, thickness));
            }
        }
    }

    @Override
    public boolean occlusionTest(TMultiPart part) {
        return NormalOcclusionTest.apply(this, part);
    }

    @Override
    public Iterable<IndexedCuboid6> getSubParts() {
        return getAllBoxes().stream()
            .map(box -> new IndexedCuboid6(null, box))
            .collect(Collectors.toList());
    }

    @Override
    public Iterable<Cuboid6> getCollisionBoxes() {
        return getAllBoxes();
    }

    private List<Cuboid6> getAllBoxes() {
        List<Cuboid6> result = new ArrayList<>();
        result.add(centerBox);
        result.addAll(sideBoxes);
        return result;
    }

    @Override
    public Iterable<Cuboid6> getOcclusionBoxes() {
        return Collections.singleton(centerBox);
    }

    @Override
    public Iterable<Cuboid6> getPartialOcclusionBoxes() {
        return sideBoxes;
    }

    @Override
    public boolean allowCompleteOcclusion() {
        return true;
    }

    @Override
    public void onEntityCollision(Entity entity) {
        factory.onEntityCollidedWithBlock(entity, this);
    }

    @Override
    public void onNeighborChanged() {
        updateRenderMask();
        updateNode();
    }

    @Override
    public boolean weakTileChanges() {
        return false;
    }

    @Override
    public void onNeighborTileChanged(int side, boolean weak) {
        updateRenderMask();
        updateNode();
    }

    ///////////////////////////////// SYNCHRONIZE //////////////////////////////////////////

    @Override
    public void onPartChanged(TMultiPart part) {
        if (part != this) {
            scheduleTick(1);
        }
    }

    @Override
    public void scheduledTick() {
        updateInternalConnection();
    }

    @Override
    public void writeDesc(MCDataOutput packet) {
        packet.writeString(block.material.toString());
        packet.writeEnum(baseProperty);
        packet.writeInt(color);
        packet.writeInt(renderMask);
    }

    @Override
    public void readDesc(MCDataInput packet) {
        block = factory.getBlock(Material.MATERIAL_REGISTRY.getObject(packet.readString()));
        baseProperty = packet.readEnum(factory.classBaseProperty);
        tileProperty = block.getActualProperty(baseProperty);
        color = packet.readInt();
        renderMask = packet.readInt();
        reinitShape();
    }

    ///////////////////////////////// CAPABILITIES /////////////////////////////////////////

    private C networkCapability;
    protected C getNetworkCapability() {
        return networkCapability == null ? (networkCapability = factory.createCapability(this)) : networkCapability;
    }

    public boolean hasCapabilityInternal(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == factory.capability;//TODO Covers
    }

    public <U> U getCapabilityInternal(@Nonnull Capability<U> capability, @Nullable EnumFacing facing) {
        if (capability == factory.capability) {
            return factory.capability.cast(getNetworkCapability());//TODO Covers
        }
        return null;
    }

    @Nullable
    @Override
    public ICapabilityProvider getCapabilityProviderAtSide(@Nonnull EnumFacing facing) {
        BlockPos.MutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain(pos());
        pos.move(facing);
        World world = world();
        ICapabilityProvider result = world == null ? null : world.getTileEntity(pos);
        if (result != null && color != factory.getDefaultColor()) {
            MetaTileEntity mte = BlockMachine.getMetaTileEntity(world, pos);
            if (mte != null && mte.getPaintingColor() != MetaTileEntity.DEFAULT_PAINTING_COLOR
                && mte.getPaintingColor() != color) {
                result = null;
            }
        }
        pos.move(facing.getOpposite());
        return result;
    }
    //////////////////////////////////// RENDER ////////////////////////////////////////////

    @SideOnly(Side.CLIENT)
    @Override
    public boolean renderStatic(Vector3 pos, BlockRenderLayer layer, CCRenderState ccrs) {
        ccrs.setBrightness(world(), pos());
        factory.getRenderer().renderBlock(block.material, baseProperty, color, ccrs, new IVertexOperation[] {new Translation(pos())}, renderMask);
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderBreaking(Vector3 pos, TextureAtlasSprite texture, final CCRenderState ccrs) {
        ccrs.setPipeline(pos.translation(), new IconTransformation(texture));
        BlockRenderer.renderCuboid(ccrs, centerBox, 0);
        for(Cuboid6 box : sideBoxes) {
            BlockRenderer.renderCuboid(ccrs, box, 0);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addDestroyEffects(CuboidRayTraceResult hit, ParticleManager manager) {
        PipeLikeRenderer.handleDestroyEffects(world(), getBlockState(), pos(), manager);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addHitEffects(CuboidRayTraceResult hit, ParticleManager manager) {
        CustomParticleHandler.handleHitEffects(getBlockState(), world(), hit, manager);
    }
}
