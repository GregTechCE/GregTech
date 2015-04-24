/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.ItemList;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_RecipeRegistrator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingDustTiny implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingDustTiny()
/*    */   {
/* 18 */     OrePrefixes.dustTiny.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 23 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), ItemList.Schematic_Dust.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 100, 4);
/* 24 */     if (!aMaterial.mBlastFurnaceRequired) {
/* 25 */       GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
/* 26 */       if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) { GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }), aMaterial, aPrefix.mMaterialAmount, null, null, null);
/*    */       }
/*    */     }
/* 29 */     if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMELTING)) {
/* 30 */       if (aMaterial.mBlastFurnaceRequired) {
/* 31 */         GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.mSmeltInto, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), null, (int)Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
/* 32 */         GT_ModHandler.removeFurnaceSmelting(aStack);
/*    */       } else {
/* 34 */         GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 1L));
/* 35 */         GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 130, 3, true);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingDustTiny.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */