package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;

public class ProcessingCell
        implements IOreRegistrationHandler {
    public ProcessingCell() {
        OrePrefixes.cell.add(this);
        OrePrefixes.cellPlasma.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Material aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        switch (aPrefix) {
            case cell:
                if (aMaterial == Materials.Empty) {
                    GT_ModHandler.removeRecipeByOutput(aStack);
                } else if(aMaterial == Materials.Air) {
                    GT_ModHandler.addCompressionRecipe(ItemList.Cell_Empty.get(1L), GT_Utility.copyAmount(1L, aStack));
                } else {
                    if (aMaterial.mFuelPower > 0) {
                        GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aStack), GT_Utility.getFluidForFilledItem(aStack, true) == null ? GT_Utility.getContainerItem(aStack, true) : null, aMaterial.mFuelPower, aMaterial.mFuelType);
                    }
                    if ((aMaterial.mMaterialList.size() > 0) && ((aMaterial.mExtraData & 0x3) != 0)) {
                        int tAllAmount = 0;
                        MaterialStack tMat2;
                        for (Iterator i$ = aMaterial.mMaterialList.iterator(); i$.hasNext(); tAllAmount = (int) (tAllAmount + tMat2.mAmount)) {
                            tMat2 = (MaterialStack) i$.next();
                        }
                        int tItemAmount = 0L;
                        int tCapsuleCount = GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(aStack) * -tAllAmount;
                        int tDensityMultiplier = aMaterial.getDensity() > 3628800L ? aMaterial.getDensity() / 3628800L : 1L;
                        ArrayList<ItemStack> tList = new ArrayList<>();
                        for (MaterialStack tMat : aMaterial.mMaterialList) {
                            if (tMat.mAmount > 0L) {
                                ItemStack tStack;
                                if (tMat.mMaterial == Materials.Air) {
                                    tStack = ItemList.Cell_Air.get(tMat.mAmount * tDensityMultiplier / 2L);
                                } else {
                                    tStack = OreDictionaryUnifier.get(OrePrefixes.dust, tMat.mMaterial, tMat.mAmount);
                                    if (tStack == null) {
                                        tStack = OreDictionaryUnifier.get(OrePrefixes.cell, tMat.mMaterial, tMat.mAmount);
                                    }
                                }
                                if (tItemAmount + tMat.mAmount * 3628800L <= aStack.getMaxStackSize() * aMaterial.getDensity()) {
                                    tItemAmount += tMat.mAmount * 3628800L;
                                    if (tStack != null) {
                                        tStack.stackSize = ((int) (tStack.stackSize * tDensityMultiplier));
                                        while ((tStack.stackSize > 64) && (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCount(tStack) * 64 < 0L ? tList.size() < 5 : tList.size() < 6) && (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCount(tStack) * 64 <= 64L)) {
                                            tCapsuleCount += GT_ModHandler.getCapsuleCellContainerCount(tStack) * 64;
                                            tList.add(GT_Utility.copyAmount(64L, tStack));
                                            tStack.stackSize -= 64;
                                        }
                                        if ((tStack.stackSize > 0) && tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tStack) <= 64L) {
                                            if (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tStack) < 0L ? tList.size() < 5 : tList.size() < 6) {
                                                tCapsuleCount += GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tStack);
                                                tList.add(tStack);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        tItemAmount = (tItemAmount * tDensityMultiplier % aMaterial.getDensity() > 0L ? 1 : 0) + tItemAmount * tDensityMultiplier / aMaterial.getDensity();
                        if (tList.size() > 0) {
                            if ((aMaterial.mExtraData & 0x1) != 0) {
                                //GT_Values.RA.addElectrolyzerRecipe(GT_Utility.copyAmount(tItemAmount, new Object[] { aStack }), tCapsuleCount >  0L ? (int)tCapsuleCount : 0, (ItemStack)tList.get(0), tList.size() <  2 ? null : (ItemStack)tList.get(1), tList.size() <  3 ? null : (ItemStack)tList.get(2), tList.size() <  4 ? null : (ItemStack)tList.get(3), tList.size() <  5 ? null : (ItemStack)tList.get(4), tList.size()    < 6 ? null : tCapsuleCount < 0L ? ItemList.Cell_Empty.get(-tCapsuleCount, new Object[0]) : (ItemStack)tList.get(5), (int)Math.max(1L, Math.abs(aMaterial.getProtons() * 8L * tItemAmount)), Math.min(4, tList.size()) * 30);
                                GT_Values.RA.addElectrolyzerRecipe(GT_Utility.copyAmount(tItemAmount, aStack), tCapsuleCount <= 0L ? 0 : (int) tCapsuleCount, tList.get(0), tList.size() >= 2 ? tList.get(1) : null, tList.size() >= 3 ? tList.get(2) : null, tList.size() >= 4 ? tList.get(3) : null, tList.size() >= 5 ? tList.get(4) : null, tCapsuleCount >= 0L ? tList.size() >= 6 ? tList.get(5) : null : ItemList.Cell_Empty.get(-tCapsuleCount), (int) Math.max(1L, Math.abs(aMaterial.getProtons() * 8L * tItemAmount)), Math.min(4, tList.size()) * 30);
                            }
                            if ((aMaterial.mExtraData & 0x2) != 0) {
                                //GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(tItemAmount, new Object[] { aStack }), tCapsuleCount > 0L ? (int)tCapsuleCount : 0, (ItemStack)tList.get(0), tList.size() < 2 ? null : (ItemStack)tList.get(1), tList.size() < 3 ? null : (ItemStack)tList.get(2), tList.size() < 4 ? null : (ItemStack)tList.get(3), tList.size() < 5 ? null : (ItemStack)tList.get(4), tList.size() < 6 ? null : tCapsuleCount < 0L ? ItemList.Cell_Empty.get(-tCapsuleCount, new Object[0]) : (ItemStack)tList.get(5), (int)Math.max(1L, Math.abs(aMaterial.getMass() * 2L * tItemAmount)));
                                GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(tItemAmount, aStack), tCapsuleCount <= 0L ? 0 : (int) tCapsuleCount, tList.get(0), tList.size() >= 2 ? tList.get(1) : null, tList.size() >= 3 ? tList.get(2) : null, tList.size() >= 4 ? tList.get(3) : null, tList.size() >= 5 ? tList.get(4) : null, tCapsuleCount >= 0L ? tList.size() >= 6 ? tList.get(5) : null : ItemList.Cell_Empty.get(-tCapsuleCount), (int) Math.max(1L, Math.abs(aMaterial.getMass() * 2L * tItemAmount)));
                            }
                        }
                    }
                }
                break;
            case cellPlasma:
                if (aMaterial == Materials.Empty) {
                    GT_ModHandler.removeRecipeByOutput(aStack);
                } else {
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aStack), GT_Utility.getFluidForFilledItem(aStack, true) == null ? GT_Utility.getContainerItem(aStack, true) : null, (int) Math.max(1024L, 1024L * aMaterial.getMass()), 4);
                    GT_Values.RA.addVacuumFreezerRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefixes.cell, aMaterial, 1L), (int) Math.max(aMaterial.getMass() * 2L, 1L));
                }
                break;
        }
    }
}
