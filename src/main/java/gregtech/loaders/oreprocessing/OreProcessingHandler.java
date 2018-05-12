package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.material.type.DustMaterial.MatFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;
import static gregtech.api.unification.material.type.MetalMaterial.MatFlags.GENERATE_BOLT_SCREW;
import static gregtech.api.unification.material.type.MetalMaterial.MatFlags.GENERATE_RING;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_GEAR;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_ROD;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.MORTAR_GRINDABLE;
import static gregtech.api.unification.ore.OrePrefix.Conditions.isToolMaterial;
import static gregtech.api.unification.ore.OrePrefix.and;
import static gregtech.api.unification.ore.OrePrefix.noFlag;

public class OreProcessingHandler {

    public void registerProcessing() {
        OrePrefix.log.addProcessingHandler(this::processLog);
        OrePrefix.plank.addProcessingHandler(this::processPlank);
        OrePrefix.plate.addProcessingHandler(this::processPlate, this::processPolarizing);
        OrePrefix.plateDense.addProcessingHandler(this::processPlateDense, this::processPolarizing);
        OrePrefix.rotor.addProcessingHandler(this::processRotor, this::processPolarizing);
        //OrePrefix.stoneCobble.addProcessingHandler(this::processStone);
        //OrePrefix.stoneSmooth.addProcessingHandler(this::processStone);
        //OrePrefix.stone.addProcessingHandler(this::processStone);
        OrePrefix.stick.addProcessingHandler(this::processStick, this::processPolarizing);
        OrePrefix.stickLong.addProcessingHandler(this::processLongStick, this::processPolarizing);
        OrePrefix.dust.addProcessingHandler(this::processDust, this::processDecomposition, this::processShaping);
        OrePrefix.ingot.addProcessingHandler(this::processIngot, this::processPolarizing, this::processShaping);
        OrePrefix.nugget.addProcessingHandler(this::processNugget, this::processPolarizing);
        OrePrefix.dustSmall.addProcessingHandler(this::processSmallDust);
        OrePrefix.dustTiny.addProcessingHandler(this::processTinyDust);
        OrePrefix.turbineBlade.addProcessingHandler(this::processTurbine);
        OrePrefix.block.addProcessingHandler(this::processBlock);
        OrePrefix.bolt.addProcessingHandler(this::processBolt, this::processPolarizing);
        OrePrefix.screw.addProcessingHandler(this::processScrew, this::processPolarizing);
        OrePrefix.wireFine.addProcessingHandler(this::processFineWire, this::processPolarizing);
        OrePrefix.foil.addProcessingHandler(this::processFoil, this::processPolarizing);
        OrePrefix.lens.addProcessingHandler(this::processLens);
        OrePrefix.ore.addProcessingHandler(this::processOre);
        OrePrefix.frameGt.addProcessingHandler(this::processFrame);
        OrePrefix.oreBasalt.addProcessingHandler(this::processOre);
        OrePrefix.oreBlackgranite.addProcessingHandler(this::processOre);
        OrePrefix.oreEndstone.addProcessingHandler(this::processOre);
        OrePrefix.oreGravel.addProcessingHandler(this::processOre);
        OrePrefix.oreNetherrack.addProcessingHandler(this::processOre);
        OrePrefix.oreMarble.addProcessingHandler(this::processOre);
        OrePrefix.oreRedgranite.addProcessingHandler(this::processOre);
        OrePrefix.oreSand.addProcessingHandler(this::processOre);
        OrePrefix.crushed.addProcessingHandler(this::processCrushedOre);
        OrePrefix.crushedPurified.addProcessingHandler(this::processCrushedPurified);
        OrePrefix.crushedCentrifuged.addProcessingHandler(this::processCrushedCentrifuged);
        OrePrefix.crystalline.addProcessingHandler(this::processCrystallizedPurified);
        OrePrefix.dustImpure.addProcessingHandler(this::processDirtyDust);
        OrePrefix.dustPure.addProcessingHandler(this::processPure);
        OrePrefix.dustSmall.addProcessingHandler(this::processSmallDust);
        OrePrefix.dustTiny.addProcessingHandler(this::processTinyDust);
        OrePrefix.gear.addProcessingHandler(this::processGear);
        OrePrefix.gearSmall.addProcessingHandler(this::processGear);
        OrePrefix.gem.addProcessingHandler(this::processGem);
        OrePrefix.wireGtSingle.addProcessingHandler(this::processWireSingle);
        OrePrefix.wireGtDouble.addProcessingHandler(this::processWireDouble);
        OrePrefix.wireGtQuadruple.addProcessingHandler(this::processWireQuadruple);
        OrePrefix.wireGtOctal.addProcessingHandler(this::processWireOctal);
        OrePrefix.wireGtTwelve.addProcessingHandler(this::processWireTwelve);
        OrePrefix.wireGtHex.addProcessingHandler(this::processWireHex);
        OrePrefix.toolHeadShovel.addProcessingHandler(this::processShovelHead);
        OrePrefix.toolHeadAxe.addProcessingHandler(this::processAxeHead);
        OrePrefix.toolHeadPickaxe.addProcessingHandler(this::processPickaxeHead);
        OrePrefix.toolHeadSword.addProcessingHandler(this::processSwordHead);
        OrePrefix.toolHeadHoe.addProcessingHandler(this::processHoeHead);
        OrePrefix.toolHeadSaw.addProcessingHandler(this::processSawHead);
        OrePrefix.toolHeadChainsaw.addProcessingHandler(this::processChainSawHead);
        OrePrefix.toolHeadDrill.addProcessingHandler(this::processDrillHead);
        OrePrefix.toolHeadPlow.addProcessingHandler(this::processPlowHead);
        OrePrefix.toolHeadSense.addProcessingHandler(this::processSenseHead);
        OrePrefix.toolHeadWrench.addProcessingHandler(this::processWrenchHead);
        OrePrefix.toolHeadBuzzSaw.addProcessingHandler(this::processBuzzSawHead);
        OrePrefix.toolHeadFile.addProcessingHandler(this::processFileHead);
        OrePrefix.toolHeadUniversalSpade.addProcessingHandler(this::processSpadeHead);
        OrePrefix.toolHeadScrewdriver.addProcessingHandler(this::processScrewdriverHead);
        OrePrefix.toolHeadHammer.addProcessingHandler(this::processHammerHead);
    }

    private static final List<OrePrefix> GEM_ORDER = Arrays.asList(
        OrePrefix.gemChipped, OrePrefix.gemFlawed, OrePrefix.gem, OrePrefix.gemFlawless, OrePrefix.gemExquisite
    );

    private void processDust(OrePrefix dustPrefix, Material material) {
        if (!(material instanceof DustMaterial))
            return;
        ItemStack dustStack = OreDictUnifier.get(dustPrefix, material);
        if (!dustStack.isEmpty()) {
            if (material instanceof GemMaterial) {
                ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);

                if (material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)) {

                    RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                        .input(dustPrefix, material)
                        .fluidInputs(Materials.Water.getFluid(200))
                        .chancedOutput(gemStack, 7000)
                        .duration(2000)
                        .EUt(24)
                        .buildAndRegister();

                    RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                        .input(dustPrefix, material)
                        .fluidInputs(ModHandler.getDistilledWater(200))
                        .chancedOutput(gemStack, 9000)
                        .duration(1500)
                        .EUt(24)
                        .buildAndRegister();
                } else if (!material.hasFlag(Material.MatFlags.EXPLOSIVE | DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                        .input(dustPrefix, material, 4)
                        .outputs(GTUtility.copyAmount(3, gemStack))
                        .explosivesAmount(4)
                        .buildAndRegister();
                }
            } else if (material instanceof MetalMaterial && !material.hasFlag(Material.MatFlags.FLAMMABLE | DustMaterial.MatFlags.NO_SMELTING)) {
                MetalMaterial metalMaterial = (MetalMaterial) material;

                ItemStack tinyDustStack = OreDictUnifier.get(OrePrefix.dustTiny, metalMaterial);
                ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, metalMaterial);
                ItemStack nuggetStack = OreDictUnifier.get(OrePrefix.nugget, metalMaterial);

                if (metalMaterial.blastFurnaceTemperature <= 0) {
                    ModHandler.addSmeltingRecipe(dustStack, ingotStack);
                    ModHandler.addSmeltingRecipe(tinyDustStack, nuggetStack);
                } else {
                    int duration = Math.max(1, (int) (material.getMass() * metalMaterial.blastFurnaceTemperature / 50L));
                    ModHandler.removeFurnaceSmelting(ingotStack);
                    RecipeMaps.BLAST_RECIPES.recipeBuilder()
                        .input(dustPrefix, material)
                        .outputs(ingotStack)
                        .duration(duration).EUt(120)
                        .blastFurnaceTemp(metalMaterial.blastFurnaceTemperature)
                        .buildAndRegister();
                    RecipeMaps.BLAST_RECIPES.recipeBuilder()
                        .input(OrePrefix.dustTiny, material)
                        .outputs(nuggetStack)
                        .duration(Math.max(1, duration / 9)).EUt(120)
                        .blastFurnaceTemp(metalMaterial.blastFurnaceTemperature)
                        .buildAndRegister();
                    if (metalMaterial.blastFurnaceTemperature <= 1000) {
                        ModHandler.addRCFurnaceRecipe(dustStack, ingotStack, duration);
                        ModHandler.addRCFurnaceRecipe(tinyDustStack, nuggetStack, Math.max(1, duration / 9));
                    }
                }
            } else if (material.hasFlag(MatFlags.GENERATE_PLATE) &&
                !material.hasFlag(Material.MatFlags.EXPLOSIVE | DustMaterial.MatFlags.NO_SMASHING)) {
                RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                    .input(dustPrefix, material)
                    .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                    .buildAndRegister();
            }
        }
    }

    private void processFrame(OrePrefix framePrefix, Material material) {
        if (material instanceof MetalMaterial && !framePrefix.isIgnored(material) && material.hasFlag(GENERATE_PLATE | GENERATE_ROD)) {
            ModHandler.addShapedRecipe(String.format("frame_%s", material),
                OreDictUnifier.get(framePrefix, material, 4),
                "PPP",
                "SSS",
                "SwS",
                'P', new UnificationEntry(OrePrefix.plate, material),
                'S', new UnificationEntry(OrePrefix.stick, material));
        }
    }

    private void processNugget(OrePrefix orePrefix, Material material) {
        ItemStack nuggetStack = OreDictUnifier.get(orePrefix, material);
        if (!nuggetStack.isEmpty()) {
            if (material instanceof MetalMaterial) {
                ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);
                if (!ingotStack.isEmpty()) {
                    ModHandler.addShapelessRecipe(String.format("nugget_disassembling_%s", material.toString()),
                        GTUtility.copyAmount(9, nuggetStack), new UnificationEntry(OrePrefix.ingot, material));
                    ModHandler.addShapedRecipe(String.format("nugget_assembling_%s", material.toString()),
                        ingotStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(orePrefix, material));

                    FluidMaterial fluidMaterial = (FluidMaterial) material;
                    RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(MetaItems.SHAPE_MOLD_NUGGET)
                        .fluidInputs(fluidMaterial.getFluid(L))
                        .outputs(OreDictUnifier.get(orePrefix, material, 9))
                        .duration((int) material.getMass())
                        .EUt(8)
                        .buildAndRegister();
                }

            } else if (material instanceof GemMaterial) {
                //sometimes happens because of other mods
                ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);
                if (!gemStack.isEmpty()) {
                    ModHandler.addShapelessRecipe(String.format("nugget_disassembling_%s", material.toString()),
                        GTUtility.copyAmount(9, nuggetStack), new UnificationEntry(OrePrefix.gem, material));
                    ModHandler.addShapedRecipe(String.format("nugget_assembling_%s", material.toString()),
                        gemStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(orePrefix, material));
                }
            }
        }
    }

    private void processSmallDust(OrePrefix orePrefix, Material material) {
        if (material instanceof DustMaterial) {
            ItemStack smallDustStack = OreDictUnifier.get(orePrefix, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

            ModHandler.addShapedRecipe(String.format("small_dust_disassembling_%s", material.toString()),
                GTUtility.copyAmount(4, smallDustStack), "  ", " X", 'X', new UnificationEntry(OrePrefix.dust, material));
            ModHandler.addShapedRecipe(String.format("small_dust_assembling_%s", material.toString()),
                dustStack, "XX", "XX", 'X', new UnificationEntry(orePrefix, material));
        }
    }

    private void processTinyDust(OrePrefix orePrefix, Material material) {
        if (material instanceof DustMaterial) {
            ItemStack tinyDustStack = OreDictUnifier.get(orePrefix, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

            ModHandler.addShapedRecipe(String.format("tiny_dust_disassembling_%s", material.toString()),
                GTUtility.copyAmount(9, tinyDustStack), "X ", "  ", 'X', new UnificationEntry(OrePrefix.dust, material));
            ModHandler.addShapedRecipe(String.format("tiny_dust_assembling_%s", material.toString()),
                dustStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(orePrefix, material));
        }
    }

    private void processBlock(OrePrefix blockPrefix, Material material) {
        if (!(material instanceof DustMaterial))
            return;
        ItemStack blockStack = OreDictUnifier.get(blockPrefix, material);
        if (!blockStack.isEmpty()) {
            if (material.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID)) {
                FluidMaterial fluidMaterial = (FluidMaterial) material;
                RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                    .notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                    .fluidInputs(fluidMaterial.getFluid(L * 9))
                    .outputs(blockStack)
                    .duration((int) material.getMass()).EUt(8)
                    .buildAndRegister();
            }

            if (material.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE)) {
                ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .input(blockPrefix, material)
                    .outputs(GTUtility.copyAmount(9, plateStack))
                    .duration((int) (material.getMass() * 8L)).EUt(30)
                    .buildAndRegister();
            }

            ItemStack blockComponent;
            UnificationEntry blockEntry;
            if (material instanceof GemMaterial) {
                blockComponent = OreDictUnifier.get(OrePrefix.gem, material);
                blockEntry = new UnificationEntry(OrePrefix.gem, material);
            } else if (material instanceof MetalMaterial) {
                blockComponent = OreDictUnifier.get(OrePrefix.ingot, material);
                blockEntry = new UnificationEntry(OrePrefix.ingot, material);
            } else {
                blockComponent = OreDictUnifier.get(OrePrefix.dust, material);
                blockEntry = new UnificationEntry(OrePrefix.dust, material);
            }
            if (!blockComponent.isEmpty()) {
                ModHandler.addShapedRecipe(String.format("%s_block_compress", material.toString()),
                    blockStack, "XXX", "XXX", "XXX",
                    'X', blockEntry);
                ModHandler.addShapelessRecipe(String.format("%s_block_decompress", material.toString()),
                    GTUtility.copyAmount(9, blockComponent),
                    new UnificationEntry(blockPrefix, material));
            }
        }
    }

    private void processBolt(OrePrefix boltPrefix, Material material) {
        if (!(material instanceof MetalMaterial) || material.hasFlag(MatFlags.NO_WORKING))
            return;
        ItemStack boltStack = OreDictUnifier.get(boltPrefix, material);
        ItemStack screwStack = OreDictUnifier.get(OrePrefix.screw, material);
        ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);
        if (!boltStack.isEmpty() && !screwStack.isEmpty()) {
            ModHandler.addShapedRecipe(String.format("%s_bolt", material.toString()),
                boltStack, "fS", "S ",
                'S', new UnificationEntry(OrePrefix.screw, material));

            RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                .input(OrePrefix.screw, material)
                .outputs(boltStack)
                .duration(20).EUt(24)
                .buildAndRegister();
        }
        if (!boltStack.isEmpty() && !ingotStack.isEmpty()) {
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_EXTRUDER_BOLT)
                .input(OrePrefix.ingot, material)
                .outputs(GTUtility.copyAmount(8, boltStack))
                .duration(15).EUt(120)
                .buildAndRegister();
        }
    }

    private void processScrew(OrePrefix screwPrefix, Material material) {
        if (!(material instanceof MetalMaterial) || material.hasFlag(MatFlags.NO_WORKING))
            return;
        ItemStack screwStack = OreDictUnifier.get(screwPrefix, material);
        ItemStack boltStack = OreDictUnifier.get(OrePrefix.bolt, material);
        if (!screwStack.isEmpty()) {
            RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .input(OrePrefix.bolt, material)
                .outputs(screwStack)
                .duration((int) Math.max(1, material.getMass() / 8L)).EUt(4)
                .buildAndRegister();

            ModHandler.addShapedRecipe(String.format("screw_%s", material.toString()),
                screwStack, "fX", "X ",
                'X', new UnificationEntry(OrePrefix.bolt, material));
        }
    }

    private void processFoil(OrePrefix foilPrefix, Material material) {
        if (!(material instanceof MetalMaterial) || material.hasFlag(MatFlags.NO_SMASHING))
            return;
        ItemStack foilStack = OreDictUnifier.get(foilPrefix, material);
        ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
        RecipeMaps.BENDER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, material)
            .outputs(GTUtility.copyAmount(4, foilStack))
            .duration((int) material.getMass()).EUt(24)
            .circuitMeta(0)
            .buildAndRegister();
    }

    private void processFineWire(OrePrefix fineWirePrefix, Material material) {
        if (!(material instanceof MetalMaterial) || material.hasFlag(DustMaterial.MatFlags.NO_WORKING | Material.MatFlags.NO_UNIFICATION))
            return;
        ItemStack fineWireStack = OreDictUnifier.get(fineWirePrefix, material);
        ItemStack foilStack = OreDictUnifier.get(OrePrefix.foil, material);
        if(!foilStack.isEmpty()) {
            ModHandler.addShapelessRecipe(String.format("fine_wire_%s", material.toString()),
                fineWireStack, 'x', new UnificationEntry(OrePrefix.foil, material));
        }
    }

    private void processIngot(OrePrefix ingotPrefix, Material material) {
        if (!(material instanceof MetalMaterial))
            return;
        if (!material.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE) || material.hasFlag(DustMaterial.MatFlags.NO_SMASHING))
            return;
        ItemStack ingotStack = OreDictUnifier.get(ingotPrefix, material);
        if (!ingotStack.isEmpty()) {
            if (material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE)) {
                ModHandler.addShapelessRecipe(String.format("mortar_grind_%s", material.toString()),
                    OreDictUnifier.get(OrePrefix.dust, material), 'm', new UnificationEntry(ingotPrefix, material));
            }
            if (!material.hasFlag(MatFlags.NO_SMASHING)) {
                ModHandler.addShapedRecipe(String.format("wrench_%s", material.toString()),
                    MetaItems.WRENCH.getStackForm(material, material),
                    "IhI",
                    "III",
                    " I ",
                    'I', ingotStack);
            }
            FluidStack fluid = ((FluidMaterial) material).getFluid(L);
            if (fluid == null) {
                GTLog.logger.error("METAL MATERIAL: {} DOESN'T HAVE FLUID", material);
            }
            RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
                .fluidInputs(((FluidMaterial) material).getFluid(L))
                .outputs(OreDictUnifier.get(OrePrefix.ingot, material))
                .duration(20).EUt(8)
                .buildAndRegister();


            ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
            if (material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE)) {
                ModHandler.addShapelessRecipe(String.format("mortar_grind_%s", material.toString()),
                    OreDictUnifier.get(OrePrefix.dust, material),
                    'm', new UnificationEntry(ingotPrefix, material));
            }

            RecipeMaps.BENDER_RECIPES.recipeBuilder()
                .circuitMeta(0)
                .input(ingotPrefix, material)
                .outputs(plateStack)
                .circuitMeta(0)
                .EUt(24).duration((int) (material.getMass() / 1.5))
                .buildAndRegister();

            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                .input(ingotPrefix, material, 2)
                .outputs(plateStack)
                .EUt(16).duration((int) (material.getMass() / 2L))
                .buildAndRegister();

            ModHandler.addShapedRecipe(String.format("plate_%s", material.toString()),
                plateStack,
                "h",
                "I",
                "I",
                'I', new UnificationEntry(ingotPrefix, material));

            ModHandler.addShapedRecipe(String.format("plate_%s", material.toString()),
                plateStack,
                "h",
                "I",
                "I",
                'I',
                new UnificationEntry(ingotPrefix, material));

            if (material.hasFlag(MetalMaterial.MatFlags.GENERATE_DENSE)) {
                ItemStack denseStack = OreDictUnifier.get(OrePrefix.plateDense, material);
                RecipeMaps.BENDER_RECIPES.recipeBuilder()
                    .input(ingotPrefix, material, 9)
                    .outputs(denseStack)
                    .circuitMeta(5)
                    .EUt(96).duration((int) (material.getMass() * 9))
                    .buildAndRegister();

                RecipeMaps.BENDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.plate, material, 9)
                    .outputs(denseStack)
                    .circuitMeta(5)
                    .EUt(96).duration((int) (material.getMass() * 1.5))
                    .buildAndRegister();
            }
        }
    }

    private void processCrushedOre(OrePrefix crushedPrefix, Material materialIn) {
        ItemStack crushedOreStack = OreDictUnifier.get(crushedPrefix, materialIn);
        if (materialIn instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) materialIn;
            ItemStack impureDustStack = OreDictUnifier.get(OrePrefix.dustImpure, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
            DustMaterial byproductMaterial = GTUtility.selectItemInList(0, material, material.oreByProducts, DustMaterial.class);

            //fallback for dirtyGravel, shard & clump
            if (impureDustStack.isEmpty()) {
                impureDustStack = dustStack;
            }

            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                .input(crushedPrefix, materialIn)
                .outputs(impureDustStack)
                .duration(10)
                .EUt(16)
                .buildAndRegister();

            RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .input(crushedPrefix, materialIn)
                .outputs(impureDustStack)
                .duration(100)
                .EUt(24)
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, byproductMaterial), 1000)
                .buildAndRegister();

            RecipeMaps.ORE_WASHER_RECIPES.recipeBuilder()
                .input(crushedPrefix, materialIn)
                .fluidInputs(ModHandler.getWater(1000))
                .outputs(OreDictUnifier.get(OrePrefix.crushedPurified, material),
                    OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial),
                    OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                .buildAndRegister();

            RecipeMaps.ORE_WASHER_RECIPES.recipeBuilder()
                .input(crushedPrefix, materialIn)
                .fluidInputs(ModHandler.getDistilledWater(1000))
                .outputs(OreDictUnifier.get(OrePrefix.crushedPurified, material),
                    OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial),
                    OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                .duration(300)
                .buildAndRegister();

            RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                .input(crushedPrefix, materialIn)
                .duration((int) material.getMass() * 20)
                .outputs(OreDictUnifier.get(crushedPrefix == OrePrefix.crushed ? OrePrefix.crushedCentrifuged : OrePrefix.dust, material),
                    OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial),
                    OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                .buildAndRegister();

            if (material.washedIn != null) {
                DustMaterial washingByproduct = GTUtility.selectItemInList(3, material, material.oreByProducts, DustMaterial.class);

                RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .input(crushedPrefix, materialIn)
                    .fluidInputs(material.washedIn.getFluid(1000))
                    .outputs(OreDictUnifier.get(crushedPrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, material))
                    .chancedOutput(OreDictUnifier.get(OrePrefix.dust, washingByproduct), 7000)
                    .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Stone), 4000)
                    .duration(800)
                    .EUt(8)
                    .buildAndRegister();
            }
        }
    }

    private void processCrushedCentrifuged(OrePrefix centrifugedPrefix, Material material) {
        if (material instanceof SolidMaterial) {
            ItemStack stack = OreDictUnifier.get(centrifugedPrefix, material);
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, solidMaterial);
            ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dustSmall, GTUtility.selectItemInList(2,
                solidMaterial, solidMaterial.oreByProducts, DustMaterial.class), 1);

            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                .input(centrifugedPrefix, material)
                .outputs(dustStack)
                .duration(20)
                .EUt(16)
                .buildAndRegister();

            RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .input(centrifugedPrefix, material)
                .outputs(dustStack)
                .chancedOutput(byproductStack, 1000)
                .duration(40)
                .EUt(16)
                .buildAndRegister();

            ItemStack output = OreDictUnifier.get(OrePrefix.nugget, solidMaterial, 10);
            if (!output.isEmpty()) {
                ModHandler.addSmeltingRecipe(stack, output);
            }

            ModHandler.addShapedRecipe("centr_%s_ore2dust", dustStack, "h", "X", 'X', new UnificationEntry(centrifugedPrefix, material));
        }
    }

    private void processCrushedPurified(OrePrefix purifiedPrefix, Material material) {
        if (material instanceof SolidMaterial) {
            ItemStack crushedPurifiedStack = OreDictUnifier.get(purifiedPrefix, material);
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ItemStack crushedCentrifugedStack = OreDictUnifier.get(OrePrefix.crushedCentrifuged, solidMaterial);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, solidMaterial);
            ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, solidMaterial, solidMaterial.oreByProducts, DustMaterial.class));

            if (!crushedCentrifugedStack.isEmpty()) {
                RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                    .input(purifiedPrefix, material)
                    .outputs(crushedCentrifugedStack, byproductStack)
                    .duration((int) (material.getMass() * 20))
                    .EUt(60)
                    .buildAndRegister();
            } else {
                RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                    .input(purifiedPrefix, material)
                    .outputs(dustStack, byproductStack)
                    .duration((int) (material.getMass() * 20))
                    .EUt(60)
                    .buildAndRegister();
            }


            if (material instanceof GemMaterial) {
                ItemStack exquisiteStack = OreDictUnifier.get(OrePrefix.gemExquisite, material);
                ItemStack flawlessStack = OreDictUnifier.get(OrePrefix.gemFlawless, material);
                ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);
                ItemStack flawedStack = OreDictUnifier.get(OrePrefix.gemFlawed, material);
                ItemStack chippedStack = OreDictUnifier.get(OrePrefix.gemChipped, material);

                if (material.hasFlag(GemMaterial.MatFlags.HIGH_SIFTER_OUTPUT)) {
                    RecipeMaps.SIFTER_RECIPES.recipeBuilder()
                        .input(purifiedPrefix, material)
                        .chancedOutput(exquisiteStack, 300)
                        .chancedOutput(flawlessStack, 1200)
                        .chancedOutput(gemStack, 4500)
                        .chancedOutput(flawedStack, 1400)
                        .chancedOutput(chippedStack, 2800)
                        .chancedOutput(dustStack, 3500)
                        .duration(800)
                        .EUt(16)
                        .buildAndRegister();
                } else {
                    RecipeMaps.SIFTER_RECIPES.recipeBuilder()
                        .input(purifiedPrefix, material)
                        .chancedOutput(exquisiteStack, 100)
                        .chancedOutput(flawlessStack, 400)
                        .chancedOutput(gemStack, 1500)
                        .chancedOutput(flawedStack, 2000)
                        .chancedOutput(chippedStack, 4000)
                        .chancedOutput(dustStack, 5000)
                        .duration(800)
                        .EUt(16)
                        .buildAndRegister();
                }
            }
        }
    }

    private void processCrystallizedPurified(OrePrefix crystallizedPrefix, Material material) {
        if (material instanceof SolidMaterial) {
            ItemStack stack = OreDictUnifier.get(crystallizedPrefix, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, ((SolidMaterial) material).macerateInto);

            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                .input(crystallizedPrefix, material)
                .outputs(dustStack)
                .duration(10)
                .EUt(10)
                .buildAndRegister();

            RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .input(crystallizedPrefix, material)
                .outputs(dustStack)
                .duration(20)
                .EUt(16)
                .buildAndRegister();
        }
    }

    private void processDecomposition(OrePrefix decomposePrefix, Material materialIn) {
        if (materialIn instanceof FluidMaterial) {
            FluidMaterial material = (FluidMaterial) materialIn;
            if (!material.materialComponents.isEmpty()
                && (material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING)
                || material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_CENTRIFUGING))) {

                //compute outputs
                ArrayList<ItemStack> outputs = new ArrayList<>();
                ArrayList<FluidStack> fluidOutputs = new ArrayList<>();
                int totalInputAmount = 0;
                for (MaterialStack component : material.materialComponents) {
                    totalInputAmount += component.amount;
                    if (component.material instanceof DustMaterial) {
                        outputs.add(OreDictUnifier.get(OrePrefix.dust, component.material, (int) component.amount));
                    } else if (component.material instanceof FluidMaterial) {
                        FluidMaterial componentMaterial = (FluidMaterial) component.material;
                        fluidOutputs.add(componentMaterial.getFluid((int) (GTValues.L * component.amount)));
                    }
                }

                //generate builder
                RecipeBuilder builder;
                if (material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING)) {
                    builder = RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder()
                        .duration((int) material.getProtons() * totalInputAmount * 8)
                        .EUt(Math.min(4, material.materialComponents.size()) * 30);
                } else {
                    builder = RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
                        .duration((int) material.getMass() * totalInputAmount * 2)
                        .EUt(30);
                }
                builder.outputs(outputs);
                builder.fluidOutputs(fluidOutputs);

                //finish builder
                if (decomposePrefix == OrePrefix.dust) {
                    builder.input(decomposePrefix, material);
                } else {
                    builder.fluidInputs(material.getFluid(GTValues.L * totalInputAmount));
                }

                //register recipe
                builder.buildAndRegister();
            }

        }
    }

    private void processDirtyDust(OrePrefix dustPrefix, Material materialIn) {
        if (materialIn instanceof DustMaterial) {
            DustMaterial material = (DustMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(dustPrefix, materialIn);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
            ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);

            if (dustPrefix == OrePrefix.dustPure && material.separatedOnto != null) {
                ItemStack separatedStack = OreDictUnifier.get(OrePrefix.dustSmall, material.separatedOnto);
                RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                    .input(dustPrefix, materialIn)
                    .outputs(dustStack)
                    .chancedOutput(separatedStack, 4000)
                    .duration((int) material.separatedOnto.getMass())
                    .EUt(24)
                    .buildAndRegister();
            }

            int byProductIndex;
            if (dustPrefix == OrePrefix.dustRefined) {
                byProductIndex = 2;
            } else if (dustPrefix == OrePrefix.dustPure) {
                byProductIndex = 1;
            } else byProductIndex = 0;
            FluidMaterial byproduct = GTUtility.selectItemInList(byProductIndex, material, material.oreByProducts, FluidMaterial.class);

            RecipeBuilder builder = RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dustPrefix, materialIn)
                .outputs(dustStack)
                .duration((int) (material.getMass() * 4))
                .EUt(24);

            if (byproduct instanceof DustMaterial) {
                builder.outputs(OreDictUnifier.get(OrePrefix.dustTiny, byproduct));
            } else {
                builder.fluidOutputs(byproduct.getFluid(GTValues.L / 9));
            }

            builder.buildAndRegister();

            if (!ingotStack.isEmpty()) {
                ModHandler.addSmeltingRecipe(stack, ingotStack);
            }
        }
    }

    private void processGear(OrePrefix gearPrefix, Material materialIn) {
        if (materialIn instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) materialIn;

            if (!material.hasFlag(SolidMaterial.MatFlags.GENERATE_GEAR)) {
                return;
            }

            ItemStack stack = OreDictUnifier.get(gearPrefix, materialIn);
            if (!stack.isEmpty()) {
                boolean isSmall = gearPrefix == OrePrefix.gearSmall;

                if (material.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID)) {
                    RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(isSmall ? MetaItems.SHAPE_MOLD_GEAR_SMALL : MetaItems.SHAPE_MOLD_GEAR)
                        .fluidInputs(material.getFluid(L * (isSmall ? 1 : 4)))
                        .outputs(stack)
                        .duration(isSmall ? 20 : 100)
                        .EUt(8)
                        .buildAndRegister();
                }

                if (isSmall) {
                    if (material instanceof MetalMaterial && !material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                        ModHandler.addShapedRecipe(String.format("small_gear_%s", material.toString()),
                            stack,
                            "h ",
                            " P",
                            'P', new UnificationEntry(OrePrefix.plate, material));
                    }
                } else {
                    ModHandler.addShapedRecipe(String.format("gear_%s", material.toString()),
                        stack,
                        "RPR",
                        "PdP",
                        "RPR",
                        'P', new UnificationEntry(OrePrefix.plate, material),
                        'R', new UnificationEntry(OrePrefix.stick, material));
                }

            }
        }
    }

    private void processGem(OrePrefix gemPrefix, Material materialIn) {
        if (materialIn instanceof GemMaterial) {
            GemMaterial material = (GemMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(gemPrefix, materialIn);

            if (!stack.isEmpty()) {

                long materialAmount = gemPrefix.materialAmount;
                ItemStack crushedStack = OreDictUnifier.getDust(material, materialAmount);

                if (material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE)) {
                    ModHandler.addShapelessRecipe(String.format("gem_to_dust_%s", material.toString()), crushedStack, "m", new UnificationEntry(gemPrefix, materialIn));
                }

                if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    OrePrefix prevPrefix = GTUtility.getItem(GEM_ORDER, GEM_ORDER.indexOf(gemPrefix) - 1, null);
                    if (prevPrefix != null) {
                        ItemStack prevStack = OreDictUnifier.get(prevPrefix, material, 2);
                        ModHandler.addShapelessRecipe(String.format("gem_to_gem_%s_%s", prevPrefix.toString(), material.toString()), prevStack, "h", new UnificationEntry(gemPrefix, materialIn));
                        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                            .input(gemPrefix, materialIn)
                            .outputs(prevStack)
                            .duration(20)
                            .EUt(16)
                            .buildAndRegister();
                    }
                }

                if (!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    if (material.hasFlag(SolidMaterial.MatFlags.GENERATE_LONG_ROD) && materialAmount >= M * 2) {
                        RecipeMaps.LATHE_RECIPES.recipeBuilder()
                            .input(gemPrefix, materialIn)
                            .outputs(OreDictUnifier.get(OrePrefix.stickLong, material, (int) (materialAmount / (M * 2))),
                                OreDictUnifier.getDust(material, materialAmount % (M * 2)))
                            .duration((int) material.getMass())
                            .EUt(16)
                            .buildAndRegister();
                    } else if (materialAmount >= M) {
                        ItemStack gemStick = OreDictUnifier.get(OrePrefix.stick, material, (int) (materialAmount / M));
                        ItemStack gemDust = OreDictUnifier.getDust(material, materialAmount % M);
                        if (!gemStick.isEmpty() && !gemDust.isEmpty()) {
                            RecipeMaps.LATHE_RECIPES.recipeBuilder()
                                .input(gemPrefix, materialIn)
                                .outputs(gemStick, gemDust)
                                .duration((int) material.getMass())
                                .EUt(16)
                                .buildAndRegister();
                        }
                    }
                }
            }
        }
    }

    private void processLens(OrePrefix lensPrefix, Material material) {
        if (material instanceof GemMaterial) {
            ItemStack stack = OreDictUnifier.get(lensPrefix, material);

            RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, material)
                .outputs(stack, OreDictUnifier.get(OrePrefix.dustSmall, material))
                .duration((int) (material.getMass() / 2L))
                .EUt(16)
                .buildAndRegister();

            EnumDyeColor dyeColor = GTUtility.determineDyeColor(material.materialRGB);
            MarkerMaterial colorMaterial = MarkerMaterials.Color.COLORS.get(dyeColor);
            OreDictUnifier.registerOre(stack, OrePrefix.craftingLens, colorMaterial);
        }
    }

    private void processLog(OrePrefix logPrefix, Material material) {
        ItemStack stack = OreDictUnifier.get(logPrefix, material);
        if (!stack.isEmpty()) {
            RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .input(logPrefix, material)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 6))
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 8000)
                .buildAndRegister();

            ModHandler.addShapedRecipe(String.format("stick_long_%s", material.toString()),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 2),
                "sLf",
                'L', new UnificationEntry(logPrefix, material));

            RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .input(logPrefix, material)
                .outputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 4), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
                .duration(160)
                .EUt(8)
                .buildAndRegister();

            ItemStack smeltingOutput = ModHandler.getSmeltingOutput(stack);
            if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1) {
                int coalAmount = smeltingOutput.getCount();

                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .input(logPrefix, material, 16)
                    .circuitMeta(0)
                    .outputs(new ItemStack(Items.COAL, 20 * coalAmount, 1))
                    .fluidOutputs(Materials.Creosote.getFluid(5000 * coalAmount))
                    .duration(440)
                    .EUt(64)
                    .buildAndRegister();
                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .input(logPrefix, material, 16)
                    .circuitMeta(1)
                    .fluidInputs(Materials.Nitrogen.getFluid(400))
                    .outputs(new ItemStack(Items.COAL, 20, 1))
                    .fluidInputs(Materials.Creosote.getFluid(4000))
                    .duration(200)
                    .EUt(96)
                    .buildAndRegister();
                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .input(logPrefix, material, 16)
                    .circuitMeta(2)
                    .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 5))
                    .fluidOutputs(Materials.OilHeavy.getFluid(300))
                    .duration(280)
                    .EUt(192)
                    .buildAndRegister();

            }

            ItemStack output = ModHandler.getRecipeOutput(GTValues.DW, stack);
            if (!output.isEmpty() && OreDictUnifier.getPrefix(output) == OrePrefix.plank) {

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .input(logPrefix, material)
                    .fluidInputs(Materials.Lubricant.getFluid(1))
                    .outputs(GTUtility.copyAmount(output.getCount() * 2, stack),
                        OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
                    .duration(200)
                    .EUt(8)
                    .buildAndRegister();

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .input(logPrefix, material)
                    .outputs(output,
                        OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
                    .duration(200)
                    .EUt(8)
                    .buildAndRegister();

                ModHandler.removeRecipes(output);
                ModHandler.addShapedRecipe(String.format("log_to_wood_%s", material.toString()),
                    GTUtility.copyAmount(output.getCount(), output),
                    "s",
                    "L",
                    'L', new UnificationEntry(logPrefix, material));
            }
        }
    }

    private void processPlank(OrePrefix plankPrefix, Material material) {
        ItemStack stack = OreDictUnifier.get(plankPrefix, material);

        if (stack.isEmpty()) {
            return;
        }

        if (material == Materials.Wood) {
            RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .input(plankPrefix, material)
                .outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2))
                .duration(10)
                .EUt(8)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material, 8).input(OrePrefix.dust, Materials.Redstone)
                .outputs(new ItemStack(Blocks.NOTEBLOCK, 1))
                .duration(200)
                .EUt(4)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material, 8).input(OrePrefix.gem, Materials.Diamond)
                .outputs(new ItemStack(Blocks.JUKEBOX, 1))
                .duration(400)
                .EUt(4)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material, 6)
                .inputs(new ItemStack(Items.BOOK, 3))
                .outputs(new ItemStack(Blocks.BOOKSHELF, 1))
                .duration(400)
                .EUt(4)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material)
                .circuitMeta(1)
                .outputs(new ItemStack(Blocks.WOODEN_BUTTON, 1))
                .duration(100)
                .EUt(4)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material, 2)
                .circuitMeta(2)
                .outputs(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE))
                .duration(200)
                .EUt(4)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material, 3)
                .circuitMeta(3)
                .outputs(new ItemStack(Blocks.TRAPDOOR))
                .duration(300)
                .EUt(4)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material, 4)
                .circuitMeta(4)
                .outputs(new ItemStack(Blocks.CRAFTING_TABLE))
                .duration(400)
                .EUt(4)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material, 6)
                .circuitMeta(6)
                .outputs(new ItemStack(Items.OAK_DOOR))
                .duration(600)
                .EUt(4)
                .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plankPrefix, material, 8)
                .circuitMeta(8)
                .outputs(new ItemStack(Blocks.CHEST, 1))
                .duration(800)
                .EUt(4)
                .buildAndRegister();

            ItemStack output = ModHandler.getRecipeOutput(null, stack, stack, stack);
            if (!output.isEmpty() && output.getCount() >= 3) {

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .input(plankPrefix, material)
                    .outputs(GTUtility.copyAmount(output.getCount() / 3, output))
                    .duration(25)
                    .EUt(4)
                    .buildAndRegister();

//                    ModHandler.removeRecipe(stack, stack, stack);
                ModHandler.addShapedRecipe(String.format("slab?_%s", material.toString()),
                    GTUtility.copyAmount(output.getCount() / 3, output),
                    "sP",
                    'P', new UnificationEntry(plankPrefix, material));
            }
        }
    }

    private void processPlate(OrePrefix platePrefix, Material material) { //for plate
        ItemStack stack = OreDictUnifier.get(platePrefix, material);
        ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
        boolean noSmashing = material.hasFlag(NO_SMASHING);
        if (!stack.isEmpty()) {
            if (material instanceof FluidMaterial) {
                FluidStack fluid = ((FluidMaterial) material).getFluid(L);
                if (fluid != null) {
                    RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                        .fluidInputs(fluid)
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                        .duration(40)
                        .EUt(8)
                        .buildAndRegister();
                }
            }
            ItemStack compressedStack = OreDictUnifier.get(OrePrefix.compressed, material); //wtf is a compressed
            RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                .input(platePrefix, material, 2)
                .explosivesAmount(2)
                .outputs(compressedStack, OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh))
                .buildAndRegister();

//        if (material == Materials.Paper)
//            ModHandler.addShapedRecipe("papar_f_scane_" + material,
//                GTUtility.copyAmount(3, stack),
//                "XXX",
//                'X', new ItemStack(Items.REEDS, 1, W));

            if (!noSmashing) {
                ModHandler.addShapedRecipe(String.format("ingot_to_plate_%s", material.toString()),
                    stack,
                    "h",
                    "X",
                    "X",
                    'X', new UnificationEntry(OrePrefix.ingot, material));

                ModHandler.addShapedRecipe(String.format("ingot_to_plate_od_%s", material.toString()),
                    stack,
                    "H",
                    "X",
                    'H', ToolDictNames.craftingToolForgeHammer,
                    'X', new UnificationEntry(OrePrefix.ingot, material));

                ModHandler.addShapedRecipe(String.format("gem_to_plate_%s", material.toString()),
                    stack,
                    "h",
                    "X",
                    'X', new UnificationEntry(OrePrefix.gem, material));

                ModHandler.addShapedRecipe(String.format("gem_to_plate_od_%s", material.toString()),
                    stack,
                    "H",
                    "X",
                    'H', ToolDictNames.craftingToolForgeHammer,
                    'X', new UnificationEntry(OrePrefix.gem, material));
            }

            if (material.hasFlag(MORTAR_GRINDABLE))
                ModHandler.addShapedRecipe(String.format("plate_to_dust_%s", material.toString()),
                    dustStack,
                    "X",
                    "m",
                    'X', new UnificationEntry(OrePrefix.plate, material));
        }
    }

    private void processPlateDense(OrePrefix orePrefix, Material material) {
        boolean noSmashing = material.hasFlag(NO_SMASHING);
        long materialMass = material.getMass();
        ItemStack stack = OreDictUnifier.get(orePrefix, material);
        if (!noSmashing) {
            RecipeMaps.BENDER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, material, 9)
                .circuitMeta(2)
                .outputs(GTUtility.copyAmount(1, stack))
                .duration((int) Math.max(materialMass * 9L, 1L))
                .EUt(96)
                .buildAndRegister();

        }
    }

    private void processPolarizing(OrePrefix polarizingPrefix, Material materialIn) {
        if (materialIn instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(polarizingPrefix, materialIn);

            if (material.magneticMaterial != null) {
                ItemStack magneticStack = OreDictUnifier.get(polarizingPrefix, material.magneticMaterial);

                RecipeMaps.POLARIZER_RECIPES.recipeBuilder() //polarizing
                    .input(polarizingPrefix, materialIn)
                    .outputs(magneticStack)
                    .duration(16)
                    .EUt(16)
                    .buildAndRegister();

                ModHandler.addSmeltingRecipe(magneticStack, stack); //de-magnetizing
            }
        }
    }

    private void processPure(OrePrefix purePrefix, Material materialIn) {
        if (materialIn instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(purePrefix, materialIn);
            DustMaterial byproductMaterial = GTUtility.selectItemInList(1, material, material.oreByProducts, DustMaterial.class);
            ItemStack pureDustStack = OreDictUnifier.get(OrePrefix.dustPure, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
            ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);

            if (pureDustStack.isEmpty()) { //fallback for reduced & cleanGravel
                pureDustStack = OreDictUnifier.get(OrePrefix.dust, material);
            }

            RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
                .input(purePrefix, materialIn)
                .outputs(dustStack, OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial))
                .duration((int) (material.getMass() * 4))
                .EUt(5)
                .buildAndRegister();

            if (!ingotStack.isEmpty()) {
                ModHandler.addSmeltingRecipe(stack, ingotStack);
            }
        }
    }

    private void processRotor(OrePrefix rotorPrefix, Material materialIn) {
        if (materialIn instanceof SolidMaterial && !materialIn.hasFlag(NO_UNIFICATION | NO_WORKING)) {
            SolidMaterial material = (SolidMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(rotorPrefix, materialIn);
            ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
            ItemStack ringStack = OreDictUnifier.get(OrePrefix.ring, material);
            ItemStack screwStack = OreDictUnifier.get(OrePrefix.screw, material);

            ModHandler.addShapedRecipe(String.format("rotor_%s", materialIn.toString()), stack,
                "PhP",
                "SRf",
                "PdP",
                'P', new UnificationEntry(OrePrefix.plate, material),
                'R', new UnificationEntry(OrePrefix.ring, material),
                'S', new UnificationEntry(OrePrefix.screw, material));

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, material, 4).input(OrePrefix.ring, material)
                .outputs(stack)
                .fluidInputs(Materials.SolderingAlloy.getFluid(32))
                .duration(240)
                .EUt(24)
                .buildAndRegister();
        }
    }

    private void processShaping(OrePrefix shapingPrefix, Material material) {
        ItemStack stack = OreDictUnifier.get(shapingPrefix, material);
        if (stack.isEmpty()) {
            return;
        }

        long materialMass = material.getMass();
        int amount = (int) (shapingPrefix.materialAmount / M);
        int voltageMultiplier;

        if ((material instanceof MetalMaterial) && ((MetalMaterial) material).blastFurnaceTemperature >= 2800) {
            voltageMultiplier = 64;
        } else {
            voltageMultiplier = 16;
        }

        if (!(amount > 0 && amount <= 64 && shapingPrefix.materialAmount % M == 0L)) {
            return;
        }

        if (material instanceof MetalMaterial && !material.hasFlag(NO_SMELTING)) {

            if (material.hasFlag(NO_SMASHING)) {
                voltageMultiplier /= 4;
            } else if (shapingPrefix.name().startsWith(OrePrefix.dust.name())) {
                return;
            }

            MetalMaterial smeltInto = ((MetalMaterial) material).smeltInto;
            if (!OrePrefix.block.isIgnored(smeltInto)) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 9)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_BLOCK)
                    .outputs(OreDictUnifier.get(OrePrefix.block, smeltInto, amount))
                    .duration(10 * amount)
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 9)
                    .notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                    .outputs(OreDictUnifier.get(OrePrefix.block, smeltInto, amount))
                    .duration(5 * amount)
                    .EUt(4 * voltageMultiplier)
                    .buildAndRegister();
            }

            if (material != smeltInto) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_INGOT)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, smeltInto, amount))
                    .duration(10)
                    .EUt(4 * voltageMultiplier)
                    .buildAndRegister();
            }

            if (amount * 2 <= 64 && !OreDictUnifier.get(OrePrefix.stick, smeltInto).isEmpty()) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                    .outputs(OreDictUnifier.get(OrePrefix.stick, smeltInto, amount * 2))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 2 <= 64 && !OreDictUnifier.get(OrePrefix.wireGtSingle, smeltInto).isEmpty()) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_WIRE)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, smeltInto, amount * 2))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 8 <= 64 && !OreDictUnifier.get(OrePrefix.bolt, smeltInto).isEmpty()) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_BOLT)
                    .outputs(OreDictUnifier.get(OrePrefix.bolt, smeltInto, amount * 8))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 4 <= 64 && !OreDictUnifier.get(OrePrefix.ring, smeltInto).isEmpty()) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_RING)
                    .outputs(OreDictUnifier.get(OrePrefix.ring, smeltInto, amount * 4))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();

                if (!material.hasFlag(NO_SMASHING) && !OreDictUnifier.get(OrePrefix.ring, material).isEmpty()) {
                    ModHandler.addShapedRecipe(String.format("ring_%s", material.toString()),
                        OreDictUnifier.get(OrePrefix.ring, material),
                        "h ",
                        " X",
                        'X', new UnificationEntry(OrePrefix.stick, material));
                }
            }
            if (and(isToolMaterial, noFlag(NO_SMASHING)).isTrue(smeltInto)) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 2)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_SWORD)
                    .outputs(OreDictUnifier.get(OrePrefix.toolHeadSword, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 3)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_PICKAXE)
                    .outputs(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 3L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_SHOVEL)
                    .outputs(OreDictUnifier.get(OrePrefix.toolHeadShovel, smeltInto, amount))
                    .duration((int) Math.max(materialMass * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 3)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_AXE)
                    .outputs(OreDictUnifier.get(OrePrefix.toolHeadAxe, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 3L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 2)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_HOE)
                    .outputs(OreDictUnifier.get(OrePrefix.toolHeadHoe, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 6)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_HAMMER)
                    .outputs(OreDictUnifier.get(OrePrefix.toolHeadHammer, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 6L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 2)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_FILE)
                    .outputs(OreDictUnifier.get(OrePrefix.toolHeadFile, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 2)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_SAW)
                    .outputs(OreDictUnifier.get(OrePrefix.toolHeadSaw, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (smeltInto.hasFlag(GENERATE_GEAR)) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 4)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_GEAR)
                    .outputs(OreDictUnifier.get(OrePrefix.gear, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 5L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 8)
                    .notConsumable(MetaItems.SHAPE_MOLD_GEAR)
                    .outputs(OreDictUnifier.get(OrePrefix.gear, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 10L * amount, amount))
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
            }

            if (smeltInto.hasFlag(GENERATE_PLATE)) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
                    .outputs(OreDictUnifier.get(OrePrefix.plate, smeltInto, amount))
                    .duration((int) Math.max(materialMass * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .input(shapingPrefix, material, 2)
                    .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                    .outputs(OreDictUnifier.get(OrePrefix.plate, smeltInto, amount))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
            }
        }
    }

    private void processStick(OrePrefix stickPrefix, Material material) {
        ItemStack stack = OreDictUnifier.get(stickPrefix, material);
        ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);
        ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);
        ItemStack longStack = OreDictUnifier.get(OrePrefix.stickLong, material);
        if (!(material instanceof DustMaterial))
            return;

        if (!stack.isEmpty()) {

            if (!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                if (material instanceof SolidMaterial) {
                    RecipeMaps.LATHE_RECIPES.recipeBuilder()
                        .inputs(material instanceof GemMaterial
                            ? CountableIngredient.from(OrePrefix.gem, material)
                            : CountableIngredient.from(OrePrefix.ingot, material))
                        .outputs(stack, OreDictUnifier.get(OrePrefix.dustSmall, ((SolidMaterial) material).macerateInto, 2))
                        .duration((int) Math.max(material.getMass() * 5L, 1L))
                        .EUt(16)
                        .buildAndRegister();
                    if (material instanceof MetalMaterial) {
                        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                            .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                            .input(OrePrefix.ingot, material)
                            .outputs(GTUtility.copyAmount(2, stack))
                            .duration(15).EUt(120)
                            .buildAndRegister();
                        ModHandler.addShapedRecipe(String.format("plunger_%s", material.toString()),
                            MetaItems.PLUNGER.getStackForm(material, material),
                            "xRR",
                            " SR",
                            "S f",
                            'S', new UnificationEntry(OrePrefix.stick, material),
                            'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));
                    }
                    SolidMaterial solidMaterial = (SolidMaterial) material;
                    if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial).isEmpty() && !OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                        ModHandler.addShapedRecipe(String.format("screwdriver_%s_%s", solidMaterial.toString(), solidMaterial.handleMaterial.toString()),
                            MetaItems.SCREWDRIVER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                            " fS",
                            " Sh",
                            "W  ",
                            'S', new UnificationEntry(OrePrefix.stick, solidMaterial),
                            'W', new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
                    }
                    ModHandler.addShapedRecipe(String.format("crowbar_%s", material.toString()),
                        MetaItems.CROWBAR.getStackForm(material, material),
                        "hDS",
                        "DSD",
                        "SDf",
                        'S', new UnificationEntry(stickPrefix, material),
                        'D', EnumDyeColor.BLUE);
                    ModHandler.addShapedRecipe(String.format("scoop_%s", material.toString()),
                        MetaItems.SCOOP.getStackForm(material, material),
                        "SWS",
                        "SSS",
                        "xSh",
                        'S', new UnificationEntry(stickPrefix, material),
                        'W', new ItemStack(Blocks.WOOL, 1, 32767));
                    ModHandler.addShapedRecipe(String.format("knife_%s", material.toString()),
                        MetaItems.KNIFE.getStackForm(material, material),
                        "fPh", " S ",
                        'S', new UnificationEntry(stickPrefix, material),
                        'P', new UnificationEntry(OrePrefix.plate, material));
                    ModHandler.addShapedRecipe(String.format("butchknife_%s", material.toString()),
                        MetaItems.BUTCHERYKNIFE.getStackForm(material, material),
                        "PPf", "PP ", "Sh ",
                        'S', new UnificationEntry(stickPrefix, material),
                        'P', new UnificationEntry(OrePrefix.plate, material));
                    if (!OreDictUnifier.get(OrePrefix.bolt, solidMaterial).isEmpty())
                    ModHandler.addShapedRecipe(String.format("soldiron_lv_%s", material.toString()),
                        MetaItems.SOLDERING_IRON_LV.getStackForm(material, Materials.Rubber/*, long[]{100000L, 32L, 1L, -1L}*/), // TODO electric tools
                        "LBf",
                        "Sd ",
                        "P  ",
                        'B', new UnificationEntry(OrePrefix.bolt, material),
                        'P', new UnificationEntry(OrePrefix.plate, material),
                        'S', new UnificationEntry(OrePrefix.stick, Materials.Iron),
                        'L', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
                }

                if (material.hasFlag(MetalMaterial.MatFlags.GENERATE_BOLT_SCREW)) {
                    ItemStack boltStack = OreDictUnifier.get(OrePrefix.bolt, material);
                    RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                        .input(stickPrefix, material)
                        .outputs(GTUtility.copyAmount(4, boltStack))
                        .duration((int) Math.max(material.getMass() * 2L, 1L))
                        .EUt(4)
                        .buildAndRegister();

                    ModHandler.addShapedRecipe(String.format("bolt_%s", material.toString()),
                        GTUtility.copyAmount(2, boltStack),
                        "s ",
                        " X",
                        'X', new UnificationEntry(stickPrefix, material));
                    if (!OreDictUnifier.get(OrePrefix.plate, material).isEmpty() && !OreDictUnifier.get(OrePrefix.screw, material).isEmpty()) {
                        ModHandler.addShapedRecipe(String.format("wirecutter_%s", material.toString()),
                            MetaItems.WIRECUTTER.getStackForm(material, material),
                            "PfP",
                            "hPd",
                            "STS",
                            'S', new UnificationEntry(stickPrefix, material),
                            'P', new UnificationEntry(OrePrefix.plate, material),
                            'T', new UnificationEntry(OrePrefix.screw, material));
                        ModHandler.addShapedRecipe(String.format("branchcutter_%s", material.toString()),
                            MetaItems.BRANCHCUTTER.getStackForm(material, material),
                            "PfP",
                            "PdP",
                            "STS",
                            'S', new UnificationEntry(stickPrefix, material),
                            'P', new UnificationEntry(OrePrefix.plate, material),
                            'T', new UnificationEntry(OrePrefix.screw, material));
                    }
                }

                ModHandler.addShapedRecipe(String.format("stick_%s", material.toString()),
                    OreDictUnifier.get(OrePrefix.stick, material, 1),
                    "f ",
                    " X",
                    'X', new UnificationEntry(OrePrefix.ingot, material));
            }
            if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                if (!longStack.isEmpty()) {
                    RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                        .input(stickPrefix, material, 2)
                        .outputs(longStack)
                        .duration((int) Math.max(material.getMass(), 1L))
                        .EUt(16)
                        .buildAndRegister();
                }
                RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                    .input(OrePrefix.stick, material)
                    .outputs(GTUtility.copy(stack, OreDictUnifier.get(OrePrefix.wireFine, material, 4)))
                    .duration(50)
                    .EUt(4)
                    .buildAndRegister();

            }
        }

    }

    private void processLongStick(OrePrefix longStickPrefix, Material material) {
        ItemStack stack = OreDictUnifier.get(longStickPrefix, material);
        ItemStack stickStack = OreDictUnifier.get(OrePrefix.stick, material);
        if (!(material instanceof DustMaterial))
            return;
        if (!stack.isEmpty() && !stickStack.isEmpty()) {
            if (material instanceof SolidMaterial) {
                SolidMaterial solidMaterial = (SolidMaterial) material;
                ModHandler.addShapedRecipe(String.format("jhammer_lith_%s", solidMaterial.toString()),
                    MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{1600000L, 512L, 3L, -1L}),
                    "SXd",
                    "PRP",
                    "MPB",
                    'X', new UnificationEntry(OrePrefix.stickLong, solidMaterial),
                    'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                    'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                    'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                    'R', new UnificationEntry(OrePrefix.spring, Materials.Titanium),
                    'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

                ModHandler.addShapedRecipe(String.format("jhammer_cad_%s", solidMaterial.toString()),
                    MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{1200000L, 512L, 3L, -1L}),
                    "SXd",
                    "PRP",
                    "MPB",
                    'X', new UnificationEntry(OrePrefix.stickLong, solidMaterial),
                    'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                    'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                    'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                    'R', new UnificationEntry(OrePrefix.spring, Materials.Titanium),
                    'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());

                ModHandler.addShapedRecipe(String.format("jhammer_sod_%s", solidMaterial.toString()),
                    MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{800000L, 512L, 3L, -1L}),
                    "SXd",
                    "PRP",
                    "MPB",
                    'X', new UnificationEntry(OrePrefix.stickLong, solidMaterial),
                    'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                    'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                    'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                    'R', new UnificationEntry(OrePrefix.spring, Materials.Titanium),
                    'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());
            }

            if (!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .input(longStickPrefix, material)
                    .outputs(GTUtility.copyAmount(2, stickStack))
                    .duration((int) Math.max(material.getMass(), 1L))
                    .EUt(4)
                    .buildAndRegister();

                ModHandler.addShapedRecipe(String.format("stick_long_%s", material.toString()),
                    GTUtility.copyAmount(2, stickStack),
                    "s", "X", 'X', new UnificationEntry(OrePrefix.stickLong, material));

                ModHandler.addShapedRecipe(String.format("stick_long_gem_flawless_%s", material.toString()),
                    stickStack,
                    "sf",
                    "G ",
                    'G', new UnificationEntry(OrePrefix.gemFlawless, material));

                ModHandler.addShapedRecipe(String.format("stick_long_gem_exquisite_%s", material.toString()),
                    GTUtility.copyAmount(2, stickStack),
                    "sf",
                    "G ",
                    'G', new UnificationEntry(OrePrefix.gemExquisite, material));
            }
            if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                if (!OreDictUnifier.get(OrePrefix.spring, material).isEmpty())
                    RecipeMaps.BENDER_RECIPES.recipeBuilder()
                        .input(longStickPrefix, material)
                        .outputs(OreDictUnifier.get(OrePrefix.spring, material))
                        .circuitMeta(1)
                        .duration(200)
                        .EUt(16)
                        .buildAndRegister();

                ModHandler.addShapedRecipe(String.format("stick_long_sticks_%s", material.toString()),
                    stack,
                    "ShS",
                    'S', new UnificationEntry(OrePrefix.stick, material));
            }
        }
    }

    private void processStone(OrePrefix stonePrefix, Material material) {
        ItemStack stack = OreDictUnifier.get(stonePrefix, material);

        switch (stonePrefix) {
            case stoneCobble: {
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(stonePrefix, material).input(OrePrefix.stick, Materials.Wood)
                    .outputs(new ItemStack(Blocks.LEVER, 1))
                    .duration(400)
                    .EUt(1)
                    .buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(stonePrefix, material, 8)
                    .circuitMeta(8)
                    .outputs(new ItemStack(Blocks.FURNACE, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(stonePrefix, material, 7).input(OrePrefix.dust, Materials.Redstone)
                    .outputs(new ItemStack(Blocks.DROPPER, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(stonePrefix, material, 7)
                    .inputs(new ItemStack(Items.BOW, 1, 0))
                    .fluidInputs(Materials.Redstone.getFluid(144))
                    .outputs(new ItemStack(Blocks.DISPENSER, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
                break;
            }
            case stoneSmooth: {
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(stonePrefix, material)
                    .circuitMeta(1)
                    .outputs(new ItemStack(Blocks.STONE_BUTTON, 1))
                    .duration(100)
                    .EUt(4)
                    .buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(stonePrefix, material, 2)
                    .circuitMeta(2)
                    .outputs(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1))
                    .duration(200)
                    .EUt(4)
                    .buildAndRegister();
                break;
            }
        }
    }

    private void processAxeHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ItemStack stack = OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial);

            if (!stack.isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("axe_%s_%s", solidMaterial.toString(), solidMaterial.handleMaterial.toString()),
                    MetaItems.AXE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("axe_%s_%s", solidMaterial.toString(), Materials.Wood),
                    MetaItems.AXE.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick,Materials.Wood));
            }

            if (smashing)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("head_axe_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadAxe, solidMaterial),
                    "PIh",
                    "P  ",
                    "f  ",
                    'P', new UnificationEntry(OrePrefix.plate, solidMaterial),
                    'I', new UnificationEntry(OrePrefix.ingot, solidMaterial));
            if (working)
                if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("head_axe_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadAxe, solidMaterial),
                    "GG ",
                    "G  ",
                    "f  ",
                    'G', new UnificationEntry(OrePrefix.gem, solidMaterial));
        }
    }

    private void processBuzzSawHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ModHandler.addShapedRecipe(String.format("bsaw_lith_%s", solidMaterial.toString()),
                MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                "PBM",
                "dXG",
                "SGP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("bsaw_cad_%s", solidMaterial.toString()),
                MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                "PBM",
                "dXG",
                "SGP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("bsaw_sod_%s", solidMaterial.toString()),
                MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                "PBM",
                "dXG",
                "SGP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

            if (working)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("bsaw_head_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadBuzzSaw, solidMaterial),
                    "wXh",
                    "X X",
                    "fXx",
                    'X', new UnificationEntry(OrePrefix.plate, solidMaterial));
        }
    }

    private void processChainSawHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ModHandler.addShapedRecipe(String.format("chsaw_lv_lith_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("chsaw_mv_tit_lith_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("chsaw_hv_lith_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("chsaw_lv_cad_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("chsaw_mv_cad_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("chsaw_hv_cad_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1200000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("chsaw_lv_sod_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("chsaw_mv_sod_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{200000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("chsaw_hv_sod_%s", solidMaterial.toString()),
                MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{800000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());

            if (working)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("chsaw_head_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadChainsaw, solidMaterial),
                    "SRS",
                    "XhX",
                    "SRS",
                    'X', new UnificationEntry(OrePrefix.plate, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.plate, Materials.Steel),
                    'R', new UnificationEntry(OrePrefix.ring, Materials.Steel));
        }
    }

    private void processDrillHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ModHandler.addShapedRecipe(String.format("drill_lv_lith_%s" , solidMaterial),
                MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("drill_lv_cad_%s" , solidMaterial.toString()),
                MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("drill_lv_sod_%s" , solidMaterial.toString()),
                MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("dill_mv_lith_%s" , solidMaterial.toString()),
                MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("drill_mv_cad_%s" , solidMaterial.toString()),
                MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("drill_mv_sod_%s" , solidMaterial.toString()),
                MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{200000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("drool_hv_lith_%s" , solidMaterial.toString()),
                MetaItems.DRILL_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

            ModHandler.addShapedRecipe(String.format("drill_hv_cad_%s" , solidMaterial.toString()),
                MetaItems.DRILL_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1200000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("drill_hv_sod_%s" , solidMaterial.toString()),
                MetaItems.DRILL_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{800000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, material),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());



            if (working)
                if(!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("drill_head_%s" , solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadDrill, solidMaterial),
                    "XSX",
                    "XSX",
                    "ShS",
                    'X', new UnificationEntry(OrePrefix.plate, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.plate, Materials.Steel));
        }
    }

    private void processFileHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("file_handle_%s", solidMaterial.toString()),
                    MetaItems.FILE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
                ModHandler.addMirroredShapedRecipe(String.format("file_%s", solidMaterial),
                    MetaItems.FILE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    "P",
                    "P",
                    "S",
                    'P', new UnificationEntry(OrePrefix.plate, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("file_handle_%s", solidMaterial.toString()),
                    MetaItems.FILE.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty()) {
                    ModHandler.addMirroredShapedRecipe(String.format("file_%s", solidMaterial),
                        MetaItems.FILE.getStackForm(solidMaterial, Materials.Wood),
                        "P",
                        "P",
                        "S",
                        'P', new UnificationEntry(OrePrefix.plate, solidMaterial),
                        'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));
                }
            }
        }
    }

    private void processHoeHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("hoe_fh_%s", solidMaterial.toString()),
                    MetaItems.HOE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("hoe_fh_%s", solidMaterial.toString()),
                    MetaItems.HOE.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));
            }
            if (smashing)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("hoe_head_m_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadHoe, solidMaterial),
                    "PIh",
                    "f  ",
                    'P', new UnificationEntry(OrePrefix.plate, solidMaterial),
                    'I', new UnificationEntry(OrePrefix.ingot, solidMaterial));
            if (working)
                if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("hoe_head_g_%s" , solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadHoe, solidMaterial),
                    "GG",
                    "f ",
                    'G', new UnificationEntry(OrePrefix.gem, solidMaterial));
        }
    }

    private void processPickaxeHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("pickaxe_%s", solidMaterial.toString()),
                    MetaItems.PICKAXE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("pickaxe_%s", solidMaterial.toString()),
                    MetaItems.PICKAXE.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));
            }

            if (smashing)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("paxe_head_m_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadPickaxe, solidMaterial),
                    "PII",
                    "f h",
                    'P', new UnificationEntry(OrePrefix.plate, solidMaterial),
                    'I', new UnificationEntry(OrePrefix.ingot, solidMaterial));
            if (working)
                if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("paze_head_g_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadPickaxe, solidMaterial),
                    "GGG",
                    "f  ",
                    'G', new UnificationEntry(OrePrefix.gem, solidMaterial));
        }
    }

    private void processPlowHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("plow_%s", solidMaterial.toString()),
                    MetaItems.PLOW.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("plow_%s", solidMaterial.toString()),
                    MetaItems.PLOW.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));
            }

            if (smashing)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("plow_head_m_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadPlow, solidMaterial),
                    "PP",
                    "PP",
                    "hf",
                    'P', new UnificationEntry(OrePrefix.plate, solidMaterial));
            if (working)
                if(!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("plow_head_g_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadPlow, solidMaterial),
                    "GG",
                    "GG",
                    " f",
                    'G', new UnificationEntry(OrePrefix.gem, solidMaterial));
        }
    }

    private void processSawHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("saw_%s", solidMaterial),
                    MetaItems.SAW.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("saw_%s", solidMaterial),
                    MetaItems.SAW.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));

            }

            if (smashing)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                    ModHandler.addShapedRecipe(String.format("saw_head_m_%s", solidMaterial.toString()),
                        OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial),
                        "PP ",
                        "fh ",
                        'P', new UnificationEntry(OrePrefix.plate, solidMaterial));
            if (working)
                if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                    ModHandler.addShapedRecipe(String.format("saw_head_g_%s", solidMaterial.toString()),
                        OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial),
                        "GGf",
                        'G', new UnificationEntry(OrePrefix.gem, solidMaterial));
        }
    }

    private void processSenseHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("sense_%s", solidMaterial.toString()),
                    MetaItems.SENSE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("sense_%s", solidMaterial.toString()),
                    MetaItems.SENSE.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));
            }

            if (smashing)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("sense_head_m_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadSense, solidMaterial),
                    "PPI",
                    "hf ",
                    'P', new UnificationEntry(OrePrefix.plate, solidMaterial),
                    'I', new UnificationEntry(OrePrefix.ingot, solidMaterial));
            if (working)
                if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                    ModHandler.addShapedRecipe(String.format("sense_head_g_%s", solidMaterial.toString()),
                        OreDictUnifier.get(OrePrefix.toolHeadSense, solidMaterial),
                        "GGG",
                        " f ",
                        "   ",
                        'G', new UnificationEntry(OrePrefix.gem, solidMaterial));

        }
    }

    private void processShovelHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("shovel_%s", solidMaterial.toString()),
                    MetaItems.SHOVEL.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("shovel_%s", solidMaterial.toString()),
                    MetaItems.SHOVEL.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));
            }
                if (smashing)
                    if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                    ModHandler.addShapedRecipe(String.format("shovel_head_m_%s", solidMaterial.toString()),
                        OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial),
                        "fPh",
                        'P', new UnificationEntry(OrePrefix.plate, solidMaterial));
                if (working)
                    if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                        ModHandler.addShapedRecipe(String.format("shovel_head_g_%s", solidMaterial.toString()),
                            OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial),
                            "fG",
                            'G', new UnificationEntry(OrePrefix.gem, solidMaterial));

            }
    }

    private void processSwordHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("sword_%s", solidMaterial.toString()),
                    MetaItems.SWORD.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
            } else {
                ModHandler.addShapelessRecipe(String.format("sword_%s", solidMaterial.toString()),
                    MetaItems.SWORD.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));
            }
                if (smashing)
                    if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                    ModHandler.addShapedRecipe(String.format("sword_head_m_%s", solidMaterial.toString()),
                        OreDictUnifier.get(OrePrefix.toolHeadSword, solidMaterial),
                        " P ",
                        "fPh",
                        'P', new UnificationEntry(OrePrefix.plate, solidMaterial));
                if (working)
                    if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                        ModHandler.addShapedRecipe(String.format("sword_head_g_%s", solidMaterial.toString()),
                            OreDictUnifier.get(OrePrefix.toolHeadSword, solidMaterial),
                            " G",
                            "fG",
                            'G', new UnificationEntry(OrePrefix.gem, solidMaterial));
            }
    }

    private void processSpadeHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.screw, solidMaterial).isEmpty())
            ModHandler.addShapelessRecipe(String.format("spade_%s", solidMaterial.toString()),
                MetaItems.UNIVERSALSPADE.getStackForm(solidMaterial, solidMaterial),
                new UnificationEntry(toolPrefix, material),
                OreDictUnifier.get(OrePrefix.stick, solidMaterial),
                new UnificationEntry(OrePrefix.screw, solidMaterial), ToolDictNames.craftingToolScrewdriver);
            if (working)
                ModHandler.addShapedRecipe(String.format("spade_head_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, solidMaterial),
                    "fX",
                    'X', new UnificationEntry(OrePrefix.toolHeadShovel, solidMaterial));

        }
    }

    private void processWrenchHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ModHandler.addShapedRecipe(String.format("wrench_lv_lith_%s", solidMaterial.toString()),
                MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("wrench_mv_lith_%s", solidMaterial.toString()),
                MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("wrench_hv_lith_%s", solidMaterial.toString()),
                MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("wrench_lv_cad_%s", solidMaterial.toString()),
                MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("wrench_mv_cad_%s", solidMaterial.toString()),
                MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("wrench_hv_cad_%s", solidMaterial.toString()),
                MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel),  //new long[]{1200000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("wrench_lv_sod_%s", solidMaterial.toString()),
                MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{50000L, 32L, 1L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("wrench_mv_sod_%s", solidMaterial.toString()),
                MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium),  //new long[]{200000L, 128L, 2L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.Titanium),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.Titanium),
                'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());
            ModHandler.addShapedRecipe(String.format("wrench_hv_sod_%s", solidMaterial.toString()),
                MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel),  //new long[]{800000L, 512L, 3L, -1L}),
                "SXd",
                "GMG",
                "PBP",
                'X', new UnificationEntry(toolPrefix, solidMaterial),
                'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
                'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());
            if (working)
                if (!OreDictUnifier.get(OrePrefix.plate, solidMaterial).isEmpty())
                ModHandler.addShapedRecipe(String.format("wrench_head_%s", solidMaterial.toString()),
                    OreDictUnifier.get(OrePrefix.toolHeadWrench, solidMaterial),
                    "hXW",
                    "XRX",
                    "WXd",
                    'X', new UnificationEntry(OrePrefix.plate, solidMaterial),
                    'R', new UnificationEntry(OrePrefix.ring, Materials.Steel),
                    'W', new UnificationEntry(OrePrefix.screw, Materials.Steel));
        }
    }
        private void processScrewdriverHead (OrePrefix toolPrefix, Material material) {
            boolean smashing = !material.hasFlag(NO_SMASHING);
            boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
            if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
                SolidMaterial solidMaterial = (SolidMaterial) material;

                ModHandler.addShapedRecipe(String.format("sdriver_lv_lith_%s", solidMaterial.toString()),
                    MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{100000L, 32L, 1L, -1L}),
                    "PdX",
                    "MGS",
                    "GBP",
                    'X', new UnificationEntry(OrePrefix.stickLong, solidMaterial),
                    'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                    'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                    'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                    'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                    'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
                ModHandler.addShapedRecipe(String.format("sdriver_lv_cad_%s", solidMaterial.toString()),
                    MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{75000L, 32L, 1L, -1L}),
                    "PdX",
                    "MGS",
                    "GBP",
                    'X', new UnificationEntry(OrePrefix.stickLong, solidMaterial),
                    'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                    'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                    'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                    'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                    'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());
                ModHandler.addShapedRecipe(String.format("sdriver_lv_sod_%s", solidMaterial.toString()),
                    MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{50000L, 32L, 1L, -1L}),
                    "PdX",
                    "MGS",
                    "GBP",
                    'X', new UnificationEntry(OrePrefix.stickLong, solidMaterial),
                    'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                    'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
                    'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
                    'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
                    'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());
            }
        }

    private void processHammerHead(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial && !ModHandler.isMaterialWood(material)) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            if (!OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial).isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("hammer_fh_%s", solidMaterial.toString()),
                    MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));

                ModHandler.addShapedRecipe(String.format("hammer_m_%s", solidMaterial.toString()),
                    MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                    "XX ",
                    "XXS",
                    "XX ",
                    'X', new UnificationEntry(OrePrefix.ingot, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));
                if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                    ModHandler.addShapedRecipe(String.format("hammer_g_%s", solidMaterial.toString()),
                        MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        "XX ",
                        "XXS",
                        "XX ",
                        'X', new UnificationEntry(OrePrefix.gem, solidMaterial),
                        'S', new UnificationEntry(OrePrefix.stick, solidMaterial.handleMaterial));

                if (smashing) {
                    ModHandler.addShapedRecipe(String.format("hammer_head_%s", solidMaterial.toString()),
                        OreDictUnifier.get(OrePrefix.toolHeadHammer, solidMaterial),
                        "II ",
                        "IIh",
                        "II ",
                        'I', new UnificationEntry(OrePrefix.ingot, solidMaterial));

                }
            } else {
                ModHandler.addShapelessRecipe(String.format("hammer_fh_%s", solidMaterial.toString()),
                    MetaItems.HARDHAMMER.getStackForm(solidMaterial, Materials.Wood),
                    new UnificationEntry(toolPrefix, material),
                    new UnificationEntry(OrePrefix.stick, Materials.Wood));

                ModHandler.addShapedRecipe(String.format("hammer_m_%s", solidMaterial.toString()),
                    MetaItems.HARDHAMMER.getStackForm(solidMaterial, Materials.Wood),
                    "XX ",
                    "XXS",
                    "XX ",
                    'X', new UnificationEntry(OrePrefix.ingot, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));
                if (!OreDictUnifier.get(OrePrefix.gem, solidMaterial).isEmpty())
                    ModHandler.addShapedRecipe(String.format("hammer_g_%s", solidMaterial.toString()),
                        MetaItems.HARDHAMMER.getStackForm(solidMaterial, Materials.Wood),
                        "XX ",
                        "XXS",
                        "XX ",
                        'X', new UnificationEntry(OrePrefix.gem, solidMaterial),
                        'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));

                if (smashing) {
                    ModHandler.addShapedRecipe(String.format("hammer_head_%s", solidMaterial.toString()),
                        OreDictUnifier.get(OrePrefix.toolHeadHammer, solidMaterial),
                        "II ",
                        "IIh",
                        "II ",
                        'I', new UnificationEntry(OrePrefix.ingot, solidMaterial));
                }
            }
        }
    }

    private void processTurbine(OrePrefix toolPrefix, Material material) {
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);
        if (material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.turbineBlade, solidMaterial, 8)
                .input(OrePrefix.stickLong, Materials.Titanium)
                .outputs(MetaItems.TURBINE.getStackForm(solidMaterial, null))
                .duration(320)
                .EUt(400)
                .buildAndRegister();
            if (working) {
                ModHandler.addShapedRecipe(String.format("turbine_blade_%s", material),
                    OreDictUnifier.get(toolPrefix, material),
                    "fPd",
                    "SPS",
                    " P ",
                    'P', new UnificationEntry(OrePrefix.plate, material),
                    'S', new UnificationEntry(OrePrefix.screw, material));
            }
        }
    }

    private void processWireSingle(OrePrefix wirePrefix, Material materialIn) {
        if (materialIn instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(wirePrefix, material); // GTUtility.getTierByVoltage(material.cableProperties.voltage) to find the tier
            if (!stack.isEmpty()) {
                if (material.cableProperties != null && GTUtility.getTierByVoltage(material.cableProperties.voltage) == 1) {
                    ModHandler.addShapelessRecipe(String.format("%s_cable_single", material.toString()), OreDictUnifier.get(OrePrefix.cableGtSingle, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMaps.PACKER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }

//                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
//                    .inputs(stack)
//                    .circuitMeta(24)
//                    .fluidInputs(Materials.Rubber.getFluid(L))
//                    .outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, material))
//                    .duration(100)
//                    .EUt(8)
//                    .buildAndRegister();

//            RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
//                .inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, material))
//                .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber))
//                .duration(100)
//                .EUt(8)
//                .buildAndRegister();
                if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    if (!OreDictUnifier.get(OrePrefix.springSmall, material, 2).isEmpty())
                    RecipeMaps.BENDER_RECIPES.recipeBuilder()
                        .input(wirePrefix, material)
                        .outputs(OreDictUnifier.get(OrePrefix.springSmall, material, 2))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    if (!OreDictUnifier.get(OrePrefix.wireFine, material, 4).isEmpty())
                    RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                        .input(wirePrefix, material)
                        .outputs(OreDictUnifier.get(OrePrefix.wireFine, material, 4))
                        .duration(200)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material)
                        .outputs(GTUtility.copy(GTUtility.copyAmount(2, stack), OreDictUnifier.get(OrePrefix.wireFine, material, 8)))
                        .duration(100)
                        .EUt(4)
                        .buildAndRegister();

                }
                if (!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    ModHandler.addShapedRecipe(String.format("%s_wire_single", material.toString()), OreDictUnifier.get(OrePrefix.wireGtSingle, material),
                        "Xx",
                        'X', new UnificationEntry(OrePrefix.plate, material));
                }
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(wirePrefix, material, 2)
                    .circuitMeta(2)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGtDouble, material))
                    .duration(150)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(wirePrefix, material, 4)
                    .circuitMeta(4)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, material))
                    .duration(200)
                    .EUt(8).buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(wirePrefix, material, 8)
                    .circuitMeta(8)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGtOctal, material))
                    .duration(300)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(wirePrefix, material, 12)
                    .circuitMeta(12)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGtTwelve, material))
                    .duration(400)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(wirePrefix, material, 16)
                    .circuitMeta(16)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGtHex, material))
                    .duration(500)
                    .EUt(8)
                    .buildAndRegister();
            }
        }
    }

    private void processWireDouble(OrePrefix wirePrefix, Material materialIn) {
        if (materialIn instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) materialIn;

            ItemStack stack = OreDictUnifier.get(wirePrefix, material);
            if (!stack.isEmpty()) {
                if (material.cableProperties != null && GTUtility.getTierByVoltage(material.cableProperties.voltage) == 1) {
                    ModHandler.addShapelessRecipe(String.format("%s_cable_double", material.toString()), OreDictUnifier.get(OrePrefix.cableGtDouble, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMaps.PACKER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGtDouble, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }

//                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
//                    .inputs(stack)
//                    .circuitMeta(24)
//                    .fluidInputs(Materials.Rubber.getFluid(L))
//                    .outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, material))
//                    .duration(100)
//                    .EUt(8)
//                    .buildAndRegister();

//            RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
//                .inputs(OreDictUnifier.get(OrePrefix.cableGtDouble, material))
//                .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber))
//                .duration(100)
//                .EUt(8)
//                .buildAndRegister();
                ModHandler.addShapelessRecipe(String.format("%s_wire_double_to_single", material.toString()), OreDictUnifier.get(OrePrefix.wireGtSingle, material, 2),
                    new UnificationEntry(wirePrefix, material));
                ModHandler.addShapelessRecipe(String.format("%s_wire_single_to_double", material.toString()), GTUtility.copyAmount(1, stack),
                    new UnificationEntry(OrePrefix.wireGtSingle, material),
                    new UnificationEntry(OrePrefix.wireGtSingle, material));
            }
        }
    }

    private void processWireQuadruple(OrePrefix wirePrefix, Material materialIn) {
        if (materialIn instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(wirePrefix, material);
            if (!stack.isEmpty()) {
                if (material.cableProperties != null && GTUtility.getTierByVoltage(material.cableProperties.voltage) == 1) {
                    ModHandler.addShapelessRecipe(String.format("%s_cable_quad", material.toString()), OreDictUnifier.get(OrePrefix.cableGtQuadruple, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMaps.PACKER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 2, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 2, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }

//                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
//                    .inputs(stack)
//                    .circuitMeta(24)
//                    .fluidInputs(Materials.Rubber.getFluid(2 * L))
//                    .outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, material))
//                    .duration(100)
//                    .EUt(8)
//                    .buildAndRegister();

//            RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
//                .inputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, material))
//                .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 2))
//                .duration(100)
//                .EUt(8)
//                .buildAndRegister();

                ModHandler.addShapelessRecipe(String.format("%s_wire_quad_to_single", material.toString()), OreDictUnifier.get(OrePrefix.wireGtSingle, material, 4),
                    new UnificationEntry(wirePrefix, material));

                ModHandler.addShapelessRecipe(String.format("%s_wire_double_to_quad", material.toString()), GTUtility.copyAmount(1, stack),
                    new UnificationEntry(OrePrefix.wireGtDouble, material),
                    new UnificationEntry(OrePrefix.wireGtDouble, material));

                ModHandler.addShapelessRecipe(String.format("%s_wire_single_to_quad", material.toString()), GTUtility.copyAmount(1, stack),
                    new UnificationEntry(OrePrefix.wireGtSingle, material),
                    new UnificationEntry(OrePrefix.wireGtSingle, material),
                    new UnificationEntry(OrePrefix.wireGtSingle, material),
                    new UnificationEntry(OrePrefix.wireGtSingle, material));
            }
        }
    }

    private void processWireOctal (OrePrefix wirePrefix, Material materialIn){
        if (materialIn instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(wirePrefix, material);
            if (!stack.isEmpty()) {
                if (material.cableProperties != null && GTUtility.getTierByVoltage(material.cableProperties.voltage) == 1) {
                    ModHandler.addShapelessRecipe(String.format("%s_cable_octal", material.toString()), OreDictUnifier.get(OrePrefix.cableGtOctal, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMaps.PACKER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 3, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGtOctal, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 3, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
//                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
//                    .inputs(stack)
//                    .circuitMeta(24)
//                    .fluidInputs(Materials.Rubber.getFluid(3 * L))
//                    .outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, material))
//                    .duration(100)
//                    .EUt(8)
//                    .buildAndRegister();

//            RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
//                .inputs(OreDictUnifier.get(OrePrefix.cableGtOctal, material))
//                .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 3))
//                .duration(100)
//                .EUt(8)
//                .buildAndRegister();

                ModHandler.addShapelessRecipe(String.format("%s_wire_octal_to_single", material.toString()), OreDictUnifier.get(OrePrefix.wireGtSingle, material, 8),
                    new UnificationEntry(wirePrefix, material));
                ModHandler.addShapelessRecipe(String.format("%s_wire_quad_to_octal", material.toString()), GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGtQuadruple, material),
                    OreDictUnifier.get(OrePrefix.wireGtQuadruple, material));
            }
        }
    }

    private void processWireTwelve(OrePrefix wirePrefix, Material materialIn) {
        if (materialIn instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(wirePrefix, material);
            if (!stack.isEmpty()) {
                if (material.cableProperties != null && GTUtility.getTierByVoltage(material.cableProperties.voltage) == 1) {
                    ModHandler.addShapelessRecipe(String.format("%s_cable_twelve", material.toString()), OreDictUnifier.get(OrePrefix.cableGtTwelve, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMaps.PACKER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 4, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGtTwelve, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGtTwelve, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 4, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
//                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
//                    .inputs(stack)
//                    .circuitMeta(24)
//                    .fluidInputs(Materials.Rubber.getFluid(4 * L))
//                    .outputs(OreDictUnifier.get(OrePrefix.cableGtTwelve, material))
//                    .duration(100)
//                    .EUt(8)
//                    .buildAndRegister();

//            RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
//                .inputs(OreDictUnifier.get(OrePrefix.cableGtTwelve, material))
//                .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 4))
//                .duration(100)
//                .EUt(8)
//                .buildAndRegister();

                ModHandler.addShapelessRecipe(String.format("%s_wire_twelve_to_single", material.toString()), OreDictUnifier.get(OrePrefix.wireGtSingle, material, 12),
                    new UnificationEntry(wirePrefix, material));

                ModHandler.addShapelessRecipe(String.format("%s_wire_quad_and_octal_to_twelve", material.toString()), GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGtOctal, material),
                    OreDictUnifier.get(OrePrefix.wireGtQuadruple, material));
            }
        }
    }

    private void processWireHex(OrePrefix wirePrefix, Material material) {
        ItemStack stack = OreDictUnifier.get(wirePrefix, material);
        if (!stack.isEmpty()) {
            ModHandler.addShapelessRecipe(String.format("%s_wire_hex_to_single", material.toString()), OreDictUnifier.get(OrePrefix.wireGtSingle, material, 16),
                new UnificationEntry(wirePrefix, material));

            ModHandler.addShapelessRecipe(String.format("%s_wire_octal_to_hex", material.toString()), GTUtility.copyAmount(1, stack),
                new UnificationEntry(OrePrefix.wireGtOctal, material),
                new UnificationEntry(OrePrefix.wireGtOctal, material));
        }
    }

    private void processOre(OrePrefix orePrefix, Material materialIn) {
        if (materialIn instanceof SolidMaterial) {
            ItemStack oreStack = OreDictUnifier.get(orePrefix, materialIn);
            SolidMaterial material = (SolidMaterial) materialIn;
            ItemStack crushedStack = OreDictUnifier.get(OrePrefix.crushed, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
            ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);
            DustMaterial byproductMaterial = GTUtility.selectItemInList(0, material, material.oreByProducts, DustMaterial.class);
            if (!oreStack.isEmpty() && !crushedStack.isEmpty() && !dustStack.isEmpty()) {

                RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                    .input(orePrefix, materialIn)
                    .outputs(crushedStack)
                    .duration(40)
                    .EUt(16)
                    .buildAndRegister();

                RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                    .input(orePrefix, materialIn)
                    .outputs(GTUtility.copyAmount(2, crushedStack))
                    .chancedOutput(dustStack, 1000)
                    .duration(200)
                    .EUt(24)
                    .buildAndRegister();
                if (!ingotStack.isEmpty()) {
                    ModHandler.addSmeltingRecipe(oreStack, ingotStack);
                }
            }
        }
    }
}
