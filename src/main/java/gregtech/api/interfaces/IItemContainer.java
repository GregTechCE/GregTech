package gregtech.api.interfaces;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IItemContainer {

    Item getItem();

    Block getBlock();

    boolean isStackEqual(ItemStack stack);

    boolean isStackEqual(ItemStack stack, boolean wildcard, boolean ignoreNBT);

    ItemStack get(int amount, ItemStack... replacements);

    ItemStack getWildcard(int amount, ItemStack... replacements);

    ItemStack getUndamaged(int amount, ItemStack... replacements);

    ItemStack getAlmostBroken(int amount, ItemStack... replacements);

    ItemStack getWithDamage(int amount, long metaValue, ItemStack... replacements);

    IItemContainer set(Item item);

    IItemContainer set(ItemStack stack);

    IItemContainer registerOre(ItemStack... oreNames);

    IItemContainer registerWildcardAsOre(ItemStack... oreNames);

    ItemStack getWithCharge(int amount, int energy, ItemStack... replacements);

    ItemStack getWithName(int amount, String displayName, ItemStack... replacements);

    boolean hasBeenSet();

}