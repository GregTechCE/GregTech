package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.ore.OrePrefixes;
import net.minecraft.item.ItemStack;

public class GT_Tool_Wrench_HV extends GT_Tool_Wrench_LV {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 800;
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
        return 2.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadWrench.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_HV;
    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mRGBa : ToolMetaItem.getSecondaryMaterial(aStack).mRGBa;
    }
}
