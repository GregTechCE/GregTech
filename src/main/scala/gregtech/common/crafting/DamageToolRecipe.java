package gregtech.common.crafting;

import javax.annotation.Nonnull;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.oredict.ShapedOreRecipe;

import gregtech.api.items.toolitem.ToolMetaItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class DamageToolRecipe extends ShapedOreRecipe {

    private Int2ObjectOpenHashMap<ItemStack> stackMap = new Int2ObjectOpenHashMap<>();

    public DamageToolRecipe(ResourceLocation group, ItemStack result, Object... recipe) {
        super(group, result, recipe);
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world) {
        stackMap.clear();
        if (!super.matches(inv, world)) {
            return false;
        }
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            Item item = stack.getItem();
            if (item instanceof ToolMetaItem) {
                ToolMetaItem toolItem = (ToolMetaItem) item;
                boolean success = toolItem.isUsable(stack, toolItem.getContainerCraftingDamage(stack));
                if (success) {
                    stackMap.put(i, stack);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> ret = super.getRemainingItems(inv);
        for (Int2ObjectOpenHashMap.Entry<ItemStack> entry : stackMap.int2ObjectEntrySet()) {
            ItemStack toolRet = entry.getValue().copy();
            ToolMetaItem toolItem = (ToolMetaItem) toolRet.getItem();
            toolItem.doDamageToItem(toolRet, toolItem.getContainerCraftingDamage(toolRet), false);
            ret.set(entry.getIntKey(), toolRet);
        }
        stackMap.clear();
        return ret;
    }
}
