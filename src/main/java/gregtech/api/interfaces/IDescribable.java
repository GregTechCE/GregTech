package gregtech.api.interfaces;

import net.minecraft.item.ItemStack;

/**
 * To get simple things like a ToolTip Description
 */
public interface IDescribable {

    /**
     * The Tooltip Text
     */
    String[] getDescription(ItemStack itemStack);

}
