package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GT_Tool_Drill_LV extends GT_Tool {

    @Override
    public int getToolDamagePerBlockBreak() {
        return GT_Mod.gregtechproxy.mHardRock ? 25 : 50;
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
    public int getBaseQuality() {
        return 0;
    }

    @Override
    public float getBaseDamage() {
        return 2.0F;
    }

    @Override
    public float getSpeedMultiplier() {
        return 3.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    @Override
    public String getCraftingSound() {
        return GregTech_API.sSoundList.get(106);
    }

    @Override
    public String getEntityHitSound() {
        return GregTech_API.sSoundList.get(106);
    }

    @Override
    public String getBreakingSound() {
        return GregTech_API.sSoundList.get(106);
    }

    @Override
    public String getMiningSound() {
        return GregTech_API.sSoundList.get(106);
    }

    @Override
    public boolean isCrowbar() {
        return false;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return ((tTool != null) && ((tTool.equals("pickaxe")) || (tTool.equals("shovel")))) ||
                (aBlock.getMaterial() == Material.ROCK) ||
                (aBlock.getMaterial() == Material.IRON) ||
                (aBlock.getMaterial() == Material.ANVIL) ||
                (aBlock.getMaterial() == Material.SAND) ||
                (aBlock.getMaterial() == Material.GRASS) ||
                (aBlock.getMaterial() == Material.GROUND) ||
                (aBlock.getMaterial() == Material.SNOW) ||
                (aBlock.getMaterial() == Material.CLAY) ||
                (aBlock.getMaterial() == Material.GLASS);
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadDrill.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_LV;
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
    }

    @Override
    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        aPlayer.addStat(AchievementList.BUILD_PICKAXE);
        aPlayer.addStat(AchievementList.BUILD_BETTER_PICKAXE);
        GT_Mod.achievements.issueAchievement(aPlayer, "driltime");
        GT_Mod.achievements.issueAchievement(aPlayer, "buildDrill");
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " got the Drill by " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }

}
