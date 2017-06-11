package gregtech.api.interfaces.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public interface IHasInventory extends ISidedInventory, IHasWorldObjectAndCoords {

    /**
     * if the Inventory of this TileEntity got modified this tick
     */
    boolean hasInventoryBeenModified();

    /**
     * @return true if this is not a Holoslot
     */
    boolean isValidSlot(int index);

    /**
     * Tries to add a Stack to the Slot.
     * It doesn't matter if the Slot is valid or invalid as described at the Function above.
     *
     * @return true if aStack == null, then false if aIndex is out of bounds, then false if aStack cannot be added, and then true if aStack has been added
     */
    boolean addStackToSlot(int index, ItemStack stack);

    /**
     * Tries to add X Items of a Stack to the Slot.
     * It doesn't matter if the Slot is valid or invalid as described at the Function above.
     *
     * @return true if aStack == null, then false if aIndex is out of bounds, then false if aStack cannot be added, and then true if aStack has been added
     */
    boolean addStackToSlot(int index, ItemStack stack, int amount);

}