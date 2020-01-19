package gregtech.common.inventory;

import gregtech.api.util.ItemStackKey;

import java.util.Objects;

public class SimpleItemInfo implements IItemInfo {

    private final ItemStackKey itemStack;
    private int totalItemAmount = 0;

    public SimpleItemInfo(ItemStackKey itemStack) {
        this.itemStack = itemStack;
    }

    public void setTotalItemAmount(int totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
    }

    @Override
    public int getTotalItemAmount() {
        return totalItemAmount;
    }

    @Override
    public ItemStackKey getItemStackKey() {
        return itemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleItemInfo)) return false;
        SimpleItemInfo that = (SimpleItemInfo) o;
        return itemStack.equals(that.itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemStack);
    }
}
