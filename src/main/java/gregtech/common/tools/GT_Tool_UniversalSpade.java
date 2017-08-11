package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.items.IIconContainer;
import gregtech.common.items.behaviors.Behaviour_Crowbar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GT_Tool_UniversalSpade extends GT_Tool {

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
        return 400;
    }

    @Override
    public int getToolDamagePerEntityAttack() {
        return 100;
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
    public float getSpeedMultiplier() {
        return 0.75F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    @Override
    public String getCraftingSound() {
        return null;
    }

    @Override
    public String getEntityHitSound() {
        return null;
    }

    @Override
    public String getBreakingSound() {
        return GregTech_API.sSoundList.get(0);
    }

    @Override
    public String getMiningSound() {
        return null;
    }

    @Override
    public boolean isCrowbar() {
        return true;
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return (tTool != null && (
                        tTool.equals("shovel") ||
                        tTool.equals("axe") ||
                        tTool.equals("saw") ||
                        tTool.equals("sword") ||
                        tTool.equals("crowbar"))) ||
                aBlock.getMaterial() == Material.SAND ||
                aBlock.getMaterial() == Material.GRASS ||
                aBlock.getMaterial() == Material.GROUND ||
                aBlock.getMaterial() == Material.SNOW ||
                aBlock.getMaterial() == Material.CLAY ||
                aBlock.getMaterial() == Material.CRAFTED_SNOW ||
                aBlock.getMaterial() == Material.LEAVES ||
                aBlock.getMaterial() == Material.VINE ||
                aBlock.getMaterial() == Material.WOOD ||
                aBlock.getMaterial() == Material.CACTUS ||
                aBlock.getMaterial() == Material.CIRCUITS ||
                aBlock.getMaterial() == Material.GOURD ||
                aBlock.getMaterial() == Material.WEB ||
                aBlock.getMaterial() == Material.CLOTH ||
                aBlock.getMaterial() == Material.CARPET ||
                aBlock.getMaterial() == Material.PLANTS ||
                aBlock.getMaterial() == Material.CAKE ||
                aBlock.getMaterial() == Material.TNT ||
                aBlock.getMaterial() == Material.SPONGE;
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadUniversalSpade.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.stick.mTextureIndex];
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
        aItem.addItemBehavior(aID, new Behaviour_Crowbar(2, 2000));
    }

    @Override
    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        aPlayer.addStat(AchievementList.BUILD_SWORD);
        GT_Mod.achievements.issueAchievement(aPlayer, "unitool");
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " has been universal digged by " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }

}
