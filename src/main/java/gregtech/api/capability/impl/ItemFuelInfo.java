package gregtech.api.capability.impl;

import net.minecraft.item.ItemStack;

/**
 * Item Fuel information
 */
public class ItemFuelInfo extends AbstractFuelInfo {

    private final ItemStack itemStack;

    public ItemFuelInfo(ItemStack itemStack, int fuelRemaining, int fuelCapacity, int fuelMinConsumed, int fuelBurnTime) {
        super(fuelRemaining, fuelCapacity, fuelMinConsumed, fuelBurnTime);
        this.itemStack = itemStack;
    }

    public String getFuelName() {
        return itemStack.getTranslationKey();
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
