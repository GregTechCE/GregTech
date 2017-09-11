package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ToolButcheryKnife extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 200;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 400;
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

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.GREEN + "")
//                .appendSibling(player.getDisplayName())
//                .appendText(TextFormatting.WHITE + " has butchered " + TextFormatting.RED)
//                .appendSibling(entity.getDisplayName());
//    }

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
