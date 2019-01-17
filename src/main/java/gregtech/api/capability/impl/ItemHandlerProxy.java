package gregtech.api.capability.impl;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class ItemHandlerProxy implements IItemHandlerModifiable {

    private IItemHandlerModifiable insertHandler;
    private IItemHandlerModifiable extractHandler;

    public ItemHandlerProxy(IItemHandlerModifiable insertHandler, IItemHandlerModifiable extractHandler) {
        this.insertHandler = insertHandler;
        this.extractHandler = extractHandler;
    }

    @Override
    public int getSlots() {
        return insertHandler.getSlots() + extractHandler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot < insertHandler.getSlots() ? insertHandler.getStackInSlot(slot) : extractHandler.getStackInSlot(slot - insertHandler.getSlots());
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return slot < insertHandler.getSlots() ? insertHandler.insertItem(slot, stack, simulate) : stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return slot >= insertHandler.getSlots() ? extractHandler.extractItem(slot - insertHandler.getSlots(), amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot < insertHandler.getSlots() ? insertHandler.getSlotLimit(slot) : extractHandler.getSlotLimit(slot - insertHandler.getSlots());
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        if (slot < insertHandler.getSlots())
            insertHandler.setStackInSlot(slot, stack);
        else
            extractHandler.setStackInSlot(slot - insertHandler.getSlots(), stack);
    }
}