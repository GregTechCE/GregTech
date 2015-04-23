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
/* 13:   */ public class ProcessingCrushedPurified
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingCrushedPurified()
/* 17:   */   {
/* 18:15 */     OrePrefixes.crushedPurified.add(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 22:   */   {
/* 23:20 */     GT_ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), (int)Math.min(5000L, Math.abs(aMaterial.getMass() * 20L)), new Object[] { GT_OreDictUnificator.get(OrePrefixes.crushedCentrifuged, aMaterial.mMacerateInto, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L) });
/* 24:21 */     ItemStack tGem = GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);
/* 25:22 */     if (tGem != null) {
/* 26:22 */       GT_Values.RA.addSifterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.gemExquisite, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, tGem, 1L), tGem, GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, tGem, 1L) }, new int[] { 100, 400, 1500, 2000, 4000, 5000 }, 800, 16);
/* 27:   */     }
/* 28:   */   }
/* 29:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingCrushedPurified
 * JD-Core Version:    0.7.0.1
 */