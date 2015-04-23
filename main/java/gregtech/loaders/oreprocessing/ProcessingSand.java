/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  8:   */ import gregtech.api.util.GT_ModHandler;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import gregtech.api.util.GT_Utility;
/* 11:   */ import net.minecraft.init.Blocks;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class ProcessingSand
/* 15:   */   implements IOreRecipeRegistrator
/* 16:   */ {
/* 17:   */   public ProcessingSand()
/* 18:   */   {
/* 19:16 */     OrePrefixes.sand.add(this);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 23:   */   {
/* 24:21 */     if (aOreDictName.equals("sandCracked")) {
/* 25:22 */       GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(16L, new Object[] { aStack }), -1, GT_ModHandler.getFuelCan(25000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 8L), null, null, null, new ItemStack(Blocks.sand, 10), 2500);
/* 26:23 */     } else if (aOreDictName.equals("sandOil")) {
/* 27:24 */       GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), 1, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Oil, 1L), new ItemStack(Blocks.sand, 1, 0), null, null, null, null, 1000);
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingSand
 * JD-Core Version:    0.7.0.1
 */