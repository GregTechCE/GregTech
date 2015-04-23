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
/* 14:   */ public class ProcessingOreSmelting
/* 15:   */   implements IOreRecipeRegistrator
/* 16:   */ {
/* 17:15 */   private final OrePrefixes[] mSmeltingPrefixes = { OrePrefixes.crushed, OrePrefixes.crushedPurified, OrePrefixes.crushedCentrifuged, OrePrefixes.dustImpure, OrePrefixes.dustPure, OrePrefixes.dustRefined };
/* 18:   */   
/* 19:   */   public ProcessingOreSmelting()
/* 20:   */   {
/* 21:18 */     for (OrePrefixes tPrefix : this.mSmeltingPrefixes) {
/* 22:18 */       tPrefix.add(this);
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 27:   */   {
/* 28:23 */     GT_ModHandler.removeFurnaceSmelting(aStack);
/* 29:24 */     if (!aMaterial.contains(SubTag.NO_SMELTING)) {
/* 30:25 */       if ((aMaterial.mBlastFurnaceRequired) || (aMaterial.mDirectSmelting.mBlastFurnaceRequired))
/* 31:   */       {
/* 32:26 */         GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), null, (int)Math.max(aMaterial.getMass() / 4L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
/* 33:27 */         if (aMaterial.mBlastFurnaceTemp <= 1000) {
/* 34:27 */           GT_ModHandler.addRCBlastFurnaceRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), aMaterial.mBlastFurnaceTemp * 2);
/* 35:   */         }
/* 36:   */       }
/* 37:   */       else
/* 38:   */       {
/* 39:29 */         switch (aPrefix.ordinal())
/* 40:   */         {
/* 41:   */         case 1: 
/* 42:   */         case 2: 
/* 43:   */         case 3: 
/* 44:31 */           ItemStack tStack = GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mDirectSmelting, aMaterial.mDirectSmelting == aMaterial ? 10L : 3L);
/* 45:32 */           if (tStack == null) {
/* 46:32 */             tStack = GT_OreDictUnificator.get(aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OrePrefixes.gem : OrePrefixes.ingot, aMaterial.mDirectSmelting, 1L);
/* 47:   */           }
/* 48:33 */           if ((tStack == null) && (!aMaterial.contains(SubTag.SMELTING_TO_GEM))) {
/* 49:33 */             tStack = GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mDirectSmelting, 1L);
/* 50:   */           }
/* 51:34 */           GT_ModHandler.addSmeltingRecipe(aStack, tStack);
/* 52:35 */           break;
/* 53:   */         default: 
/* 54:37 */           GT_ModHandler.addSmeltingRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mDirectSmelting, 1L));
/* 55:   */         }
/* 56:   */       }
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingOreSmelting
 * JD-Core Version:    0.7.0.1
 */