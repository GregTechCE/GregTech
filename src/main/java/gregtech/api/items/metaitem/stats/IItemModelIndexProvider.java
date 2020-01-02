package gregtech.api.items.metaitem.stats;

import net.minecraft.item.ItemStack;

//TODO replace with advanced IItemModelController to give more control over model loading
@FunctionalInterface
public interface IItemModelIndexProvider extends IItemComponent {

    int getModelIndex(ItemStack itemStack);
}
