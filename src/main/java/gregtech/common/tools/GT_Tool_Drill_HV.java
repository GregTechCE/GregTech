package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GT_Tool_Drill_HV
        extends GT_Tool_Drill_LV {
    public int getToolDamagePerBlockBreak() {
        return GT_Mod.gregtechproxy.mHardRock ? 400 : 800;
    }

    public int getToolDamagePerDropConversion() {
        return 1600;
    }

    public int getToolDamagePerContainerCraft() {
        return 12800;
    }

    public int getToolDamagePerEntityAttack() {
        return 3200;
    }

    public int getBaseQuality() {
        return 1;
    }

    public float getBaseDamage() {
        return 3.0F;
    }

    public float getSpeedMultiplier() {
        return 9.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 4.0F;
    }

    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        try {
            GT_Mod.instance.achievements.issueAchievement(aPlayer, "highpowerdrill");
            GT_Mod.instance.achievements.issueAchievement(aPlayer, "buildDDrill");
        } catch (Exception e) {
        }
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? gregtech.api.items.GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadDrill.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_HV;
    }
}
