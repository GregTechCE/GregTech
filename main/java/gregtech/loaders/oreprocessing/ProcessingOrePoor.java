/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.enums.SubTag;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
/* 10:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 11:   */ import gregtech.api.util.GT_Utility;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class ProcessingOrePoor
/* 15:   */   implements IOreRecipeRegistrator
/* 16:   */ {
/* 17:   */   public ProcessingOrePoor()
/* 18:   */   {
/* 19:15 */     OrePrefixes.orePoor.add(this);
/* 20:16 */     OrePrefixes.oreSmall.add(this);
/* 21:17 */     OrePrefixes.oreNormal.add(this);
/* 22:18 */     OrePrefixes.oreRich.add(this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 26:   */   {
/* 27:23 */     int aMultiplier = 1;
/* 28:24 */     switch (aPrefix.ordinal())
/* 29:   */     {
/* 30:   */     case 1: 
/* 31:25 */       aMultiplier = 1; break;
/* 32:   */     case 2: 
/* 33:26 */       aMultiplier = 2; break;
/* 34:   */     case 3: 
/* 35:27 */       aMultiplier = 3; break;
/* 36:   */     case 4: 
/* 37:28 */       aMultiplier = 4;
/* 38:   */     }
/* 39:30 */     if (aMaterial != null)
/* 40:   */     {
/* 41:31 */       GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, aMultiplier), 16, 10);
/* 42:32 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, 2 * aMultiplier), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(0, aMaterial, aMaterial.mOreByProducts), 1L), 5 * aMultiplier, GT_OreDictUnificator.getDust(aPrefix.mSecondaryMaterial), 100, true);
/* 43:33 */       if (aMaterial.contains(SubTag.NO_SMELTING)) {
/* 44:33 */         GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mDirectSmelting, aMultiplier));
/* 45:   */       }
/* 46:   */     }
/* 47:   */   }
/* 48:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingOrePoor
 * JD-Core Version:    0.7.0.1
 */