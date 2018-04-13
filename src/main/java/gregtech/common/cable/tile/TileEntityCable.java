package gregtech.common.cable.tile;

import gregtech.api.capability.IEnergyContainer;
import gregtech.common.cable.BlockCable;
import gregtech.common.cable.RoutePath;
import gregtech.common.cable.WireProperties;
import gregtech.common.cable.net.EnergyNet;
import gregtech.common.cable.net.WorldENet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityCable extends TileEntity {

    public static final int DEFAULT_INSULATION_COLOR = 0x777777;

    private IBlockState cableState;
    private int blockedConnections = 0;
    private int insulationColor = DEFAULT_INSULATION_COLOR;

    public WireProperties getCableProperties() {
        IBlockState blockState = getCableState();
        return ((BlockCable) blockState.getBlock()).getProperties(blockState.getValue(BlockCable.INSULATION));
    }

    public IBlockState getCableState() {
        if(cableState == null) {
            cableState = getWorld().getBlockState(getPos());
        }
        return cableState;
    }

    public int getBlockedConnections() {
        return blockedConnections;
    }

    public void setBlockedConnections(int blockedConnections) {
        this.blockedConnections = blockedConnections;
        if(!getWorld().isRemote) {
            BlockCable.updateCableConnections(getWorld(), getPos());
            updateClientState();
            markDirty();
        }
    }

    public int getInsulationColor() {
        return insulationColor;
    }

    public void setInsulationColor(int insulationColor) {
        this.insulationColor = insulationColor;
        if(!getWorld().isRemote) {
            BlockCable.updateCableConnections(getWorld(), getPos());
            updateClientState();
            markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.blockedConnections = compound.getInteger("BlockedConnections");
        this.insulationColor = compound.getInteger("InsulationColor");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BlockedConnections", blockedConnections);
        compound.setInteger("InsulationColor", insulationColor);
        return compound;
    }

    ///////////////////////////////// SYNCING SHIT /////////////////////////
    //Why vanilla syncing system is so fucking dumb?

    public void updateClientState() {
        IBlockState blockState = getCableState();
        //send only tile entity sync packet
        getWorld().notifyBlockUpdate(getPos(), blockState, blockState, 0b00010);
    }

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

    ///////////////////////////////// E-NET EMITTING AND PATHS STUFF /////////////////////////

    private IEnergyContainer energyContainer;
    private long lastCachedPathsTime;
    private List<RoutePath> pathsCache;

    private IEnergyContainer getEnergyContainer() {
        if(energyContainer == null) {
            energyContainer = new CableEnergyContainer(this);
        }
        return energyContainer;
    }

    private void recomputePaths(EnergyNet energyNet) {
        this.lastCachedPathsTime = System.currentTimeMillis();
        this.pathsCache = energyNet.computePatches(getPos());
    }

    public List<RoutePath> getPaths() {
        EnergyNet energyNet = getEnergyNet();
        if(pathsCache == null || energyNet.getLastUpdatedTime() > lastCachedPathsTime) {
            recomputePaths(energyNet);
        }
        return pathsCache;
    }

    public EnergyNet getEnergyNet() {
        WorldENet worldENet = WorldENet.getWorldENet(getWorld());
        return worldENet.getNetFromPos(getPos());
    }


    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER) {
            return IEnergyContainer.CAPABILITY_ENERGY_CONTAINER.cast(getEnergyContainer());
        }
        return super.getCapability(capability, facing);
    }
}
