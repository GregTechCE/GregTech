package gregtech.common.items.behaviors;

import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class Behaviour_Sonictron
        extends Behaviour_None {
    public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Sonictron();

    public static int getCurrentIndex(ItemStack aStack) {
        NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
        if (tNBTTagCompound == null) {
            tNBTTagCompound = new NBTTagCompound();
        }
        return tNBTTagCompound.getInteger("mCurrentIndex");
    }

    public static int getTickTimer(ItemStack aStack) {
        NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
        if (tNBTTagCompound == null) {
            tNBTTagCompound = new NBTTagCompound();
        }
        return tNBTTagCompound.getInteger("mTickTimer");
    }

    public static NBTTagCompound setCurrentIndex(ItemStack aStack, int aIndex) {
        NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
        if (tNBTTagCompound == null) {
            tNBTTagCompound = new NBTTagCompound();
        }
        tNBTTagCompound.setInteger("mCurrentIndex", aIndex);
        return tNBTTagCompound;
    }

    public static NBTTagCompound setTickTimer(ItemStack aStack, int aTime) {
        NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
        if (tNBTTagCompound == null) {
            tNBTTagCompound = new NBTTagCompound();
        }
        tNBTTagCompound.setInteger("mTickTimer", aTime);
        return tNBTTagCompound;
    }

    public static ItemStack[] getNBTInventory(ItemStack aStack) {
        ItemStack[] tInventory = new ItemStack[64];
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

    public static void copyInventory(ItemStack[] aInventory, ItemStack[] aNewContent, int aIndexlength) {
        for (int i = 0; i < aIndexlength; i++) {
            if (aNewContent[i] == null) {
                aInventory[i] = null;
            } else {
                aInventory[i] = GT_Utility.copy(new Object[]{aNewContent[i]});
            }
        }
    }

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        setCurrentIndex(aStack, -1);
        return false;
    }

    public ItemStack onItemRightClick(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        setCurrentIndex(aStack, 0);
        return aStack;
    }

    public void onUpdate(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, Entity aPlayer, int aTimer, boolean aIsInHand) {
        int tTickTimer = getTickTimer(aStack);
        int tCurrentIndex = getCurrentIndex(aStack);
        if ((tTickTimer++ % 2 == 0) && (tCurrentIndex > -1)) {
            ItemStack[] tInventory = getNBTInventory(aStack);
            GT_Values.GT.doSonictronSound(tInventory[tCurrentIndex], aPlayer.worldObj, aPlayer.posX, aPlayer.posY, aPlayer.posZ);
            tCurrentIndex++;
            if (tCurrentIndex > 63) {
                tCurrentIndex = -1;
            }
        }
        setTickTimer(aStack, tTickTimer);
        setCurrentIndex(aStack, tCurrentIndex);
    }
}
