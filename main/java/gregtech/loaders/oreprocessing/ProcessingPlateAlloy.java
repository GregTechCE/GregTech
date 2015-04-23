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
/* 14:   */ public class ProcessingPlateAlloy
/* 15:   */   implements IOreRecipeRegistrator
/* 16:   */ {
/* 17:   */   public ProcessingPlateAlloy()
/* 18:   */   {
/* 19:17 */     OrePrefixes.plateAlloy.add(this);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 23:   */   {
/* 24:22 */     if (aOreDictName.equals("plateAlloyCarbon"))
/* 25:   */     {
/* 26:23 */       GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("generator", 1L), GT_Utility.copyAmount(4L, new Object[] { aStack }), GT_ModHandler.getIC2Item("windMill", 1L), 6400, 8);
/* 27:   */     }
/* 28:25 */     else if (aOreDictName.equals("plateAlloyAdvanced"))
/* 29:   */     {
/* 30:26 */       GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new ItemStack(Blocks.glass, 3, 32767), GT_ModHandler.getIC2Item("reinforcedGlass", 4L), 400, 4, false);
/* 31:27 */       GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 3L), GT_ModHandler.getIC2Item("reinforcedGlass", 4L), 400, 4, false);
/* 32:   */     }
/* 33:29 */     else if (aOreDictName.equals("plateAlloyIridium"))
/* 34:   */     {
/* 35:30 */       GT_ModHandler.removeRecipeByOutput(aStack);
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingPlateAlloy
 * JD-Core Version:    0.7.0.1
 */