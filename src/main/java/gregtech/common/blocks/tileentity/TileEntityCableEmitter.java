package gregtech.common.blocks.tileentity;

import com.google.common.collect.ImmutableList;
import gregtech.api.capability.IEnergyContainer;
import gregtech.common.blocks.BlockCable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileEntityCableEmitter extends TileEntity implements IEnergyContainer, ITickable {

    private static class ConnectionInfo {
        public final TileEntity receiverContainer;
        public final List<CachedCableEntry> path;

        private long cableAmperage;
        private long cableVoltage;
        private long totalTransferLoss;

        public long getCableAmperage() {
            return cableAmperage;
        }

        public long getCableVoltage() {
            return cableVoltage;
        }

        public long getTotalTransferLoss() {
            return totalTransferLoss;
        }

        public ConnectionInfo(TileEntity receiverContainer, List<CachedCableEntry> path) {
            this.receiverContainer = receiverContainer;
            this.path = ImmutableList.copyOf(path);
            for(CachedCableEntry cachedCableEntry : this.path) {
                this.totalTransferLoss += cachedCableEntry.cableLoss;
                this.cableVoltage = Math.min(this.cableVoltage, cachedCableEntry.maxVoltage);
                this.cableAmperage = Math.min(this.cableAmperage, cachedCableEntry.maxAmperage);
            }
        }
    }

    public static class CachedCableEntry {
        public final long maxVoltage;
        public final long maxAmperage;
        public final long cableLoss;
        public final int posX, posY, posZ;

        public CachedCableEntry(BlockCable blockCable, BlockPos pos) {
            this.maxVoltage = blockCable.maxVoltage;
            this.maxAmperage = blockCable.maxAmperage;
            this.cableLoss = blockCable.cableLoss;
            this.posX = pos.getX();
            this.posY = pos.getY();
            this.posZ = pos.getZ();
        }
    }

    public final Set<ConnectionInfo> outgoingConnections = new HashSet<>();

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {

        return 0L;
    }


    private boolean shouldRefreshConnections;

    public void refreshConnections() {
        this.shouldRefreshConnections = true;
    }

    @Override
    public void update() {
        if(this.shouldRefreshConnections) {
            this.shouldRefreshConnections = false;
            this.outgoingConnections.clear();

        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        this.outgoingConnections.clear();
        this.shouldRefreshConnections = false;
    }

    @Override
    public void validate() {
        super.validate();
        this.shouldRefreshConnections = true;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER) {
            return IEnergyContainer.CAPABILITY_ENERGY_CONTAINER.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public long getEnergyStored() {
        return 0L;
    }

    @Override
    public void addEnergy(long energyToAdd) {
        
    }

    public void setEnergyStored(long energyStored) {
        long voltage = getInputVoltage();
        acceptEnergyFromNetwork(null, voltage, energyStored / voltage);
    }

    @Override
    public long getEnergyCapacity() {
        return getInputVoltage() * getInputAmperage();
    }

    @Override
    public long getInputAmperage() {
        return ((BlockCable) getBlockType()).maxAmperage;
    }

    @Override
    public long getInputVoltage() {
        return ((BlockCable) getBlockType()).maxVoltage;
    }
}
