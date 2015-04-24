/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingOrePoor implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingOrePoor()
/*    */   {
/* 15 */     OrePrefixes.orePoor.add(this);
/* 16 */     OrePrefixes.oreSmall.add(this);
/* 17 */     OrePrefixes.oreNormal.add(this);
/* 18 */     OrePrefixes.oreRich.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 23 */     int aMultiplier = 1;
/* 24 */     switch (aPrefix) {
/* 25 */     case oreSmall:  aMultiplier = 1; break;
/* 26 */     case orePoor:  aMultiplier = 2; break;
/* 27 */     case oreNormal:  aMultiplier = 3; break;
/* 28 */     case oreRich:  aMultiplier = 4;
/*    */     }
/* 30 */     if (aMaterial != null) {
/* 31 */       GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, aMultiplier), 16, 10);
/* 32 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, 2 * aMultiplier), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(0, aMaterial, aMaterial.mOreByProducts), 1L), 5 * aMultiplier, GT_OreDictUnificator.getDust(aPrefix.mSecondaryMaterial), 100, true);
/* 33 */       if (aMaterial.contains(gregtech.api.enums.SubTag.NO_SMELTING)) GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mDirectSmelting, aMultiplier));
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingOrePoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */