package gregtech.common.render.newitems;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.item.ItemStack;

public interface IItemIconProvider {

    IIconContainer getIconContainer(ItemStack itemStack);

}
