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
/* 13:   */ public class ProcessingPure
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingPure()
/* 17:   */   {
/* 18:15 */     OrePrefixes.crushedPurified.add(this);
/* 19:16 */     OrePrefixes.cleanGravel.add(this);
/* 20:17 */     OrePrefixes.reduced.add(this);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 24:   */   {
/* 25:22 */     GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, 1L), 10, 16);
/* 26:23 */     GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), 10, false);
/* 27:   */   }
/* 28:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingPure
 * JD-Core Version:    0.7.0.1
 */