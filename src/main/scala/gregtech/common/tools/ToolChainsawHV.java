package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolChainsawHV extends ToolChainsawLV {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 8;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 16;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 128;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 32;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 6.0F;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }
}
