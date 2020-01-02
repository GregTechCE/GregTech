package gregtech.api.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * Wrapper around ItemStack to allow infinitely large stack size
 * Wraps NBT serialization methods and provides ByteBuf methods
 */
public class LongItemStack {

    private final ItemStack itemStack;

    public LongItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public LongItemStack(NBTTagCompound tagCompound) {
        this.itemStack = new ItemStack(tagCompound);
        this.itemStack.setCount(tagCompound.getInteger("Count"));
    }

    public int getStackSize() {
        return itemStack.getCount();
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        itemStack.writeToNBT(tagCompound);
        tagCompound.setInteger("Count", itemStack.getCount());
        return tagCompound;
    }

    public void writeItemStack(PacketBuffer packetBuffer) {
        if (itemStack.isEmpty()) {
            packetBuffer.writeShort(-1);
        } else {
            packetBuffer.writeShort(Item.getIdFromItem(itemStack.getItem()));
            packetBuffer.writeVarInt(itemStack.getCount());
            packetBuffer.writeShort(itemStack.getMetadata());
            NBTTagCompound nbttagcompound = null;

            if (itemStack.getItem().isDamageable() ||
                itemStack.getItem().getShareTag()) {
                nbttagcompound = itemStack.getItem().getNBTShareTag(itemStack);
            }
            packetBuffer.writeCompoundTag(nbttagcompound);
        }
    }

    public static LongItemStack readItemStack(PacketBuffer packetBuffer) {
        int itemId = packetBuffer.readShort();
        if (itemId < 0) {
            return new LongItemStack(ItemStack.EMPTY);
        } else {
            int stackSize = packetBuffer.readVarInt();
            int metadata = packetBuffer.readShort();
            ItemStack itemStack = new ItemStack(Item.getItemById(itemId), stackSize, metadata);
            try {
                itemStack.getItem().readNBTShareTag(itemStack, packetBuffer.readCompoundTag());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new LongItemStack(itemStack);
        }
    }

}
