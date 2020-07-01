package gregtech.integration.jei.utils;

import gregtech.api.GTValues;

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

}
