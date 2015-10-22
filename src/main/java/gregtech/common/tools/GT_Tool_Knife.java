package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GT_Tool_Knife
        extends GT_Tool_Sword {
    public int getToolDamagePerBlockBreak() {
        return 100;
    }

    public int getToolDamagePerDropConversion() {
        return 100;
    }

    public int getToolDamagePerContainerCraft() {
        return 100;
    }

    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    public float getBaseDamage() {
        return 2.0F;
    }

    public float getSpeedMultiplier() {
        return 0.5F;
    }

    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.KNIFE : null;
    }

    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText("<" + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + "> " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " what are you doing?, " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + "?!? STAHP!!!");
    }
}
