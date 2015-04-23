/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.objects.MaterialStack;
/* 10:   */ import gregtech.api.util.GT_ModHandler;
/* 11:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 12:   */ import gregtech.api.util.GT_Utility;
/* 13:   */ import java.util.ArrayList;
/* 14:   */ import java.util.Iterator;
/* 15:   */ import java.util.List;
/* 16:   */ import net.minecraft.item.ItemStack;
/* 17:   */ 
/* 18:   */ public class ProcessingCell
/* 19:   */   implements IOreRecipeRegistrator
/* 20:   */ {
/* 21:   */   public ProcessingCell()
/* 22:   */   {
/* 23:21 */     OrePrefixes.cell.add(this);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 27:   */   {
/* 28:26 */     if (aMaterial == Materials.Empty)
/* 29:   */     {
/* 30:27 */       GT_ModHandler.removeRecipeByOutput(aStack);
/* 31:28 */       if (aModName.equalsIgnoreCase("AtomicScience")) {
/* 32:28 */         GT_ModHandler.addExtractionRecipe(ItemList.Cell_Empty.get(1L, new Object[0]), aStack);
/* 33:   */       }
/* 34:   */     }
/* 35:   */     else
/* 36:   */     {
/* 37:30 */       if (aMaterial.mFuelPower > 0) {
/* 38:30 */         GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.getFluidForFilledItem(aStack, true) == null ? GT_Utility.getContainerItem(aStack, true) : null, aMaterial.mFuelPower, aMaterial.mFuelType);
/* 39:   */       }
/* 40:32 */       if ((aMaterial.mMaterialList.size() > 0) && ((aMaterial.mExtraData & 0x3) != 0))
/* 41:   */       {
/* 42:33 */         int tAllAmount = 0;
/* 43:   */         MaterialStack tMat2;
/* 44:34 */         for (Iterator i$ = aMaterial.mMaterialList.iterator(); i$.hasNext(); tAllAmount = (int)(tAllAmount + tMat2.mAmount)) {
/* 45:34 */           tMat2 = (MaterialStack)i$.next();
/* 46:   */         }
/* 47:35 */         long tItemAmount = 0L;long tCapsuleCount = GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[] { aStack }) * -tAllAmount;long tDensityMultiplier = aMaterial.getDensity() > 3628800L ? aMaterial.getDensity() / 3628800L : 1L;
/* 48:36 */         ArrayList<ItemStack> tList = new ArrayList();
/* 49:38 */         for ( MaterialStack tMat : aMaterial.mMaterialList) {
/* 50:38 */           if (tMat.mAmount > 0L)
/* 51:   */           {
/* 53:   */             ItemStack tStack;
/* 54:39 */             if (tMat.mMaterial == Materials.Air)
/* 55:   */             {
/* 56:40 */               tStack = ItemList.Cell_Air.get(tMat.mAmount * tDensityMultiplier / 2L, new Object[0]);
/* 57:   */             }
/* 58:   */             else
/* 59:   */             {
/* 60:42 */               tStack = GT_OreDictUnificator.get(OrePrefixes.dust, tMat.mMaterial, tMat.mAmount);
/* 61:43 */               if (tStack == null) {
/* 62:44 */                 tStack = GT_OreDictUnificator.get(OrePrefixes.cell, tMat.mMaterial, tMat.mAmount);
/* 63:   */               }
/* 64:   */             }
/* 65:46 */             if (tItemAmount + tMat.mAmount * 3628800L <= aStack.getMaxStackSize() * aMaterial.getDensity())
/* 66:   */             {
/* 67:47 */               tItemAmount += tMat.mAmount * 3628800L;
/* 68:48 */               if (tStack != null)
/* 69:   */               {
/* 70:49 */                 ItemStack tmp397_395 = tStack;tmp397_395.stackSize = ((int)(tmp397_395.stackSize * tDensityMultiplier));
/* 71:50 */                 while ((tStack.stackSize > 64) && (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCount(tStack) * 64 < 0L ? tList.size() < 5 : tList.size() < 6) && (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCount(tStack) * 64 <= 64L))
/* 72:   */                 {
/* 73:51 */                   tCapsuleCount += GT_ModHandler.getCapsuleCellContainerCount(tStack) * 64;
/* 74:52 */                   tList.add(GT_Utility.copyAmount(64L, new Object[] { tStack }));
/* 75:53 */                   tStack.stackSize -= 64;
/* 76:   */                 }
/* 77:55 */                 if (tStack.stackSize > 0) {
/* 78:55 */                   if (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[] { tStack }) <= 64L) {
/* 79:55 */                     if (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[] { tStack }) < 0L ? tList.size() < 5 : tList.size() < 6)
/* 80:   */                     {
/* 81:56 */                       tCapsuleCount += GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[] { tStack });
/* 82:57 */                       tList.add(tStack);
/* 83:   */                     }
/* 84:   */                   }
/* 85:   */                 }
/* 86:   */               }
/* 87:   */             }
/* 88:   */           }
/* 89:   */         }
/* 90:62 */         tItemAmount = (tItemAmount * tDensityMultiplier % aMaterial.getDensity() > 0L ? 1 : 0) + tItemAmount * tDensityMultiplier / aMaterial.getDensity();
/* 91:63 */         if (tList.size() > 0)
/* 92:   */         {
/* 93:64 */           if ((aMaterial.mExtraData & 0x1) != 0) {
/* 94:64 */             GT_Values.RA.addElectrolyzerRecipe(GT_Utility.copyAmount(tItemAmount, new Object[] { aStack }), tCapsuleCount > 0L ? (int)tCapsuleCount : 0, (ItemStack)tList.get(0), tList.size() < 2 ? null : (ItemStack)tList.get(1), tList.size() < 3 ? null : (ItemStack)tList.get(2), tList.size() < 4 ? null : (ItemStack)tList.get(3), tList.size() < 5 ? null : (ItemStack)tList.get(4), tList.size() < 6 ? null : tCapsuleCount < 0L ? ItemList.Cell_Empty.get(-tCapsuleCount, new Object[0]) : (ItemStack)tList.get(5), (int)Math.max(1L, Math.abs(aMaterial.getProtons() * 8L * tItemAmount)), Math.min(4, tList.size()) * 30);
/* 95:   */           }
/* 96:65 */           if ((aMaterial.mExtraData & 0x2) != 0) {
/* 97:65 */             GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(tItemAmount, new Object[] { aStack }), tCapsuleCount > 0L ? (int)tCapsuleCount : 0, (ItemStack)tList.get(0), tList.size() < 2 ? null : (ItemStack)tList.get(1), tList.size() < 3 ? null : (ItemStack)tList.get(2), tList.size() < 4 ? null : (ItemStack)tList.get(3), tList.size() < 5 ? null : (ItemStack)tList.get(4), tList.size() < 6 ? null : tCapsuleCount < 0L ? ItemList.Cell_Empty.get(-tCapsuleCount, new Object[0]) : (ItemStack)tList.get(5), (int)Math.max(1L, Math.abs(aMaterial.getMass() * 2L * tItemAmount)));
/* 98:   */           }
/* 99:   */         }
/* :0:   */       }
/* :1:   */     }
/* :2:   */   }
/* :3:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingCell
 * JD-Core Version:    0.7.0.1
 */