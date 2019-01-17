package gregtech.api.util.watch;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class WatchedItemStackHandlerWrapper extends WatchedItemStackHandler {
    private IItemHandlerModifiable delegate;

    public WatchedItemStackHandlerWrapper(IItemHandlerModifiable delegate) {
        this.delegate = delegate;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        delegate.setStackInSlot(slot, stack);
        onContentsChanged(slot);
    }

    @Override
    public int getSlots() {
        return delegate.getSlots();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        return delegate.getStackInSlot(slot);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        ItemStack itemStack = delegate.insertItem(slot, stack, simulate);
        if (!simulate) onContentsChanged(slot);
        return itemStack;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack itemStack = delegate.extractItem(slot, amount, simulate);
        if (!simulate) onContentsChanged(slot);
        return itemStack;
    }

    @Override
    public int getSlotLimit(int slot) {
        return delegate.getSlotLimit(slot);
    }
}
