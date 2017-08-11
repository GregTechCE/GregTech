package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.enums.Textures;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.items.IIconContainer;
import net.minecraft.item.ItemStack;

public class GT_Tool_Drill_MV extends GT_Tool_Drill_LV {

    @Override
    public int getToolDamagePerBlockBreak() {
        return GT_Mod.gregtechproxy.mHardRock ? 100 : 200;
    }

    @Override
    public int getToolDamagePerDropConversion() {
        return 400;
    }

    @Override
    public int getToolDamagePerContainerCraft() {
        return 3200;
    }

    @Override
    public int getToolDamagePerEntityAttack() {
        return 800;
    }

    @Override
    public int getBaseQuality() {
        return 1;
    }

    @Override
    public float getBaseDamage() {
        return 2.5F;
    }

    @Override
    public float getSpeedMultiplier() {
        return 6.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 2.0F;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? gregtech.api.items.GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadDrill.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_MV;
    }
}
