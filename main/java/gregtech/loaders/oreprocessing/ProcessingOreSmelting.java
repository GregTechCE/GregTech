/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.enums.SubTag;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingOreSmelting implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/* 15 */   private final OrePrefixes[] mSmeltingPrefixes = { OrePrefixes.crushed, OrePrefixes.crushedPurified, OrePrefixes.crushedCentrifuged, OrePrefixes.dustImpure, OrePrefixes.dustPure, OrePrefixes.dustRefined };
/*    */   
/*    */   public ProcessingOreSmelting() {
/* 18 */     for (OrePrefixes tPrefix : this.mSmeltingPrefixes) tPrefix.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 23 */     GT_ModHandler.removeFurnaceSmelting(aStack);
/* 24 */     if (!aMaterial.contains(SubTag.NO_SMELTING)) {
/* 25 */       if ((aMaterial.mBlastFurnaceRequired) || (aMaterial.mDirectSmelting.mBlastFurnaceRequired)) {
/* 26 */         GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), null, (int)Math.max(aMaterial.getMass() / 4L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
/* 27 */         if (aMaterial.mBlastFurnaceTemp <= 1000) GT_ModHandler.addRCBlastFurnaceRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), aMaterial.mBlastFurnaceTemp * 2);
/*    */       } else {
/* 29 */         switch (aPrefix) {
/*    */         case crushed: case crushedPurified: case crushedCentrifuged: 
/* 31 */           ItemStack tStack = GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mDirectSmelting, aMaterial.mDirectSmelting == aMaterial ? 10L : 3L);
/* 32 */           if (tStack == null) tStack = GT_OreDictUnificator.get(aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OrePrefixes.gem : OrePrefixes.ingot, aMaterial.mDirectSmelting, 1L);
/* 33 */           if ((tStack == null) && (!aMaterial.contains(SubTag.SMELTING_TO_GEM))) tStack = GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mDirectSmelting, 1L);
/* 34 */           GT_ModHandler.addSmeltingRecipe(aStack, tStack);
/* 35 */           break;
/*    */         default: 
/* 37 */           GT_ModHandler.addSmeltingRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mDirectSmelting, 1L));
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingOreSmelting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */