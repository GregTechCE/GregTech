package gregtech.api.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InventoryUtils {

    //Returns the number of empty slots in an item inventory. Mostly for use with multiblock item inventories.
    public static int getNumberOfEmptySlotsInInventory(IItemHandlerModifiable inventory) {
        int emptySlots = 0;
        for(int index = 0; index < inventory.getSlots(); index++) {
            if(inventory.getStackInSlot(index).isEmpty()) {
                emptySlots++;
            }
        }

        return emptySlots;
    }

    /**
     * Determines whether all specified items will fit into a target inventory by
     * simulating merging like items into existing stacks, then checking if there
     * are enough empty stacks left to accommodate the remaining items.
     * <br /><br />
     * <b>Precondition:</b> the target inventory must not virtualize ItemStacks such that
     *               they can exceed the maximum stackable size of the item as
     *               defined by {@link net.minecraft.item.ItemStack#getMaxStackSize()}.
     * <br /><br />
     * @param items the items you want to insert
     * @param inventory the target inventory receiving items
     * @return {@code true} if inventory contains sufficient slots to merge and
     *         insert all requested items, {@code false} otherwise.
     */
    public static boolean simulateItemStackMerge(List<ItemStack> items, IItemHandlerModifiable inventory) {
        // if there's enough empty output slots then we don't need to compute merges.
        int emptySlots = getNumberOfEmptySlotsInInventory(inventory);
        if(items.size() <= emptySlots) {
            return true;
        }

        // Deep copy the recipe output itemstacks
        List<ItemStack> itemStackList = new ArrayList<>(items.size());
        for(ItemStack i : items) {
            itemStackList.add(i.copy());
        }

        // Sort by the number of items in each stack so we merge smallest stacks first.
        itemStackList.sort((i,j) -> Integer.compare(i.getCount(), j.getCount()));

        // Deep copy the contents of the output bus
        int numSlots = inventory.getSlots();
        List<ItemStack> inventoryStacks = new ArrayList<>(numSlots);
        for(int slotIndex = 0; slotIndex < numSlots; slotIndex++) {
            ItemStack stack = inventory.getStackInSlot(slotIndex);
            if(!stack.isEmpty())
                inventoryStacks.add(stack.copy());
        }

        // Since we're mutating the collection during iteration, use an iterator.
        Iterator<ItemStack> outIter = itemStackList.iterator();
        while(outIter.hasNext()) {
            ItemStack currentOut = outIter.next();

            // Find a matching item in the output bus, if any
            for(ItemStack currentInv : inventoryStacks) {
                if(ItemStack.areItemsEqual(currentInv, currentOut)) {
                    // if it's possible to merge stacks
                    int availableSlots = currentInv.getMaxStackSize() - currentInv.getCount();
                    if(availableSlots > 0) {
                        int mergeable = Math.min(availableSlots, currentOut.getCount());
                        currentOut.shrink(mergeable);
                        currentInv.grow(mergeable);

                        // if the output stack was merged completely, remove it and stop looking
                        if(currentOut.isEmpty()) {
                            outIter.remove();
                            break;
                        }
                    }
                }
            }
        }

        // We now have merged everything possible.
        // Return whether there are now sufficient empty slots to fit the unmerged items.
        return itemStackList.size() <= emptySlots;
    }

}
