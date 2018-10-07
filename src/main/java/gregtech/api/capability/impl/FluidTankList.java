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
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.*;

public class FluidTankList implements IFluidHandler, IMultipleTankHandler, INBTSerializable<NBTTagCompound> {

    protected final List<IFluidTank> fluidTanks;
    protected IFluidTankProperties[] properties;

    public FluidTankList(IFluidTank... fluidTanks) {
        this.fluidTanks = Arrays.asList(fluidTanks);
    }

    public FluidTankList(List<? extends IFluidTank> fluidTanks) {
        this.fluidTanks = new ArrayList<>(fluidTanks);
    }

    public FluidTankList(FluidTankList parent, IFluidTank... additionalTanks) {
        this.fluidTanks = new ArrayList<>();
        this.fluidTanks.addAll(parent.fluidTanks);
        this.fluidTanks.addAll(Arrays.asList(additionalTanks));
    }

    public List<IFluidTank> getFluidTanks() {
        return Collections.unmodifiableList(fluidTanks);
    }

    @Override
    public Iterator<IFluidTank> iterator() {
        return getFluidTanks().iterator();
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
        List<IFluidTankProperties> props = Lists.newArrayList();
        for(IFluidTank fluidTank : fluidTanks) {
            if(fluidTank instanceof FluidTank) {
                props.add(new FluidTankPropertiesWrapper((FluidTank) fluidTank));
            } else if(fluidTank instanceof IFluidHandler) {
                props.addAll(Arrays.asList(((IFluidHandler) fluidTank).getTankProperties()));
            }
        }
        return props.toArray(new IFluidTankProperties[0]);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0)
            return 0;
        IFluidTank tankWithFluid = fluidTanks.stream()
            .filter(tank -> resource.isFluidEqual(tank.getFluid()))
            .findAny().orElseGet(() -> fluidTanks.stream()
                .filter(tank -> tank.getFluidAmount() == 0)
                .findFirst().orElse(null));
        return tankWithFluid == null ? 0 : tankWithFluid.fill(resource, doFill);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0)
            return null;

        resource = resource.copy();

        FluidStack totalDrained = null;
        for (IFluidTank handler : fluidTanks) {
            if(!resource.isFluidEqual(handler.getFluid()))
                continue;
            FluidStack drain = handler.drain(resource.amount, doDrain);
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
        for (IFluidTank handler : fluidTanks) {
            if (totalDrained == null) {
                totalDrained = handler.drain(maxDrain, doDrain);
                if (totalDrained != null) {
                    maxDrain -= totalDrained.amount;
                }
            } else {
                FluidStack copy = totalDrained.copy();
                copy.amount = maxDrain;
                if(!copy.isFluidEqual(handler.getFluid()))
                    continue;
                FluidStack drain = handler.drain(copy.amount, doDrain);
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
            NBTBase writeTag;
            IFluidTank fluidTank = fluidTanks.get(i);
            if(fluidTank instanceof FluidTank) {
                writeTag = ((FluidTank) fluidTank).writeToNBT(new NBTTagCompound());
            } else if(fluidTank instanceof INBTSerializable) {
                writeTag = ((INBTSerializable) fluidTank).serializeNBT();
            } else writeTag = new NBTTagCompound();

            tanks.appendTag(writeTag);
        }
        fluidInventory.setTag("Tanks", tanks);
        return fluidInventory;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList tanks = nbt.getTagList("Tanks", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbt.getInteger("TankAmount"); i++) {
            NBTBase nbtTag = tanks.get(i);
            IFluidTank fluidTank = fluidTanks.get(i);
            if(fluidTank instanceof FluidTank) {
                ((FluidTank) fluidTank).readFromNBT((NBTTagCompound) nbtTag);
            } else if(fluidTank instanceof INBTSerializable) {
                ((INBTSerializable) fluidTank).deserializeNBT(nbtTag);
            }
        }
    }

    protected void validateTankIndex(int tank) {
        if (tank < 0 || tank >= fluidTanks.size())
            throw new RuntimeException("Tank " + tank + " not in valid range - (0," + fluidTanks.size() + "]");
    }
}
