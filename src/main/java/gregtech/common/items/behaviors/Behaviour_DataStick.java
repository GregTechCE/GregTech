package gregtech.common.items.behaviors;

import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import java.util.List;

public class Behaviour_DataStick extends Behaviour_None {

    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        String tString = GTUtility.ItemNBT.getBookTitle(aStack);
        if (GTUtility.isStringValid(tString)) {
            aList.add(tString);
        }
        tString = GTUtility.ItemNBT.getBookAuthor(aStack);
        if (GTUtility.isStringValid(tString)) {
            aList.add("by " + tString);
        }
        short tMapID = GTUtility.ItemNBT.getMapID(aStack);
        if (tMapID >= 0) {
            aList.add("Map ID: " + tMapID);
        }
        tString = GTUtility.ItemNBT.getPunchCardData(aStack);
        if (GTUtility.isStringValid(tString)) {
            aList.add("Punch Card Data");
            int i = 0;int j = tString.length();
            for (; i < j; i += 64) {
                aList.add(tString.substring(i, Math.min(i + 64, j)));
            }
        }
        return aList;
    }

}
