/*  1:   */ package gregtech.common.items;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.items.GT_MetaGenerated_Item_X32;
/*  6:   */ 
/*  7:   */ public class GT_MetaGenerated_Item_03
/*  8:   */   extends GT_MetaGenerated_Item_X32
/*  9:   */ {
/* 10:   */   public static GT_MetaGenerated_Item_03 INSTANCE;
/* 11:   */   
/* 12:   */   public GT_MetaGenerated_Item_03()
/* 13:   */   {
/* 14:11 */     super("metaitem.03", new OrePrefixes[] { OrePrefixes.crateGtDust, OrePrefixes.crateGtIngot, OrePrefixes.crateGtGem, OrePrefixes.crateGtPlate });
/* 15:12 */     INSTANCE = this;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean doesShowInCreative(OrePrefixes aPrefix, Materials aMaterial, boolean aDoShowAllItems)
/* 19:   */   {
/* 20:17 */     return aDoShowAllItems;
/* 21:   */   }
/* 22:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.GT_MetaGenerated_Item_03
 * JD-Core Version:    0.7.0.1
 */