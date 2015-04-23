/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.enums.SubTag;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import gregtech.api.util.GT_Utility;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingGemFlawless
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingGemFlawless()
/* 17:   */   {
/* 18:15 */     OrePrefixes.gemFlawless.add(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 22:   */   {
/* 23:20 */     if (aMaterial.mFuelPower > 0) {
/* 24:20 */       GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, aMaterial.mFuelPower * 4, aMaterial.mFuelType);
/* 25:   */     }
/* 26:21 */     if (!aMaterial.contains(SubTag.NO_WORKING)) {
/* 27:21 */       GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1L), GT_OreDictUnificator.getDust(aMaterial, aPrefix.mMaterialAmount - OrePrefixes.stickLong.mMaterialAmount), (int)Math.max(aMaterial.getMass() * 5L, 1L), 16);
/* 28:   */     }
/* 29:22 */     GT_Values.RA.addForgeHammerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 2L), 64, 16);
/* 30:   */   }
/* 31:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingGemFlawless
 * JD-Core Version:    0.7.0.1
 */