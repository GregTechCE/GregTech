package gregtech.api.cable.tile;

import gregtech.api.cable.RoutePath;
import gregtech.api.capability.IEnergyContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CableEnergyContainer implements IEnergyContainer {

    private final TileEntityCable tileEntityCable;

    public CableEnergyContainer(TileEntityCable tileEntityCable) {
        this.tileEntityCable = tileEntityCable;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        List<RoutePath> paths = tileEntityCable.getPaths();
        long amperesUsed = 0;
        for(RoutePath routePath : paths) {
            if(routePath.totalLoss >= voltage)
                continue; //do not emit if loss is too high
            if(voltage > routePath.minVoltage || amperage > routePath.minAmperage) {
                //if voltage or amperage is too big, burn cables down and break
                routePath.burnCablesInPath(tileEntityCable.getWorld(), voltage, amperage);
                break;
            }
            amperesUsed += dispatchEnergyToNode(routePath.destination,
                voltage - routePath.totalLoss, amperage - amperesUsed);
            if(amperesUsed == amperage)
                break; //do not continue if all amperes are exhausted
        }
        return amperesUsed;
    }

    private long dispatchEnergyToNode(BlockPos nodePos, long voltage, long amperage) {
        long amperesUsed = 0L;
        //use pooled mutable to avoid creating new objects every tick
        World world = tileEntityCable.getWorld();
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        for(EnumFacing facing : EnumFacing.VALUES) {
            blockPos.setPos(nodePos).move(facing);
            //do not allow cables to load chunks
            if(!world.isBlockLoaded(nodePos)) continue;
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if(tileEntity == null) continue;
            IEnergyContainer energyContainer = tileEntity.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, null);
            if(energyContainer == null) continue;
            amperesUsed += energyContainer.acceptEnergyFromNetwork(facing.getOpposite(), voltage, amperage - amperesUsed);
            if(amperesUsed == amperage)
                break;
        }
        blockPos.release();
        return amperesUsed;
    }

    @Override
    public long getInputAmperage() {
        return tileEntityCable.getCableProperties().amperage;
    }

    @Override
    public long getInputVoltage() {
        return tileEntityCable.getCableProperties().voltage;
    }

    @Override
    public long getEnergyCapacity() {
        return getInputVoltage() * getInputAmperage();
    }

    @Override
    public void addEnergy(long energyToAdd) {
        //just a fallback case if somebody will call this method
        acceptEnergyFromNetwork(EnumFacing.UP,
            energyToAdd / getInputVoltage(),
            energyToAdd / getInputAmperage());
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public long getEnergyStored() {
        return 0;
    }

}
