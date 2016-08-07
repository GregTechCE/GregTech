package gregtech.common.render.newrenderer;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.item.ItemStack;

public interface IIconProvider {

    IIconContainer getIconContainer(ItemStack itemStack);

}
