package gregtech.loaders.oreprocessing;

import gregtech.GT_Mod;
import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class ProcessingDust implements IOreRegistrationHandler {
    public ProcessingDust() {
        OrePrefix.dust.add(this);
        OrePrefix.dustPure.add(this);
        OrePrefix.dustImpure.add(this);
        OrePrefix.dustRefined.add(this);
        OrePrefix.dustSmall.add(this);
        OrePrefix.dustTiny.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.orePrefix) {
            case dust:
                if (uEntry.material.mFuelPower > 0)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                if (GT_Utility.getFluidForFilledItem(OreDictionaryUnifier.get(OrePrefix.cell, uEntry.material, 1), true) == null)
                    GT_Values.RA.addCannerRecipe(stack, ItemList.Cell_Empty.get(1), OreDictionaryUnifier.get(OrePrefix.cell, uEntry.material, 1), null, 100, 1);
                if (!uEntry.material.mBlastFurnaceRequired) {
                    GT_RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                    if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                        GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                    }
                }

                ItemStack tDustStack;
                if ((null != (tDustStack = OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L))) && (!uEntry.material.contains(SubTag.NO_SMELTING))) {
                    if (uEntry.material.mBlastFurnaceRequired) {
                        ModHandler.removeFurnaceSmelting(stack);
                        GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(1, stack), null, null, null, uEntry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, uEntry.material.mSmeltInto, tDustStack, 1L) : GT_Utility.copyAmount(1L, new Object[]{tDustStack}), null, (int) Math.max(uEntry.material.getMass() / 40L, 1L) * uEntry.material.mBlastFurnaceTemp, 120, uEntry.material.mBlastFurnaceTemp);
                        if (uEntry.material.mBlastFurnaceTemp <= 1000) {
                            ModHandler.addRCBlastFurnaceRecipe(GT_Utility.copyAmount(1, stack), GT_Utility.copyAmount(1L tDustStack), uEntry.material.mBlastFurnaceTemp);
                        }
                    } else {
                        ModHandler.addSmeltingRecipe(stack, tDustStack);
                    }
                } else if (!uEntry.material.contains(SubTag.NO_WORKING)) {
                    if ((!OrePrefix.block.isIgnored(uEntry.material)) && (null == OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1))) {
                        ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9, stack), OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1));
                    }
                    if (((OrePrefix.block.isIgnored(uEntry.material)) || (null == OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1))) && (uEntry.material != Materials.GraniteRed) && (uEntry.material != Materials.GraniteBlack) && (uEntry.material != Materials.Glass) && (uEntry.material != Materials.Obsidian) && (uEntry.material != Materials.Glowstone) && (uEntry.material != Materials.Paper)) {
                        ModHandler.addCompressionRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1));
                    }
                }


                if ((uEntry.material.mMaterialList.size() > 0) && ((uEntry.material.mExtraData & 0x3) != 0)) {
                    int tItemAmount = 0;
                    int tCapsuleCount = 0;
                    long tDensityMultiplier = uEntry.material.getDensity() > 3628800L ? uEntry.material.getDensity() / 3628800L : 1L;
                    ArrayList<ItemStack> tList = new ArrayList();
                    for (MaterialStack tMat : uEntry.material.mMaterialList)
                        if (tMat.mAmount > 0L) {
                            if (tMat.mMaterial == Materials.Air) {
                                tDustStack = ItemList.Cell_Air.get(tMat.mAmount / 2L);
                            } else {
                                tDustStack = OreDictionaryUnifier.get(OrePrefix.dust, tMat.mMaterial, tMat.mAmount);
                                if (tDustStack == null)
                                    tDustStack = OreDictionaryUnifier.get(OrePrefix.cell, tMat.mMaterial, tMat.mAmount);
                            }
                            if (tItemAmount + tMat.mAmount * 3628800L <= stack.getMaxStackSize() * uEntry.material.getDensity()) {
                                tItemAmount += tMat.mAmount * 3628800L;
                                if (tDustStack != null) {
                                    ItemStack tmp793_791 = tDustStack;
                                    tmp793_791.stackSize = ((int) (tmp793_791.stackSize * tDensityMultiplier));
                                    while ((tDustStack.stackSize > 64) && (tList.size() < 6) && (tCapsuleCount + ModHandler.getCapsuleCellContainerCount(tDustStack) * 64 <= 64L)) {
                                        tCapsuleCount += ModHandler.getCapsuleCellContainerCount(tDustStack) * 64;
                                        tList.add(GT_Utility.copyAmount(64, tDustStack));
                                        tDustStack.stackSize -= 64;
                                    }
                                    if ((tDustStack.stackSize > 0) && (tList.size() < 6) && (tCapsuleCount + ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tDustStack) <= 64L)) {
                                        tCapsuleCount += ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tDustStack);
                                        tList.add(tDustStack);
                                    }
                                }
                            }
                        }
                    tItemAmount = (tItemAmount * tDensityMultiplier % uEntry.material.getDensity() > 0 ? 1 : 0) + tItemAmount * tDensityMultiplier / uEntry.material.getDensity();
                    if (tList.size() > 0) {
                        FluidStack tFluid = null;
                        int tList_sS = tList.size();
                        for (int i = 0; i < tList_sS; i++) {
                            if ((!ItemList.Cell_Air.isStackEqual(tList.get(i))) && ((tFluid = GT_Utility.getFluidForFilledItem((ItemStack) tList.get(i), true)) != null)) {
                                tFluid.amount *= ((ItemStack) tList.get(i)).stackSize;
                                tCapsuleCount -= ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tList.get(i));
                                tList.remove(i);
                                break;
                            }
                        }
                        if ((uEntry.material.mExtraData & 0x1) != 0)
                            GT_Values.RA.addElectrolyzerRecipe(GT_Utility.copyAmount(tItemAmount, stack), tCapsuleCount > 0L ? ItemList.Cell_Empty.get(tCapsuleCount) : null, null, tFluid, tList.size() < 1 ? null : (ItemStack) tList.get(0), tList.size() < 2 ? null : (ItemStack) tList.get(1), tList.size() < 3 ? null : (ItemStack) tList.get(2), tList.size() < 4 ? null : (ItemStack) tList.get(3), tList.size() < 5 ? null : (ItemStack) tList.get(4), tList.size() < 6 ? null : (ItemStack) tList.get(5), null, (int) Math.max(1L, Math.abs(uEntry.material.getProtons() * 2L * tItemAmount)), Math.min(4, tList.size()) * 30);
                        if ((uEntry.material.mExtraData & 0x2) != 0) {
                            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(tItemAmount, stack), tCapsuleCount > 0L ? ItemList.Cell_Empty.get(tCapsuleCount) : null, null, tFluid, tList.size() < 1 ? null : (ItemStack) tList.get(0), tList.size() < 2 ? null : (ItemStack) tList.get(1), tList.size() < 3 ? null : (ItemStack) tList.get(2), tList.size() < 4 ? null : (ItemStack) tList.get(3), tList.size() < 5 ? null : (ItemStack) tList.get(4), tList.size() < 6 ? null : (ItemStack) tList.get(5), null, (int) Math.max(1L, Math.abs(uEntry.material.getMass() * 4L * tItemAmount)), Math.min(4, tList.size()) * 5);
                        }
                    }
                }
                if (uEntry.material.contains(SubTag.CRYSTALLISABLE)) {
                    GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1, stack), Materials.Water.getFluid(200), OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1), 7000, 2000, 24);
                    GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1, stack), ModHandler.getDistilledWater(200), OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1), 9000, 1500, 24);
                }

                switch (uEntry.material.defaultLocalName) {
                    case "NULL":
                        break;
                    case "Glass":
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), new ItemStack(net.minecraft.init.Blocks.GLASS));
                        break;
                    case "Nether Quartz": case "Quartz": case "Certus Quartz":
                        if (gregtech.api.GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QuartzDustSmeltingIntoAESilicon", true))
                            ModHandler.removeFurnaceSmelting(stack);
                        break;
                    case "Meat Raw":
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.MeatCooked, 1));
                        break;
                    case "Mercury":
                        System.err.println("Quicksilver Dust?, To melt that, you don't even need a Furnace...");
                        break;
                    case "Tetrahedrite": case "Chalcopyrite": case "Malachite":
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Copper, 6));
                        break;
                    case "Pentlandite":
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Nickel, 6));
                        break;
                    case "Garnierite":
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Nickel, 1));
                        break;
                    case "Cassiterite": case "Cassiterite Sand":
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Tin, 1));
                        break;
                    case "Magnetite": case "Vanadium Magnetite": case "Basaltic Mineral Sand": case "Granitic Mineral Sand":
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Iron, 3));
                        break;
                    case "Yellow Limonite": case "Brown Limonite": case "Banded Iron":
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Iron, 1));
                        break;
                    case "Coal":
                        if (GT_Mod.gregtechproxy.mTEMachineRecipes)
                            ModHandler.addLiquidTransposerFillRecipe(GT_Utility.copyAmount(1, stack), Materials.Water.getFluid(125), OreDictionaryUnifier.get(OrePrefix.dust, Materials.HydratedCoal, 1), 125);
                        break;
                    case "Hydrated Coal":
                        if (GT_Mod.gregtechproxy.mTEMachineRecipes)
                            ModHandler.addLiquidTransposerEmptyRecipe(GT_Utility.copyAmount(1, stack), Materials.Water.getFluid(125), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Coal, 1), 125);
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Coal, 1));
                        break;
                    case "Diamond":
                        GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4, stack), 32, ItemList.IC2_Industrial_Diamond.get(3), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 16));
                        break;
                    case "Opal": case "Olivine": case "Emerald": case "Ruby": case "Sapphire": case "Green Sapphire": case "Topaz": case "Blue Topaz": case "Tanzanite":
                        GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4, stack), 24, OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 3), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 12));
                        break;
                    case "Fools Ruby": case "Garnet Red": case "Garnet Yellow": case "Jasper": case "Amber": case "Monazite": case "Forcicium": case "Forcillium": case "Force":
                        GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4, stack), 16, OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 3), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 8));
                }
                break;
            case dustPure: case dustImpure:case dustRefined:
                Material tByProduct = GT_Utility.selectItemInList(uEntry.orePrefix == OrePrefix.dustRefined ? 2 : uEntry.orePrefix == OrePrefix.dustPure ? 1 : 0, uEntry.material, uEntry.material.mOreByProducts);

                if (uEntry.orePrefix == OrePrefix.dustPure) {
                    if (uEntry.material.contains(SubTag.ELECTROMAGNETIC_SEPERATION_GOLD))
                        GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Gold, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Gold, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    if (uEntry.material.contains(SubTag.ELECTROMAGNETIC_SEPERATION_IRON))
                        GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Iron, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Iron, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    if (uEntry.material.contains(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)) {
                        GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Neodymium, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Neodymium, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    }
                }
                if (uEntry.material.contains(SubTag.CRYSTALLISABLE)) {
                    GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1, stack), Materials.Water.getFluid(200), OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1), 9000, 2000, 24);
                    GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1, stack), ModHandler.getDistilledWater(200), OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1), 9500, 1500, 24);
                }

                ItemStack tImpureStack = OreDictionaryUnifier.get(OrePrefix.dustTiny, tByProduct, OreDictionaryUnifier.get(OrePrefix.nugget, tByProduct, 1), 1);
                if (tImpureStack == null) {
                    tImpureStack = OreDictionaryUnifier.get(OrePrefix.dustSmall, tByProduct, 1);
                    if (tImpureStack == null) {
                        tImpureStack = OreDictionaryUnifier.get(OrePrefix.dust, tByProduct, OreDictionaryUnifier.get(OrePrefix.gem, tByProduct, 1), 1);
                        if (tImpureStack == null) {
                            tImpureStack = OreDictionaryUnifier.get(OrePrefix.cell, tByProduct, 1);
                            if (tImpureStack == null) {
                                GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), null, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass()));
                            } else {
                                FluidStack tFluid = GT_Utility.getFluidForFilledItem(tImpureStack, true);
                                if (tFluid == null) {
                                    GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(9, stack), 1, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 9L), tImpureStack, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 72L));
                                } else {
                                    tFluid.amount /= 10;
                                    GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1, stack), null, null, tFluid, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1L), null, null, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 8L), 5);
                                }
                            }
                        } else {
                            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(9, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 9), tImpureStack, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 72L));
                        }
                    } else {
                        GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(2, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 2), tImpureStack, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 16L));
                    }
                } else {
                    GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), tImpureStack, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 8L));
                }
                break;
            case dustSmall:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(4, stack), ItemList.Schematic_Dust.get(0), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), 100, 4);
                if (!uEntry.material.mBlastFurnaceRequired) {
                    GT_RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                    if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                        GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                    }
                }
                if (uEntry.material.mBlastFurnaceRequired) {
                    GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(4, stack), null, null, null, uEntry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, uEntry.material.mSmeltInto, OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 1L) : OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), null, (int) Math.max(uEntry.material.getMass() / 40L, 1L) * uEntry.material.mBlastFurnaceTemp, 120, uEntry.material.mBlastFurnaceTemp);
                } else {
                    ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(4, stack), ItemList.Shape_Mold_Ingot.get(0), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 130, 3, true);
                }
                break;
            case dustTiny:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(9, stack), ItemList.Schematic_Dust.get(0), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1L), 100, 4);
                if (!uEntry.material.mBlastFurnaceRequired) {
                    GT_RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                    if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                        GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                    }
                }
                if (!uEntry.material.contains(SubTag.NO_SMELTING)) {
                    if (uEntry.material.mBlastFurnaceRequired) {
                        GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(9, stack), null, null, null, uEntry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, uEntry.material.mSmeltInto, OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 1L) : OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), null, (int) Math.max(uEntry.material.getMass() / 40L, 1L) * uEntry.material.mBlastFurnaceTemp, 120, uEntry.material.mBlastFurnaceTemp);
                        ModHandler.removeFurnaceSmelting(stack);
                    } else {
                        ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material.mSmeltInto, 1L));
                        ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(9, stack), ItemList.Shape_Mold_Ingot.get(0), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 130, 3, true);
                    }
                }
                break;
        }
    }
}