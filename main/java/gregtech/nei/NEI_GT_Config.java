/*  1:   */ package gregtech.nei;
/*  2:   */ 
/*  3:   */ import codechicken.nei.api.IConfigureNEI;
/*  4:   */ import gregtech.api.util.GT_Recipe;
/*  5:   */ 
/*  6:   */ public class NEI_GT_Config
/*  7:   */   implements IConfigureNEI
/*  8:   */ {
/*  9: 8 */   public static boolean sIsAdded = true;
/* 10:   */   
/* 11:   */   public void loadConfig()
/* 12:   */   {
/* 13:12 */     sIsAdded = false;
/* 14:14 */     for (GT_Recipe.GT_Recipe_Map tMap : GT_Recipe.GT_Recipe_Map.sMappings) {
/* 15:14 */       if (tMap.mNEIAllowed) {
/* 16:14 */         new GT_NEI_DefaultHandler(tMap);
/* 17:   */       }
/* 18:   */     }
/* 19:26 */     sIsAdded = true;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getName()
/* 23:   */   {
/* 24:31 */     return "GregTech NEI Plugin";
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getVersion()
/* 28:   */   {
/* 29:37 */     return "(5.03a)";
/* 30:   */   }
/* 31:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.nei.NEI_GT_Config
 * JD-Core Version:    0.7.0.1
 */