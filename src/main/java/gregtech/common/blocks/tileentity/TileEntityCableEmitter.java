package gregtech.common.blocks.tileentity;

import com.google.common.collect.ImmutableList;
import gregtech.api.capability.IEnergyContainer;
import gregtech.common.blocks.BlockCable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;

public class TileEntityCableEmitter extends TileEntity implements IEnergyContainer {

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

    private static class CachedCableEntry {
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
        List<IEnergyContainer> containers = new ArrayList<>();
        outgoingConnections.forEach(connectionInfo -> {
            if (voltage > connectionInfo.getCableVoltage() && amperage > connectionInfo.getCableAmperage()) {
                connectionInfo.path.forEach(cableEntry -> burnCable(cableEntry, entry -> entry.maxVoltage < voltage && entry.maxAmperage < amperage));
            } else if (voltage > connectionInfo.getCableVoltage()) {
                connectionInfo.path.forEach(cableEntry -> burnCable(cableEntry, entry -> entry.maxVoltage < voltage));
            } else if (amperage > connectionInfo.getCableAmperage()) {
                connectionInfo.path.forEach(cableEntry -> burnCable(cableEntry, entry -> entry.maxAmperage < amperage));
            } else {
                IEnergyContainer container = connectionInfo.receiverContainer.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, side);
                if (container != null) {
                    containers.add(container);
                }
            }
        });
        // TODO transfer loss???
        long amperesUsed = 0;
        for (IEnergyContainer container : containers) {
            amperesUsed += container.acceptEnergyFromNetwork(null, voltage, amperage);
        }
        return amperesUsed;
    }

    protected void burnCable(CachedCableEntry cableEntry, Predicate<CachedCableEntry> burnPredicate) {
        if (burnPredicate.test(cableEntry)) {
            // burn baby, burn
            this.getWorld().setBlockState(new BlockPos(cableEntry.posX, cableEntry.posY, cableEntry.posZ), Blocks.FIRE.getDefaultState());
        }
    }

    public void refreshConnections() {
        this.outgoingConnections.clear();
        PooledMutableBlockPos currentPos = PooledMutableBlockPos.retain(getPos());
        List<BlockPos> visited = new ArrayList<>();
        Stack<EnumFacing> moveStack = new Stack<>();
        Stack<CachedCableEntry> pathStack = new Stack<>();
        while(true) {
            for(EnumFacing facing : EnumFacing.VALUES) {
                currentPos.move(facing);
                if (!visited.contains(currentPos)) {
                    visited.add(currentPos);
                } else {
                    break;
                }
                EnumFacing opposite = facing.getOpposite();
                IBlockState blockStateAt = world.getBlockState(currentPos);
                if(blockStateAt.getBlock() instanceof BlockCable) {
                    //if it is cable, move forward and add opposite direction to move stack
                    moveStack.push(opposite);
                    pathStack.push(new CachedCableEntry((BlockCable) blockStateAt.getBlock(), currentPos));
                    continue;
                } else if(blockStateAt.getBlock().hasTileEntity(blockStateAt)) {
                    //if it is tile entity container, check if tile entity has IEnergyContainer and then add it to connections
                    TileEntity tileEntity = world.getTileEntity(currentPos);
                    IEnergyContainer container = tileEntity == null ? null : tileEntity.getCapability(
                        IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, opposite);
                    if(container != null && container.inputsEnergy(opposite)) {
                        ConnectionInfo connectionInfo = new ConnectionInfo(tileEntity, pathStack);
                        this.outgoingConnections.add(connectionInfo);
                    }
                }
                //move back if we aren't cable and din't continue
                currentPos.move(opposite);
            }
            //if we didn't found any cable, go back, or return
            if(!moveStack.isEmpty()) {
                currentPos.move(moveStack.pop());
            } else break;
        }
        currentPos.release();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        this.outgoingConnections.clear();
    }

    @Override
    public void validate() {
        super.validate();
        this.refreshConnections();
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
