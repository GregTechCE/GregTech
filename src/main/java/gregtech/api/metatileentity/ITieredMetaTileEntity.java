package gregtech.api.metatileentity;

import gregtech.api.GTValues;

public interface ITieredMetaTileEntity {

    String getMetaName();

    /**
     * Tier of machine determines it's input voltage, storage and generation rate
     *
     * @return tier of this machine
     */
    int getTier();

    /**
     * Returns tier less tooltip key
     * It is generated from getMetaName by removing last part (like: ".lv") and adding ".tooltip" part
     *
     * @return tier less tooltip key
     */
    default String getTierlessTooltipKey() {
        String metaName = getMetaName();
        int lastIndexOfDot = metaName.lastIndexOf('.');
        String voltageName = lastIndexOfDot == -1 ? null : metaName.substring(lastIndexOfDot + 1);
        if (isVoltageName(voltageName)) {
            return metaName.substring(0, lastIndexOfDot);
        }
        return metaName;
    }

    static boolean isVoltageName(String string) {
        for (String voltageName : GTValues.VN) {
            if (voltageName.equalsIgnoreCase(string)) return true;
        }
        return false;
    }
}
