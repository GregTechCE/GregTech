package gregtech.common.pipelike;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.worldentries.PipeNet;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class CableEnergyContainer implements IEnergyContainer {

    final ITilePipeLike<Insulation, WireProperties> tileEntityCable;
    long lastCachedPathsTime = 0;
    List<PipeNet.RoutePath<WireProperties, ?, Long>> pathsCache;

    public CableEnergyContainer(ITilePipeLike<Insulation, WireProperties> tileEntityCable) {
        this.tileEntityCable = tileEntityCable;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        EnergyNet net = getEnergyNet();
        return net != null ? net.acceptEnergy(this, voltage, amperage, side) : 0;
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
        return Long.MAX_VALUE;
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

    private EnergyNet getEnergyNet() {
        return CableFactory.INSTANCE.getPipeNetAt(tileEntityCable);
    }

    public long[] getAverageData() {
        EnergyNet net = getEnergyNet();
        if (net != null) return net.getStatisticData(tileEntityCable.getTilePos());
        return EnergyNet.NO_DATA;
    }

}
