package gregtech.api.util.watch;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Consumer;

public class WatchedItemStackHandler extends ItemStackHandler {
    private Consumer<ItemStack> onItemChanged;

    public WatchedItemStackHandler() {
        super();
    }

    public WatchedItemStackHandler(int size) {
        super(size);
    }

    public WatchedItemStackHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    public Consumer<ItemStack> getOnItemChanged() {
        return onItemChanged;
    }

    public WatchedItemStackHandler setOnItemChanged(Consumer<ItemStack> onItemChanged) {
        this.onItemChanged = onItemChanged;
        return this;
    }

    protected void onContentsChanged(int slot) {
        if (getOnItemChanged() != null)
            getOnItemChanged().accept(getStackInSlot(slot));
    }
}
