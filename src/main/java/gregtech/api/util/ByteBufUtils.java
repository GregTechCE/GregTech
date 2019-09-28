package gregtech.api.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.io.IOException;

public class ByteBufUtils {

    public static void writeFluidStack(PacketBuffer buf, @Nullable FluidStack fluidStack) {
        buf.writeBoolean(fluidStack != null);
        if (fluidStack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            fluidStack.writeToNBT(tagCompound);
            buf.writeCompoundTag(tagCompound);
        }
    }

    public static FluidStack readFluidStack(PacketBuffer buf) {
        if (!buf.readBoolean()) {
            return null;
        }
        try {
            NBTTagCompound tagCompound = buf.readCompoundTag();
            return FluidStack.loadFluidStackFromNBT(tagCompound);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void writeFluidStackDelta(PacketBuffer buf, FluidStack oldFluidStack, FluidStack newFluidStack) {
        if (oldFluidStack != null && oldFluidStack.isFluidEqual(newFluidStack)) {
            buf.writeBoolean(true);
            buf.writeVarInt(newFluidStack.amount);
        } else {
            buf.writeBoolean(false);
            writeFluidStack(buf, newFluidStack);
        }
    }

    public static FluidStack readFluidStackDelta(PacketBuffer buf, FluidStack currentFluid) {
        if (buf.readBoolean()) {
            int newFluidAmount = buf.readVarInt();
            if (currentFluid == null) {
                GTLog.logger.error("Received fluid stack delta without acquiring initial state!", new Throwable());
                return null;
            }
            return GTUtility.copyAmount(newFluidAmount, currentFluid);
        } else {
            return readFluidStack(buf);
        }
    }
}
