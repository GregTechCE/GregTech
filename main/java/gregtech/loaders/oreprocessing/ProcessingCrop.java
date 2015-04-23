/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
/* 10:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 11:   */ import gregtech.api.util.GT_Utility;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import net.minecraftforge.fluids.FluidRegistry;
/* 14:   */ import net.minecraftforge.fluids.FluidStack;
/* 15:   */ 
/* 16:   */ public class ProcessingCrop
/* 17:   */   implements IOreRecipeRegistrator
/* 18:   */ {
/* 19:   */   public ProcessingCrop()
/* 20:   */   {
/* 21:17 */     OrePrefixes.crop.add(this);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:22 */     GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
/* 27:23 */     if (aOreDictName.equals("cropTea"))
/* 28:   */     {
/* 29:24 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.tea"), false);
/* 30:25 */       GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.tea"), false);
/* 31:   */     }
/* 32:26 */     else if (aOreDictName.equals("cropGrape"))
/* 33:   */     {
/* 34:27 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.grapejuice"), false);
/* 35:28 */       GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.grapejuice"), false);
/* 36:   */     }
/* 37:29 */     else if (aOreDictName.equals("cropChilipepper"))
/* 38:   */     {
/* 39:30 */       GT_ModHandler.addPulverisationRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chili, 1L));
/* 40:   */     }
/* 41:31 */     else if (aOreDictName.equals("cropCoffee"))
/* 42:   */     {
/* 43:32 */       GT_ModHandler.addPulverisationRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coffee, 1L));
/* 44:   */     }
/* 45:33 */     else if (aOreDictName.equals("cropPotato"))
/* 46:   */     {
/* 47:34 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Raw_PotatoChips.get(1L, new Object[0]), 64, 4);
/* 48:35 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Stripes.get(0L, new Object[0]), ItemList.Food_Raw_Fries.get(1L, new Object[0]), 64, 4);
/* 49:36 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.potatojuice"), true);
/* 50:37 */       GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.potatojuice"), true);
/* 51:   */     }
/* 52:38 */     else if (aOreDictName.equals("cropLemon"))
/* 53:   */     {
/* 54:39 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Lemon.get(4L, new Object[0]), 64, 4);
/* 55:40 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.lemonjuice"), false);
/* 56:41 */       GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.lemonjuice"), false);
/* 57:42 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.getFluid("potion.vodka"), FluidRegistry.getFluid("potion.leninade"), true);
/* 58:   */     }
/* 59:43 */     else if (aOreDictName.equals("cropTomato"))
/* 60:   */     {
/* 61:44 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Tomato.get(4L, new Object[0]), 64, 4);
/* 62:   */     }
/* 63:45 */     else if (aOreDictName.equals("cropCucumber"))
/* 64:   */     {
/* 65:46 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Cucumber.get(4L, new Object[0]), 64, 4);
/* 66:   */     }
/* 67:47 */     else if (aOreDictName.equals("cropOnion"))
/* 68:   */     {
/* 69:48 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Onion.get(4L, new Object[0]), 64, 4);
/* 70:   */     }
/* 71:   */   }
/* 72:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingCrop
 * JD-Core Version:    0.7.0.1
 */