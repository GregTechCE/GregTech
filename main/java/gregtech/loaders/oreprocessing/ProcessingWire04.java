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
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingWire04
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingWire04()
/* 17:   */   {
/* 18:15 */     OrePrefixes.wireGt04.add(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 22:   */   {
/* 23:20 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt04, aMaterial, 1L), 200, 8);
/* 24:21 */     GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt04, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 2L), 200, 8);
/* 25:22 */     GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt04, aMaterial, 1L), new Object[] { aOreDictName, OrePrefixes.plate.get(Materials.Rubber), OrePrefixes.plate.get(Materials.Rubber) });
/* 26:23 */     GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 4L), new Object[] { aOreDictName });
/* 27:24 */     GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new Object[] { OrePrefixes.wireGt02.get(aMaterial), OrePrefixes.wireGt02.get(aMaterial) });
/* 28:   */   }
/* 29:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingWire04
 * JD-Core Version:    0.7.0.1
 */