package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IItemCapabilityProvider extends IMetaItemStats {

    ICapabilityProvider createProvider(ItemStack itemStack);

}
