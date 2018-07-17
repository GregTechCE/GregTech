package gregtech.common.pipelike;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.common.cable.RoutePath;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class CableEnergyContainer implements IEnergyContainer {

    private final ITilePipeLike<Insulation, WireProperties> tileEntityCable;
    private long lastCachedPathsTime;
    private List<RoutePath> pathsCache;

    public CableEnergyContainer(ITilePipeLike<Insulation, WireProperties> tileEntityCable) {
        this.tileEntityCable = tileEntityCable;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        /*List<RoutePath> paths = getPaths();
        long amperesUsed = 0;
        for(RoutePath routePath : paths) {
            if(routePath.totalLoss >= voltage)
                continue; //do not emit if loss is too high
            if(voltage > routePath.minVoltage || amperage > routePath.minAmperage) {
                //if voltage or amperage is too big, burn cables down and break
                routePath.burnCablesInPath(tileEntityCable.getCableWorld(), voltage, amperage);
                break;
            }
            amperesUsed += dispatchEnergyToNode(routePath.destination,
                voltage - routePath.totalLoss, amperage - amperesUsed);
            if(amperesUsed == amperage)
                break; //do not continue if all amperes are exhausted
        }
        return amperesUsed;*/
        return 0;
    }

    private long dispatchEnergyToNode(BlockPos nodePos, long voltage, long amperage) {
        /*long amperesUsed = 0L;
        //use pooled mutable to avoid creating new objects every tick
        World world = tileEntityCable.getCableWorld();
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        for(EnumFacing facing : EnumFacing.VALUES) {
            blockPos.setPos(nodePos).move(facing);
            //do not allow cables to load chunks
            if(!world.isBlockLoaded(nodePos)) continue;
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if(tileEntity == null || tileEntity instanceof TileEntityCable) continue;
            IEnergyContainer energyContainer = tileEntity.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, null);
            if(energyContainer == null) continue;
            amperesUsed += energyContainer.acceptEnergyFromNetwork(facing.getOpposite(), voltage, amperage - amperesUsed);
            if(amperesUsed == amperage)
                break;
        }
        blockPos.release();
        return amperesUsed;*/
        return 0;
    }

    @Override
    public long getInputAmperage() {
        return tileEntityCable.getTileProperty().getAmperage();
    }

    @Override
    public long getInputVoltage() {
        return tileEntityCable.getTileProperty().getVoltage();
    }

    @Override
    public long getEnergyCapacity() {
        return getInputVoltage() * getInputAmperage();
    }

    @Override
    public long addEnergy(long energyToAdd) {
        //just a fallback case if somebody will call this method
        return acceptEnergyFromNetwork(EnumFacing.UP,
            energyToAdd / getInputVoltage(),
            energyToAdd / getInputAmperage()) * getInputVoltage();
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

    /*private void recomputePaths(EnergyNet energyNet) {
        this.lastCachedPathsTime = System.currentTimeMillis();
        this.pathsCache = energyNet.computePatches(tileEntityCable.getPos());
    }

    private List<RoutePath> getPaths() {
        EnergyNet energyNet = getEnergyNet();
        if(pathsCache == null || energyNet.getLastUpdatedTime() > lastCachedPathsTime) {
            recomputePaths(energyNet);
        }
        return pathsCache;
    }

    private EnergyNet getEnergyNet() {
        WorldENet worldENet = WorldENet.getWorldENet(tileEntityCable.getCableWorld());
        return worldENet.getNetFromPos(tileEntityCable.getCablePos());
    }*/

}
