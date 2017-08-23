package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTechAPI;
import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ToolDrillLV extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return GT_Mod.gregtechproxy.mHardRock ? 25 : 50;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 100;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(106);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(106);
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(106);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(106);
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tTool = block.getBlock().getHarvestTool(block);
        return ((tTool != null) && ((tTool.equals("pickaxe")) || (tTool.equals("shovel")))) ||
                (block.getMaterial() == Material.ROCK) ||
                (block.getMaterial() == Material.IRON) ||
                (block.getMaterial() == Material.ANVIL) ||
                (block.getMaterial() == Material.SAND) ||
                (block.getMaterial() == Material.GRASS) ||
                (block.getMaterial() == Material.GROUND) ||
                (block.getMaterial() == Material.SNOW) ||
                (block.getMaterial() == Material.CLAY) ||
                (block.getMaterial() == Material.GLASS);
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return 0;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadDrill.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_LV;
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        super.onToolCrafted(stack, player);
        player.addStat(AchievementList.BUILD_PICKAXE);
        player.addStat(AchievementList.BUILD_BETTER_PICKAXE);
        GT_Mod.achievements.issueAchievement(player, "driltime");
        GT_Mod.achievements.issueAchievement(player, "buildDrill");
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(entity.getDisplayName())
                .appendText(TextFormatting.WHITE + " got the Drill by " + TextFormatting.GREEN)
                .appendSibling(player.getDisplayName());
    }
}
