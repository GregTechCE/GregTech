/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  4:   */ import gregtech.api.util.GT_Utility;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.nbt.NBTTagCompound;
/*  8:   */ import net.minecraft.nbt.NBTTagList;
/*  9:   */ 
/* 10:   */ public class Behaviour_DataOrb
/* 11:   */   extends Behaviour_None
/* 12:   */ {
/* 13:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 14:   */   {
/* 15:15 */     if (!getDataTitle(aStack).equals(""))
/* 16:   */     {
/* 17:16 */       aList.add(getDataTitle(aStack));
/* 18:17 */       aList.add(getDataName(aStack));
/* 19:   */     }
/* 20:19 */     return aList;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static void copyInventory(ItemStack[] aInventory, ItemStack[] aNewContent, int aIndexlength)
/* 24:   */   {
/* 25:23 */     for (int i = 0; i < aIndexlength; i++) {
/* 26:24 */       if (aNewContent[i] == null) {
/* 27:25 */         aInventory[i] = null;
/* 28:   */       } else {
/* 29:27 */         aInventory[i] = GT_Utility.copy(new Object[] { aNewContent[i] });
/* 30:   */       }
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static String getDataName(ItemStack aStack)
/* 35:   */   {
/* 36:32 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 37:33 */     if (tNBT == null) {
/* 38:33 */       return "";
/* 39:   */     }
/* 40:34 */     return tNBT.getString("mDataName");
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static String getDataTitle(ItemStack aStack)
/* 44:   */   {
/* 45:38 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 46:39 */     if (tNBT == null) {
/* 47:39 */       return "";
/* 48:   */     }
/* 49:40 */     return tNBT.getString("mDataTitle");
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static NBTTagCompound setDataName(ItemStack aStack, String aDataName)
/* 53:   */   {
/* 54:44 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 55:45 */     if (tNBT == null) {
/* 56:45 */       tNBT = new NBTTagCompound();
/* 57:   */     }
/* 58:46 */     tNBT.setString("mDataName", aDataName);
/* 59:47 */     aStack.setTagCompound(tNBT);
/* 60:48 */     return tNBT;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public static NBTTagCompound setDataTitle(ItemStack aStack, String aDataTitle)
/* 64:   */   {
/* 65:52 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 66:53 */     if (tNBT == null) {
/* 67:53 */       tNBT = new NBTTagCompound();
/* 68:   */     }
/* 69:54 */     tNBT.setString("mDataTitle", aDataTitle);
/* 70:55 */     aStack.setTagCompound(tNBT);
/* 71:56 */     return tNBT;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static ItemStack[] getNBTInventory(ItemStack aStack)
/* 75:   */   {
/* 76:60 */     ItemStack[] tInventory = new ItemStack[256];
/* 77:61 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 78:62 */     if (tNBT == null) {
/* 79:62 */       return tInventory;
/* 80:   */     }
/* 81:64 */     NBTTagList tNBT_ItemList = tNBT.getTagList("Inventory", 10);
/* 82:65 */     for (int i = 0; i < tNBT_ItemList.tagCount(); i++)
/* 83:   */     {
/* 84:66 */       NBTTagCompound tag = tNBT_ItemList.getCompoundTagAt(i);
/* 85:67 */       byte slot = tag.getByte("Slot");
/* 86:68 */       if ((slot >= 0) && (slot < tInventory.length)) {
/* 87:69 */         tInventory[slot] = GT_Utility.loadItem(tag);
/* 88:   */       }
/* 89:   */     }
/* 90:72 */     return tInventory;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public static NBTTagCompound setNBTInventory(ItemStack aStack, ItemStack[] aInventory)
/* 94:   */   {
/* 95:76 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 96:77 */     if (tNBT == null) {
/* 97:77 */       tNBT = new NBTTagCompound();
/* 98:   */     }
/* 99:79 */     NBTTagList tNBT_ItemList = new NBTTagList();
/* :0:80 */     for (int i = 0; i < aInventory.length; i++)
/* :1:   */     {
/* :2:81 */       ItemStack stack = aInventory[i];
/* :3:82 */       if (stack != null)
/* :4:   */       {
/* :5:83 */         NBTTagCompound tag = new NBTTagCompound();
/* :6:84 */         tag.setByte("Slot", (byte)i);
/* :7:85 */         stack.writeToNBT(tag);
/* :8:86 */         tNBT_ItemList.appendTag(tag);
/* :9:   */       }
/* ;0:   */     }
/* ;1:89 */     tNBT.setTag("Inventory", tNBT_ItemList);
/* ;2:90 */     aStack.setTagCompound(tNBT);
/* ;3:91 */     return tNBT;
/* ;4:   */   }
/* ;5:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_DataOrb
 * JD-Core Version:    0.7.0.1
 */