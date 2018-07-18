package gregtech.api.pipelike;

import gregtech.api.block.machines.BlockMachine;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.type.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityPipeLike<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C> extends TileEntity implements ITilePipeLike<Q, P> {

    private PipeFactory<Q, P, C> factory;

    private IBlockState blockState;
    private Material material;
    private Q baseProperty;
    private P tileProperty;
    private int color;
    private int internalConnections = 0;
    private int renderMask = 0;

    /**
     * Dangerous constructor. Must set the {@link #factory} after initialized.
     * Called by {@link TileEntity#create(World, NBTTagCompound)}
     */
    public TileEntityPipeLike(){}

    protected TileEntityPipeLike(PipeFactory<Q, P, C> factory) {
        this.factory = factory;
        color = factory.getDefaultColor();
    }

    @Override
    public PipeFactory<Q, P, C> getFactory() {
        return factory;
    }

    protected IBlockState getBlockState() {
        return blockState == null ? (blockState = getWorld().getBlockState(pos)) : blockState;
    }

    @Override
    public Q getBaseProperty() {
        return baseProperty == null ? (baseProperty = getBlockState().getValue(factory.baseProperty)) : baseProperty;
    }

    @SuppressWarnings("unchecked")
    @Override
    public P getTileProperty() {
        return tileProperty == null ? (tileProperty = ((BlockPipeLike<Q, P, C>) getBlockState().getBlock()).getActualProperty(getBaseProperty())) : tileProperty;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Material getMaterial() {
        return material == null ? (material = ((BlockPipeLike<Q, P, C>) getBlockState().getBlock()).material) : material;
    }

    @Override
    public int getInternalConnections() {
        return internalConnections;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        updateRenderMask(false);
        if (!world.isRemote) {
            IBlockState blockState = getBlockState();
            world.notifyBlockUpdate(pos, blockState, blockState, 0b00010);
        }
        updateNode();
        markDirty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        factory = PipeFactory.allFactories.get(compound.getString("factory"));
        color = compound.getInteger("color");
        internalConnections = compound.getInteger("internalConnections");
        renderMask = compound.getInteger("renderMask");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("factory", factory.name);
        compound.setInteger("color", color);
        compound.setInteger("internalConnections", internalConnections);
        compound.setInteger("renderMask", renderMask);
        return compound;
    }

    public void initFromItemStackData(NBTTagCompound compound) {//TODO Covers

    }

    public void writeItemStackData(NBTTagCompound compound) {//TODO Covers

    }

    @Override
    public void updateInternalConnection() {
        /*int lastValue = internalConnections;
        internalConnections = 0;
        //TODO Covers
        lastValue ^= internalConnections;
        if ((lastValue & 0b111111_111111_000000_000000) != 0) updateRenderMask();
        if ((lastValue & 0b111111_000000_111111_111111) != 0) updateNode();
        if ((lastValue & 0b111111_000000_000000_000000) != 0) markDirty();*/
    }

    protected void updateRenderMask() {
        updateRenderMask(true);
    }

    protected void updateRenderMask(boolean update) {
        int lastValue = renderMask;
        renderMask = factory.getRenderMask(this, world, pos);
        lastValue ^= renderMask;

        if (update && lastValue != 0 && !world.isRemote) {
            IBlockState blockState = getBlockState();
            world.notifyBlockUpdate(pos, blockState, blockState, 0b00010);
        }
    }

    protected void updateNode() {
        if (!world.isRemote) {
            factory.updateNode(world, pos, this);
        }
    }

    ///////////////////////////////// SYNCHRONIZING ////////////////////////////////////////

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        handleUpdateTag(pkt.getNbtCompound());
        getWorld().markBlockRangeForRenderUpdate(getPos().add(-1, -1, -1), getPos().add(1, 1, 1));
    }

    ///////////////////////////////// CAPABILITIES /////////////////////////////////////////

    private C networkCapability;
    private C getNetworkCapability() {
        return networkCapability == null ? (networkCapability = factory.createCapability(this)) : networkCapability;
    }

    public boolean hasCapabilityInternal(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == factory.capability || super.hasCapability(capability, facing);
    }

    public <U> U getCapabilityInternal(@Nonnull Capability<U> capability, @Nullable EnumFacing facing) {
        if (capability == factory.capability) {
            return factory.capability.cast(getNetworkCapability());
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return filterHasCapabilityResult(capability, facing, hasCapabilityInternal(capability, facing), false);
    }

    @Nullable
    @Override
    public <U> U getCapability(@Nonnull Capability<U> capability, @Nullable EnumFacing facing) {
        return filterGetCapabilityResult(capability, facing, getCapabilityInternal(capability, facing), false);
    }

    //use mutable pos for frequent side check
    protected ThreadLocal<BlockPos.MutableBlockPos> mutablePos = ThreadLocal.withInitial(() -> new BlockPos.MutableBlockPos(pos));

    @Nullable
    @Override
    public ICapabilityProvider getCapabilityProviderAtSide(@Nonnull EnumFacing facing) {
        BlockPos.MutableBlockPos pos = mutablePos.get();
        pos.move(facing);
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
}
