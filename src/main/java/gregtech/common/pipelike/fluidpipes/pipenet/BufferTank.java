package gregtech.common.pipelike.fluidpipes.pipenet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

class BufferTank implements IFluidTank, INBTSerializable<NBTTagCompound> {
    final int capacity;
    FluidStack bufferedStack = null;
    int moveCountDown = 0;
    final int dirCountDown[] = new int[6];
    FluidTankInfo info;

    public BufferTank(int capacity) {
        this.capacity = capacity;
    }

    void setCountDown(EnumFacing fromDir, boolean lock) {
        int countDown = bufferedStack == null ? 0 : Math.max(1, FluidPipeNet.TICK_RATE * (1000 + bufferedStack.getFluid().getViscosity(bufferedStack)) / 2000);
        if (lock) this.moveCountDown = countDown;
        if (fromDir != null) this.dirCountDown[fromDir.getIndex()] = countDown * 8;
    }

    boolean isEmpty() {
        return getFluidAmount() <= 0;
    }

    void tick() {
        if (moveCountDown > 0) moveCountDown--;
        for (int i = 0; i < 6; i++) {
            if (dirCountDown[i] > 0) dirCountDown[i]--;
        }
    }

    boolean canFluidMove() {
        return getFluidAmount() > 0 && moveCountDown == 0;
    }

    boolean isFacingValid(EnumFacing facing) {
        return facing == null || dirCountDown[facing.getIndex()] <= 0;
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        return bufferedStack;
    }

    @Override
    public int getFluidAmount() {
        return bufferedStack == null ? 0 : bufferedStack.amount;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public FluidTankInfo getInfo() {
        return info == null ? (info = new FluidTankInfo(this)) : info;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return fill(resource, null, doFill);
    }

    int fill(FluidStack stack, EnumFacing fromDir, boolean doFill) {
        if (bufferedStack == null || bufferedStack.isFluidEqual(stack)) {
            int filled = Math.min(stack.amount, capacity - (bufferedStack == null ? 0 : bufferedStack.amount));
            if (doFill) {
                if (bufferedStack == null) {
                    bufferedStack = new FluidStack(stack, filled);
                    setCountDown(fromDir, true);
                } else {
                    bufferedStack.amount += filled;
                    setCountDown(fromDir, false);
                }
            }
            return filled;
        }
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (bufferedStack == null) return null;
        int drained = Math.min(maxDrain, bufferedStack.amount);
        if (doDrain) {
            bufferedStack.amount -= drained;
            if (bufferedStack.amount <= 0) bufferedStack = null;
        }
        return new FluidStack(bufferedStack, drained);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        if (bufferedStack != null) bufferedStack.writeToNBT(tag);
        int[] data = new int[7];
        data[6] = moveCountDown;
        System.arraycopy(dirCountDown, 0, data, 0, 6);
        tag.setIntArray("CountDowns", data);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        bufferedStack = FluidStack.loadFluidStackFromNBT(nbt);
        int[] data = nbt.getIntArray("CountDowns");
        System.arraycopy(data, 0, dirCountDown, 0, 6);
        moveCountDown = data[6];
    }
}
