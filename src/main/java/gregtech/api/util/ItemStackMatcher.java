package gregtech.api.util;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class ItemStackMatcher {

    private ItemStack filterStack;
    public boolean ignoreDamage;
    public boolean respectNBTData;

    public ItemStackMatcher(ItemStack filterStack, boolean ignoreDamage, boolean respectNBTData) {
        Preconditions.checkNotNull(filterStack, "filterStack");
        this.filterStack = filterStack.copy();
        this.ignoreDamage = ignoreDamage;
        this.respectNBTData = respectNBTData;
    }

    public ItemStackMatcher copy() {
        return new ItemStackMatcher(filterStack.copy(), ignoreDamage, respectNBTData);
    }

    public static ItemStackMatcher readFromNBT(NBTTagCompound tagCompound) {
        ItemStack filterStack = new ItemStack(tagCompound.getCompoundTag("FilterItem"));
        boolean ignoreDamage = tagCompound.getBoolean("IgnoreDamage");
        boolean respectNBT = tagCompound.getBoolean("RespectNBT");
        return new ItemStackMatcher(filterStack, ignoreDamage, respectNBT);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setTag("FilterItem", filterStack.writeToNBT(new NBTTagCompound()));
        tagCompound.setBoolean("IgnoreDamage", ignoreDamage);
        tagCompound.setBoolean("RespectNBT", respectNBTData);
        return tagCompound;
    }

    public ItemStack getFilterStack() {
        return filterStack;
    }

    public void setFilterStack(ItemStack filterStack) {
        Preconditions.checkNotNull(filterStack, "filterStack");
        this.filterStack = filterStack.copy();
    }

    public boolean matches(ItemStack itemStack) {
        if(itemStack.getItem() != filterStack.getItem())
            return false;
        if(!ignoreDamage && itemStack.getItemDamage() != filterStack.getItemDamage())
            return false;
        //noinspection RedundantIfStatement
        if(!respectNBTData && !ItemStack.areItemStackTagsEqual(itemStack, filterStack))
            return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemStackMatcher)) return false;
        ItemStackMatcher matcher = (ItemStackMatcher) o;
        return ignoreDamage == matcher.ignoreDamage &&
            respectNBTData == matcher.respectNBTData &&
            ItemStack.areItemStacksEqual(filterStack, matcher.filterStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterStack, ignoreDamage, respectNBTData);
    }
}
