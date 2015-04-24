/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.ItemList;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class ProcessingCrop implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingCrop()
/*    */   {
/* 17 */     OrePrefixes.crop.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, net.minecraft.item.ItemStack aStack)
/*    */   {
/* 22 */     GT_ModHandler.addCompressionRecipe(gregtech.api.util.GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
/* 23 */     if (aOreDictName.equals("cropTea")) {
/* 24 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.tea"), false);
/* 25 */       GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.tea"), false);
/* 26 */     } else if (aOreDictName.equals("cropGrape")) {
/* 27 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.grapejuice"), false);
/* 28 */       GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.grapejuice"), false);
/* 29 */     } else if (aOreDictName.equals("cropChilipepper")) {
/* 30 */       GT_ModHandler.addPulverisationRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chili, 1L));
/* 31 */     } else if (aOreDictName.equals("cropCoffee")) {
/* 32 */       GT_ModHandler.addPulverisationRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coffee, 1L));
/* 33 */     } else if (aOreDictName.equals("cropPotato")) {
/* 34 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Raw_PotatoChips.get(1L, new Object[0]), 64, 4);
/* 35 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Stripes.get(0L, new Object[0]), ItemList.Food_Raw_Fries.get(1L, new Object[0]), 64, 4);
/* 36 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.potatojuice"), true);
/* 37 */       GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.potatojuice"), true);
/* 38 */     } else if (aOreDictName.equals("cropLemon")) {
/* 39 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Lemon.get(4L, new Object[0]), 64, 4);
/* 40 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.lemonjuice"), false);
/* 41 */       GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.lemonjuice"), false);
/* 42 */       GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.getFluid("potion.vodka"), FluidRegistry.getFluid("potion.leninade"), true);
/* 43 */     } else if (aOreDictName.equals("cropTomato")) {
/* 44 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Tomato.get(4L, new Object[0]), 64, 4);
/* 45 */     } else if (aOreDictName.equals("cropCucumber")) {
/* 46 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Cucumber.get(4L, new Object[0]), 64, 4);
/* 47 */     } else if (aOreDictName.equals("cropOnion")) {
/* 48 */       GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Onion.get(4L, new Object[0]), 64, 4);
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingCrop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */