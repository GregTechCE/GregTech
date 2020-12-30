package gregtech.api.util;

import net.minecraft.item.*;
import net.minecraftforge.items.*;

import java.util.*;

public class InventoryUtils {

    /**
     * @param inventory the target inventory
     * @return the number of empty slots in {@code inventory}
     */
    public static int getNumberOfEmptySlotsInInventory(IItemHandler inventory) {
        int emptySlots = 0;
        for(int index = 0; index < inventory.getSlots(); index++) {
            if(inventory.getStackInSlot(index).isEmpty()) {
                emptySlots++;
            }
        }

        return emptySlots;
    }

    /**
     * Creates a deep copy of the target inventory, optionally keeping empty ItemStacks
     *
     * @param inventory the target inventory
     * @param keepEmpty whether to keep empty ItemStacks (if {@code true}, stacks will be kept).
     * @return a deep copy of the inventory.
     */
    public static List<ItemStack> deepCopy(IItemHandler inventory, boolean keepEmpty) {
        int numSlots = inventory.getSlots();
        List<ItemStack> inventoryStacks = new ArrayList<>(numSlots);
        for(int slotIndex = 0; slotIndex < numSlots; slotIndex++) {
            ItemStack stack = inventory.getStackInSlot(slotIndex);
            if(keepEmpty || !stack.isEmpty())
                inventoryStacks.add(stack.copy());
        }
        return inventoryStacks;
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
     * <b>Precondition:</b> the target inventory must actually accept the types of items
     *               you are trying to insert.
     * <br /><br />
     * @param inputItems the items you want to insert
     * @param inventory the target inventory receiving items
     * @return {@code true} if inventory contains sufficient slots to merge and
     *         insert all requested items, {@code false} otherwise.
     */
    public static boolean simulateItemStackMerge(List<ItemStack> inputItems,
                                                 IItemHandler inventory)
    {
        //First Ensure that the passed list of ItemStacks is actually within its max stacksize
        List<ItemStack> items = new ArrayList<>();
        for(ItemStack stack : inputItems) {
            if(!stack.isEmpty()) {
                if(stack.getCount() > stack.getMaxStackSize()) {
                    computeItemStacks(items, stack);
                }
                else {
                    items.add(stack.copy());
                }
            }
        }

        // If there's enough empty output slots then we don't need to compute merges.
        final int emptySlots = getNumberOfEmptySlotsInInventory(inventory);
        if(items.size() <= emptySlots)
            return true;

        // Deep copy the recipe output ItemStacks
        final List<ItemStack> itemStacks = new ArrayList<>(items.size());
        items.forEach(itemStack -> itemStacks.add(itemStack.copy()));

        // Sort by the number of items in each stack so we merge smallest stacks first.
        itemStacks.sort(Comparator.comparingInt(ItemStack::getCount));

        // Deep copy the contents of the target inventory, skipping empty stacks
        final List<ItemStack> inventoryStacks = deepCopy(inventory, false);

        // Perform a merge of the ItemStacks
        mergeItemStacks(itemStacks, inventoryStacks);

        // Return whether there are now sufficient empty slots to fit the unmerged items.
        return itemStacks.size() <= emptySlots;
    }

    /**
     * Merges stacks of identical items from a source into a destination.<br />
     * Successfully merged items will be removed from {@code source} and will appear in {@code destination}.<br />
     * Empty stacks in {@code destination} are not considered for this process.
     *
     * @param source      the ItemStacks to merge into {@code destination}.
     * @param destination a target inventory of existing ItemStacks.
     */
    private static void mergeItemStacks(Collection<ItemStack> source, Collection<ItemStack> destination) {
        // Since we're mutating the collection during iteration, use an iterator.
        final Iterator<ItemStack> sourceItemStacks = source.iterator();
        while(sourceItemStacks.hasNext()) {
            final ItemStack sourceItemStack = sourceItemStacks.next();

            // Find a matching item in the output bus, if any
            for(ItemStack destItemStack : destination)
                if(ItemStack.areItemsEqual(destItemStack, sourceItemStack)) {
                    // if it's possible to merge stacks
                    final int availableSlots = destItemStack.getMaxStackSize() - destItemStack.getCount();
                    if(availableSlots > 0) {
                        final int itemCount = Math.min(availableSlots, sourceItemStack.getCount());
                        sourceItemStack.shrink(itemCount);
                        destItemStack.grow(itemCount);

                        // if the output stack was merged completely, remove it and stop looking
                        if(sourceItemStack.isEmpty()) {
                            sourceItemStacks.remove();
                            break;
                        }
                    }
                }
        }
    }

    /**
     * Computes a resultant number of ItemStacks from an ItemStack exceeding its
     * {@link net.minecraft.item.ItemStack#getMaxStackSize()}.<br />
     * Resultant ItemStacks will be added to {@code collapsedStack} as full stacks followed
     * by a partial stack if applicable.<br />
     *
     * @param stack      the ItemStack exceeding its maximum stack size.
     * @param collapsedStack A Collection of ItemStacks for the resultant ItemStacks to be merged into.
     */
    public static void computeItemStacks(Collection<ItemStack> collapsedStack, ItemStack stack) {
        int overCount  = stack.getCount();
        int maxCount = stack.getMaxStackSize();

        int numStacks = overCount / maxCount;
        int remainder = overCount % maxCount;

        for(int fullStackCount = numStacks; fullStackCount > 0; fullStackCount--) {
            ItemStack fullStack = stack.copy();
            fullStack.setCount(maxCount);
            collapsedStack.add(fullStack);
        }

        if(remainder > 0) {
            ItemStack partialStack = stack.copy();
            partialStack.setCount(remainder);
            collapsedStack.add(partialStack);
        }
    }
}
