package gregtech.common.cable.tile;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.common.cable.ICableTile;
import gregtech.common.cable.RoutePath;
import gregtech.api.capability.IEnergyContainer;
import gregtech.common.cable.net.EnergyNet;
import gregtech.common.cable.net.WorldENet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class CableEnergyContainer implements IEnergyContainer {

    private final ICableTile tileEntityCable;
    private long lastCachedUpdate;
    private List<RoutePath> pathsCache;

    public CableEnergyContainer(ICableTile tileEntityCable) {
        this.tileEntityCable = tileEntityCable;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        List<RoutePath> paths = getPaths();
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
        return amperesUsed;
    }

    private long dispatchEnergyToNode(BlockPos nodePos, long voltage, long amperage) {
        long amperesUsed = 0L;
        //use pooled mutable to avoid creating new objects every tick
        World world = tileEntityCable.getCableWorld();
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        for(EnumFacing facing : EnumFacing.VALUES) {
            blockPos.setPos(nodePos).move(facing);
            //do not allow cables to load chunks
            if(!world.isBlockLoaded(nodePos)) continue;
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if(tileEntity == null || tileEntity instanceof TileEntityCable) continue;
            IEnergyContainer energyContainer = tileEntity.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
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
        return tileEntityCable.getWireProperties().amperage;
    }

    @Override
    public long getInputVoltage() {
        return tileEntityCable.getWireProperties().voltage;
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

    private void recomputePaths(EnergyNet energyNet) {
        this.lastCachedUpdate = energyNet.getLastUpdate();
        this.pathsCache = energyNet.computePatches(tileEntityCable.getCablePos());
    }

    private List<RoutePath> getPaths() {
        EnergyNet energyNet = getEnergyNet();
        if(energyNet == null) {
            return Collections.emptyList();
        }
        if(pathsCache == null || energyNet.getLastUpdate() > lastCachedUpdate) {
            recomputePaths(energyNet);
        }
        return pathsCache;
    }

    private EnergyNet getEnergyNet() {
        WorldENet worldENet = WorldENet.getWorldENet(tileEntityCable.getCableWorld());
        return worldENet.getNetFromPos(tileEntityCable.getCablePos());
    }

    @Override
    public boolean isOneProbeHidden() {
        return true;
    }
}
