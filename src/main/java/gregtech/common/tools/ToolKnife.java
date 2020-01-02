package gregtech.common.tools;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ToolKnife extends ToolSword {

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SWORD);
    }


    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return -0.7f; //3x faster than sword attack
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

}
