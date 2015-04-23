/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enchants.Enchantment_EnderDamage;
/*  4:   */ import gregtech.api.enums.GT_Values;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import gregtech.api.util.GT_Utility.ItemNBT;
/* 11:   */ import net.minecraft.enchantment.Enchantment;
/* 12:   */ import net.minecraft.enchantment.EnchantmentHelper;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class ProcessingArrows
/* 16:   */   implements IOreRecipeRegistrator
/* 17:   */ {
/* 18:   */   public ProcessingArrows()
/* 19:   */   {
/* 20:17 */     for (OrePrefixes tPrefix : OrePrefixes.values()) {
/* 21:17 */       if (tPrefix.name().startsWith("arrowGt")) {
/* 22:17 */         tPrefix.add(this);
/* 23:   */       }
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 28:   */   {
/* 29:23 */     ItemStack tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 30:24 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.smite, EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, tOutput) + 3);
/* 31:25 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.HolyWater.getFluid(25L), tOutput, null, null, null, 100, 2);
/* 32:   */     
/* 33:27 */     tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 34:28 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.fireAspect, EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, tOutput) + 3);
/* 35:29 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.FierySteel.getFluid(25L), tOutput, null, null, null, 100, 2);
/* 36:   */     
/* 37:31 */     tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 38:32 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.fireAspect, EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, tOutput) + 1);
/* 39:33 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Blaze.getMolten(18L), tOutput, null, null, null, 100, 2);
/* 40:   */     
/* 41:35 */     tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 42:36 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.knockback, EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, tOutput) + 1);
/* 43:37 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Rubber.getMolten(18L), tOutput, null, null, null, 100, 2);
/* 44:   */     
/* 45:39 */     tOutput = GT_Utility.copyAmount(1L, new Object[] { aStack });GT_Utility.updateItemStack(tOutput);
/* 46:40 */     GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment_EnderDamage.INSTANCE, EnchantmentHelper.getEnchantmentLevel(Enchantment_EnderDamage.INSTANCE.effectId, tOutput) + 1);
/* 47:41 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Mercury.getFluid(25L), tOutput, null, null, null, 100, 2);
/* 48:   */   }
/* 49:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingArrows
 * JD-Core Version:    0.7.0.1
 */