package gregtech.common.items.behaviors;

import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

public class Behaviour_DataOrb
        extends Behaviour_None {
    public static void copyInventory(ItemStack[] aInventory, ItemStack[] aNewContent, int aIndexlength) {
        for (int i = 0; i < aIndexlength; i++) {
            if (aNewContent[i] == null) {
                aInventory[i] = null;
            } else {
                aInventory[i] = GT_Utility.copy(new Object[]{aNewContent[i]});
            }
        }
    }

    public static String getDataName(ItemStack aStack) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            return "";
        }
        return tNBT.getString("mDataName");
    }

    public static String getDataTitle(ItemStack aStack) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            return "";
        }
        return tNBT.getString("mDataTitle");
    }

    public static NBTTagCompound setDataName(ItemStack aStack, String aDataName) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            tNBT = new NBTTagCompound();
        }
        tNBT.setString("mDataName", aDataName);
        aStack.setTagCompound(tNBT);
        return tNBT;
    }

    public static NBTTagCompound setDataTitle(ItemStack aStack, String aDataTitle) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            tNBT = new NBTTagCompound();
        }
        tNBT.setString("mDataTitle", aDataTitle);
        aStack.setTagCompound(tNBT);
        return tNBT;
    }

    public static ItemStack[] getNBTInventory(ItemStack aStack) {
        ItemStack[] tInventory = new ItemStack[256];
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            return tInventory;
        }
        NBTTagList tNBT_ItemList = tNBT.getTagList("Inventory", 10);
        for (int i = 0; i < tNBT_ItemList.tagCount(); i++) {
            NBTTagCompound tag = tNBT_ItemList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if ((slot >= 0) && (slot < tInventory.length)) {
                tInventory[slot] = GT_Utility.loadItem(tag);
            }
        }
        return tInventory;
    }

    public static NBTTagCompound setNBTInventory(ItemStack aStack, ItemStack[] aInventory) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            tNBT = new NBTTagCompound();
        }
        NBTTagList tNBT_ItemList = new NBTTagList();
        for (int i = 0; i < aInventory.length; i++) {
            ItemStack stack = aInventory[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                tNBT_ItemList.appendTag(tag);
            }
        }
        tNBT.setTag("Inventory", tNBT_ItemList);
        aStack.setTagCompound(tNBT);
        return tNBT;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        if (!(getDataTitle(aStack).length() == 0)) {
            aList.add(getDataTitle(aStack));
            aList.add(getDataName(aStack));
        }
        return aList;
    }
}
