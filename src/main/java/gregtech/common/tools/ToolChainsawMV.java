package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolChainsawMV extends ToolChainsawLV {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 200;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 400;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 3200;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 800;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.5F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 2.0F;
    }
}
