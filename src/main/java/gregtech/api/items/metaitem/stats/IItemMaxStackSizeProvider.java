package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;

public interface IItemMaxStackSizeProvider extends IMetaItemStats {

    int getMaxStackSize(ItemStack itemStack, int defaultValue);

}
