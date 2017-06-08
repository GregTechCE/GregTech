package gregtech.api.interfaces;

import net.minecraft.item.ItemStack;

public interface IDamagableItem {

    boolean doDamageToItem(ItemStack stack, int vanillaDamage);

}