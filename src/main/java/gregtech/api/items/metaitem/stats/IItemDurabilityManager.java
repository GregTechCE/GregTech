package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;

public interface IItemDurabilityManager extends IMetaItemStats {

    boolean showsDurabilityBar(ItemStack itemStack);

    double getDurabilityForDisplay(ItemStack itemStack);

    int getRGBDurabilityForDisplay(ItemStack itemStack);
}
