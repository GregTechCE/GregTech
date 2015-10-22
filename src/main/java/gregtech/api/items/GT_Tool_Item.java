package gregtech.api.items;

import gregtech.api.util.GT_ModHandler;

/**
 * This is just a basic Tool, which has normal durability and could break Blocks.
 */
public class GT_Tool_Item extends GT_Generic_Item {
    public GT_Tool_Item(String aUnlocalized, String aEnglish, String aTooltip, int aMaxDamage, int aEntityDamage, boolean aSwingIfUsed) {
        this(aUnlocalized, aEnglish, aTooltip, aMaxDamage, aEntityDamage, aSwingIfUsed, -1, -1);
    }

    public GT_Tool_Item(String aUnlocalized, String aEnglish, String aTooltip, int aMaxDamage, int aEntityDamage, boolean aSwingIfUsed, int aChargedGTID, int aDisChargedGTID) {
        this(aUnlocalized, aEnglish, aTooltip, aMaxDamage, aEntityDamage, aSwingIfUsed, aChargedGTID, aDisChargedGTID, 0, 0.0F);
    }

    public GT_Tool_Item(String aUnlocalized, String aEnglish, String aTooltip, int aMaxDamage, int aEntityDamage, boolean aSwingIfUsed, int aChargedGTID, int aDisChargedGTID, int aToolQuality, float aToolStrength) {
        super(aUnlocalized, aEnglish, aTooltip, aTooltip != null && !aTooltip.equals("Doesn't work as intended, this is a Bug"));
        setMaxDamage(aMaxDamage);
        setMaxStackSize(1);
        setNoRepair();
        setFull3D();
        GT_ModHandler.registerBoxableItemToToolBox(this);
    }
}