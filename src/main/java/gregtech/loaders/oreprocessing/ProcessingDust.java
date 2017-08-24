package gregtech.loaders.oreprocessing;

import gregtech.GT_Mod;
import gregtech.api.ConfigCategories;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class ProcessingDust implements IOreRegistrationHandler {
    public ProcessingDust() {
        OrePrefix.dust.addProcessingHandler(this);
        OrePrefix.dustPure.addProcessingHandler(this);
        OrePrefix.dustImpure.addProcessingHandler(this);
        OrePrefix.dustRefined.addProcessingHandler(this);
        OrePrefix.dustSmall.addProcessingHandler(this);
        OrePrefix.dustTiny.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.orePrefix) {
            case dust:
                if (uEntry.material.mFuelPower > 0)
                    GTValues.RA.addFuel(GTUtility.copyAmount(1, stack), null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                if (GTUtility.getFluidForFilledItem(OreDictionaryUnifier.get(OrePrefix.cell, uEntry.material, 1), true) == null)
                    RecipeMap.CANNER_RECIPES.recipeBuilder()
                            .inputs(stack, ItemList.Cell_Empty.get(1))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.cell, uEntry.material, 1))
                            .duration(100)
                            .EUt(1)
                            .buildAndRegister();
                if (!uEntry.material.mBlastFurnaceRequired) {
                    RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                    if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                        RecipeRegistrator.registerReverseArcSmelting(GTUtility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                    }
                }

                ItemStack dustStack;
                if ((null != (dustStack = OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L))) && (!uEntry.material.contains(SubTag.NO_SMELTING))) {
                    if (uEntry.material.mBlastFurnaceRequired) {
                        ModHandler.removeFurnaceSmelting(stack);
                        GTValues.RA.addBlastRecipe(GTUtility.copyAmount(1, stack), null, null, null, uEntry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, uEntry.material.mSmeltInto, dustStack, 1L) : GTUtility.copyAmount(1L, new Object[]{dustStack}), null, (int) Math.max(uEntry.material.getMass() / 40L, 1L) * uEntry.material.mBlastFurnaceTemp, 120, uEntry.material.mBlastFurnaceTemp);
                        if (uEntry.material.mBlastFurnaceTemp <= 1000) {
                            ModHandler.addRCBlastFurnaceRecipe(GTUtility.copyAmount(1, stack), GTUtility.copyAmount(1, dustStack), uEntry.material.mBlastFurnaceTemp);
                        }
                    } else {
                        ModHandler.addSmeltingRecipe(stack, dustStack);
                    }
                } else if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    if ((!OrePrefix.block.isIgnored(uEntry.material)) && (null == OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1))) {
                        ModHandler.addCompressionRecipe(GTUtility.copyAmount(9, stack), OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1));
                    }
                    if (((OrePrefix.block.isIgnored(uEntry.material)) || (null == OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1))) && (uEntry.material != Materials.GraniteRed) && (uEntry.material != Materials.GraniteBlack) && (uEntry.material != Materials.Glass) && (uEntry.material != Materials.Obsidian) && (uEntry.material != Materials.Glowstone) && (uEntry.material != Materials.Paper)) {
                        ModHandler.addCompressionRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1));
                    }
                }


                if ((uEntry.material.mMaterialList.size() > 0) && ((uEntry.material.mExtraData & 0x3) != 0)) {
                    long tItemAmount = 0;
                    int tCapsuleCount = 0;
                    long tDensityMultiplier = uEntry.material.getDensity() > 3628800L ? uEntry.material.getDensity() / 3628800L : 1L;
                    ArrayList<ItemStack> tList = new ArrayList();
                    for (MaterialStack tMat : uEntry.material.mMaterialList)
                        if (tMat.mAmount > 0L) {
                            if (tMat.mMaterial == Materials.Air) {
                                dustStack = ItemList.Cell_Air.get(tMat.mAmount / 2L);
                            } else {
                                dustStack = OreDictionaryUnifier.get(OrePrefix.dust, tMat.mMaterial, tMat.mAmount);
                                if (dustStack == null)
                                    dustStack = OreDictionaryUnifier.get(OrePrefix.cell, tMat.mMaterial, tMat.mAmount);
                            }
                            if (tItemAmount + tMat.mAmount * 3628800L <= stack.getMaxStackSize() * uEntry.material.getDensity()) {
                                tItemAmount += tMat.mAmount * 3628800L;
                                if (dustStack != null) {
                                    ItemStack tmp793_791 = dustStack;
                                    tmp793_791.stackSize = ((int) (tmp793_791.stackSize * tDensityMultiplier));
                                    while ((dustStack.stackSize > 64) && (tList.size() < 6) && (tCapsuleCount + ModHandler.getCapsuleCellContainerCount(dustStack) * 64 <= 64L)) {
                                        tCapsuleCount += ModHandler.getCapsuleCellContainerCount(dustStack) * 64;
                                        tList.add(GTUtility.copyAmount(64, dustStack));
                                        dustStack.stackSize -= 64;
                                    }
                                    if ((dustStack.stackSize > 0) && (tList.size() < 6) && (tCapsuleCount + ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(dustStack) <= 64L)) {
                                        tCapsuleCount += ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(dustStack);
                                        tList.add(dustStack);
                                    }
                                }
                            }
                        }
                    tItemAmount = (tItemAmount * tDensityMultiplier % uEntry.material.getDensity() > 0 ? 1 : 0) + tItemAmount * tDensityMultiplier / uEntry.material.getDensity();
                    if (tList.size() > 0) {
                        FluidStack tFluid = null;
                        int tList_sS = tList.size();
                        for (int i = 0; i < tList_sS; i++) {
                            if ((!ItemList.Cell_Air.isStackEqual(tList.get(i))) && ((tFluid = GTUtility.getFluidForFilledItem(tList.get(i), true)) != null)) {
                                tFluid.amount *=  tList.get(i).stackSize;
                                tCapsuleCount -= ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(tList.get(i));
                                tList.remove(i);
                                break;
                            }
                        }
                        if ((uEntry.material.mExtraData & 0x1) != 0)
                            GTValues.RA.addElectrolyzerRecipe(GTUtility.copyAmount(tItemAmount, stack), tCapsuleCount > 0L ? ItemList.Cell_Empty.get(tCapsuleCount) : null, null, tFluid, tList.size() < 1 ? null : (ItemStack) tList.get(0), tList.size() < 2 ? null : (ItemStack) tList.get(1), tList.size() < 3 ? null : (ItemStack) tList.get(2), tList.size() < 4 ? null : (ItemStack) tList.get(3), tList.size() < 5 ? null : (ItemStack) tList.get(4), tList.size() < 6 ? null : (ItemStack) tList.get(5), null, (int) Math.max(1L, Math.abs(uEntry.material.getProtons() * 2L * tItemAmount)), Math.min(4, tList.size()) * 30);
                        if ((uEntry.material.mExtraData & 0x2) != 0) {
                            GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(tItemAmount, stack), tCapsuleCount > 0L ? ItemList.Cell_Empty.get(tCapsuleCount) : null, null, tFluid, tList.size() < 1 ? null : (ItemStack) tList.get(0), tList.size() < 2 ? null : (ItemStack) tList.get(1), tList.size() < 3 ? null : (ItemStack) tList.get(2), tList.size() < 4 ? null : (ItemStack) tList.get(3), tList.size() < 5 ? null : (ItemStack) tList.get(4), tList.size() < 6 ? null : (ItemStack) tList.get(5), null, (int) Math.max(1L, Math.abs(uEntry.material.getMass() * 4L * tItemAmount)), Math.min(4, tList.size()) * 5);
                        }
                    }
                }
                if (uEntry.material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)) {
                    RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .fluidInputs(Materials.Water.getFluid(200))
                            .chancedOutput(OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1), 7000)
                            .duration(2000)
                            .EUt(24)
                            .buildAndRegister();
                    RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .fluidInputs(ModHandler.getDistilledWater(200))
                            .chancedOutput(OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1), 9000)
                            .duration(1500)
                            .EUt(24)
                            .buildAndRegister();
                }

                switch (uEntry.material.toString()) {
                    case "NULL":
                        break;
                    case "Glass":
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), new ItemStack(net.minecraft.init.Blocks.GLASS));
                        break;
                    case "NetherQuartz": case "Quartz": case "CertusQuartz":
                        if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QuartzDustSmeltingIntoAESilicon", true))
                            ModHandler.removeFurnaceSmelting(stack);
                        break;
                    case "MeatRaw":
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.MeatCooked, 1));
                        break;
                    case "Mercury":
                        System.err.println("Quicksilver Dust?, To melt that, you don't even need a Furnace...");
                        break;
                    case "Tetrahedrite": case "Chalcopyrite": case "Malachite":
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Copper, 6));
                        break;
                    case "Pentlandite":
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Nickel, 6));
                        break;
                    case "Garnierite":
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Nickel, 1));
                        break;
                    case "Cassiterite": case "CassiteriteSand":
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Tin, 1));
                        break;
                    case "Magnetite": case "VanadiumMagnetite": case "BasalticMineral Sand": case "GraniticMineral Sand":
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Iron, 3));
                        break;
                    case "YellowLimonite": case "BrownLimonite": case "BandedIron":
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Iron, 1));
                        break;
                    case "Coal":
                        if (GT_Mod.gregtechproxy.mTEMachineRecipes)
                            ModHandler.addLiquidTransposerFillRecipe(GTUtility.copyAmount(1, stack), Materials.Water.getFluid(125), OreDictionaryUnifier.get(OrePrefix.dust, Materials.HydratedCoal, 1), 125);
                        break;
                    case "HydratedCoal":
                        if (GT_Mod.gregtechproxy.mTEMachineRecipes)
                            ModHandler.addLiquidTransposerEmptyRecipe(GTUtility.copyAmount(1, stack), Materials.Water.getFluid(125), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Coal, 1), 125);
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Coal, 1));
                        break;
                    case "Diamond":
                        RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(4, stack))
                                .explosivesAmount(32)
                                .outputs(ItemList.IC2_Industrial_Diamond.get(3), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 16))
                                .buildAndRegister();
                        break;
                    case "Opal": case "Olivine": case "Emerald": case "Ruby": case "Sapphire": case "GreenSapphire": case "Topaz": case "BlueTopaz": case "Tanzanite":
                        RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(4, stack))
                                .explosivesAmount(24)
                                .outputs(OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 3), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 12))
                                .buildAndRegister();
                        break;
                    case "FoolsRuby": case "GarnetRed": case "GarnetYellow": case "Jasper": case "Amber": case "Monazite": case "Forcicium": case "Forcillium": case "Force":
                        RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(4, stack))
                                .explosivesAmount(16)
                                .outputs(OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 3), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 8))
                                .buildAndRegister();
                }
                break;
            case dustPure: case dustImpure:case dustRefined:
                Material tByProduct = GTUtility.selectItemInList(uEntry.orePrefix == OrePrefix.dustRefined ? 2 : uEntry.orePrefix == OrePrefix.dustPure ? 1 : 0, uEntry.material, uEntry.material.mOreByProducts);

                if (uEntry.orePrefix == OrePrefix.dustPure) {
                    if (uEntry.material.contains(SubTag.ELECTROMAGNETIC_SEPERATION_GOLD))
                        GTValues.RA.addElectromagneticSeparatorRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Gold, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Gold, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    if (uEntry.material.contains(SubTag.ELECTROMAGNETIC_SEPERATION_IRON))
                        GTValues.RA.addElectromagneticSeparatorRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Iron, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Iron, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    if (uEntry.material.contains(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)) {
                        GTValues.RA.addElectromagneticSeparatorRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Neodymium, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Neodymium, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    }
                }
                if (uEntry.material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)) {
                    GTValues.RA.addAutoclaveRecipe(GTUtility.copyAmount(1, stack), Materials.Water.getFluid(200), OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1), 9000, 2000, 24);
                    GTValues.RA.addAutoclaveRecipe(GTUtility.copyAmount(1, stack), ModHandler.getDistilledWater(200), OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1), 9500, 1500, 24);
                }

                ItemStack tImpureStack = OreDictionaryUnifier.get(OrePrefix.dustTiny, tByProduct, OreDictionaryUnifier.get(OrePrefix.nugget, tByProduct, 1), 1);
                if (tImpureStack == null) {
                    tImpureStack = OreDictionaryUnifier.get(OrePrefix.dustSmall, tByProduct, 1);
                    if (tImpureStack == null) {
                        tImpureStack = OreDictionaryUnifier.get(OrePrefix.dust, tByProduct, OreDictionaryUnifier.get(OrePrefix.gem, tByProduct, 1), 1);
                        if (tImpureStack == null) {
                            tImpureStack = OreDictionaryUnifier.get(OrePrefix.cell, tByProduct, 1);
                            if (tImpureStack == null) {
                                GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(1, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), null, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass()));
                            } else {
                                FluidStack tFluid = GTUtility.getFluidForFilledItem(tImpureStack, true);
                                if (tFluid == null) {
                                    GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(9, stack), 1, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 9L), tImpureStack, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 72L));
                                } else {
                                    tFluid.amount /= 10;
                                    GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(1, stack), null, null, tFluid, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1L), null, null, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 8L), 5);
                                }
                            }
                        } else {
                            GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(9, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 9), tImpureStack, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 72L));
                        }
                    } else {
                        GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(2, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 2), tImpureStack, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 16L));
                    }
                } else {
                    GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(1, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), tImpureStack, null, null, null, null, (int) Math.max(1L, uEntry.material.getMass() * 8L));
                }
                break;
            case dustSmall:
                GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(4, stack), ItemList.Schematic_Dust.get(0), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), 100, 4);
                if (!uEntry.material.mBlastFurnaceRequired) {
                    RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                    if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                        RecipeRegistrator.registerReverseArcSmelting(GTUtility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                    }
                }
                if (uEntry.material.mBlastFurnaceRequired) {
                    GTValues.RA.addBlastRecipe(GTUtility.copyAmount(4, stack), null, null, null, uEntry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, uEntry.material.mSmeltInto, OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 1L) : OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), null, (int) Math.max(uEntry.material.getMass() / 40L, 1L) * uEntry.material.mBlastFurnaceTemp, 120, uEntry.material.mBlastFurnaceTemp);
                } else {
                    ModHandler.addAlloySmelterRecipe(GTUtility.copyAmount(4, stack), ItemList.Shape_Mold_Ingot.get(0), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 130, 3, true);
                }
                break;
            case dustTiny:
                GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(9, stack), ItemList.Schematic_Dust.get(0), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1L), 100, 4);
                if (!uEntry.material.mBlastFurnaceRequired) {
                    RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.mMaterialAmount, null);
                    if (uEntry.material.mSmeltInto.mArcSmeltInto != uEntry.material) {
                        RecipeRegistrator.registerReverseArcSmelting(GTUtility.copyAmount(1, stack), uEntry.material, uEntry.orePrefix.mMaterialAmount, null, null, null);
                    }
                }
                if (!uEntry.material.contains(SubTag.NO_SMELTING)) {
                    if (uEntry.material.mBlastFurnaceRequired) {
                        GTValues.RA.addBlastRecipe(GTUtility.copyAmount(9, stack), null, null, null, uEntry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, uEntry.material.mSmeltInto, OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 1L) : OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), null, (int) Math.max(uEntry.material.getMass() / 40L, 1L) * uEntry.material.mBlastFurnaceTemp, 120, uEntry.material.mBlastFurnaceTemp);
                        ModHandler.removeFurnaceSmelting(stack);
                    } else {
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material.mSmeltInto, 1L));
                        ModHandler.addAlloySmelterRecipe(GTUtility.copyAmount(9, stack), ItemList.Shape_Mold_Ingot.get(0), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 130, 3, true);
                    }
                }
                break;
        }
    }
}