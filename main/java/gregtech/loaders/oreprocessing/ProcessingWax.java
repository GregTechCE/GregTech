/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  8:   */ import gregtech.api.util.GT_Utility;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ 
/* 11:   */ public class ProcessingWax
/* 12:   */   implements IOreRecipeRegistrator
/* 13:   */ {
/* 14:   */   public ProcessingWax()
/* 15:   */   {
/* 16:13 */     OrePrefixes.wax.add(this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 20:   */   {
/* 21:18 */     if (aOreDictName.equals("waxMagical")) {
/* 22:18 */       GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, 6, 5);
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingWax
 * JD-Core Version:    0.7.0.1
 */