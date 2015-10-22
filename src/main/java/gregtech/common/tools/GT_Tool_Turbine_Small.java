package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;

public class GT_Tool_Turbine_Small extends GT_Tool_Turbine {
    @Override
    public float getSpeedMultiplier() {
        return 1.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    @Override
    public float getBaseDamage() {
        return 0.0F;
    }

    @Override
    public IIconContainer getTurbineIcon() {
        return Textures.ItemIcons.TURBINE_SMALL;
    }
}
