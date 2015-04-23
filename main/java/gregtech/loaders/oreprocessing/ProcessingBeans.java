/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  6:   */ import gregtech.api.util.GT_ModHandler;
/*  7:   */ import gregtech.api.util.GT_OreDictUnificator;
/*  8:   */ import gregtech.api.util.GT_Utility;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ 
/* 11:   */ public class ProcessingBeans
/* 12:   */   implements IOreRecipeRegistrator
/* 13:   */ {
/* 14:   */   public ProcessingBeans()
/* 15:   */   {
/* 16:14 */     OrePrefixes.beans.add(this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 20:   */   {
/* 21:19 */     if (aOreDictName.equals("beansCocoa")) {
/* 22:19 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cocoa, 1L));
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingBeans
 * JD-Core Version:    0.7.0.1
 */