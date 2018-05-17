package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

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
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.1F;
    }

//    @Override
//    public int[] getEnchantmentLevels(ItemStack stack) {
//        return new int[]{(2 + ToolMetaItem.getPrimaryMaterial(stack).harvestLevel) / 2};
//    }

    @Override
    public int getColor(boolean isToolHead, ItemStack stack) {
        return isToolHead ? ToolMetaItem.getPrimaryMaterial(stack).materialRGB : ToolMetaItem.getHandleMaterial(stack).materialRGB;
    }

    @Override
    public float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        return 0;
    }

    @Override
    public float getMagicDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase player) {
        return 0;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return 0;
    }
}
