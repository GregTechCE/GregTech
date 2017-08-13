package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.item.ItemStack;

public class GT_Tool_Wrench_MV extends GT_Tool_Wrench_LV {

    @Override
    public int getToolDamagePerBlockBreak() {
        return 200;
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
    public float getBaseDamage(ItemStack stack) {
        return 1.5F;
    }

    @Override
    public float getSpeedMultiplier() {
        return 3.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 2.0F;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefix.toolHeadWrench.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_MV;
    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mRGBa : ToolMetaItem.getSecondaryMaterial(aStack).mRGBa;
    }

}
