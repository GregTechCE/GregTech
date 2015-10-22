package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GT_Tool_ButcheryKnife
        extends GT_Tool {
    public int getToolDamagePerBlockBreak() {
        return 200;
    }

    public int getToolDamagePerDropConversion() {
        return 100;
    }

    public int getToolDamagePerContainerCraft() {
        return 100;
    }

    public int getToolDamagePerEntityAttack() {
        return 400;
    }

    public float getBaseDamage() {
        return 1.0F;
    }

    public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity) {
        return aOriginalHurtResistance * 2;
    }

    public float getSpeedMultiplier() {
        return 0.1F;
    }

    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    public boolean isWeapon() {
        return true;
    }

    public boolean isMiningTool() {
        return false;
    }

    public Enchantment[] getEnchantments(ItemStack aStack) {
        return LOOTING_ENCHANTMENT;
    }

    public int[] getEnchantmentLevels(ItemStack aStack) {
        return new int[]{(2 + GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mToolQuality) / 2};
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.BUTCHERYKNIFE : null;
    }

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText(EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " has butchered " + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE);
    }

    public boolean isMinableBlock(Block aBlock, byte aMetaData) {
        return false;
    }
}
