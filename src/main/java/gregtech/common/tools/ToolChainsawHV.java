package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolChainsawHV extends ToolChainsawLV {

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
        return 4.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }

//    @Override
//    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
//        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadChainsaw.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_HV;
//    }
}
