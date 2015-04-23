/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  8:   */ import gregtech.api.objects.MaterialStack;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import gregtech.api.util.GT_Utility;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingPipeRestrictive
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingPipeRestrictive()
/* 17:   */   {
/* 18:14 */     for (OrePrefixes tPrefix : OrePrefixes.values()) {
/* 19:14 */       if (tPrefix.name().startsWith("pipeRestrictive")) {
/* 20:14 */         tPrefix.add(this);
/* 21:   */       }
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 26:   */   {
/* 27:19 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(aOreDictName.replaceFirst("Restrictive", ""), null, 1L, false, true), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, aPrefix.mSecondaryMaterial.mAmount / OrePrefixes.ring.mMaterialAmount), GT_Utility.copyAmount(1L, new Object[] { aStack }), (int)(aPrefix.mSecondaryMaterial.mAmount * 400L / OrePrefixes.ring.mMaterialAmount), 4);
/* 28:   */   }
/* 29:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingPipeRestrictive
 * JD-Core Version:    0.7.0.1
 */