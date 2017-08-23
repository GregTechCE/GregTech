package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;

public class ProcessingCell implements IOreRegistrationHandler {
    public ProcessingCell() {
        OrePrefix.cell.addProcessingHandler(this);
        OrePrefix.cellPlasma.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.orePrefix) {
            case cell:
                if (uEntry.material == Materials.Empty) {
                    ModHandler.removeRecipeByOutput(stack);
                } else if(uEntry.material == Materials.Air) {
                    ModHandler.addCompressionRecipe(ItemList.Cell_Empty.get(1), GT_Utility.copyAmount(1, stack));
                } else {
                    if (uEntry.material.mFuelPower > 0) {
                        GTValues.RA.addFuel(GT_Utility.copyAmount(1, stack), GT_Utility.getFluidForFilledItem(stack, true) == null ? GT_Utility.getContainerItem(stack, true) : null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                    }
                    if ((uEntry.material.mMaterialList.size() > 0) && ((uEntry.material.mExtraData & 0x3) != 0)) {
                        int tAllAmount = 0;
                        MaterialStack tMat2;
                        for (Iterator i$ = uEntry.material.mMaterialList.iterator(); i$.hasNext(); tAllAmount = (int) (tAllAmount + tMat2.mAmount)) {
                            tMat2 = (MaterialStack) i$.next();
                        }
                        long tItemAmount = 0L;
                        int tCapsuleCount = ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(stack) * -tAllAmount;
                        long tDensityMultiplier = uEntry.material.getDensity() > 3628800L ? uEntry.material.getDensity() / 3628800L : 1L;
                        ArrayList<ItemStack> tList = new ArrayList<>();
                        for (MaterialStack tMat : uEntry.material.mMaterialList) {
                            if (tMat.mAmount > 0L) {
                                ItemStack tStack;
                                if (tMat.mMaterial == Materials.Air) {
                                    tStack = ItemList.Cell_Air.get(tMat.mAmount * tDensityMultiplier / 2L);
                                } else {
                                    tStack = OreDictionaryUnifier.get(OrePrefix.dust, tMat.mMaterial, tMat.mAmount);
                                    if (tStack == null) {
                                        tStack = OreDictionaryUnifier.get(OrePrefix.cell, tMat.mMaterial, tMat.mAmount);
                                    }
                                }
                                if (tItemAmount + tMat.mAmount * 3628800L <= stack.getMaxStackSize() * uEntry.material.getDensity()) {
                                    tItemAmount += tMat.mAmount * 3628800L;
                                    if (tStack != null) {
                                        tStack.stackSize = ((int) (tStack.stackSize * tDensityMultiplier));
                                        while ((tStack.stackSize > 64) && (tCapsuleCount + ModHandler.getCapsuleCellContainerCount(tStack) * 64 < 0L ? tList.size() < 5 : tList.size() < 6) && (tCapsuleCount + ModHandler.getCapsuleCellContainerCount(tStack) * 64 <= 64L)) {
                                            tCapsuleCount += ModHandler.getCapsuleCellContainerCount(tStack) * 64;
                                            tList.add(GT_Utility.copyAmount(64, tStack));
                                            tStack.stackSize -= 64;
                                        }
                                        if ((tStack.stackSize > 0) && tCapsuleCount + ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tStack) <= 64L) {
                                            if (tCapsuleCount + ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tStack) < 0L ? tList.size() < 5 : tList.size() < 6) {
                                                tCapsuleCount += ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tStack);
                                                tList.add(tStack);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        tItemAmount = (tItemAmount * tDensityMultiplier % uEntry.material.getDensity() > 0L ? 1L : 0L) + tItemAmount * tDensityMultiplier / uEntry.material.getDensity();
                        if (tList.size() > 0) {
                            if ((uEntry.material.mExtraData & 0x1) != 0) {
                                //GTValues.RA.addElectrolyzerRecipe(GT_Utility.copyAmount(tItemAmount, new Object[] { stack }), tCapsuleCount >  0L ? (int)tCapsuleCount : 0, (ItemStack)tList.get(0), tList.size() <  2 ? null : (ItemStack)tList.get(1), tList.size() <  3 ? null : (ItemStack)tList.get(2), tList.size() <  4 ? null : (ItemStack)tList.get(3), tList.size() <  5 ? null : (ItemStack)tList.get(4), tList.size()    < 6 ? null : tCapsuleCount < 0L ? ItemList.Cell_Empty.get(-tCapsuleCount, new Object[0]) : (ItemStack)tList.get(5), (int)Math.max(1L, Math.abs(uEntry.material.getProtons() * 8L * tItemAmount)), Math.min(4, tList.size()) * 30);
                                GTValues.RA.addElectrolyzerRecipe(GT_Utility.copyAmount((int)tItemAmount, stack), tCapsuleCount <= 0L ? 0 : tCapsuleCount, tList.get(0), tList.size() >= 2 ? tList.get(1) : null, tList.size() >= 3 ? tList.get(2) : null, tList.size() >= 4 ? tList.get(3) : null, tList.size() >= 5 ? tList.get(4) : null, tCapsuleCount >= 0L ? tList.size() >= 6 ? tList.get(5) : null : ItemList.Cell_Empty.get(-tCapsuleCount), (int) Math.max(1L, Math.abs(uEntry.material.getProtons() * 8L * tItemAmount)), Math.min(4, tList.size()) * 30);
                            }
                            if ((uEntry.material.mExtraData & 0x2) != 0) {
                                //GTValues.RA.addCentrifugeRecipe(GT_Utility.copyAmount(tItemAmount, new Object[] { stack }), tCapsuleCount > 0L ? (int)tCapsuleCount : 0, (ItemStack)tList.get(0), tList.size() < 2 ? null : (ItemStack)tList.get(1), tList.size() < 3 ? null : (ItemStack)tList.get(2), tList.size() < 4 ? null : (ItemStack)tList.get(3), tList.size() < 5 ? null : (ItemStack)tList.get(4), tList.size() < 6 ? null : tCapsuleCount < 0L ? ItemList.Cell_Empty.get(-tCapsuleCount, new Object[0]) : (ItemStack)tList.get(5), (int)Math.max(1L, Math.abs(uEntry.material.getMass() * 2L * tItemAmount)));
                                GTValues.RA.addCentrifugeRecipe(GT_Utility.copyAmount((int)tItemAmount, stack), tCapsuleCount <= 0L ? 0 : tCapsuleCount, tList.get(0), tList.size() >= 2 ? tList.get(1) : null, tList.size() >= 3 ? tList.get(2) : null, tList.size() >= 4 ? tList.get(3) : null, tList.size() >= 5 ? tList.get(4) : null, tCapsuleCount >= 0L ? tList.size() >= 6 ? tList.get(5) : null : ItemList.Cell_Empty.get(-tCapsuleCount), (int) Math.max(1L, Math.abs(uEntry.material.getMass() * 2L * tItemAmount)));
                            }
                        }
                    }
                }
                break;
            case cellPlasma:
                if (uEntry.material == Materials.Empty) {
                    ModHandler.removeRecipeByOutput(stack);
                } else {
                    GTValues.RA.addFuel(GT_Utility.copyAmount(1, stack), GT_Utility.getFluidForFilledItem(stack, true) == null ? GT_Utility.getContainerItem(stack, true) : null, (int) Math.max(1024L, 1024L * uEntry.material.getMass()), 4);
                    GTValues.RA.addVacuumFreezerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.cell, uEntry.material, 1), (int) Math.max(uEntry.material.getMass() * 2L, 1L));
                }
                break;
        }
    }
}
