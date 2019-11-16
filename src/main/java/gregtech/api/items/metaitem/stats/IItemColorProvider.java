package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemColorProvider extends IItemComponent {

    int getItemStackColor(ItemStack itemStack, int tintIndex);
}
