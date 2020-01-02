package gregtech.api.items;

import net.minecraft.item.ItemStack;

/**
 * Indicates an unified item that can be damaged an has custom logic of handling it's damage
 */
public interface IToolItem {

    /**
     * Tries to apply given amount of damage to item
     * If simulated, actual damage won't be applied and durability won't be changed
     * <p>
     * DO NOT USE METHODS BELOW TO CHECK IF TOOL CAN RECEIVE SPECIFIED AMOUNT OF DAMAGE,
     * Use this method with simulate = true to check so, because it can be different for electric items!
     */
    boolean damageItem(ItemStack stack, int damage, boolean simulate);

    /**
     * @return amount of internal damage this item have
     * Item is not forced to modify this value in #damageItem, it's used only for rendering purpose
     */
    int getItemDamage(ItemStack stack);

    /**
     * @return amount of max internal damage this item can receive.
     * Only used for rendering purposes
     */
    int getMaxItemDamage(ItemStack stack);
}