package gregtech.api.cable.tile;

import gregtech.api.cable.BlockCable;
import gregtech.api.cable.RoutePath;
import gregtech.api.cable.WireProperties;
import gregtech.api.cable.net.EnergyNet;
import gregtech.api.cable.net.WorldENet;
import gregtech.api.capability.IEnergyContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityCable extends TileEntity {

    private IEnergyContainer energyContainer;
    private IBlockState cableState;

    private List<RoutePath> pathsCache;
    private long lastCachedPathsTime;

    public TileEntityCable() {
        this.energyContainer = new CableEnergyContainer(this);
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

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER) {
            return IEnergyContainer.CAPABILITY_ENERGY_CONTAINER.cast(energyContainer);
        }
        return super.getCapability(capability, facing);
    }
}
