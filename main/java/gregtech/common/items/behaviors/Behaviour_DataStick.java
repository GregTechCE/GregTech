/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  4:   */ import gregtech.api.util.GT_Utility;
/*  5:   */ import gregtech.api.util.GT_Utility.ItemNBT;
/*  6:   */ import java.util.List;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ 
/*  9:   */ public class Behaviour_DataStick
/* 10:   */   extends Behaviour_None
/* 11:   */ {
/* 12:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 13:   */   {
/* 14:14 */     String tString = GT_Utility.ItemNBT.getBookTitle(aStack);
/* 15:15 */     if (GT_Utility.isStringValid(tString)) {
/* 16:15 */       aList.add(tString);
/* 17:   */     }
/* 18:16 */     tString = GT_Utility.ItemNBT.getBookAuthor(aStack);
/* 19:17 */     if (GT_Utility.isStringValid(tString)) {
/* 20:17 */       aList.add("by " + tString);
/* 21:   */     }
/* 22:19 */     short tMapID = GT_Utility.ItemNBT.getMapID(aStack);
/* 23:20 */     if (tMapID >= 0) {
/* 24:20 */       aList.add("Map ID: " + tMapID);
/* 25:   */     }
/* 26:21 */     tString = GT_Utility.ItemNBT.getPunchCardData(aStack);
/* 27:22 */     if (GT_Utility.isStringValid(tString))
/* 28:   */     {
/* 29:23 */       aList.add("Punch Card Data");
/* 30:24 */       int i = 0;
/* 31:24 */       for (int j = tString.length(); i < j; i += 64) {
/* 32:24 */         aList.add(tString.substring(i, Math.min(i + 64, j)));
/* 33:   */       }
/* 34:   */     }
/* 35:26 */     return aList;
/* 36:   */   }
/* 37:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_DataStick
 * JD-Core Version:    0.7.0.1
 */