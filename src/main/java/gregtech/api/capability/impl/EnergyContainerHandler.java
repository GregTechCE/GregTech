package gregtech.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.util.GTUtility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EnergyContainerHandler extends MTETrait implements IEnergyContainer {

    private final long maxCapacity;
    private long energyStored;

    private final long maxInputVoltage;
    private final long maxInputAmperage;

    private final long maxOutputVoltage;
    private final long maxOutputAmperage;

    public EnergyContainerHandler(long maxCapacity, long maxInputVoltage, long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage) {
        this.maxCapacity = maxCapacity;
        this.maxInputVoltage = maxInputVoltage;
        this.maxInputAmperage = maxInputAmperage;
        this.maxOutputVoltage = maxOutputVoltage;
        this.maxOutputAmperage = maxOutputAmperage;
    }

    @Override
    public String getName() {
        return "EnergyContainer";
    }

    @Nullable
    @Override
    public Capability<?> getImplementingCapability() {
        return IEnergyContainer.CAPABILITY_ENERGY_CONTAINER;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setLong("EnergyStored", energyStored);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        this.energyStored = compound.getLong("EnergyStored");
    }

    @Override
    public long getEnergyStored() {
        return this.energyStored;
    }

    @Override
    public void setEnergyStored(long energyStored) {
        this.energyStored = energyStored;
        if(!metaTileEntity.getWorld().isRemote) {
            metaTileEntity.markDirty();
            writeCustomData(0, buf -> buf.writeLong(energyStored));
        }
    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        buffer.writeLong(this.energyStored);
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        this.energyStored = buffer.readLong();
    }

    @Override
    public void receiveCustomData(int id, PacketBuffer buffer) {
        if(id == 0) {
            this.energyStored = buffer.readLong();
        }
    }

    @Override
    public void update() {
        if (getMetaTileEntity().getWorld().isRemote)
            return;
        if(getEnergyStored() >= getOutputVoltage() && getOutputVoltage() > 0 && getOutputAmperage() > 0) {
            long outputVoltage = getOutputVoltage();
            long outputAmperes = Math.min(getEnergyStored() / outputVoltage, getOutputAmperage());
            if(outputAmperes == 0) return;
            long amperesUsed = 0;
            for(EnumFacing side : EnumFacing.VALUES) {
                if(!outputsEnergy(side)) continue;
                TileEntity tileEntity = metaTileEntity.getWorld().getTileEntity(metaTileEntity.getPos().offset(side));
                EnumFacing oppositeSide = side.getOpposite();
                if(tileEntity != null && tileEntity.hasCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, oppositeSide)) {
                    IEnergyContainer energyContainer = tileEntity.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, oppositeSide);
                    if(energyContainer.inputsEnergy(oppositeSide)) continue;
                    amperesUsed += energyContainer.acceptEnergyFromNetwork(oppositeSide, outputVoltage, outputAmperes - amperesUsed);
                    if(amperesUsed == outputAmperes) break;
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
                BlockPos pos = metaTileEntity.getPos();
                metaTileEntity.getWorld().setBlockToAir(pos);
                //TODO config option for explosions
                metaTileEntity.getWorld().createExplosion(null,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    GTUtility.getTierByVoltage(voltage), true);
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
    public long getEnergyCapacity() {
        return this.maxCapacity;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return getInputVoltage() > 0;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return getOutputVoltage() > 0;
    }

    @Override
    public long getOutputVoltage() {
        return this.maxOutputVoltage;
    }

    @Override
    public long getOutputAmperage() {
        return this.maxOutputAmperage;
    }

    @Override
    public long getInputAmperage() {
        return this.maxInputAmperage;
    }

    @Override
    public long getInputVoltage() {
        return this.maxInputVoltage;
    }
}
