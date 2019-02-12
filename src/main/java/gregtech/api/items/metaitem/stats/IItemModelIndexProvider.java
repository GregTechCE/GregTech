package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;

public interface IItemModelIndexProvider extends IMetaItemStats {

    int getModelIndex(ItemStack itemStack);
}
