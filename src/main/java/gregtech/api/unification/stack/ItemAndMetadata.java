package gregtech.api.unification.stack;

import gregtech.api.util.GTUtility;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ItemAndMetadata {

    public final Item item;
    public final int itemDamage;

    public ItemAndMetadata(Item item, int itemDamage) {
        this.item = item;
        this.itemDamage = itemDamage;
    }

    public ItemAndMetadata(ItemStack itemStack) {
        this.item = itemStack.getItem();
        this.itemDamage = GTUtility.getActualItemDamageFromStack(itemStack);
    }

    public ItemStack toItemStack() {
        return new ItemStack(item, 1, itemDamage);
    }

    public ItemStack toItemStack(int stackSize) {
        return new ItemStack(item, stackSize, itemDamage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemAndMetadata)) return false;

        ItemAndMetadata that = (ItemAndMetadata) o;

        if (itemDamage != that.itemDamage) return false;
        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        int result = item.hashCode();
        result = 31 * result + itemDamage;
        return result;
    }

    @Override
    public String toString() {
        return this.item.getTranslationKey(toItemStack());
    }

}
