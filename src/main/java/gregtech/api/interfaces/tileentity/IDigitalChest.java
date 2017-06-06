package gregtech.api.interfaces.tileentity;

import net.minecraft.item.ItemStack;

public interface IDigitalChest extends IHasWorldObjectAndCoords {

    /**
     * Is this even a TileEntity of a Digital Chest?
     * I need things like this Function for MetaTileEntities, you MUST check this!!!
     * Do not assume that it's a Digital Chest or similar Device, when it just implements this Interface.
     */
    boolean isDigitalChest();

    /**
     * Gives an Array of Stacks with Size (of all the Data-stored Items) of the correspondent Item kinds (regular QChests have only one)
     * Does NOT include the 64 "ready" Items inside the Slots, and neither the 128 Items in the overflow Buffer.
     */
    ItemStack[] getStoredItemData();

    /**
     * A generic Interface for just setting the amount of contained Items
     */
    void setItemCount(int aCount);

    /**
     * Gets the maximum Item count for this QChest alike Storage. This applies to the Data-Storage, not for the up to 192 buffered Items!
     */
    int getMaxItemCount();

}