package gregtech.common.cable.tile;

import gregtech.api.capability.IEnergyContainer;
import gregtech.common.cable.*;
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

public class TileEntityCable extends TileEntity implements ICableTile {

    public static final int DEFAULT_INSULATION_COLOR = 0x777777;

    private IBlockState cableState;
    private int blockedConnections = 0;
    private int insulationColor = DEFAULT_INSULATION_COLOR;

    private IBlockState getCableState() {
        if(cableState == null) {
            cableState = getWorld().getBlockState(getPos());
        }
        return cableState;
    }

    @Override
    public World getCableWorld() {
        return getWorld();
    }

    @Override
    public BlockPos getCablePos() {
        return getPos();
    }

    public int getBlockedConnections() {
        return blockedConnections;
    }

    public void setBlockedConnections(int blockedConnections) {
        this.blockedConnections = blockedConnections;
        if(!getWorld().isRemote) {
            BlockCable.updateCableConnections(this, getWorld(), getPos());
            updateClientState();
            markDirty();
        }
    }

    @Override
    public int getInsulationColor() {
        return insulationColor;
    }

    @Override
    public Insulation getInsulation() {
        return getCableState().getValue(BlockCable.INSULATION);
    }

    @Override
    public WireProperties getWireProperties() {
        return ((BlockCable) getCableState().getBlock()).getProperties(getInsulation());
    }

    public void setInsulationColor(int insulationColor) {
        this.insulationColor = insulationColor;
        if(!getWorld().isRemote) {
            BlockCable.updateCableConnections(this, getWorld(), getPos());
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

    private IEnergyContainer getEnergyContainer() {
        if(energyContainer == null) {
            energyContainer = new CableEnergyContainer(this);
        }
        return energyContainer;
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
