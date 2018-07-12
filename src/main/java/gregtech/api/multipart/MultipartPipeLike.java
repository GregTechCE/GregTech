package gregtech.api.multipart;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.multipart.*;
import gregtech.api.pipelike.*;
import gregtech.api.render.PipeLikeRenderer;
import gregtech.api.unification.material.type.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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

public class MultipartPipeLike<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends ITileProperty, T extends ITilePipeLike<Q, P>, C> extends TMultiPart implements TNormalOcclusionPart, TPartialOcclusionPart, ITilePipeLike<Q, P> {

    public final PipeLikeObjectFactory<Q, P, T, C> factory;

    private BlockPipeLike<Q, P, T, C> block;
    private Q baseProperty;
    private P tileProperty;
    private int color;
    private int internalConnection = 0;
    private int renderMask = 0;

    private Cuboid6 centerBox;
    private List<Cuboid6> sideBoxes = new ArrayList<>();

    public MultipartPipeLike(PipeLikeObjectFactory<Q, P, T, C> factory) {
        this.factory = factory;
    }

    @SuppressWarnings("unchecked")
    public MultipartPipeLike(PipeLikeObjectFactory<Q, P, T, C> factory, TileEntityPipeLike<Q, P, T, C> tile, IBlockState state) {
        this.factory = factory;
        block = (BlockPipeLike<Q, P, T, C>) state.getBlock();
        baseProperty = state.getValue(block.getBaseProperty());
        tileProperty = block.getActualProperty(baseProperty);
        color = tile.getColor();
        reinitShape();
    }

    /////////////////////////////// BASE PROPERTIES ////////////////////////////////////////

    @Override
    public void save(NBTTagCompound tag) {
        tag.setString("material", block.material.toString());
        tag.setInteger("baseProperty", baseProperty.ordinal());
        tag.setInteger("color", color);
        tag.setInteger("internalConnection", internalConnection);
        tag.setInteger("renderMask", renderMask);
    }

    @Override
    public void load(NBTTagCompound tag) {
        block = factory.getBlock(Material.MATERIAL_REGISTRY.getObject(tag.getString("material")));
        baseProperty = factory.getBaseProperty(tag.getInteger("baseProperty"));
        tileProperty = block.getActualProperty(baseProperty);
        color = tag.getInteger("color");
        internalConnection = tag.getInteger("internalConnection");
        renderMask = tag.getInteger("renderMask");
        reinitShape();
    }

    @Override
    public ResourceLocation getType() {
        return factory.multipartType;
    }

    @Override
    public World getWorld() {
        return world();
    }

    @Override
    public BlockPos getPos() {
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
        updateRenderMask(true);
        onConnectionUpdate();
    }

    @Override
    public int getInternalConnections() {
        return internalConnection;
    }

    ///////////////////////////////// ITEM DROPPED /////////////////////////////////////////

    protected ItemStack getItem() {
        return block.getItem(baseProperty);
    }

    @Override
    public Iterable<ItemStack> getDrops() {//TODO Tools
        return Collections.singleton(getItem());//TODO Covers?
    }

    @Override
    public void harvest(EntityPlayer player, CuboidRayTraceResult hit) {
        super.harvest(player, hit);//TODO Tools
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
        int lastValue = internalConnection;
        internalConnection = 0;
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (tile().canReplacePart(this, new NormallyOccludedPart(PipeLikeObjectFactory.getSideBox(facing, thickness)))) {
                internalConnection |= MASK_FORMAL_CONNECTION << facing.getIndex();
            } else {
                internalConnection |= (MASK_BLOCKED | MASK_INPUT_DISABLED | MASK_OUTPUT_DISABLED) << facing.getIndex();//TODO Covers
            }
        }

        lastValue ^= internalConnection;
        if ((lastValue & 0b111111_111111_000000_000000) != 0) updateRenderMask(false);
        if ((lastValue & 0b111111_000000_111111_111111) != 0) onConnectionUpdate();
        //TODO Check active
    }

    protected void updateRenderMask(boolean update) {
        if (tile() == null || tile().isInvalid() || world() == null) return;

        int lastValue = renderMask;
        renderMask = factory.getRenderMask(this, world(), pos());
        lastValue ^= renderMask;

        if ((lastValue & 0b000000_111111) != 0) {
            reinitSides();
            update = true;
        }
        if (update && !world().isRemote) {
            sendDescUpdate();
        }
    }

    protected void onConnectionUpdate() {
        World world = world();
        if (world != null && !world.isRemote) {
            factory.onConnectionUpdate(this, world, pos());
            if (tile() != null) tile().notifyPartChange(this);
        }
    }

    /////////////////////////////// COLLISION BOXES ////////////////////////////////////////

    protected void reinitShape() {
        centerBox = PipeLikeObjectFactory.getSideBox(null, baseProperty.getThickness());
        reinitSides();
    }

    protected void reinitSides() {
        sideBoxes.clear();
        float thickness = baseProperty.getThickness();
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (0 != (renderMask & PipeLikeRenderer.MASK_FORMAL_CONNECTION << facing.getIndex())) {
                sideBoxes.add(PipeLikeObjectFactory.getSideBox(facing, thickness));
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

    ///////////////////////////////// SYNCHRONIZE //////////////////////////////////////////

    @Override
    public void onAdded() {
        super.onAdded();
        updateInternalConnection();
        //TODO attach into network
    }

    @Override
    public void onRemoved() {
        //TODO detach from network
    }

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
    public void onNeighborChanged() {
        updateRenderMask(false);
        scheduleTick(1);
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

    //use mutable pos for frequent side check
    protected ThreadLocal<BlockPos.MutableBlockPos> mutablePos = ThreadLocal.withInitial(() -> new BlockPos.MutableBlockPos(pos()));

    @Nullable
    @Override
    public ICapabilityProvider getCapabilityProviderAtSide(@Nonnull EnumFacing facing) {
        BlockPos.MutableBlockPos pos = mutablePos.get();
        pos.move(facing);
        World world = world();
        ICapabilityProvider result = world == null ? null : world.getTileEntity(pos);
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
}
