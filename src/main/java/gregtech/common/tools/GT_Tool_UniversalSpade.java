package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.behaviors.Behaviour_Crowbar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class GT_Tool_UniversalSpade extends GT_Tool {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 400;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 100;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 0;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.75F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(0);
    }

    @Override
    public boolean isCrowbar(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isGrafter(ItemStack stack) {
        return false;
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
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
    public boolean isMinableBlock(IBlockState aBlock, ItemStack stack) {
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
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        return 0;
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
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

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefix.toolHeadUniversalSpade.mTextureIndex] : ToolMetaItem.getSecondaryMaterial(aStack).mIconSet.mTextures[OrePrefix.stick.mTextureIndex];
    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mRGBa : ToolMetaItem.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem aItem, int aID) {
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
