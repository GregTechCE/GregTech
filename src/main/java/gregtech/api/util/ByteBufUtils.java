package gregtech.api.util;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ByteBufUtils {

    public static void writeRelativeBlockList(PacketBuffer buf, BlockPos origin, List<BlockPos> blockList) {
        buf.writeVarInt(blockList.size());
        for (BlockPos blockPos1 : blockList) {
            BlockPos blockPos = blockPos1.subtract(origin);
            buf.writeVarInt(blockPos.getX());
            buf.writeVarInt(blockPos.getY());
            buf.writeVarInt(blockPos.getZ());
        }
    }

    public static List<BlockPos> readRelativeBlockList(PacketBuffer buf, BlockPos origin) {
        ArrayList<BlockPos> resultList = new ArrayList<>();
        int amount = buf.readVarInt();
        for (int i = 0; i < amount; i++) {
            int posX = origin.getX() + buf.readVarInt();
            int posY = origin.getY() + buf.readVarInt();
            int posZ = origin.getZ() + buf.readVarInt();
            resultList.add(new BlockPos(posX, posY, posZ));
        }
        return resultList;
    }

    public static void writeIntList(PacketBuffer buf, TIntList intList) {
        buf.writeVarInt(intList.size());
        for (int i = 0; i < intList.size(); i++) {
            buf.writeVarInt(intList.get(i));
        }
    }

    public static TIntList readIntList(PacketBuffer buf) {
        TIntArrayList intArrayList = new TIntArrayList();
        int amount = buf.readVarInt();
        for (int i = 0; i < amount; i++) {
            intArrayList.add(buf.readVarInt());
        }
        return intArrayList;
    }

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
