/*   1:    */ package gregtech.loaders.oreprocessing;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   7:    */ import gregtech.api.enums.*;
/*  10:    */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  11:    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  12:    */ import gregtech.api.util.GT_Config;
/*  13:    */ import gregtech.api.util.GT_ModHandler;
/*  14:    */ import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
/*  15:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  16:    */ import gregtech.api.util.GT_Utility;

/*  17:    */ import java.util.ArrayList;

/*  18:    */ import net.minecraft.init.Blocks;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ 
/*  21:    */ public class ProcessingOre
/*  22:    */   implements IOreRecipeRegistrator
/*  23:    */ {
/*  24:    */   public ProcessingOre()
/*  25:    */   {
/*  26: 19 */     for (OrePrefixes tPrefix : OrePrefixes.values()) {
/*  27: 19 */       if ((tPrefix.name().startsWith("ore")) && (tPrefix != OrePrefixes.orePoor) && (tPrefix != OrePrefixes.oreSmall) && (tPrefix != OrePrefixes.oreRich) && (tPrefix != OrePrefixes.oreNormal)) {
/*  28: 19 */         tPrefix.add(this);
/*  29:    */       }
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*  34:    */   {
/*  35: 24 */     boolean tIsRich = (aPrefix == OrePrefixes.oreNether) || (aPrefix == OrePrefixes.oreEnd) || (aPrefix == OrePrefixes.oreDense);
/*  36: 26 */     if (aMaterial == Materials.Oilsands) {
/*  37: 27 */       GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, Materials.Oil.getFluid(tIsRich ? 1000L : 500L), new ItemStack(Blocks.sand, 1, 0), null, null, null, null, null, new int[] { tIsRich ? 10000 : 5000 }, tIsRich ? 2000 : 1000, 5);
/*  38:    */     } else {
/*  39: 29 */       registerStandardOreRecipes(aPrefix, aMaterial, GT_Utility.copyAmount(1L, new Object[] { aStack }), Math.max(1, GregTech_API.sOPStuff.get(ConfigCategories.Materials.oreprocessingoutputmultiplier, aMaterial.toString(), 1)) * (tIsRich ? 2 : 1));
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43: 33 */   private ArrayList<Materials> mAlreadyListedOres = new ArrayList(1000);
/*  44:    */   
/*  45:    */   private boolean registerStandardOreRecipes(OrePrefixes aPrefix, Materials aMaterial, ItemStack aOreStack, int aMultiplier)
/*  46:    */   {
/*  47: 36 */     if ((aOreStack == null) || (aMaterial == null)) {
/*  48: 36 */       return false;
/*  49:    */     }
/*  50: 37 */     GT_ModHandler.addValuableOre(GT_Utility.getBlockFromStack(aOreStack), aOreStack.getItemDamage(), aMaterial.mOreValue);
/*  51: 38 */     Materials tMaterial = aMaterial.mOreReplacement;Materials tPrimaryByMaterial = null;Materials tSecondaryByMaterial = null;
/*  52: 39 */     aMultiplier = Math.max(1, aMultiplier);
/*  53: 40 */     aOreStack = GT_Utility.copyAmount(1L, new Object[] { aOreStack });
/*  54: 41 */     aOreStack.stackSize = 1;
/*  55:    */     
/*  56:    */ 
/*  57: 44 */     ItemStack tIngot = GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mDirectSmelting, 1L);
/*  58: 45 */     ItemStack tGem = GT_OreDictUnificator.get(OrePrefixes.gem, tMaterial, 1L);
/*  59: 46 */     ItemStack tSmeltInto = tIngot == null ? null : aMaterial.contains(SubTag.SMELTING_TO_GEM) ? GT_OreDictUnificator.get(OrePrefixes.gem, tMaterial.mDirectSmelting, GT_OreDictUnificator.get(OrePrefixes.crystal, tMaterial.mDirectSmelting, GT_OreDictUnificator.get(OrePrefixes.gem, tMaterial, GT_OreDictUnificator.get(OrePrefixes.crystal, tMaterial, 1L), 1L), 1L), 1L) : tIngot;
/*  60:    */     
/*  61: 48 */     ItemStack tSmall = GT_OreDictUnificator.get(OrePrefixes.dustSmall, tMaterial, 1L);
/*  62: 49 */     ItemStack tDust = GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial, tGem, 1L);
/*  63: 50 */     ItemStack tCleaned = GT_OreDictUnificator.get(OrePrefixes.crushedPurified, tMaterial, tDust, 1L);
/*  64: 51 */     ItemStack tCrushed = GT_OreDictUnificator.get(OrePrefixes.crushed, tMaterial, aMaterial.mOreMultiplier * aMultiplier);
/*  65: 52 */     ItemStack tPrimaryByProduct = null;ItemStack tPrimaryByProductSmall = null;ItemStack tSecondaryByProduct = null;ItemStack tSecondaryByProductSmall = null;
/*  66: 54 */     if (tCrushed == null) {
/*  67: 55 */       tCrushed = GT_OreDictUnificator.get(OrePrefixes.dustImpure, tMaterial, GT_Utility.copyAmount(aMaterial.mOreMultiplier * aMultiplier, new Object[] { tCleaned, tDust, tGem }), aMaterial.mOreMultiplier * aMultiplier);
/*  68:    */     }
/*  69: 58 */     ArrayList<ItemStack> tByProductStacks = new ArrayList();
/*  70: 60 */     for (Materials tMat : aMaterial.mOreByProducts)
/*  71:    */     {
/*  72: 61 */       ItemStack tByProduct = GT_OreDictUnificator.get(OrePrefixes.dust, tMat, 1L);
/*  73: 62 */       if (tByProduct != null) {
/*  74: 62 */         tByProductStacks.add(tByProduct);
/*  75:    */       }
/*  76: 63 */       if (tPrimaryByProduct == null)
/*  77:    */       {
/*  78: 64 */         tPrimaryByMaterial = tMat;
/*  79: 65 */         tPrimaryByProduct = GT_OreDictUnificator.get(OrePrefixes.dust, tMat, 1L);
/*  80: 66 */         tPrimaryByProductSmall = GT_OreDictUnificator.get(OrePrefixes.dustSmall, tMat, 1L);
/*  81: 67 */         if (tPrimaryByProductSmall == null) {
/*  82: 67 */           tPrimaryByProductSmall = GT_OreDictUnificator.get(OrePrefixes.dustTiny, tMat, GT_OreDictUnificator.get(OrePrefixes.nugget, tMat, 2L), 2L);
/*  83:    */         }
/*  84:    */       }
/*  85: 69 */       if ((tSecondaryByProduct == null) || (tSecondaryByMaterial == tPrimaryByMaterial))
/*  86:    */       {
/*  87: 70 */         tSecondaryByMaterial = tMat;
/*  88: 71 */         tSecondaryByProduct = GT_OreDictUnificator.get(OrePrefixes.dust, tMat, 1L);
/*  89: 72 */         tSecondaryByProductSmall = GT_OreDictUnificator.get(OrePrefixes.dustSmall, tMat, 1L);
/*  90: 73 */         if (tSecondaryByProductSmall == null) {
/*  91: 73 */           tSecondaryByProductSmall = GT_OreDictUnificator.get(OrePrefixes.dustTiny, tMat, GT_OreDictUnificator.get(OrePrefixes.nugget, tMat, 2L), 2L);
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95: 77 */     if ((!tByProductStacks.isEmpty()) && (!this.mAlreadyListedOres.contains(aMaterial)))
/*  96:    */     {
/*  97: 78 */       this.mAlreadyListedOres.add(aMaterial);
/*  98: 79 */       GT_Recipe.GT_Recipe_Map.sByProductList.addFakeRecipe(false, new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.ore, aMaterial, aOreStack, 1L) }, (ItemStack[])tByProductStacks.toArray(new ItemStack[tByProductStacks.size()]), null, null, null, null, 0, 0, 0);
/*  99:    */     }
/* 100: 82 */     if (tPrimaryByMaterial == null) {
/* 101: 82 */       tPrimaryByMaterial = tMaterial;
/* 102:    */     }
/* 103: 83 */     if (tPrimaryByProduct == null) {
/* 104: 83 */       tPrimaryByProduct = tDust;
/* 105:    */     }
/* 106: 84 */     if (tPrimaryByProductSmall == null) {
/* 107: 84 */       tPrimaryByProductSmall = tSmall;
/* 108:    */     }
/* 109: 86 */     if (tSecondaryByMaterial == null) {
/* 110: 86 */       tSecondaryByMaterial = tPrimaryByMaterial;
/* 111:    */     }
/* 112: 87 */     if (tSecondaryByProduct == null) {
/* 113: 87 */       tSecondaryByProduct = tPrimaryByProduct;
/* 114:    */     }
/* 115: 88 */     if (tSecondaryByProductSmall == null) {
/* 116: 88 */       tSecondaryByProductSmall = tPrimaryByProductSmall;
/* 117:    */     }
/* 118: 90 */     boolean tHasSmelting = false;
/* 119: 92 */     if (tSmeltInto != null)
/* 120:    */     {
/* 121: 93 */       if ((aMaterial.mBlastFurnaceRequired) || (aMaterial.mDirectSmelting.mBlastFurnaceRequired))
/* 122:    */       {
/* 123: 94 */         GT_ModHandler.removeFurnaceSmelting(aOreStack);
/* 124:    */       }
/* 125:    */       else
/* 126:    */       {
/* 127: 96 */         GT_ModHandler.addInductionSmelterRecipe(aOreStack, new ItemStack(Blocks.sand, 1), GT_Utility.mul(aMultiplier * (aMaterial.contains(SubTag.INDUCTIONSMELTING_LOW_OUTPUT) ? 1 : 2) * aMaterial.mSmeltingMultiplier, new Object[] { tSmeltInto }), ItemList.TE_Slag_Rich.get(1L, new Object[0]), 300 * aMultiplier, 10 * aMultiplier);
/* 128: 97 */         GT_ModHandler.addInductionSmelterRecipe(aOreStack, ItemList.TE_Slag_Rich.get(aMultiplier, new Object[0]), GT_Utility.mul(aMultiplier * (aMaterial.contains(SubTag.INDUCTIONSMELTING_LOW_OUTPUT) ? 2 : 3) * aMaterial.mSmeltingMultiplier, new Object[] { tSmeltInto }), ItemList.TE_Slag.get(aMultiplier, new Object[0]), 300 * aMultiplier, 95);
/* 129: 98 */         tHasSmelting = GT_ModHandler.addSmeltingRecipe(aOreStack, GT_Utility.copyAmount(aMultiplier * aMaterial.mSmeltingMultiplier, new Object[] { tSmeltInto }));
/* 130:    */       }
/* 131:101 */       if (aMaterial.contains(SubTag.BLASTFURNACE_CALCITE_TRIPLE)) {
/* 132:102 */         GT_Values.RA.addBlastRecipe(aOreStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, aMultiplier), null, null, GT_Utility.mul(aMultiplier * 3 * aMaterial.mSmeltingMultiplier, new Object[] { tSmeltInto }), ItemList.TE_Slag.get(1L, new Object[] { GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.DarkAsh, 1L) }), tSmeltInto.stackSize * 500, 120, 1500);
/* 133:103 */       } else if (aMaterial.contains(SubTag.BLASTFURNACE_CALCITE_DOUBLE)) {
/* 134:104 */         GT_Values.RA.addBlastRecipe(aOreStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, aMultiplier), null, null, GT_Utility.mul(aMultiplier * 2 * aMaterial.mSmeltingMultiplier, new Object[] { tSmeltInto }), ItemList.TE_Slag.get(1L, new Object[] { GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.DarkAsh, 1L) }), tSmeltInto.stackSize * 500, 120, 1500);
/* 135:    */       }
/* 136:    */     }
/* 137:108 */     if (!tHasSmelting) {
/* 138:109 */       tHasSmelting = GT_ModHandler.addSmeltingRecipe(aOreStack, GT_OreDictUnificator.get(OrePrefixes.gem, tMaterial.mDirectSmelting, Math.max(1, aMultiplier * aMaterial.mSmeltingMultiplier / 2)));
/* 139:    */     }
/* 140:112 */     if (tCrushed != null)
/* 141:    */     {
/* 142:113 */       GT_Values.RA.addForgeHammerRecipe(aOreStack, GT_Utility.copy(new Object[] { GT_Utility.copyAmount(tCrushed.stackSize, new Object[] { tGem }), tCrushed }), 16, 10);
/* 143:114 */       GT_ModHandler.addPulverisationRecipe(aOreStack, GT_Utility.mul(2L, new Object[] { tCrushed }), tMaterial.contains(SubTag.PULVERIZING_CINNABAR) ? GT_OreDictUnificator.get(OrePrefixes.crystal, Materials.Cinnabar, GT_OreDictUnificator.get(OrePrefixes.gem, tPrimaryByMaterial, GT_Utility.copyAmount(1L, new Object[] { tPrimaryByProduct }), 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.gem, tPrimaryByMaterial, GT_Utility.copyAmount(1L, new Object[] { tPrimaryByProduct }), 1L), tPrimaryByProduct == null ? 0 : tPrimaryByProduct.stackSize * 10 * aMultiplier * aMaterial.mByProductMultiplier, GT_OreDictUnificator.getDust(aPrefix.mSecondaryMaterial), 50, true);
/* 144:    */     }
/* 145:116 */     return true;
/* 146:    */   }
/* 147:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingOre
 * JD-Core Version:    0.7.0.1
 */