package gregtech.common.tools;

import com.google.common.collect.Lists;
import gregtech.api.enchants.EnchantmentData;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ToolButcheryKnife extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 2;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 4;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 6.0f;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return 0.1f;
    }

    @Override
    public List<EnchantmentData> getEnchantments(ItemStack stack) {
        return Lists.newArrayList(new EnchantmentData(Enchantments.SHARPNESS, 2));
    }



}
