package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.common.items.behaviors.Behaviour_SoftHammer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GT_Tool_SoftHammer extends GT_Tool {

    @Override
    public int getToolDamagePerBlockBreak() {
        return 50;
    }

    @Override
    public int getToolDamagePerDropConversion() {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft() {
        return 800;
    }

    @Override
    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    @Override
    public int getBaseQuality() {
        return 0;
    }

    @Override
    public float getBaseDamage() {
        return 3.0F;
    }

    @Override
    public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity) {
        return aOriginalHurtResistance * 2;
    }

    @Override
    public float getSpeedMultiplier() {
        return 0.1F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 8.0F;
    }

    @Override
    public String getCraftingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(101));
    }

    @Override
    public String getEntityHitSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(101));
    }

    @Override
    public String getBreakingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    @Override
    public String getMiningSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(101));
    }

    @Override
    public boolean isCrowbar() {
        return false;
    }

    @Override
    public boolean isMiningTool() {
        return false;
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
        return false;
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadMallet.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.handleMallet.mTextureIndex];
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
        aItem.addItemBehavior(aID, new Behaviour_SoftHammer(100));
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " was stopped working by " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }
}
