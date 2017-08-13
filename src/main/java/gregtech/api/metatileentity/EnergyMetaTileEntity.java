package gregtech.api.metatileentity;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.util.GT_Utility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public abstract class EnergyMetaTileEntity extends PaintableMetaTileEntity implements IEnergyContainer {

    protected long energyStored;

    public EnergyMetaTileEntity(IMetaTileEntityFactory factory) {
        super(factory);
    }

    @Override
    public <T> boolean hasCapability(Capability<T> capability, EnumFacing side) {
        return super.hasCapability(capability, side) || capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if(capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER) {
            return IEnergyContainer.CAPABILITY_ENERGY_CONTAINER.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void saveNBTData(NBTTagCompound data) {
        super.saveNBTData(data);
        data.setLong("Energy", this.energyStored);
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        super.loadNBTData(data);
        this.energyStored = data.getLong("Energy");
    }

    @Override
    public void onPreTick(long tickTimer) {
        super.onPreTick(tickTimer);
        if(getEnergyStored() >= getOutputVoltage() && getOutputVoltage() > 0 && getOutputAmperage() > 0) {
            long outputVoltage = getOutputVoltage();
            long outputAmperes = Math.min(getEnergyStored() / outputVoltage, getOutputAmperage());
            long amperesUsed = 0;
            for(EnumFacing side : EnumFacing.VALUES) {
                if(outputsEnergy(side)) {
                    TileEntity tileEntity = getWorldObj().getTileEntity(getWorldPos().offset(side));
                    EnumFacing oppositeSide = side.getOpposite();
                    if(tileEntity != null && tileEntity.hasCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, oppositeSide)) {
                        IEnergyContainer energyContainer = tileEntity.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, oppositeSide);
                        amperesUsed += energyContainer.acceptEnergyFromNetwork(oppositeSide, outputVoltage, outputAmperes - amperesUsed);
                        if(amperesUsed == outputAmperes) {
                            break;
                        }
                    }
                }
            }
            if(amperesUsed > 0) {
                setEnergyStored(getEnergyStored() - amperesUsed * outputVoltage);
            }
        }
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        long canAccept = getEnergyCapacity() - getEnergyStored();
        if(inputsEnergy(side) || side == null) {
            if(voltage > getInputVoltage()) {
                doExplosion(GT_Utility.getTier(voltage));
                return Math.min(amperage, getInputAmperage());
            }
            if(canAccept >= voltage) {
                long amperesAccepted = Math.min(canAccept / voltage, Math.min(amperage, getInputAmperage()));
                if(amperesAccepted > 0) {
                    setEnergyStored(getEnergyStored() + voltage * amperesAccepted);
                    return amperesAccepted;
                }
            }
        }
        return 0;
    }

    @Override
    public long getEnergyStored() {
        return energyStored;
    }

    @Override
    public void setEnergyStored(long energyStored) {
        this.energyStored = energyStored;
        if(!getWorldObj().isRemote) {
            markDirty();
            holder.writeCustomData(4, buf -> buf.writeLong(energyStored));
        }
    }

}
