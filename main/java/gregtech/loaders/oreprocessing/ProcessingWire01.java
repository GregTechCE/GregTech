/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.enums.SubTag;
/*  8:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  9:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/* 10:   */ import gregtech.api.util.GT_ModHandler;
/* 11:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 12:   */ import gregtech.api.util.GT_Utility;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class ProcessingWire01
/* 16:   */   implements IOreRecipeRegistrator
/* 17:   */ {
/* 18:   */   public ProcessingWire01()
/* 19:   */   {
/* 20:17 */     OrePrefixes.wireGt01.add(this);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 24:   */   {
/* 25:22 */     GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), new Object[] { aOreDictName, OrePrefixes.plate.get(Materials.Rubber) });
/* 26:23 */     if (!aMaterial.contains(SubTag.NO_SMASHING))
/* 27:   */     {
/* 28:24 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.springSmall, aMaterial, 2L), 100, 8);
/* 29:25 */       GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 4L), 200, 8);
/* 30:   */     }
/* 31:27 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), 100, 8);
/* 32:28 */     GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 1L), 100, 8);
/* 33:29 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt02, aMaterial, 1L), 150, 8);
/* 34:30 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt04, aMaterial, 1L), 200, 8);
/* 35:31 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt08, aMaterial, 1L), 300, 8);
/* 36:32 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(12L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 12L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt12, aMaterial, 1L), 400, 8);
/* 37:33 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(16L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 16L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt16, aMaterial, 1L), 500, 8);
/* 38:   */   }
/* 39:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingWire01
 * JD-Core Version:    0.7.0.1
 */