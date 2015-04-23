/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  8:   */ import gregtech.api.util.GT_OreDictUnificator;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ 
/* 12:   */ public class ProcessingRecycling
/* 13:   */   implements IOreRecipeRegistrator
/* 14:   */ {
/* 15:   */   public ProcessingRecycling()
/* 16:   */   {
/* 17:14 */     for (OrePrefixes tPrefix : OrePrefixes.values()) {
/* 18:14 */       if ((tPrefix.mIsMaterialBased) && (tPrefix.mMaterialAmount > 0L) && (tPrefix.mIsContainer)) {
/* 19:14 */         tPrefix.add(this);
/* 20:   */       }
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:19 */     if ((aMaterial != Materials.Empty) && (GT_Utility.getFluidForFilledItem(aStack, true) == null)) {
/* 27:19 */       GT_Values.RA.addCannerRecipe(aStack, null, GT_Utility.getContainerItem(aStack, true), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, aPrefix.mMaterialAmount / 3628800L), (int)Math.max(aMaterial.getMass() / 2L, 1L), 2);
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingRecycling
 * JD-Core Version:    0.7.0.1
 */