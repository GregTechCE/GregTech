package gregtech.common.pipelike.fluidpipes.pipenet;

import gregtech.common.pipelike.fluidpipes.FluidPipeProperties;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.Arrays;

abstract class Pipe implements INBTSerializable<NBTTagCompound> {
    final int capacity;
    final int heatLimit;
    final boolean isGasProof;
    abstract boolean removable();
    abstract IFluidTankProperties[] getTankProperties(EnumFacing facing);
    abstract BufferTank getAccessibleTank(EnumFacing fromDir);
    abstract Type getType();
    abstract void tick();

    Pipe(FluidPipeProperties properties) {
        this.capacity = properties.getFluidCapacity();
        this.heatLimit = properties.getHeatLimit();
        this.isGasProof = properties.isGasProof();
    }

    class TankPropertiesWrapper implements IFluidTankProperties {

        BufferTank bufferedStack;
        boolean accessible;

        TankPropertiesWrapper(BufferTank bufferedFluid, boolean accessible) {
            bufferedStack = bufferedFluid;
        }

        @Nullable
        @Override
        public FluidStack getContents() {
            return bufferedStack.bufferedStack;
        }

        @Override
        public int getCapacity() {
            return capacity;
        }

        @Override
        public boolean canFill() {
            return accessible;
        }

        @Override
        public boolean canDrain() {
            return accessible;
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {
            return accessible;
        }

        @Override
        public boolean canDrainFluidType(FluidStack fluidStack) {
            return accessible;
        }
    }

    static Pipe create(FluidPipeProperties properties) {
        return properties.getMultiple() > 1 ? new MultiPipe(properties) : new NormalPipe(properties);
    }

    enum Type {NORMAL, MULTIPLE}

    static class NormalPipe extends Pipe {
        BufferTank tank = new BufferTank(capacity);

        NormalPipe(FluidPipeProperties properties) {
            super(properties);
        }

        @Override
        Type getType() {
            return Type.NORMAL;
        }

        @Override
        IFluidTankProperties[] getTankProperties(EnumFacing facing) {
            return new IFluidTankProperties[]{new TankPropertiesWrapper(tank, true)};
        }

        @Override
        BufferTank getAccessibleTank(EnumFacing fromDir) {
            return tank;
        }

        @Override
        public boolean removable() {
            return tank.isEmpty();
        }

        @Override
        void tick() {
            tank.tick();
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("Tank", tank.serializeNBT());
            return tag;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            tank.deserializeNBT(nbt.getCompoundTag("Tank"));
        }
    }

    static class MultiPipe extends Pipe {
        final BufferTank[] tanks;
        final int[] entrances = new int[6];

        MultiPipe(FluidPipeProperties properties) {
            super(properties);
            this.tanks = new BufferTank[properties.getMultiple()];
            for (int i = 0; i < tanks.length; i++) tanks[i] = new BufferTank(capacity);
        }

        @Override
        Type getType() {
            return Type.MULTIPLE;
        }

        @Override
        IFluidTankProperties[] getTankProperties(EnumFacing facing) {
            IFluidTankProperties[] result = new IFluidTankProperties[tanks.length];
            for (int i = 0; i < tanks.length; i++) {
                result[i] = new TankPropertiesWrapper(tanks[i], facing == null || i == entrances[facing.getIndex()]);
            }
            return result;
        }

        @Override
        BufferTank getAccessibleTank(EnumFacing fromDir) {
            if (fromDir == null) return null;
            int index = entrances[fromDir.getIndex()];
            return index >= 0 && index < tanks.length ? tanks[index] : null;
        }

        @Override
        public boolean removable() {
            for (BufferTank tank : tanks) if (!tank.isEmpty()) return false;
            for (int c : entrances) if (c >= 0) return false;
            return true;
        }

        @Override
        void tick() {
            for (BufferTank tank : tanks) tank.tick();
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = new NBTTagCompound();
            NBTTagList list = new NBTTagList();
            for (BufferTank tank : tanks) list.appendTag(tank.serializeNBT());
            tag.setTag("Tanks", list);
            tag.setIntArray("Entrances", Arrays.copyOf(entrances, 6));
            return tag;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            NBTTagList list = nbt.getTagList("Tanks", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tanks.length; i++) tanks[i].deserializeNBT(list.getCompoundTagAt(i));
            int[] data = nbt.getIntArray("Entrances");
            System.arraycopy(data, 0, entrances, 0, 6);
        }
    }
}
