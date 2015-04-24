/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingPlateAlloy implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingPlateAlloy()
/*    */   {
/* 17 */     OrePrefixes.plateAlloy.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 22 */     if (aOreDictName.equals("plateAlloyCarbon")) {
/* 23 */       GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("generator", 1L), GT_Utility.copyAmount(4L, new Object[] { aStack }), GT_ModHandler.getIC2Item("windMill", 1L), 6400, 8);
/*    */     }
/* 25 */     else if (aOreDictName.equals("plateAlloyAdvanced")) {
/* 26 */       GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new ItemStack(Blocks.glass, 3, 32767), GT_ModHandler.getIC2Item("reinforcedGlass", 4L), 400, 4, false);
/* 27 */       GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 3L), GT_ModHandler.getIC2Item("reinforcedGlass", 4L), 400, 4, false);
/*    */     }
/* 29 */     else if (aOreDictName.equals("plateAlloyIridium")) {
/* 30 */       GT_ModHandler.removeRecipeByOutput(aStack);
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingPlateAlloy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */