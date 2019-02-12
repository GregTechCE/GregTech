package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;

public interface IItemColorProvider extends IMetaItemStats {

    int getItemStackColor(ItemStack itemStack, int tintIndex);
}
