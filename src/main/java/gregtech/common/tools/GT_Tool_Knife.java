package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.items.IIconContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GT_Tool_Knife extends GT_Tool_Sword {

    @Override
    public int getToolDamagePerBlockBreak() {
        return 100;
    }

    @Override
    public int getToolDamagePerDropConversion() {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft() {
        return 100;
    }

    @Override
    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    @Override
    public float getBaseDamage() {
        return 2.0F;
    }

    @Override
    public float getSpeedMultiplier() {
        return 0.5F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.KNIFE : null;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " has been murdered by " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }

}
