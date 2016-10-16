package gregtech.loaders.oreprocessing;

import gregtech.GT_Mod;
import gregtech.api.enums.*;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class ProcessingDust implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingDust() {
        OrePrefixes.dust.add(this);
        OrePrefixes.dustPure.add(this);
        OrePrefixes.dustImpure.add(this);
        OrePrefixes.dustRefined.add(this);
        OrePrefixes.dustSmall.add(this);
        OrePrefixes.dustTiny.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        switch (aPrefix) {
            case dust:
                if (aMaterial.mFuelPower > 0)
                    GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, aMaterial.mFuelPower, aMaterial.mFuelType);
                if (GT_Utility.getFluidForFilledItem(GT_OreDictUnificator.get(OrePrefixes.cell, aMaterial, 1L), true) == null)
                    GT_Values.RA.addCannerRecipe(aStack, ItemList.Cell_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.cell, aMaterial, 1L), null, 100, 1);
                if (!aMaterial.mBlastFurnaceRequired) {
                    GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
                    if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
                        GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[]{aStack}), aMaterial, aPrefix.mMaterialAmount, null, null, null);
                    }
                }

                ItemStack tDustStack;
                if ((null != (tDustStack = GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L))) && (!aMaterial.contains(SubTag.NO_SMELTING))) {
                    if (aMaterial.mBlastFurnaceRequired) {
                        GT_ModHandler.removeFurnaceSmelting(aStack);
                        GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.mSmeltInto, tDustStack, 1L) : GT_Utility.copyAmount(1L, new Object[]{tDustStack}), null, (int) Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
                        if (aMaterial.mBlastFurnaceTemp <= 1000) {
                            GT_ModHandler.addRCBlastFurnaceRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_Utility.copyAmount(1L, new Object[]{tDustStack}), aMaterial.mBlastFurnaceTemp);
                        }
                    } else {
                        GT_ModHandler.addSmeltingRecipe(aStack, tDustStack);
                    }
                } else if (!aMaterial.contains(SubTag.NO_WORKING)) {
                    if ((!OrePrefixes.block.isIgnored(aMaterial)) && (null == GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L))) {
                        GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L));
                    }
                    if (((OrePrefixes.block.isIgnored(aMaterial)) || (null == GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L))) && (aMaterial != Materials.GraniteRed) && (aMaterial != Materials.GraniteBlack) && (aMaterial != Materials.Glass) && (aMaterial != Materials.Obsidian) && (aMaterial != Materials.Glowstone) && (aMaterial != Materials.Paper)) {
                        GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L));
                    }
                }


                if ((aMaterial.mMaterialList.size() > 0) && ((aMaterial.mExtraData & 0x3) != 0)) {
                    long tItemAmount = 0L;
                    long tCapsuleCount = 0L;
                    long tDensityMultiplier = aMaterial.getDensity() > 3628800L ? aMaterial.getDensity() / 3628800L : 1L;
                    ArrayList<ItemStack> tList = new ArrayList();
                    for (MaterialStack tMat : aMaterial.mMaterialList)
                        if (tMat.mAmount > 0L) {
                            if (tMat.mMaterial == Materials.Air) {
                                tDustStack = ItemList.Cell_Air.get(tMat.mAmount / 2L, new Object[0]);
                            } else {
                                tDustStack = GT_OreDictUnificator.get(OrePrefixes.dust, tMat.mMaterial, tMat.mAmount);
                                if (tDustStack == null)
                                    tDustStack = GT_OreDictUnificator.get(OrePrefixes.cell, tMat.mMaterial, tMat.mAmount);
                            }
                            if (tItemAmount + tMat.mAmount * 3628800L <= aStack.getMaxStackSize() * aMaterial.getDensity()) {
                                tItemAmount += tMat.mAmount * 3628800L;
                                if (tDustStack != null) {
                                    ItemStack tmp793_791 = tDustStack;
                                    tmp793_791.stackSize = ((int) (tmp793_791.stackSize * tDensityMultiplier));
                                    while ((tDustStack.stackSize > 64) && (tList.size() < 6) && (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCount(tDustStack) * 64 <= 64L)) {
                                        tCapsuleCount += GT_ModHandler.getCapsuleCellContainerCount(tDustStack) * 64;
                                        tList.add(GT_Utility.copyAmount(64L, new Object[]{tDustStack}));
                                        tDustStack.stackSize -= 64;
                                    }
                                    if ((tDustStack.stackSize > 0) && (tList.size() < 6) && (tCapsuleCount + GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[]{tDustStack}) <= 64L)) {
                                        tCapsuleCount += GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[]{tDustStack});
                                        tList.add(tDustStack);
                                    }
                                }
                            }
                        }
                    tItemAmount = (tItemAmount * tDensityMultiplier % aMaterial.getDensity() > 0L ? 1 : 0) + tItemAmount * tDensityMultiplier / aMaterial.getDensity();
                    if (tList.size() > 0) {
                        FluidStack tFluid = null;
                        int tList_sS = tList.size();
                        for (int i = 0; i < tList_sS; i++) {
                            if ((!ItemList.Cell_Air.isStackEqual(tList.get(i))) && ((tFluid = GT_Utility.getFluidForFilledItem((ItemStack) tList.get(i), true)) != null)) {
                                tFluid.amount *= ((ItemStack) tList.get(i)).stackSize;
                                tCapsuleCount -= GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(new ItemStack[]{(ItemStack) tList.get(i)});
                                tList.remove(i);
                                break;
                            }
                        }
                        if ((aMaterial.mExtraData & 0x1) != 0)
                            GT_Values.RA.addElectrolyzerRecipe(GT_Utility.copyAmount(tItemAmount, new Object[]{aStack}), tCapsuleCount > 0L ? ItemList.Cell_Empty.get(tCapsuleCount, new Object[0]) : null, null, tFluid, tList.size() < 1 ? null : (ItemStack) tList.get(0), tList.size() < 2 ? null : (ItemStack) tList.get(1), tList.size() < 3 ? null : (ItemStack) tList.get(2), tList.size() < 4 ? null : (ItemStack) tList.get(3), tList.size() < 5 ? null : (ItemStack) tList.get(4), tList.size() < 6 ? null : (ItemStack) tList.get(5), null, (int) Math.max(1L, Math.abs(aMaterial.getProtons() * 2L * tItemAmount)), Math.min(4, tList.size()) * 30);
                        if ((aMaterial.mExtraData & 0x2) != 0) {
                            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(tItemAmount, new Object[]{aStack}), tCapsuleCount > 0L ? ItemList.Cell_Empty.get(tCapsuleCount, new Object[0]) : null, null, tFluid, tList.size() < 1 ? null : (ItemStack) tList.get(0), tList.size() < 2 ? null : (ItemStack) tList.get(1), tList.size() < 3 ? null : (ItemStack) tList.get(2), tList.size() < 4 ? null : (ItemStack) tList.get(3), tList.size() < 5 ? null : (ItemStack) tList.get(4), tList.size() < 6 ? null : (ItemStack) tList.get(5), null, (int) Math.max(1L, Math.abs(aMaterial.getMass() * 4L * tItemAmount)), Math.min(4, tList.size()) * 5);
                        }
                    }
                }
                if (aMaterial.contains(SubTag.CRYSTALLISABLE)) {
                    GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Water.getFluid(200L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 7000, 2000, 24);
                    GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_ModHandler.getDistilledWater(200L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 9000, 1500, 24);
                }

                switch (aMaterial.mName) {
                    case "NULL":
                        break;
                    case "Glass":
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(net.minecraft.init.Blocks.glass));
                        break;
                    case "NetherQuartz": case "Quartz": case "CertusQuartz":
                        if (gregtech.api.GregTech_API.sRecipeFile.get(gregtech.api.enums.ConfigCategories.Recipes.disabledrecipes, "QuartzDustSmeltingIntoAESilicon", true))
                            GT_ModHandler.removeFurnaceSmelting(aStack);
                        break;
                    case "MeatRaw":
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L));
                        break;
                    case "Mercury":
                        System.err.println("Quicksilver Dust?, To melt that, you don't even need a Furnace...");
                        break;
                    case "Tetrahedrite": case "Chalcopyrite": case "Malachite":
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Copper, 6L));
                        break;
                    case "Pentlandite":
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Nickel, 6L));
                        break;
                    case "Garnierite":
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Nickel, 1L));
                        break;
                    case "Cassiterite": case "CassiteriteSand":
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L));
                        break;
                    case "Magnetite": case "VanadiumMagnetite": case "BasalticMineralSand": case "GraniticMineralSand":
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Iron, 3L));
                        break;
                    case "YellowLimonite": case "BrownLimonite": case "BandedIron":
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 1L));
                        break;
                    case "Coal":
                        if (GT_Mod.gregtechproxy.mTEMachineRecipes)
                            GT_ModHandler.addLiquidTransposerFillRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Water.getFluid(125L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HydratedCoal, 1L), 125);
                        break;
                    case "HydratedCoal":
                        if (GT_Mod.gregtechproxy.mTEMachineRecipes)
                            GT_ModHandler.addLiquidTransposerEmptyRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Water.getFluid(125L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L), 125);
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L));
                        break;
                    case "Diamond":
                        GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), 32, ItemList.IC2_Industrial_Diamond.get(3L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 16L));
                        break;
                    case "Opal": case "Olivine": case "Emerald": case "Ruby": case "Sapphire": case "GreenSapphire": case "Topaz": case "BlueTopaz": case "Tanzanite":
                        GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), 24, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 3L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 12L));
                        break;
                    case "FoolsRuby": case "GarnetRed": case "GarnetYellow": case "Jasper": case "Amber": case "Monazite": case "Forcicium": case "Forcillium": case "Force":
                        GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), 16, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 3L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 8L));
                }
                break;
            case dustPure: case dustImpure:case dustRefined:
                Materials tByProduct = (Materials) GT_Utility.selectItemInList(aPrefix == OrePrefixes.dustRefined ? 2 : aPrefix == OrePrefixes.dustPure ? 1 : 0, aMaterial, aMaterial.mOreByProducts);

                if (aPrefix == OrePrefixes.dustPure) {
                    if (aMaterial.contains(SubTag.ELECTROMAGNETIC_SEPERATION_GOLD))
                        GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Gold, 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Gold, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    if (aMaterial.contains(SubTag.ELECTROMAGNETIC_SEPERATION_IRON))
                        GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Iron, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    if (aMaterial.contains(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)) {
                        GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Neodymium, 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Neodymium, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    }
                }
                if (aMaterial.contains(SubTag.CRYSTALLISABLE)) {
                    GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Water.getFluid(200L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 9000, 2000, 24);
                    GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), gregtech.api.util.GT_ModHandler.getDistilledWater(200L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 9500, 1500, 24);
                }

                ItemStack tImpureStack = GT_OreDictUnificator.get(OrePrefixes.dustTiny, tByProduct, GT_OreDictUnificator.get(OrePrefixes.nugget, tByProduct, 1L), 1L);
                if (tImpureStack == null) {
                    tImpureStack = GT_OreDictUnificator.get(OrePrefixes.dustSmall, tByProduct, 1L);
                    if (tImpureStack == null) {
                        tImpureStack = GT_OreDictUnificator.get(OrePrefixes.dust, tByProduct, GT_OreDictUnificator.get(OrePrefixes.gem, tByProduct, 1L), 1L);
                        if (tImpureStack == null) {
                            tImpureStack = GT_OreDictUnificator.get(OrePrefixes.cell, tByProduct, 1L);
                            if (tImpureStack == null) {
                                GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), 0, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), null, null, null, null, null, (int) Math.max(1L, aMaterial.getMass()));
                            } else {
                                FluidStack tFluid = GT_Utility.getFluidForFilledItem(tImpureStack, true);
                                if (tFluid == null) {
                                    GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), 1, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 9L), tImpureStack, null, null, null, null, (int) Math.max(1L, aMaterial.getMass() * 72L));
                                } else {
                                    tFluid.amount /= 10;
                                    GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, null, tFluid, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), null, null, null, null, null, null, (int) Math.max(1L, aMaterial.getMass() * 8L), 5);
                                }
                            }
                        } else {
                            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), 0, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 9L), tImpureStack, null, null, null, null, (int) Math.max(1L, aMaterial.getMass() * 72L));
                        }
                    } else {
                        GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), 0, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 2L), tImpureStack, null, null, null, null, (int) Math.max(1L, aMaterial.getMass() * 16L));
                    }
                } else {
                    GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), 0, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), tImpureStack, null, null, null, null, (int) Math.max(1L, aMaterial.getMass() * 8L));
                }
                break;
            case dustSmall:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), ItemList.Schematic_Dust.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 100, 4);
                if (!aMaterial.mBlastFurnaceRequired) {
                    GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
                    if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
                        GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[]{aStack}), aMaterial, aPrefix.mMaterialAmount, null, null, null);
                    }
                }
                if (aMaterial.mBlastFurnaceRequired) {
                    GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.mSmeltInto, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), null, (int) Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
                } else {
                    gregtech.api.util.GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 130, 3, true);
                }
                break;
            case dustTiny:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), ItemList.Schematic_Dust.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 100, 4);
                if (!aMaterial.mBlastFurnaceRequired) {
                    GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
                    if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
                        GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[]{aStack}), aMaterial, aPrefix.mMaterialAmount, null, null, null);
                    }
                }
                if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMELTING)) {
                    if (aMaterial.mBlastFurnaceRequired) {
                        GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.mSmeltInto, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), null, (int) Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
                        GT_ModHandler.removeFurnaceSmelting(aStack);
                    } else {
                        GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 1L));
                        GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 130, 3, true);
                    }
                }
                break;
        }
    }
}
