package gregtech.api.capability.impl;

import net.minecraft.item.ItemStack;

/**
 * Item Fuel information
 */
public class ItemFuelInfo extends AbstractFuelInfo {

    private final ItemStack itemStack;

    public ItemFuelInfo(ItemStack itemStack, int fuelRemaining, int fuelCapacity, int fuelBurnTime) {
        super(fuelRemaining, fuelCapacity, fuelBurnTime);
        this.itemStack = itemStack;
    }

    public String getFuelName() {
        return itemStack.getDisplayName();
    }
}
