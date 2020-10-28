package gregtech.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.ItemStackHandler;

/**
 * This is facade for ItemStackHandler which supports stack sizes > 127
 */
public class LargeStackSizeItemStackHandler extends ItemStackHandler {

    private static final String BigStackSizeTagKey = "BigStackSize";
    private static final Byte FakeStackSize = new Byte("1");

    public LargeStackSizeItemStackHandler() {
        super();
    }

    public LargeStackSizeItemStackHandler(int maxMatchSlots) {
        super(maxMatchSlots);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = super.serializeNBT();

        if (stacks.stream().anyMatch(x -> x.getCount() > Byte.MAX_VALUE)) {
            NBTTagCompound stackSizes = new NBTTagCompound();
            NBTTagList items = tagCompound.getTagList("Items", 10);

            //save big stack size data
            for (int i = 0; i < stacks.size(); i++) {
                ItemStack itemStack = stacks.get(i);
                if (itemStack != ItemStack.EMPTY && itemStack.getCount() > Byte.MAX_VALUE) {
                    stackSizes.setInteger(String.valueOf(i), itemStack.getCount());
                }
            }
            tagCompound.setTag(BigStackSizeTagKey, stackSizes);

            //fix size overflow of existing item tags
            for (NBTBase itemBase : items.tagList) {
                NBTTagCompound item = (NBTTagCompound) itemBase;

                byte size = item.getByte("Count");
                if (size < 0)
                    item.setByte("Count", FakeStackSize);
            }
        }

        return tagCompound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tagCompound) {
        super.deserializeNBT(tagCompound);

        if (tagCompound.hasKey(BigStackSizeTagKey)) {
            NBTTagCompound stackSizes = tagCompound.getCompoundTag(BigStackSizeTagKey);

            for (String tagKey : stackSizes.getKeySet()) {
                int size = stackSizes.getInteger(tagKey);
                int slot = Integer.parseInt(tagKey);
                stacks.get(slot).setCount(size);
            }
        }
    }
}
