package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;

public interface IItemNameProvider extends IMetaItemStats {

    String getItemStackDisplayName(ItemStack itemStack, String unlocalizedName);

}
