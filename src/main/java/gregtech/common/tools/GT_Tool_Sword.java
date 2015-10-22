package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class GT_Tool_Sword
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
        return 100;
    }

    public int getBaseQuality() {
        return 0;
    }

    public float getBaseDamage() {
        return 4.0F;
    }

    public float getSpeedMultiplier() {
        return 1.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    public String getCraftingSound() {
        return null;
    }

    public String getEntityHitSound() {
        return null;
    }

    public String getBreakingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    public String getMiningSound() {
        return null;
    }

    public boolean canBlock() {
        return true;
    }

    public boolean isCrowbar() {
        return false;
    }

    public boolean isWeapon() {
        return true;
    }

    public boolean isMinableBlock(Block aBlock, byte aMetaData) {
        String tTool = aBlock.getHarvestTool(aMetaData);
        return ((tTool != null) && (tTool.equals("sword"))) || (aBlock.getMaterial() == Material.leaves) || (aBlock.getMaterial() == Material.gourd) || (aBlock.getMaterial() == Material.vine) || (aBlock.getMaterial() == Material.web) || (aBlock.getMaterial() == Material.cloth) || (aBlock.getMaterial() == Material.carpet) || (aBlock.getMaterial() == Material.plants) || (aBlock.getMaterial() == Material.cactus) || (aBlock.getMaterial() == Material.cake) || (aBlock.getMaterial() == Material.tnt) || (aBlock.getMaterial() == Material.sponge);
    }

    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadSword.mTextureIndex] : Textures.ItemIcons.HANDLE_SWORD;
    }

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        aPlayer.triggerAchievement(AchievementList.buildSword);
    }
}
