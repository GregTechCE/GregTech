package gregtech.api.capability.impl;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class EnergyContainerHandler extends MTETrait implements IEnergyContainer {

    private final long maxCapacity;
    private long energyStored;

    private final long maxInputVoltage;
    private final long maxInputAmperage;

    private final long maxOutputVoltage;
    private final long maxOutputAmperage;

    private Predicate<EnumFacing> sideInputCondition;
    private Predicate<EnumFacing> sideOutputCondition;

    public EnergyContainerHandler(MetaTileEntity tileEntity, long maxCapacity, long maxInputVoltage, long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage) {
        super(tileEntity);
        this.maxCapacity = maxCapacity;
        this.maxInputVoltage = maxInputVoltage;
        this.maxInputAmperage = maxInputAmperage;
        this.maxOutputVoltage = maxOutputVoltage;
        this.maxOutputAmperage = maxOutputAmperage;
    }

    public void setSideInputCondition(Predicate<EnumFacing> sideInputCondition) {
        this.sideInputCondition = sideInputCondition;
    }

    public void setSideOutputCondition(Predicate<EnumFacing> sideOutputCondition) {
        this.sideOutputCondition = sideOutputCondition;
    }

    public static EnergyContainerHandler emitterContainer(MetaTileEntity tileEntity, long maxCapacity, long maxOutputVoltage, long maxOutputAmperage) {
        return new EnergyContainerHandler(tileEntity, maxCapacity, 0L, 0L, maxOutputVoltage, maxOutputAmperage);
    }

    public static EnergyContainerHandler receiverContainer(MetaTileEntity tileEntity, long maxCapacity, long maxInputVoltage, long maxInputAmperage) {
        return new EnergyContainerHandler(tileEntity, maxCapacity, maxInputVoltage, maxInputAmperage, 0L, 0L);
    }

    @Override
    public String getName() {
        return "EnergyContainer";
    }

    @Nullable
    @Override
    public Capability<?> getImplementingCapability() {
        return GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER;
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

    public void dischargeEnergyContainers(IItemHandlerModifiable itemHandler, int slotIndex) {
        ItemStack stackInSlot = itemHandler.getStackInSlot(slotIndex);
        if(stackInSlot.isEmpty()) return;
        stackInSlot = stackInSlot.copy();
        IElectricItem electricItem = stackInSlot.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if(electricItem == null || !electricItem.canProvideChargeExternally()) return;
        int machineTier = GTUtility.getTierByVoltage(Math.max(getInputVoltage(), getOutputVoltage()));
        if(getEnergyCanBeInserted() > 0) {
            long dischargedBy = electricItem.discharge(getEnergyCanBeInserted(), machineTier, false, true, false);
            if(dischargedBy == 0L) return;
            itemHandler.setStackInSlot(slotIndex, stackInSlot);
            addEnergy(dischargedBy);
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
                if(tileEntity != null && tileEntity.hasCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, oppositeSide)) {
                    IEnergyContainer energyContainer = tileEntity.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, oppositeSide);
                    if(energyContainer == null || !energyContainer.inputsEnergy(oppositeSide)) continue;
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
        if(side == null || inputsEnergy(side)) {
            if(voltage > getInputVoltage()) {
                BlockPos pos = metaTileEntity.getPos();
                metaTileEntity.getWorld().setBlockToAir(pos);
                if(ConfigHolder.doExplosions) {
                    metaTileEntity.getWorld().createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, GTUtility.getTierByVoltage(voltage), true);
                }
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
        return !outputsEnergy(side) && getInputVoltage() > 0 && (sideInputCondition == null || sideInputCondition.test(side));
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return getOutputVoltage() > 0 && (sideOutputCondition == null || sideOutputCondition.test(side));
    }

    @Override
    public long addEnergy(long energyToAdd) {
        long oldEnergyStored = getEnergyStored();
        long newEnergyStored = (maxCapacity - oldEnergyStored < energyToAdd) ? maxCapacity : (oldEnergyStored + energyToAdd);
        if(newEnergyStored < 0)
            newEnergyStored = 0;
        setEnergyStored(newEnergyStored);
        return newEnergyStored - oldEnergyStored;
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
