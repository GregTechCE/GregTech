package gregtech.api.interfaces.metaitem;

import net.minecraft.item.ItemStack;

public interface IItemDurabilityManager extends IMetaItemStats {

    boolean showsDurabilityBar(ItemStack itemStack);

    double getDurabilityForDisplay(ItemStack itemStack);

}
