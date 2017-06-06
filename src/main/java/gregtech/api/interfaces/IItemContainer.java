package gregtech.api.interfaces;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IItemContainer {

    Item getItem();

    Block getBlock();

    boolean isStackEqual(Object aStack);

    boolean isStackEqual(Object aStack, boolean aWildcard, boolean aIgnoreNBT);

    ItemStack get(long aAmount, Object... aReplacements);

    ItemStack getWildcard(long aAmount, Object... aReplacements);

    ItemStack getUndamaged(long aAmount, Object... aReplacements);

    ItemStack getAlmostBroken(long aAmount, Object... aReplacements);

    ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements);

    IItemContainer set(Item aItem);

    IItemContainer set(ItemStack aStack);

    IItemContainer registerOre(Object... aOreNames);

    IItemContainer registerWildcardAsOre(Object... aOreNames);

    ItemStack getWithCharge(long aAmount, int aEnergy, Object... aReplacements);

    ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements);

    boolean hasBeenSet();

}