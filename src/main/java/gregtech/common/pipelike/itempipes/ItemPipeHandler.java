package gregtech.common.pipelike.itempipes;

import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.worldentries.pipenet.RoutePath;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemPipeHandler implements IItemHandler {

    final ITilePipeLike<TypeItemPipe, ItemPipeProperties> tile;
    long lastCachedPathTime = 0;
    List<RoutePath<ItemPipeProperties, ?, Long>> pathsCache;

    public ItemPipeHandler(ITilePipeLike<TypeItemPipe, ItemPipeProperties> tile) {
        this.tile = tile;
    }

    @Override
    public int getSlots() {
        try {
            return tile.getTileProperty().getTransferCapacity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return ItemStack.EMPTY;//TODO
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return ItemStack.EMPTY;//TODO
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;//TODO
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }
}
