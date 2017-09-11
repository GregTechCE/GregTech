package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolTurbineHuge extends ToolTurbine {

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }
}
