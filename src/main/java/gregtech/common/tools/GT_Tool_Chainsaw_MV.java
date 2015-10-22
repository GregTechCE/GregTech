package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.item.ItemStack;

public class GT_Tool_Chainsaw_MV
        extends GT_Tool_Chainsaw_LV {
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
        return 3.5F;
    }

    public float getSpeedMultiplier() {
        return 3.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 2.0F;
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? gregtech.api.items.GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadChainsaw.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_MV;
    }
}
