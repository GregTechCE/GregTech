/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import gregtech.api.util.GT_Utility.ItemNBT;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingArrows implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingArrows()
/*    */   {
/* 17 */     for (OrePrefixes tPrefix : OrePrefixes.values()) if (tPrefix.name().startsWith("arrowGt")) { tPrefix.add(this);
/*    */       }
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 23 */     ItemStack tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 24 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.smite, EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, tOutput) + 3);
/* 25 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.HolyWater.getFluid(25L), tOutput, null, null, null, 100, 2);
/*    */     
/* 27 */     tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 28 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.fireAspect, EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, tOutput) + 3);
/* 29 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.FierySteel.getFluid(25L), tOutput, null, null, null, 100, 2);
/*    */     
/* 31 */     tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 32 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.fireAspect, EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, tOutput) + 1);
/* 33 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Blaze.getMolten(18L), tOutput, null, null, null, 100, 2);
/*    */     
/* 35 */     tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 36 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.knockback, EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, tOutput) + 1);
/* 37 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Rubber.getMolten(18L), tOutput, null, null, null, 100, 2);
/*    */     
/* 39 */     tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 40 */     GT_Utility.ItemNBT.addEnchantment(tOutput, gregtech.api.enchants.Enchantment_EnderDamage.INSTANCE, EnchantmentHelper.getEnchantmentLevel(gregtech.api.enchants.Enchantment_EnderDamage.INSTANCE.effectId, tOutput) + 1);
/* 41 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Mercury.getFluid(25L), tOutput, null, null, null, 100, 2);
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingArrows.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */