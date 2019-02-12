package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;

public interface IItemContainerItemProvider extends IMetaItemStats {

    ItemStack getContainerItem(ItemStack itemStack);
}
