/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.objects.ItemData;
/* 10:   */ import gregtech.api.objects.MaterialStack;
/* 11:   */ import gregtech.api.util.GT_ModHandler;
/* 12:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 13:   */ import gregtech.api.util.GT_Utility;
/* 14:   */ import net.minecraft.item.ItemStack;
/* 15:   */ 
/* 16:   */ public class ProcessingFood
/* 17:   */   implements IOreRecipeRegistrator
/* 18:   */ {
/* 19:   */   public ProcessingFood()
/* 20:   */   {
/* 21:18 */     OrePrefixes.food.add(this);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:23 */     if (aOreDictName.equals("foodCheese"))
/* 27:   */     {
/* 28:24 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Cheese.get(4L, new Object[0]), 64, 4);
/* 29:25 */       GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Cheese, 3628800L, new MaterialStack[0]));
/* 30:   */     }
/* 31:26 */     else if (aOreDictName.equals("foodDough"))
/* 32:   */     {
/* 33:27 */       GT_ModHandler.removeFurnaceSmelting(aStack);
/* 34:28 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Food_Flat_Dough.get(1L, new Object[0]), 16, 4);
/* 35:   */       
/* 36:30 */       GT_Values.RA.addMixerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sugar, 1L), null, null, null, null, ItemList.Food_Dough_Sugar.get(2L, new Object[0]), 32, 8);
/* 37:31 */       GT_Values.RA.addMixerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cocoa, 1L), null, null, null, null, ItemList.Food_Dough_Chocolate.get(2L, new Object[0]), 32, 8);
/* 38:32 */       GT_Values.RA.addMixerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chocolate, 1L), null, null, null, null, ItemList.Food_Dough_Chocolate.get(2L, new Object[0]), 32, 8);
/* 39:   */       
/* 40:34 */       GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Mold_Bun.get(0L, new Object[0]), ItemList.Food_Raw_Bun.get(1L, new Object[0]), 128, 4);
/* 41:35 */       GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Bread.get(0L, new Object[0]), ItemList.Food_Raw_Bread.get(1L, new Object[0]), 256, 4);
/* 42:36 */       GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), ItemList.Shape_Mold_Baguette.get(0L, new Object[0]), ItemList.Food_Raw_Baguette.get(1L, new Object[0]), 384, 4);
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingFood
 * JD-Core Version:    0.7.0.1
 */