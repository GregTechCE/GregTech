package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolTurbineLarge extends ToolTurbine {

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 5.0F;
    }

    @Override
    public IIconContainer getTurbineIcon() {
        return Textures.ItemIcons.TURBINE_LARGE;
    }
}
