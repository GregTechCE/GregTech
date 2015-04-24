/*     */ package gregtech.loaders.oreprocessing;
/*     */ 
/*     */ import gregtech.api.enums.GT_Values;
/*     */ import gregtech.api.enums.ItemList;
/*     */ import gregtech.api.enums.Materials;
/*     */ import gregtech.api.enums.OrePrefixes;
/*     */ import gregtech.api.enums.SubTag;
/*     */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*     */ import gregtech.api.objects.MaterialStack;
/*     */ import gregtech.api.util.GT_ModHandler;
/*     */ import gregtech.api.util.GT_OreDictUnificator;
/*     */ import gregtech.api.util.GT_RecipeRegistrator;
/*     */ import gregtech.api.util.GT_Utility;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ public class ProcessingDust implements gregtech.api.interfaces.IOreRecipeRegistrator
/*     */ {
/*     */   public ProcessingDust()
/*     */   {
/*  23 */     OrePrefixes.dust.add(this);
/*     */   }
/*     */   
/*     */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*     */   {
/*  28 */     if (aMaterial.mFuelPower > 0) GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, aMaterial.mFuelPower, aMaterial.mFuelType);
/*  29 */     if (GT_Utility.getFluidForFilledItem(GT_OreDictUnificator.get(OrePrefixes.cell, aMaterial, 1L), true) == null) GT_Values.RA.addCannerRecipe(aStack, ItemList.Cell_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.cell, aMaterial, 1L), null, 100, 1);
/*  30 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, new Object[] { aStack }), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtDust, aMaterial, 1L), 100, 8);
/*  31 */     GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtDust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
/*  32 */     if (!aMaterial.mBlastFurnaceRequired) {
/*  33 */       GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
/*  34 */       if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) { GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }), aMaterial, aPrefix.mMaterialAmount, null, null, null);
/*     */       }
/*     */     }
/*     */     
/*     */     ItemStack tStack;
/*  39 */     if ((null != (tStack = GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L))) && (!aMaterial.contains(SubTag.NO_SMELTING))) {
/*  40 */       if (aMaterial.mBlastFurnaceRequired) {
/*  41 */         GT_ModHandler.removeFurnaceSmelting(aStack);
/*  42 */         GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.mSmeltInto, tStack, 1L) : GT_Utility.copyAmount(1L, new Object[] { tStack }), null, (int)Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
/*  43 */         if (aMaterial.mBlastFurnaceTemp <= 1000) GT_ModHandler.addRCBlastFurnaceRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.copyAmount(1L, new Object[] { tStack }), aMaterial.mBlastFurnaceTemp);
/*     */       } else {
/*  45 */         GT_ModHandler.addSmeltingRecipe(aStack, tStack);
/*     */       }
/*     */     }
/*  48 */     else if (!aMaterial.contains(SubTag.NO_WORKING)) {
/*  49 */       if (OrePrefixes.block.isIgnored(aMaterial)) {
/*  50 */         if ((null == GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L)) && (aMaterial != Materials.GraniteRed) && (aMaterial != Materials.GraniteBlack) && (aMaterial != Materials.Glass) && (aMaterial != Materials.Obsidian) && (aMaterial != Materials.Glowstone) && (aMaterial != Materials.Paper) && (aMaterial != Materials.Wood)) {
/*  51 */           GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L));
/*     */         }
/*     */       }
/*  54 */       else if (null == GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L)) {
/*  55 */         GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  61 */     if ((aMaterial.mMaterialList.size() > 0) && ((aMaterial.mExtraData & 0x3) != 0)) {
/*  62 */       long tItemAmount = 0L;long tCapsuleCount = 0L;long tDensityMultiplier = aMaterial.getDensity() > 3628800L ? aMaterial.getDensity() / 3628800L : 1L;
/*  63 */       ArrayList<ItemStack> tList = new ArrayList();
/*  64 */       for (MaterialStack tMat : aMaterial.mMaterialList) if (tMat.mAmount > 0L)
/*     */         {
/*  66 */           if (tMat.mMaterial == Materials.Air) {
/*  67 */             tStack = ItemList.Cell_Air.get(tMat.mAmount / 2L, new Object[0]);
/*     */           } else {
/*  69 */             tStack = GT_OreDictUnificator.get(OrePrefixes.dust, tMat.mMaterial, tMat.mAmount);
/*  70 */             if (tStack == null)
/*  71 */               tStack = GT_OreDictUnificator.get(OrePrefixes.cell, tMat.mMaterial, tMat.mAmount);
/*     */           }
/*  73 */           if (tItemAmount + tMat.mAmount * 3628800L <= aStack.getMaxStackSize() * aMaterial.getDensity()) {
/*  74 */             tItemAmount += tMat.mAmount * 3628800L;
/*  75 */             if (tStack != null) {
/*  76 */               ItemStack tmp793_791 = tStack;tmp793_791.stackSize = ((int)(tmp793_791.stackSize * tDensityMultiplier));
/*  77 */               while ((tStack.stackSize > 64) && (tList.size() < 6) && (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCount(tStack) * 64 <= 64L)) {
/*  78 */                 tCapsuleCount += GT_ModHandler.getCapsuleCellContainerCount(tStack) * 64;
/*  79 */                 tList.add(GT_Utility.copyAmount(64L, new Object[] { tStack }));
/*  80 */                 tStack.stackSize -= 64;
/*     */               }
/*  82 */               if ((tStack.stackSize > 0) && (tList.size() < 6)) { if (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[] { tStack }) <= 64L) {
/*  83 */                   tCapsuleCount += GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[] { tStack });
/*  84 */                   tList.add(tStack);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*  90 */       tItemAmount = (tItemAmount * tDensityMultiplier % aMaterial.getDensity() > 0L ? 1 : 0) + tItemAmount * tDensityMultiplier / aMaterial.getDensity();
/*  91 */       if (tList.size() > 0) {
/*  92 */         FluidStack tFluid = null;
/*  93 */         for (int i = 0; i < tList.size(); i++) { if ((!ItemList.Cell_Air.isStackEqual(tList.get(i))) && ((tFluid = GT_Utility.getFluidForFilledItem((ItemStack)tList.get(i), true)) != null)) {
/*  94 */             tFluid.amount *= ((ItemStack)tList.get(i)).stackSize;
/*  95 */             tCapsuleCount -= GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[] { (ItemStack)tList.get(i) });
/*  96 */             tList.remove(i);
/*  97 */             break;
/*     */           }
/*     */         }
/* 100 */         if ((aMaterial.mExtraData & 0x1) != 0) GT_Values.RA.addElectrolyzerRecipe(GT_Utility.copyAmount(tItemAmount, new Object[] { aStack }), tCapsuleCount > 0L ? ItemList.Cell_Empty.get(tCapsuleCount, new Object[0]) : null, null, tFluid, (ItemStack)tList.get(0), tList.size() < 2 ? null : (ItemStack)tList.get(1), tList.size() < 3 ? null : (ItemStack)tList.get(2), tList.size() < 4 ? null : (ItemStack)tList.get(3), tList.size() < 5 ? null : (ItemStack)tList.get(4), tList.size() < 6 ? null : (ItemStack)tList.get(5), null, (int)Math.max(1L, Math.abs(aMaterial.getProtons() * 2L * tItemAmount)), Math.min(4, tList.size()) * 30);
/* 101 */         if ((aMaterial.mExtraData & 0x2) != 0) { GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(tItemAmount, new Object[] { aStack }), tCapsuleCount > 0L ? ItemList.Cell_Empty.get(tCapsuleCount, new Object[0]) : null, null, tFluid, (ItemStack)tList.get(0), tList.size() < 2 ? null : (ItemStack)tList.get(1), tList.size() < 3 ? null : (ItemStack)tList.get(2), tList.size() < 4 ? null : (ItemStack)tList.get(3), tList.size() < 5 ? null : (ItemStack)tList.get(4), tList.size() < 6 ? null : (ItemStack)tList.get(5), null, (int)Math.max(1L, Math.abs(aMaterial.getMass() * 4L * tItemAmount)), Math.min(4, tList.size()) * 5);
/*     */         }
/*     */       }
/*     */     }
/* 105 */     if (aMaterial.contains(SubTag.CRYSTALLISABLE)) {
/* 106 */       GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Water.getFluid(200L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 7000, 2000, 24);
/* 107 */       GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getDistilledWater(200L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 9000, 1500, 24);
/*     */     }
/*     */     
/* 110 */     switch (aMaterial) {
/*     */     case _NULL: 
/*     */       break;
/*     */     case Glass: 
/* 114 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new ItemStack(net.minecraft.init.Blocks.glass));
/* 115 */       break;
/*     */     case NetherQuartz: case Quartz: case CertusQuartz: 
/* 117 */       if (gregtech.api.GregTech_API.sRecipeFile.get(gregtech.api.enums.ConfigCategories.Recipes.disabledrecipes, "QuartzDustSmeltingIntoAESilicon", true)) GT_ModHandler.removeFurnaceSmelting(aStack);
/*     */       break;
/*     */     case MeatRaw: 
/* 120 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L));
/* 121 */       break;
/*     */     case Mercury: 
/* 123 */       System.err.println("Quicksilver Dust?, To melt that, you don't even need a Furnace...");
/* 124 */       break;
/*     */     case Tetrahedrite: case Chalcopyrite: case Malachite: 
/* 126 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Copper, 6L));
/* 127 */       break;
/*     */     case Pentlandite: 
/* 129 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Nickel, 6L));
/* 130 */       break;
/*     */     case Garnierite: 
/* 132 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Nickel, 1L));
/* 133 */       break;
/*     */     case Cassiterite: case CassiteriteSand: 
/* 135 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L));
/* 136 */       break;
/*     */     case Magnetite: case VanadiumMagnetite: case BasalticMineralSand: case GraniticMineralSand: 
/* 138 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Iron, 3L));
/* 139 */       break;
/*     */     case YellowLimonite: case BrownLimonite: case BandedIron: 
/* 141 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 1L));
/* 142 */       break;
/*     */     case Coal: 
/* 144 */       GT_ModHandler.addLiquidTransposerFillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Water.getFluid(125L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HydratedCoal, 1L), 125);
/* 145 */       break;
/*     */     case HydratedCoal: 
/* 147 */       GT_ModHandler.addLiquidTransposerEmptyRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Water.getFluid(125L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L), 125);
/* 148 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L));
/* 149 */       break;
/*     */     case Diamond: 
/* 151 */       GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), 32, ItemList.IC2_Industrial_Diamond.get(3L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 16L));
/* 152 */       break;
/*     */     case Opal: case Olivine: case Emerald: case Ruby: case Sapphire: case GreenSapphire: case Topaz: case BlueTopaz: case Tanzanite: 
/* 154 */       GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), 24, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 3L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 12L));
/* 155 */       break;
/*     */     case FoolsRuby: case GarnetRed: case GarnetYellow: case Jasper: case Amber: case Monazite: case Forcicium: case Forcillium: case Force: 
/* 157 */       GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), 16, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 3L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 8L));
/*     */     }
/*     */   }
/*     */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingDust.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */