package gregtech.api.unification.stack;

import gregtech.api.GTValues;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class SimpleItemStack {

    public final Item item;
    public final int itemDamage;
    public final int stackSize;

    public SimpleItemStack(Item item, int itemDamage, int stackSize) {
        this.item = item;
        this.itemDamage = itemDamage;
        this.stackSize = stackSize;
    }

    public SimpleItemStack(ItemStack itemStack) {
        this.item = itemStack.getItem();
        this.itemDamage = itemStack.getItemDamage();
        this.stackSize = itemStack.getCount();
    }

    public SimpleItemStack(Item item, int itemDamage) {
        this(item, itemDamage, 1);
    }

    public ItemStack asItemStack() {
        return new ItemStack(item, stackSize, itemDamage);
    }

    public ItemStack asItemStack(int stackSize) {
        return new ItemStack(item, stackSize, itemDamage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleItemStack)) return false;

        SimpleItemStack that = (SimpleItemStack) o;

        if (itemDamage != that.itemDamage && itemDamage != GTValues.W && that.itemDamage != GTValues.W) return false;
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
        return this.stackSize + "x" + this.item.getUnlocalizedName(asItemStack());
    }

}