package gregtech.integration.jei.utils;

import gregtech.api.GTValues;
import gregtech.common.blocks.BlockWireCoil;

public class JEIHelpers {

    /**
     * Returns the short name for the minimum required power tier for a specified voltage
     */
    public static String getMinTierForVoltage(long voltage) {
        for (int i = 0; i < GTValues.V.length; i++) {
            if (voltage <= GTValues.V[i]) {
                return GTValues.VN[i];
            }
        }
        return "";
    }

    public static String getMinTierForTemperature(String propertyKey, Object prop) {
        if (!propertyKey.equals("blast_furnace_temperature")) {
            return "";
        }

        for (BlockWireCoil.CoilType values : BlockWireCoil.CoilType.values()) {
             if ((Integer)prop <= values.getCoilTemperature()) {
                 return values.getMaterial().getLocalizedName();
             }
        }

        return "";
    }
}
