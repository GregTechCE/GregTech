/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  4:   */ import gregtech.api.util.GT_Utility;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.nbt.NBTTagCompound;
/*  8:   */ 
/*  9:   */ public class Behaviour_PrintedPages
/* 10:   */   extends Behaviour_None
/* 11:   */ {
/* 12:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 13:   */   {
/* 14:14 */     if (GT_Utility.isStringValid(getTitle(aStack))) {
/* 15:14 */       aList.add(getTitle(aStack));
/* 16:   */     }
/* 17:15 */     if (GT_Utility.isStringValid(getAuthor(aStack))) {
/* 18:15 */       aList.add("by " + getAuthor(aStack));
/* 19:   */     }
/* 20:16 */     return aList;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static String getTitle(ItemStack aStack)
/* 24:   */   {
/* 25:20 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 26:21 */     if (tNBT == null) {
/* 27:21 */       return "";
/* 28:   */     }
/* 29:22 */     return tNBT.getString("title");
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static String getAuthor(ItemStack aStack)
/* 33:   */   {
/* 34:26 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 35:27 */     if (tNBT == null) {
/* 36:27 */       return "";
/* 37:   */     }
/* 38:28 */     return tNBT.getString("author");
/* 39:   */   }
/* 40:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_PrintedPages
 * JD-Core Version:    0.7.0.1
 */