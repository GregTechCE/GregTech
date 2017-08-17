package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolTurbineSmall extends ToolTurbine {

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 0.0F;
    }

    @Override
    public IIconContainer getTurbineIcon() {
        return Textures.ItemIcons.TURBINE_SMALL;
    }
}
