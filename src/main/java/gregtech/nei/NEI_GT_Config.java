package gregtech.nei;

import codechicken.nei.api.IConfigureNEI;
import gregtech.api.util.GT_Recipe;

public class NEI_GT_Config
        implements IConfigureNEI {
    public static boolean sIsAdded = true;

    public void loadConfig() {
        sIsAdded = false;
        for (GT_Recipe.GT_Recipe_Map tMap : GT_Recipe.GT_Recipe_Map.sMappings) {
            if (tMap.mNEIAllowed) {
                new GT_NEI_DefaultHandler(tMap);
            }
        }
        sIsAdded = true;
    }

    public String getName() {
        return "GregTech NEI Plugin";
    }

    public String getVersion() {
        return "(5.03a)";
    }
}
