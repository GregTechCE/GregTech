package gregtech.loaders.oreprocessing;

import gregtech.GregTechMod;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMELTING;

public class ProcessingDust implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.dust.addProcessingHandler(this);
        OrePrefix.dustPure.addProcessingHandler(this);
        OrePrefix.dustImpure.addProcessingHandler(this);
        OrePrefix.dustRefined.addProcessingHandler(this);
        OrePrefix.dustSmall.addProcessingHandler(this);
        OrePrefix.dustTiny.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (entry.orePrefix) {
            case dust:
                if (OreDictionaryUnifier.get(OrePrefix.cell, entry.material).getItem().hasContainerItem(stack)) {
                    RecipeMap.CANNER_RECIPES.recipeBuilder()
                            .inputs(stack, ModHandler.IC2.getIC2Item(ItemName.fluid_cell, 1))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.cell, entry.material, 1))
                            .duration(100)
                            .EUt(1)
                            .buildAndRegister();
                }
                if (entry.material instanceof MetalMaterial) {
                    if (((MetalMaterial) entry.material).blastFurnaceTemperature <= 0) {
                        RecipeRegistrator.registerReverseFluidSmelting(stack, entry.material, entry.orePrefix.materialAmount, null);

                        if (((MetalMaterial) entry.material).arcSmeltInto != entry.material) {
                            RecipeRegistrator.registerReverseArcSmelting(GTUtility.copyAmount(1, stack), entry.material, entry.orePrefix.materialAmount, null, null, null);
                        }
                    }
                }

                if (entry.material instanceof MetalMaterial && !entry.material.hasFlag(NO_SMELTING)) {

                    ItemStack ingotStack = OreDictionaryUnifier.get(OrePrefix.ingot, ((MetalMaterial) entry.material).smeltInto);
                    if (((MetalMaterial) entry.material).blastFurnaceTemperature > 0) {

                        ModHandler.removeFurnaceSmelting(stack);

                        int blastFurnaceTemperature = ((MetalMaterial) entry.material).blastFurnaceTemperature;
                        RecipeMap.BLAST_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(1, stack))
                                .outputs(blastFurnaceTemperature > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, ((MetalMaterial) entry.material).smeltInto) : GTUtility.copyAmount(1, ingotStack))
                                .duration((int) Math.max(entry.material.getMass() / 40L, 1L) * blastFurnaceTemperature)
                                .EUt(120)
                                .blastFurnaceTemp(blastFurnaceTemperature)
                                .buildAndRegister();

                        if (blastFurnaceTemperature <= 1000) {
                            ModHandler.addRCBlastFurnaceRecipe(GTUtility.copyAmount(1, stack),
                                    GTUtility.copyAmount(1, ingotStack),
                                    blastFurnaceTemperature);
                        }
                    } else {
                        ModHandler.addSmeltingRecipe(stack, ingotStack);
                    }
                } else if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    if (!OrePrefix.block.isIgnored(entry.material)
                            && null == OreDictionaryUnifier.get(OrePrefix.gem, entry.material)) {

                        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(9, stack))
                                .outputs(OreDictionaryUnifier.get(OrePrefix.block, entry.material))
                                .buildAndRegister();
                    }
                    if ((OrePrefix.block.isIgnored(entry.material) || null == OreDictionaryUnifier.get(OrePrefix.block, entry.material))
                            && entry.material != Materials.GraniteRed
                            && entry.material != Materials.GraniteBlack
                            && entry.material != Materials.Glass
                            && entry.material != Materials.Obsidian
                            && entry.material != Materials.Glowstone
                            && entry.material != Materials.Paper) {

                        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(1, stack))
                                .outputs(OreDictionaryUnifier.get(OrePrefix.plate, entry.material))
                    }
                }


                if (entry.material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)) {
                    RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .fluidInputs(Materials.Water.getFluid(200))
                            .chancedOutput(OreDictionaryUnifier.get(OrePrefix.gem, entry.material, 1), 7000)
                            .duration(2000)
                            .EUt(24)
                            .buildAndRegister();
                    RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .fluidInputs(ModHandler.getDistilledWater(200))
                            .chancedOutput(OreDictionaryUnifier.get(OrePrefix.gem, entry.material, 1), 9000)
                            .duration(1500)
                            .EUt(24)
                            .buildAndRegister();
                }

                switch (entry.material.toString()) {
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
                        if (GregTechMod.gregtechproxy.mTEMachineRecipes)
                            ModHandler.addLiquidTransposerFillRecipe(GTUtility.copyAmount(1, stack), Materials.Water.getFluid(125), OreDictionaryUnifier.get(OrePrefix.dust, Materials.HydratedCoal, 1), 125);
                        break;
                    case "HydratedCoal":
                        if (GregTechMod.gregtechproxy.mTEMachineRecipes)
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
                                .outputs(OreDictionaryUnifier.get(OrePrefix.gem, entry.material, 3), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 12))
                                .buildAndRegister();
                        break;
                    case "FoolsRuby": case "GarnetRed": case "GarnetYellow": case "Jasper": case "Amber": case "Monazite": case "Forcicium": case "Forcillium": case "Force":
                        RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(4, stack))
                                .explosivesAmount(16)
                                .outputs(OreDictionaryUnifier.get(OrePrefix.gem, entry.material, 3), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 8))
                                .buildAndRegister();
                }
                break;
            case dustPure: case dustImpure:case dustRefined:
                Material tByProduct = GTUtility.selectItemInList(entry.orePrefix == OrePrefix.dustRefined ? 2 : entry.orePrefix == OrePrefix.dustPure ? 1 : 0, entry.material, entry.material.mOreByProducts);

                if (entry.orePrefix == OrePrefix.dustPure) {
                    if (entry.material.hasFlag(ELECTROMAGNETIC_SEPERATION_GOLD))
                        GTValues.RA.addElectromagneticSeparatorRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Gold, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Gold, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    if (entry.material.contains(SubTag.ELECTROMAGNETIC_SEPERATION_IRON))
                        GTValues.RA.addElectromagneticSeparatorRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Iron, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Iron, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    if (entry.material.contains(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)) {
                        GTValues.RA.addElectromagneticSeparatorRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Neodymium, 1L), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Neodymium, 1L), new int[]{10000, 4000, 2000}, 400, 24);
                    }
                }
                if (entry.material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)) {
                    GTValues.RA.addAutoclaveRecipe(GTUtility.copyAmount(1, stack), Materials.Water.getFluid(200), OreDictionaryUnifier.get(OrePrefix.gem, entry.material, 1), 9000, 2000, 24);
                    GTValues.RA.addAutoclaveRecipe(GTUtility.copyAmount(1, stack), ModHandler.getDistilledWater(200), OreDictionaryUnifier.get(OrePrefix.gem, entry.material, 1), 9500, 1500, 24);
                }

                ItemStack tImpureStack = OreDictionaryUnifier.get(OrePrefix.dustTiny, tByProduct, OreDictionaryUnifier.get(OrePrefix.nugget, tByProduct, 1), 1);
                if (tImpureStack == null) {
                    tImpureStack = OreDictionaryUnifier.get(OrePrefix.dustSmall, tByProduct, 1);
                    if (tImpureStack == null) {
                        tImpureStack = OreDictionaryUnifier.get(OrePrefix.dust, tByProduct, OreDictionaryUnifier.get(OrePrefix.gem, tByProduct, 1), 1);
                        if (tImpureStack == null) {
                            tImpureStack = OreDictionaryUnifier.get(OrePrefix.cell, tByProduct, 1);
                            if (tImpureStack == null) {
                                GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(1, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 1), null, null, null, null, null, (int) Math.max(1L, entry.material.getMass()));
                            } else {
                                FluidStack tFluid = GTUtility.getFluidForFilledItem(tImpureStack, true);
                                if (tFluid == null) {
                                    GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(9, stack), 1, OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 9L), tImpureStack, null, null, null, null, (int) Math.max(1L, entry.material.getMass() * 72L));
                                } else {
                                    tFluid.amount /= 10;
                                    GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(1, stack), null, null, tFluid, OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 1L), null, null, null, null, null, null, (int) Math.max(1L, entry.material.getMass() * 8L), 5);
                                }
                            }
                        } else {
                            GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(9, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 9), tImpureStack, null, null, null, null, (int) Math.max(1L, entry.material.getMass() * 72L));
                        }
                    } else {
                        GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(2, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 2), tImpureStack, null, null, null, null, (int) Math.max(1L, entry.material.getMass() * 16L));
                    }
                } else {
                    GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(1, stack), 0, OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 1), tImpureStack, null, null, null, null, (int) Math.max(1L, entry.material.getMass() * 8L));
                }
                break;
            case dustSmall:
                GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(4, stack), ItemList.Schematic_Dust.get(0), OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 1), 100, 4);
                if (!entry.material.mBlastFurnaceRequired) {
                    RecipeRegistrator.registerReverseFluidSmelting(stack, entry.material, entry.orePrefix.mMaterialAmount, null);
                    if (entry.material.mSmeltInto.mArcSmeltInto != entry.material) {
                        RecipeRegistrator.registerReverseArcSmelting(GTUtility.copyAmount(1, stack), entry.material, entry.orePrefix.mMaterialAmount, null, null, null);
                    }
                }
                if (entry.material.mBlastFurnaceRequired) {
                    GTValues.RA.addBlastRecipe(GTUtility.copyAmount(4, stack), null, null, null, entry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, entry.material.mSmeltInto, OreDictionaryUnifier.get(OrePrefix.ingot, entry.material.mSmeltInto, 1L), 1L) : OreDictionaryUnifier.get(OrePrefix.ingot, entry.material.mSmeltInto, 1L), null, (int) Math.max(entry.material.getMass() / 40L, 1L) * entry.material.mBlastFurnaceTemp, 120, entry.material.mBlastFurnaceTemp);
                } else {
                    ModHandler.addAlloySmelterRecipe(GTUtility.copyAmount(4, stack), ItemList.Shape_Mold_Ingot.get(0), OreDictionaryUnifier.get(OrePrefix.ingot, entry.material.mSmeltInto, 1L), 130, 3, true);
                }
                break;
            case dustTiny:
                GTValues.RA.addBoxingRecipe(GTUtility.copyAmount(9, stack), ItemList.Schematic_Dust.get(0), OreDictionaryUnifier.get(OrePrefix.dust, entry.material, 1L), 100, 4);
                if (!entry.material.mBlastFurnaceRequired) {
                    RecipeRegistrator.registerReverseFluidSmelting(stack, entry.material, entry.orePrefix.mMaterialAmount, null);
                    if (entry.material.mSmeltInto.mArcSmeltInto != entry.material) {
                        RecipeRegistrator.registerReverseArcSmelting(GTUtility.copyAmount(1, stack), entry.material, entry.orePrefix.mMaterialAmount, null, null, null);
                    }
                }
                if (!entry.material.contains(SubTag.NO_SMELTING)) {
                    if (entry.material.mBlastFurnaceRequired) {
                        GTValues.RA.addBlastRecipe(GTUtility.copyAmount(9, stack), null, null, null, entry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, entry.material.mSmeltInto, OreDictionaryUnifier.get(OrePrefix.ingot, entry.material.mSmeltInto, 1L), 1L) : OreDictionaryUnifier.get(OrePrefix.ingot, entry.material.mSmeltInto, 1L), null, (int) Math.max(entry.material.getMass() / 40L, 1L) * entry.material.mBlastFurnaceTemp, 120, entry.material.mBlastFurnaceTemp);
                        ModHandler.removeFurnaceSmelting(stack);
                    } else {
                        ModHandler.addSmeltingRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, entry.material.mSmeltInto, 1L));
                        ModHandler.addAlloySmelterRecipe(GTUtility.copyAmount(9, stack), ItemList.Shape_Mold_Ingot.get(0), OreDictionaryUnifier.get(OrePrefix.ingot, entry.material.mSmeltInto, 1L), 130, 3, true);
                    }
                }
                break;
        }
    }
}