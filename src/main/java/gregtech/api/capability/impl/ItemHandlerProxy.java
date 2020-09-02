package gregtech.api.capability.impl;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class ItemHandlerProxy implements IItemHandler {

    private IItemHandler insertHandler;
    private IItemHandler extractHandler;
    private boolean canExtractFromInsertionSlot;

    public ItemHandlerProxy(IItemHandler insertHandler, IItemHandler extractHandler) {
        this.insertHandler = insertHandler;
        this.extractHandler = extractHandler;
        this.canExtractFromInsertionSlot = false;
    }

    public ItemHandlerProxy(IItemHandler insertHandler, IItemHandler extractHandler, boolean canExtractFromInsertionSlot) {
        this.insertHandler = insertHandler;
        this.extractHandler = extractHandler;
        this.canExtractFromInsertionSlot = canExtractFromInsertionSlot;
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
        return slot >= insertHandler.getSlots() ?
            extractHandler.extractItem(slot - insertHandler.getSlots(), amount, simulate)
            :
            this.canExtractFromInsertionSlot ?
                insertHandler.extractItem(slot, amount, simulate)
                : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot < insertHandler.getSlots() ? insertHandler.getSlotLimit(slot) : extractHandler.getSlotLimit(slot - insertHandler.getSlots());
    }
}