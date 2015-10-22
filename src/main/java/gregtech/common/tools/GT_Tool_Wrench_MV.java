package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.item.ItemStack;

public class GT_Tool_Wrench_MV
        extends GT_Tool_Wrench_LV {
    public int getToolDamagePerBlockBreak() {
        return 200;
    }

    public int getToolDamagePerDropConversion() {
        return 400;
    }

    public int getToolDamagePerContainerCraft() {
        return 3200;
    }

    public int getToolDamagePerEntityAttack() {
        return 800;
    }

    public int getBaseQuality() {
        return 1;
    }

    public float getBaseDamage() {
        return 1.5F;
    }

    public float getSpeedMultiplier() {
        return 3.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 2.0F;
    }

    public boolean canBlock() {
        return false;
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadWrench.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_MV;
    }

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }
}
