package gregtech.api.capability.impl;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.ConfigHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToLongFunction;

import static gregtech.api.util.GTUtility.*;

public class EnergyContainerBatteryBuffer extends MTETrait implements IEnergyContainer.IEnergyContainerOverflowSafe {

    private final int tier;

    public EnergyContainerBatteryBuffer(MetaTileEntity metaTileEntity, int tier) {
        super(metaTileEntity);
        this.tier = tier;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        long initialAmperage = amperage;
        if(side == null || inputsEnergy(side)) {
            if (voltage > getInputVoltage()) {
                BlockPos pos = metaTileEntity.getPos();
                metaTileEntity.getWorld().setBlockToAir(pos);
                if (ConfigHolder.doExplosions) {
                    metaTileEntity.getWorld().createExplosion(null,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        getTierByVoltage(voltage), true);
                }
                return Math.min(amperage, getInputAmperage());
            }
            IItemHandlerModifiable inventory = getInventory();
            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack batteryStack = inventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) continue;
                if(electricItem.charge(voltage, getTier(), true, true) == voltage) {
                    electricItem.charge(voltage, getTier(), true, false);
                    inventory.setStackInSlot(i, batteryStack);
                    if(--amperage == 0) break;
                }
            }
        }
        return initialAmperage - amperage;
    }

    @Override
    public void update() {
        if(!metaTileEntity.getWorld().isRemote) {
            EnumFacing outFacing = metaTileEntity.getFrontFacing();
            TileEntity tileEntity = metaTileEntity.getWorld().getTileEntity
                (metaTileEntity.getPos().offset(outFacing));
            if(tileEntity == null) return;
            IEnergyContainer energyContainer = tileEntity.getCapability(
                GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, outFacing.getOpposite());
            if(energyContainer == null) return;
            IItemHandlerModifiable inventory = getInventory();
            long voltage = getOutputVoltage();
            long maxAmperage = 0L;
            TIntList slotsList = new TIntArrayList();
            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack batteryStack = inventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) continue;
                if(electricItem.discharge(voltage, getTier(), true, true, true) == voltage) {
                    slotsList.add(i);
                    maxAmperage++;
                }
            }
            if(maxAmperage == 0) return;
            long amperageUsed = energyContainer.acceptEnergyFromNetwork(outFacing.getOpposite(), voltage, maxAmperage);
            if(amperageUsed == 0) return;
            for (int i : slotsList.toArray()) {
                ItemStack batteryStack = inventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) continue;
                electricItem.discharge(voltage, getTier(), true, true, false);
                inventory.setStackInSlot(i, batteryStack);
                if(--amperageUsed == 0) break;
            }
        }
    }

    private List<IElectricItem> getElectricItems() {
        List<IElectricItem> electricItems = new ArrayList<>();
        IItemHandlerModifiable inventory = getInventory();
        for(int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem != null) electricItems.add(electricItem);
        }
        return electricItems;
    }

    private long getCastedSum(ToLongFunction<IElectricItem> toLong) {
        return castedSum(getElectricItems().stream()
            .mapToLong(toLong)
            .toArray());
    }

    private BigInteger getActualSum(ToLongFunction<IElectricItem> toLong) {
        return sum(getElectricItems().stream()
            .mapToLong(toLong)
            .toArray());
    }

    private final ToLongFunction<IElectricItem> getEnergyStored = electricItem -> electricItem.discharge(Long.MAX_VALUE, getTier(), true, true, true);

    @Override
    public long getEnergyCapacity() {
        return getCastedSum(IElectricItem::getMaxCharge);
    }

    @Override
    public BigInteger getEnergyCapacityActual() {
        return getActualSum(IElectricItem::getMaxCharge);
    }

    @Override
    public long getEnergyStored() {
        return getCastedSum(getEnergyStored);
    }

    @Override
    public BigInteger getEnergyStoredActual() {
        return getActualSum(getEnergyStored);
    }

    @Override
    public long getInputAmperage() {
        long inputAmperage = 0L;
        IItemHandlerModifiable inventory = getInventory();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            inputAmperage++;
        }
        return inputAmperage;
    }

    public IElectricItem getBatteryContainer(ItemStack itemStack) {
        IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (electricItem == null || electricItem.getTier() != getTier() ||
            !electricItem.canProvideChargeExternally())
            return null;
        return electricItem;
    }

    @Override
    public long addEnergy(long energyToAdd) {
        long inputVoltage = getInputVoltage();
        return acceptEnergyFromNetwork(null, inputVoltage, energyToAdd / inputVoltage) * inputVoltage;
    }

    @Override
    public long getInputVoltage() {
        return GTValues.V[getTier()];
    }

    @Override
    public long getOutputVoltage() {
        return getInputVoltage();
    }

    @Override
    public long getOutputAmperage() {
        return getInputAmperage();
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return getMetaTileEntity().getFrontFacing() != side;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return !inputsEnergy(side);
    }

    @Override
    public String getName() {
        return "BatteryEnergyContainer";
    }

    @Nullable
    @Override
    public Capability<?> getImplementingCapability() {
        return GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER;
    }

    protected IItemHandlerModifiable getInventory() {
        return metaTileEntity.getImportItems();
    }

    protected int getTier() {
        return tier;
    }
}
