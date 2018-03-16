package gregtech.api.capability.impl;

import com.google.common.collect.Lists;
import gregtech.api.capability.IMultipleTankHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FluidTankHandler implements IFluidHandler, IMultipleTankHandler, INBTSerializable<NBTTagCompound> {

    protected final List<FluidTank> fluidTanks;
    protected IFluidTankProperties[] properties;

    public FluidTankHandler(FluidTank... fluidTanks) {
        this.fluidTanks = Arrays.asList(fluidTanks);
    }

    @Override
    public int getTanks() {
        return fluidTanks.size();
    }

    @Override
    public IFluidTank getTankAt(int index) {
        return fluidTanks.get(index);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        if (properties == null) {
            List<IFluidTankProperties> props = Lists.newArrayList();
            fluidTanks.forEach(tank -> Collections.addAll(props, tank.getTankProperties()));
            properties = props.toArray(new IFluidTankProperties[props.size()]);
        }
        return properties;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0)
            return 0;

        resource = resource.copy();

        int totalFillAmount = 0;
        for (IFluidHandler handler : fluidTanks) {
            int fillAmount = handler.fill(resource, doFill);
            totalFillAmount += fillAmount;
            resource.amount -= fillAmount;
            if (resource.amount <= 0)
                break;
        }
        return totalFillAmount;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0)
            return null;

        resource = resource.copy();

        FluidStack totalDrained = null;
        for (IFluidHandler handler : fluidTanks) {
            FluidStack drain = handler.drain(resource, doDrain);
            if (drain != null) {
                if (totalDrained == null)
                    totalDrained = drain;
                else
                    totalDrained.amount += drain.amount;

                resource.amount -= drain.amount;
                if (resource.amount <= 0)
                    break;
            }
        }
        return totalDrained;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain == 0)
            return null;
        FluidStack totalDrained = null;
        for (IFluidHandler handler : fluidTanks) {
            if (totalDrained == null) {
                totalDrained = handler.drain(maxDrain, doDrain);
                if (totalDrained != null) {
                    maxDrain -= totalDrained.amount;
                }
            } else {
                FluidStack copy = totalDrained.copy();
                copy.amount = maxDrain;
                FluidStack drain = handler.drain(copy, doDrain);
                if (drain != null) {
                    totalDrained.amount += drain.amount;
                    maxDrain -= drain.amount;
                }
            }

            if (maxDrain <= 0)
                break;
        }
        return totalDrained;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound fluidInventory = new NBTTagCompound();
        fluidInventory.setInteger("TankAmount", this.getTanks());

        NBTTagList tanks = new NBTTagList();
        for (int i = 0; i < this.getTanks(); i++) {
            tanks.appendTag(this.fluidTanks.get(i).writeToNBT(new NBTTagCompound()));
        }
        fluidInventory.setTag("Tanks", tanks);
        return fluidInventory;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList tanks = nbt.getTagList("Tanks", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbt.getInteger("TankAmount"); i++) {
            NBTBase nbtTag = tanks.get(i);
            if (nbtTag instanceof NBTTagCompound) {
                fluidTanks.get(i).readFromNBT((NBTTagCompound) nbtTag);
            }
        }
    }

    protected void validateTankIndex(int tank) {
        if (tank < 0 || tank >= fluidTanks.size())
            throw new RuntimeException("Tank " + tank + " not in valid range - (0," + fluidTanks.size() + "]");
    }
}
