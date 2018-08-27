package gregtech.common.pipelike.cable.tile;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.common.pipelike.cable.*;
import gregtech.common.pipelike.cable.net.WorldENet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityCable extends TileEntityPipeBase<Insulation, WireProperties> {

    private IEnergyContainer energyContainer;

    private IEnergyContainer getEnergyContainer() {
        if(energyContainer == null) {
            energyContainer = new CableEnergyContainer(this);
        }
        return energyContainer;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) {
            return GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER.cast(getEnergyContainer());
        }
        return super.getCapability(capability, facing);
    }

}
