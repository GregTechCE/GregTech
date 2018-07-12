package gregtech.api.pipelike;

import gregtech.api.unification.material.type.Material;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityPipeLike<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends ITileProperty, T extends ITilePipeLike<Q, P>, C> extends TileEntity implements ITilePipeLike<Q, P> {

    public final PipeLikeObjectFactory<Q, P, T, C> factory;

    private IBlockState blockState;
    private Material material;
    private Q baseProperty;
    private P tileProperty;
    private int color;
    private int internalConnections = 0;

    protected TileEntityPipeLike(PipeLikeObjectFactory<Q, P, T, C> factory) {
        this.factory = factory;
        color = factory.getDefaultColor();
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
        return tileProperty == null ? (tileProperty = ((BlockPipeLike<Q, P, T, C>) getBlockState().getBlock()).getActualProperty(getBaseProperty())) : tileProperty;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Material getMaterial() {
        return material == null ? (material = ((BlockPipeLike<Q, P, T, C>) getBlockState().getBlock()).material) : material;
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
        onConnectionUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        color = compound.getInteger("color");
        internalConnections = compound.getInteger("internalConnections");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("color", color);
        compound.setInteger("internalConnections", internalConnections);
        return compound;
    }

    public void initFromItemStackData(NBTTagCompound compound) {//TODO Covers

    }

    public void writeItemStackData(NBTTagCompound compound) {//TODO Covers

    }

    @Override
    public void updateInternalConnection() {
        //TODO Covers
        //TODO Check active
    }

    protected void onConnectionUpdate() {
        if (!world.isRemote) {
            factory.onConnectionUpdate(this, world, pos);
            IBlockState blockState = getBlockState();
            //send only tile entity sync packet
            getWorld().notifyBlockUpdate(getPos(), blockState, blockState, 0b00010);
            markDirty();
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
        pos.move(facing.getOpposite());
        return result;
    }
}
