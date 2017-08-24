package gregtech.common.items.behaviors;

import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class Behaviour_PrintedPages extends Behaviour_None {

    public static String getTitle(ItemStack aStack) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            return "";
        }
        return tNBT.getString("title");
    }

    public static String getAuthor(ItemStack aStack) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            return "";
        }
        return tNBT.getString("author");
    }

    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        if (GTUtility.isStringValid(getTitle(aStack))) {
            aList.add(getTitle(aStack));
        }
        if (GTUtility.isStringValid(getAuthor(aStack))) {
            aList.add("by " + getAuthor(aStack));
        }
        return aList;
    }
}
