package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.enums.Textures;
import gregtech.api.items.IIconContainer;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GT_Tool_Drill_HV extends GT_Tool_Drill_LV {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return GT_Mod.gregtechproxy.mHardRock ? 400 : 800;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 1600;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 12800;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 3200;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 9.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        GT_Mod.achievements.issueAchievement(aPlayer, "highpowerdrill");
        GT_Mod.achievements.issueAchievement(aPlayer, "buildDDrill");
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefix.toolHeadDrill.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_HV;
    }

}
