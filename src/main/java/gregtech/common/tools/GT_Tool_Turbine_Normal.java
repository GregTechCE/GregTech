package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;

public class GT_Tool_Turbine_Normal extends GT_Tool_Turbine {
    @Override
    public float getSpeedMultiplier() {
        return 2.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 2.0F;
    }

    @Override
    public float getBaseDamage() {
        return 2.5F;
    }

    @Override
    public IIconContainer getTurbineIcon() {
        return Textures.ItemIcons.TURBINE;
    }
}
