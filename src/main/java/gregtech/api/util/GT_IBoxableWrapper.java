package gregtech.api.util;

import static gregtech.api.enums.GT_Values.T;
import ic2.api.item.IBoxable;
import net.minecraft.item.ItemStack;

public class GT_IBoxableWrapper implements IBoxable {
	@Override
	public boolean canBeStoredInToolbox(ItemStack itemstack) {
		return T;
	}
}