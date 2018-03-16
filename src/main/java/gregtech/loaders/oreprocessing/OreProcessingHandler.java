package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.material.type.DustMaterial.MatFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
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

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMASHING;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMELTING;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.MORTAR_GRINDABLE;

public class OreProcessingHandler {

    public void registerProcessing() {
        OrePrefix.dust.addProcessingHandler(this::processDust);
        OrePrefix.ingot.addProcessingHandler(this::processIngot);
        OrePrefix.nugget.addProcessingHandler(this::processNugget);
        OrePrefix.dustSmall.addProcessingHandler(this::processSmallDust);
        OrePrefix.dustTiny.addProcessingHandler(this::processTinyDust);
        OrePrefix.block.addProcessingHandler(this::processBlock);
        OrePrefix.bolt.addProcessingHandler(this::processBolt);
        OrePrefix.screw.addProcessingHandler(this::processScrew);
        OrePrefix.wireFine.addProcessingHandler(this::processFineWire);
        OrePrefix.foil.addProcessingHandler(this::processFoil);
        OrePrefix.lens.addProcessingHandler(this::processCraftingLens);
        OrePrefix.crushed.addProcessingHandler(this::processingCrushedOre);
        OrePrefix.crushedPurified.addProcessingHandler(this::processingCrushedPurified);
        OrePrefix.crushedCentrifuged.addProcessingHandler(this::processingCrushedCentrifuged);
        OrePrefix.crystalline.addProcessingHandler(this::processingCrystallizedPurified);
        OrePrefix.dustImpure.addProcessingHandler(this::processingDirtyDust);
        OrePrefix.dustPure.addProcessingHandler(this::processingPure);
        OrePrefix.dustSmall.addProcessingHandler(this::processSmallDust);
        OrePrefix.dustTiny.addProcessingHandler(this::processTinyDust);
        OrePrefix.gear.addProcessingHandler(this::processingGear);
        OrePrefix.gem.addProcessingHandler(this::processingGem);

    }
    private static List<OrePrefix> ORDER = Arrays.asList(
        OrePrefix.gemChipped, OrePrefix.gemFlawed, OrePrefix.gem, OrePrefix.gemFlawless, OrePrefix.gemExquisite
    );

    private void processDust(OrePrefix dustPrefix, Material material) {
        if(!(material instanceof DustMaterial))
            return;
        ItemStack dustStack = OreDictUnifier.get(dustPrefix, material);

        if (material instanceof GemMaterial) {
            ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);

            if(material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)) {
                RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
                    .inputs(dustStack)
                    .fluidInputs(Materials.Water.getFluid(200))
                    .chancedOutput(gemStack, 7000)
                    .duration(2000)
                    .EUt(24)
                    .buildAndRegister();

                RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
                    .inputs(dustStack)
                    .fluidInputs(ModHandler.getDistilledWater(200))
                    .chancedOutput(gemStack, 9000)
                    .duration(1500)
                    .EUt(24)
                    .buildAndRegister();
            } else if(!material.hasFlag(Material.MatFlags.EXPLOSIVE | DustMaterial.MatFlags.NO_SMASHING)) {
                RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(4, dustStack))
                    .outputs(GTUtility.copyAmount(3, gemStack))
                    .explosivesAmount(4)
                    .buildAndRegister();
            }
        } else if(material instanceof MetalMaterial && !material.hasFlag(Material.MatFlags.FLAMMABLE | DustMaterial.MatFlags.NO_SMELTING)) {
            MetalMaterial metalMaterial = (MetalMaterial) material;

            ItemStack tinyDustStack = OreDictUnifier.get(OrePrefix.dustTiny, metalMaterial);
            ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, metalMaterial);
            ItemStack nuggetStack = OreDictUnifier.get(OrePrefix.nugget, metalMaterial);

            if(metalMaterial.blastFurnaceTemperature <= 0) {
                ModHandler.addSmeltingRecipe(dustStack, ingotStack);
                ModHandler.addSmeltingRecipe(tinyDustStack, nuggetStack);
            } else {
                int duration = Math.max(1, (int) (material.getMass() * metalMaterial.blastFurnaceTemperature / 50L));
                ModHandler.removeFurnaceSmelting(ingotStack);
                RecipeMap.BLAST_RECIPES.recipeBuilder()
                    .inputs(dustStack).outputs(ingotStack)
                    .duration(duration).EUt(120)
                    .blastFurnaceTemp(metalMaterial.blastFurnaceTemperature)
                    .buildAndRegister();
                RecipeMap.BLAST_RECIPES.recipeBuilder()
                    .inputs(tinyDustStack).outputs(nuggetStack)
                    .duration(Math.max(1, duration / 9)).EUt(120)
                    .blastFurnaceTemp(metalMaterial.blastFurnaceTemperature)
                    .buildAndRegister();
                if(metalMaterial.blastFurnaceTemperature <= 1000) {
                    ModHandler.addRCFurnaceRecipe(dustStack, ingotStack, duration);
                    ModHandler.addRCFurnaceRecipe(tinyDustStack, nuggetStack, Math.max(1, duration / 9));
                }
            }
        } else if(material.hasFlag(MatFlags.GENERATE_PLATE) &&
            !material.hasFlag(Material.MatFlags.EXPLOSIVE | DustMaterial.MatFlags.NO_SMASHING)) {
            RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
                .inputs(dustStack)
                .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                .buildAndRegister();
        }
    }

    private void processNugget(OrePrefix orePrefix, Material material) {
        ItemStack nuggetStack = OreDictUnifier.get(orePrefix, material);

        if(material instanceof MetalMaterial) {
            ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);
            if (!ingotStack.isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("nugget_disassembling_%s", material.toString()),
                    GTUtility.copyAmount(9, nuggetStack), ingotStack);
                ModHandler.addShapedRecipe(String.format("nugget_assembling_%s", material.toString()),
                    ingotStack, "XXX", "XXX", "XXX", 'X', nuggetStack);
            }

        } else if(material instanceof GemMaterial) {
            //sometimes happens because of other mods
            ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);
            if (!gemStack.isEmpty()) {
                ModHandler.addShapelessRecipe(String.format("nugget_disassembling_%s", material.toString()),
                    GTUtility.copyAmount(9, nuggetStack), gemStack);
                ModHandler.addShapedRecipe(String.format("nugget_assembling_%s", material.toString()),
                    gemStack, "XXX", "XXX", "XXX", 'X', nuggetStack);
            }
        }
    }

    private void processSmallDust(OrePrefix orePrefix, Material material) {
        if (material instanceof DustMaterial) {
            ItemStack smallDustStack = OreDictUnifier.get(orePrefix, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

            ModHandler.addShapedRecipe(String.format("small_dust_disassembling_%s", material.toString()),
                GTUtility.copyAmount(4, smallDustStack), "  ", " X", 'X', dustStack);
            ModHandler.addShapedRecipe(String.format("small_dust_assembling_%s", material.toString()),
                dustStack, "XX", "XX", 'X', smallDustStack);
        }
    }

    private void processTinyDust(OrePrefix orePrefix, Material material) {
        if (material instanceof DustMaterial) {
            ItemStack tinyDustStack = OreDictUnifier.get(orePrefix, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

            ModHandler.addShapedRecipe(String.format("tiny_dust_disassembling_%s", material.toString()),
                GTUtility.copyAmount(9, tinyDustStack), "X ", "  ", 'X', dustStack);
            ModHandler.addShapedRecipe(String.format("tiny_dust_assembling_%s", material.toString()),
                dustStack, "XXX", "XXX", "XXX", 'X', tinyDustStack);
        }
    }

    private void processBlock(OrePrefix blockPrefix, Material material) {
        if(!(material instanceof DustMaterial))
            return;
        ItemStack blockStack = OreDictUnifier.get(blockPrefix, material);

        if (material.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID)) {
            FluidMaterial fluidMaterial = (FluidMaterial) material;
            RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                .fluidInputs(fluidMaterial.getFluid(L * 9))
                .outputs(blockStack)
                .duration((int) material.getMass()).EUt(8)
                .buildAndRegister();
        }

        if(material.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE)) {
            ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
            RecipeMap.CUTTER_RECIPES.recipeBuilder()
                .inputs(blockStack)
                .outputs(GTUtility.copyAmount(9, plateStack))
                .duration((int) (material.getMass() * 8L)).EUt(30)
                .buildAndRegister();
        }

        ItemStack blockComponent;
        if(material instanceof GemMaterial) {
            blockComponent = OreDictUnifier.get(OrePrefix.gem, material);
        } else if(material instanceof MetalMaterial) {
            blockComponent = OreDictUnifier.get(OrePrefix.ingot, material);
        } else {
            blockComponent = OreDictUnifier.get(OrePrefix.dust, material);
        }
        ModHandler.addShapedRecipe(String.format("%s_block_compress", material.toString()),
            blockStack, "XXX", "XXX", "XXX",
            'X', blockComponent);
        ModHandler.addShapelessRecipe(String.format("%s_block_decompress", material.toString()),
            GTUtility.copyAmount(9, blockComponent),
            blockStack);
    }

    private void processBolt(OrePrefix boltPrefix, Material material) {
        if(!(material instanceof MetalMaterial) || material.hasFlag(MatFlags.NO_WORKING))
            return;
        ItemStack boltStack = OreDictUnifier.get(boltPrefix, material);
        ItemStack screwStack = OreDictUnifier.get(OrePrefix.screw, material);
        ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);

        ModHandler.addShapedRecipe(String.format("%s_bolt", material.toString()),
            boltStack, "fS ", "S  ",
            'S', screwStack);

        RecipeMap.CUTTER_RECIPES.recipeBuilder()
            .inputs(screwStack)
            .outputs(boltStack)
            .duration(20).EUt(24)
            .buildAndRegister();

        RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
            .notConsumable(MetaItems.SHAPE_EXTRUDER_BOLT)
            .inputs(ingotStack)
            .outputs(GTUtility.copyAmount(16, boltStack))
            .duration(15).EUt(120)
            .buildAndRegister();
    }

    private void processScrew(OrePrefix screwPrefix, Material material) {
        if (!(material instanceof MetalMaterial) || material.hasFlag(MatFlags.NO_WORKING))
            return;
        ItemStack screwStack = OreDictUnifier.get(screwPrefix, material);
        ItemStack boltStack = OreDictUnifier.get(OrePrefix.bolt, material);

        RecipeMap.LATHE_RECIPES.recipeBuilder()
            .inputs(boltStack).outputs(screwStack)
            .duration((int) Math.max(1, material.getMass() / 8L)).EUt(4)
            .buildAndRegister();

        ModHandler.addShapedRecipe(String.format("screw_%s", material.toString()),
            screwStack, "fX", "X ", 'X', boltStack);
    }

    private void processFoil(OrePrefix foilPrefix, Material material) {
        if (!(material instanceof MetalMaterial) || material.hasFlag(MatFlags.NO_SMASHING))
            return;
        RecipeMap.BENDER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, material, 4))
            .outputs(OreDictUnifier.get(foilPrefix, material, 4))
            .duration((int) material.getMass()).EUt(24)
            .circuitMeta(0)
            .buildAndRegister();
    }

    private void processFineWire(OrePrefix fineWirePrefix, Material material) {
        if (!(material instanceof MetalMaterial) || material.hasFlag(DustMaterial.MatFlags.NO_WORKING | Material.MatFlags.NO_UNIFICATION))
            return;
        ItemStack fineWireStack = OreDictUnifier.get(fineWirePrefix, material);
        ModHandler.addShapelessRecipe(String.format("fine_wire_%s", material.toString()),
            fineWireStack, 'x', OreDictUnifier.get(OrePrefix.ingot, material));
    }

    private void processIngot(OrePrefix ingotPrefix, Material material) {
        if (!(material instanceof MetalMaterial))
            return;
        ItemStack ingotStack = OreDictUnifier.get(ingotPrefix, material);

        if (material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE)) {
            ModHandler.addShapelessRecipe(String.format("mortar_grind_%s", material.toString()),
                OreDictUnifier.get(OrePrefix.dust, material), 'm', ingotStack);
        }

        RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
            .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
            .fluidInputs(((FluidMaterial) material).getFluid(L))
            .outputs(OreDictUnifier.get(OrePrefix.ingot, material))
            .duration(20).EUt(8)
            .buildAndRegister();

        if (!material.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE) ||
            material.hasFlag(DustMaterial.MatFlags.NO_SMASHING))
            return;
        ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);

        RecipeMap.BENDER_RECIPES.recipeBuilder()
            .circuitMeta(0)
            .inputs(ingotStack)
            .outputs(plateStack)
            .circuitMeta(0)
            .EUt(24).duration((int) (material.getMass() / 1.5))
            .buildAndRegister();

        RecipeMap.HAMMER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(2, ingotStack))
            .outputs(plateStack)
            .EUt(16).duration((int) (material.getMass() / 2L))
            .buildAndRegister();

        ModHandler.addShapedRecipe(String.format("plate_%s", material.toString()),
            plateStack, "h  ", "I  ", "I  ", 'I',
            ingotStack);

        if (material.hasFlag(MetalMaterial.MatFlags.GENERATE_DENSE)) {
            RecipeMap.BENDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(9, ingotStack))
                .outputs(OreDictUnifier.get(OrePrefix.plateDense, material))
                .circuitMeta(5)
                .EUt(96).duration((int) (material.getMass() * 9))
                .buildAndRegister();

            RecipeMap.BENDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(9, plateStack))
                .outputs(OreDictUnifier.get(OrePrefix.plateDense, material))
                .circuitMeta(5)
                .EUt(96).duration((int) (material.getMass() * 1.5))
                .buildAndRegister();
        }
    }
    private void processCraftingLens(OrePrefix lensPrefix, Material material) {

        if (material == MarkerMaterials.Color.Red) {
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.foil, Materials.Copper))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(MetaItems.CIRCUIT_PARTS_WIRING_BASIC.getStackForm())
                .duration(64)
                .EUt(30)
                .buildAndRegister();
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.foil, Materials.AnnealedCopper))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(MetaItems.CIRCUIT_PARTS_WIRING_BASIC.getStackForm())
                .duration(64)
                .EUt(30)
                .buildAndRegister();
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.foil, Materials.Gold))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(MetaItems.CIRCUIT_PARTS_WIRING_ADVANCED.getStackForm())
                .duration(64)
                .EUt(120)
                .buildAndRegister();
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.foil, Materials.Electrum))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(MetaItems.CIRCUIT_PARTS_WIRING_ADVANCED.getStackForm())
                .duration(64)
                .EUt(120)
                .buildAndRegister();
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.foil, Materials.Platinum))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(MetaItems.CIRCUIT_PARTS_WIRING_ELITE.getStackForm())
                .duration(64)
                .EUt(480)
                .buildAndRegister();
        } else if (material == MarkerMaterials.Color.Green) {
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Olivine))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm())
                .duration(256)
                .EUt(480)
                .buildAndRegister();
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Emerald))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm())
                .duration(256)
                .EUt(480)
                .buildAndRegister();
        } else if (material == MarkerMaterials.Color.White) {
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(new ItemStack(Blocks.SANDSTONE, 1, 2))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(new ItemStack(Blocks.SANDSTONE, 1, 1))
                .duration(20)
                .EUt(16)
                .buildAndRegister();
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(new ItemStack(Blocks.STONE))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(new ItemStack(Blocks.STONEBRICK, 1, 3))
                .duration(50)
                .EUt(16)
                .buildAndRegister();
            RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .inputs(new ItemStack(Blocks.QUARTZ_BLOCK))
                .notConsumable(OreDictUnifier.get(OrePrefix.lens, material))
                .outputs(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1))
                .duration(50)
                .EUt(16)
                .buildAndRegister();
        }
    }
    private void processingCrushedOre(OrePrefix crushedPrefix, Material materialIn) {
        ItemStack oreStack = OreDictUnifier.get(crushedPrefix, materialIn);
        if (materialIn instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) materialIn;
            ItemStack impureDustStack = OreDictUnifier.get(OrePrefix.dustImpure, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
            DustMaterial byproductMaterial = GTUtility.selectItemInList(0, material, material.oreByProducts, DustMaterial.class);

            //fallback for dirtyGravel, shard & clump
            if (impureDustStack.isEmpty()) {
                impureDustStack = dustStack;
            }

            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                .inputs(oreStack)
                .outputs(impureDustStack)
                .duration(10)
                .EUt(16)
                .buildAndRegister();

            RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                .inputs(oreStack)
                .outputs(impureDustStack)
                .duration(100)
                .EUt(24)
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, byproductMaterial), 1000);

            RecipeMap.ORE_WASHER_RECIPES.recipeBuilder()
                .inputs(oreStack)
                .fluidInputs(ModHandler.getWater(1000))
                .outputs(OreDictUnifier.get(crushedPrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, material),
                    OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial),
                    OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                .buildAndRegister();

            RecipeMap.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(oreStack)
                .duration((int) material.getMass() * 20)
                .outputs(OreDictUnifier.get(crushedPrefix == OrePrefix.crushed ? OrePrefix.crushedCentrifuged : OrePrefix.dust, material),
                    OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial),
                    OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                .buildAndRegister();

            if (material.washedIn != null) {
                DustMaterial washingByproduct = GTUtility.selectItemInList(3, material, material.oreByProducts, DustMaterial.class);

                RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .inputs(oreStack)
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
    private void processingCrushedCentrifuged(OrePrefix centrifugedPrefix, Material material){
        if (material instanceof SolidMaterial) {
            ItemStack stack = OreDictUnifier.get(centrifugedPrefix, material);
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, solidMaterial);
            ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dust, GTUtility.selectItemInList(2,
                solidMaterial, solidMaterial.oreByProducts, DustMaterial.class), 1);

            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(dustStack)
                .duration(10)
                .EUt(16)
                .buildAndRegister();

            RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(dustStack)
                .chancedOutput(byproductStack, 1000)
                .buildAndRegister();
        }
    }
    private void processingCrushedPurified(OrePrefix purifiedPrefix, Material material) {
        if (material instanceof SolidMaterial) {
            ItemStack crushedPurifiedStack = OreDictUnifier.get(purifiedPrefix, material);
            SolidMaterial solidMaterial = (SolidMaterial) material;
            ItemStack crushedCentrifugedStack = OreDictUnifier.get(OrePrefix.crushedCentrifuged, solidMaterial);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, solidMaterial);
            ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, solidMaterial, solidMaterial.oreByProducts, DustMaterial.class));

            if (!crushedCentrifugedStack.isEmpty()) {
                RecipeMap.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                    .inputs(crushedPurifiedStack)
                    .outputs(crushedCentrifugedStack, byproductStack)
                    .duration((int) (material.getMass() * 20))
                    .EUt(60)
                    .buildAndRegister();
            } else {
                RecipeMap.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                    .inputs(crushedPurifiedStack)
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
                    RecipeMap.SIFTER_RECIPES.recipeBuilder()
                        .inputs(crushedPurifiedStack)
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
                    RecipeMap.SIFTER_RECIPES.recipeBuilder()
                        .inputs(crushedPurifiedStack)
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
    private void processingCrystallizedPurified(OrePrefix crystallizedPrefix, Material material) {
        if (material instanceof SolidMaterial) {
            ItemStack stack = OreDictUnifier.get(crystallizedPrefix, material);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, ((SolidMaterial) material).macerateInto);

            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .outputs(dustStack)
                .duration(10)
                .EUt(10)
                .buildAndRegister();

            RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .outputs(dustStack)
                .duration(20)
                .EUt(16)
                .buildAndRegister();
        }
    }
    private void processingDecomposition(OrePrefix decomposePrefix, Material materialIn) {
        if (materialIn instanceof FluidMaterial) {
            FluidMaterial material = (FluidMaterial) materialIn;
            if (!material.materialComponents.isEmpty() && (
                material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING) ||
                    material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_CENTRIFUGING))) {

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
                    builder = RecipeMap.ELECTROLYZER_RECIPES.recipeBuilder()
                        .duration((int) material.getProtons() * totalInputAmount * 8)
                        .EUt(Math.min(4, material.materialComponents.size()) * 30);
                } else {
                    builder = RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
                        .duration((int) material.getMass() * totalInputAmount * 2)
                        .EUt(30);
                }
                builder.outputs(outputs);
                builder.fluidOutputs(fluidOutputs);

                //finish builder
                if (decomposePrefix == OrePrefix.dust) {
                    builder.inputs(OreDictUnifier.get(decomposePrefix, material));
                } else {
                    builder.fluidInputs(material.getFluid(GTValues.L * totalInputAmount));
                }

                //register recipe
                builder.buildAndRegister();
            }

        }
    }
    private void processingDirtyDust(OrePrefix dustPrefix, Material materialIn) {
        if(materialIn instanceof DustMaterial) {
            DustMaterial material = (DustMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(dustPrefix, materialIn);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

            if(dustPrefix == OrePrefix.dustPure && material.separatedOnto != null) {
                ItemStack separatedStack = OreDictUnifier.get(OrePrefix.dustSmall, material.separatedOnto);
                RecipeMap.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(dustStack)
                    .chancedOutput(separatedStack, 4000)
                    .duration((int) material.separatedOnto.getMass())
                    .EUt(24)
                    .buildAndRegister();
            }

            int byProductIndex;
            if(dustPrefix == OrePrefix.dustRefined) {
                byProductIndex = 2;
            } else if(dustPrefix == OrePrefix.dustPure) {
                byProductIndex = 1;
            } else byProductIndex = 0;
            FluidMaterial byproduct = GTUtility.selectItemInList(byProductIndex, material, material.oreByProducts, FluidMaterial.class);

            RecipeBuilder builder = RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(dustStack)
                .duration((int) (material.getMass() * 4))
                .EUt(24);

            if(byproduct instanceof DustMaterial) {
                builder.outputs(OreDictUnifier.get(OrePrefix.dustTiny, byproduct));
            } else {
                builder.fluidOutputs(byproduct.getFluid(GTValues.L / 9));
            }

            builder.buildAndRegister();
        }
    }
    private void processingGear(OrePrefix gearPrefix, Material materialIn){
        if(materialIn instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(gearPrefix, materialIn);
            boolean isSmall = gearPrefix == OrePrefix.gearSmall;

            if(material.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID)) {
                RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                    .notConsumable(isSmall ? MetaItems.SHAPE_MOLD_GEAR_SMALL : MetaItems.SHAPE_MOLD_GEAR)
                    .fluidInputs(material.getFluid(L * (isSmall ? 1 : 4)))
                    .outputs(stack)
                    .duration(isSmall ? 20 : 100)
                    .EUt(8)
                    .buildAndRegister();
            }

            if(isSmall) {
                if(material instanceof MetalMaterial && !material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    ModHandler.addShapedRecipe("sgear_" + material,
                        stack,
                        "h##",
                        "#P#",
                        'P', OreDictUnifier.get(OrePrefix.plate, material));
                }
            } else {
                ModHandler.addShapedRecipe("gear_" + material,
                    stack,
                    "RPR",
                    "PdP",
                    "RPR",
                    'P', OreDictUnifier.get(OrePrefix.plate, material),
                    'R', OreDictUnifier.get(OrePrefix.stick, material));
            }

        }
    }
    private void processingGem(OrePrefix gemPrefix, Material materialIn){
        if(materialIn instanceof GemMaterial) {
            GemMaterial material = (GemMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(gemPrefix, materialIn);
            long materialAmount = gemPrefix.materialAmount;
            ItemStack crushedStack = OreDictUnifier.getDust(material, materialAmount);

            if (material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE)) {
                ModHandler.addShapelessRecipe("gem_t_dust_" + material, crushedStack, "m", stack);
            }

            if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                OrePrefix prevPrefix = GTUtility.getItem(ORDER, ORDER.indexOf(gemPrefix) - 1, null);
                if(prevPrefix != null) {
                    ItemStack prevStack = OreDictUnifier.get(prevPrefix, material, 2);
                    ModHandler.addShapelessRecipe("gem_t_gem_" + material, prevStack, "h", stack);
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .outputs(prevStack)
                        .duration(20)
                        .EUt(16)
                        .buildAndRegister();
                }
            }

            if(!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                if (material.hasFlag(SolidMaterial.MatFlags.GENERATE_LONG_ROD) && materialAmount >= M * 2) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .outputs(OreDictUnifier.get(OrePrefix.stickLong, material, (int) (materialAmount / (M * 2))),
                            OreDictUnifier.getDust(material, materialAmount % (M * 2)))
                        .duration((int) material.getMass())
                        .EUt(16)
                        .buildAndRegister();
                } else if (materialAmount >= M) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .outputs(OreDictUnifier.get(OrePrefix.stick, material, (int) (materialAmount / M)),
                            OreDictUnifier.getDust(material, materialAmount % M))
                        .duration((int) material.getMass())
                        .EUt(16)
                        .buildAndRegister();
                }
            }
        }
    }
    private void processingLens(OrePrefix lensPrefix, Material material){
        if(material instanceof GemMaterial) {
            ItemStack stack = OreDictUnifier.get(lensPrefix, material);

            RecipeMap.LATHE_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.plate, material))
                .outputs(stack, OreDictUnifier.get(OrePrefix.dustSmall, material))
                .duration((int) (material.getMass() / 2L))
                .EUt(16)
                .buildAndRegister();

            EnumDyeColor dyeColor = GTUtility.determineDyeColor(material.materialRGB);
            MarkerMaterial colorMaterial = MarkerMaterials.Color.COLORS.get(dyeColor);
            OreDictUnifier.registerOre(stack, OrePrefix.craftingLens, colorMaterial);
        }
    }
    private void processingLog(OrePrefix logPrefix, Material material){
        ItemStack stack = OreDictUnifier.get(logPrefix, material);

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(stack)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 6))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 8000)
            .buildAndRegister();

        ModHandler.addShapedRecipe("stick_long_" + material,
            OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 2),
            "sLf",
            'L', OreDictUnifier.get(logPrefix, material));

        RecipeMap.LATHE_RECIPES.recipeBuilder()
            .inputs(stack)
            .outputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 4), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
            .duration(160)
            .EUt(8)
            .buildAndRegister();

        ItemStack smeltingOutput = ModHandler.getSmeltingOutput(stack);
        if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1) {
            int coalAmount = smeltingOutput.getCount();

            RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(16, stack))
                .circuitMeta(0)
                .outputs(new ItemStack(Items.COAL, 20 * coalAmount, 1))
                .fluidOutputs(Materials.Creosote.getFluid(5000 * coalAmount))
                .duration(440)
                .EUt(64)
                .buildAndRegister();
            RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(16, stack))
                .circuitMeta(1)
                .fluidInputs(Materials.Nitrogen.getFluid(400))
                .outputs(new ItemStack(Items.COAL, 20, 1))
                .fluidInputs(Materials.Creosote.getFluid(4000))
                .duration(200)
                .EUt(96)
                .buildAndRegister();
            RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(16, stack))
                .circuitMeta(2)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 5))
                .fluidOutputs(Materials.OilHeavy.getFluid(300))
                .duration(280)
                .EUt(192)
                .buildAndRegister();

        }

        ItemStack output = ModHandler.getRecipeOutput(GTValues.DW, stack);
        if (!output.isEmpty() && OreDictUnifier.getPrefix(output) == OrePrefix.plank) {

            RecipeMap.CUTTER_RECIPES.recipeBuilder()
                .inputs(stack)
                .fluidInputs(Materials.Lubricant.getFluid(1))
                .outputs(GTUtility.copyAmount(output.getCount() * 2, stack),
                    OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
                .duration(200)
                .EUt(8)
                .buildAndRegister();

            RecipeMap.CUTTER_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(output,
                    OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
                .duration(200)
                .EUt(8)
                .buildAndRegister();

            ModHandler.removeRecipes(output);
            ModHandler.addShapedRecipe("log_t_wood_" + material ,
                GTUtility.copyAmount(output.getCount(), output),
                "s##",
                "L##",
                'L', stack);
        }
    }
    private void processingPlank(OrePrefix plankPrefix, Material material){
        ItemStack stack = OreDictUnifier.get(plankPrefix, material);

        if (material == Materials.Wood) {
            RecipeMap.LATHE_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2))
                .duration(10)
                .EUt(8)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(8, stack), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1))
                .outputs(new ItemStack(Blocks.NOTEBLOCK, 1))
                .duration(200)
                .EUt(4)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(8, stack), OreDictUnifier.get(OrePrefix.gem, Materials.Diamond, 1))
                .outputs(new ItemStack(Blocks.JUKEBOX, 1))
                .duration(400)
                .EUt(4)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(6, stack), new ItemStack(Items.BOOK, 3))
                .outputs(new ItemStack(Blocks.BOOKSHELF, 1))
                .duration(400)
                .EUt(4)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .circuitMeta(1)
                .outputs(new ItemStack(Blocks.WOODEN_BUTTON, 1))
                .duration(100)
                .EUt(4)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .circuitMeta(2)
                .outputs(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE))
                .duration(200)
                .EUt(4)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(3, stack))
                .circuitMeta(3)
                .outputs(new ItemStack(Blocks.TRAPDOOR))
                .duration(300)
                .EUt(4)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(4, stack))
                .circuitMeta(4)
                .outputs(new ItemStack(Blocks.CRAFTING_TABLE))
                .duration(400)
                .EUt(4)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(6, stack))
                .circuitMeta(6)
                .outputs(new ItemStack(Items.OAK_DOOR))
                .duration(600)
                .EUt(4)
                .buildAndRegister();

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(8, stack))
                .circuitMeta(8)
                .outputs(new ItemStack(Blocks.CHEST, 1))
                .duration(800)
                .EUt(4)
                .buildAndRegister();

            if (stack.getItemDamage() == W) {
                for (byte i = 0; i < 64; i++) { // FIXME
                    ItemStack itemStack = stack.copy();
                    itemStack.setItemDamage(i);

                    ItemStack output = ModHandler.getRecipeOutput(null, itemStack, itemStack, itemStack);
                    if (!output.isEmpty() && output.getCount() >= 3) {

                        RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, itemStack))
                            .outputs(GTUtility.copyAmount(output.getCount() / 3, output))
                            .duration(25)
                            .EUt(4)
                            .buildAndRegister();

//                        ModHandler.removeRecipe(itemStack, itemStack, itemStack);
                        ModHandler.addShapedRecipe("slab?_" + material,
                            GTUtility.copyAmount(output.getCount() / 3, output),
                            "sP",
                            'P', itemStack);
                    }
                    if(itemStack.isEmpty() && i >= 16) break;
                }
            } else {
                ItemStack output = ModHandler.getRecipeOutput(null, stack, stack, stack);
                if (!output.isEmpty() && output.getCount() >= 3) {

                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(GTUtility.copyAmount(output.getCount() / 3, output))
                        .duration(25)
                        .EUt(4)
                        .buildAndRegister();

//                    ModHandler.removeRecipe(stack, stack, stack);
                    ModHandler.addShapedRecipe("slab?_" + material,
                        GTUtility.copyAmount(output.getCount() / 3, output),
                        "sP",
                        'P', stack);
                }
            }
        }
    }
    private void processingPlate(OrePrefix platePrefix, Material material){ //for plate and plateDense
        ItemStack stack = OreDictUnifier.get(platePrefix, material);
        boolean noSmashing = material.hasFlag(NO_SMASHING);
        long materialMass = material.getMass();

        switch (platePrefix) {
            case plate:
                if (material instanceof FluidMaterial) {
                    RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                        .fluidInputs(((FluidMaterial) material).getFluid(L))
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                        .duration(32)
                        .EUt(8)
                        .buildAndRegister();
                }

                RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(2, stack))
                    .explosivesAmount(2)
                    .outputs(OreDictUnifier.get(OrePrefix.compressed, material), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh))
                    .buildAndRegister();

                if (material == Materials.Paper)
                    ModHandler.addShapedRecipe("papar_f_scane_" + material,
                        GTUtility.copyAmount(3, stack),
                        "XXX",
                        'X', new ItemStack(Items.REEDS, 1, W));

                if (!noSmashing) {
                    ModHandler.addShapedRecipe("ingot_t_plate_" + material,
                        OreDictUnifier.get(OrePrefix.plate, material),
                        "h",
                        "X",
                        "X",
                        'X', new UnificationEntry(OrePrefix.ingot, material));

                    ModHandler.addShapedRecipe("ingot_t_plate_od_" + material,
                        OreDictUnifier.get(OrePrefix.plate, material),
                        "H",
                        "X",
                        'H', ToolDictNames.craftingToolForgeHammer,
                        'X', new UnificationEntry(OrePrefix.ingot, material));

                    ModHandler.addShapedRecipe("gem_t_plate_" + material,
                        OreDictUnifier.get(OrePrefix.plate, material),
                        "h",
                        "X",
                        'X', new UnificationEntry(OrePrefix.gem, material));

                    ModHandler.addShapedRecipe("gem_t_plate_od_" + material,
                        OreDictUnifier.get(OrePrefix.plate, material),
                        "H",
                        "X",
                        'H', ToolDictNames.craftingToolForgeHammer,
                        'X', new UnificationEntry(OrePrefix.gem, material));
                }

                if (material.hasFlag(MORTAR_GRINDABLE))
                    ModHandler.addShapedRecipe("plate_t_dust_" + material,
                        OreDictUnifier.get(OrePrefix.dust, material),
                        "X",
                        "m",
                        'X', new UnificationEntry(OrePrefix.plate, material));

                break;
            case plateDense:
                if (!noSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.plate, material, 9))
                        .outputs(GTUtility.copyAmount(1, stack))
                        .duration((int) Math.max(materialMass * 9L, 1L))
                        .EUt(96)
                        .buildAndRegister();

                }
                break;
        }
    }
    private void processingPolarizing(OrePrefix polarizingPrefix, Material materialIn){
        if(materialIn instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(polarizingPrefix, materialIn);

            if(material.magneticMaterial != null) {
                ItemStack magneticStack = OreDictUnifier.get(polarizingPrefix, material.magneticMaterial);

                RecipeMap.POLARIZER_RECIPES.recipeBuilder() //polarizing
                    .inputs(stack)
                    .outputs(magneticStack)
                    .duration(16)
                    .EUt(16)
                    .buildAndRegister();

                ModHandler.addSmeltingRecipe(magneticStack, stack); //de-magnetizing
            }
        }
    }
    private void processingPure(OrePrefix purePrefix, Material materialIn) {
        if (materialIn instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(purePrefix, materialIn);
            DustMaterial byproductMaterial = GTUtility.selectItemInList(1, material, material.oreByProducts, DustMaterial.class);
            ItemStack pureDustStack = OreDictUnifier.get(OrePrefix.dustPure, material);

            if (pureDustStack.isEmpty()) { //fallback for reduced & cleanGravel
                pureDustStack = OreDictUnifier.get(OrePrefix.dust, material);
            }

            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(OreDictUnifier.get(OrePrefix.dustPure, material))
                .duration(10)
                .EUt(16)
                .buildAndRegister();

            RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(pureDustStack)
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, byproductMaterial), 1000)
                .buildAndRegister();
        }
    }
    private void processingRotor(OrePrefix rotorPrefix, Material materialIn){
        if (materialIn instanceof SolidMaterial && !materialIn.hasFlag(NO_UNIFICATION | NO_WORKING)) {
            SolidMaterial material = (SolidMaterial) materialIn;
            ItemStack stack = OreDictUnifier.get(rotorPrefix, materialIn);
            ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
            ItemStack ringStack = OreDictUnifier.get(OrePrefix.ring, material);

            ModHandler.addShapedRecipe("rotor_" + materialIn, stack,
                "PhP",
                "SRf",
                "PdP",
                'P', plateStack,
                'R', ringStack,
                'S', OreDictUnifier.get(OrePrefix.screw, material));

//            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
//                .inputs(GTUtility.copyAmount(4, plateStack), ringStack)
//                .outputs(stack)
//                .fluidInputs(Materials.SolderingAlloy.getFluid(32)) // TODO MATERIAL FLUIDS
//                .duration(240)
//                .EUt(24)
//                .buildAndRegister();
        }
    }
    private void processingShaping(OrePrefix shapingPrefix, Material material){
        ItemStack stack = OreDictUnifier.get(shapingPrefix, material);
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
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(9, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_BLOCK)
                    .outputs(OreDictUnifier.get(OrePrefix.block, smeltInto, amount))
                    .duration(10 * amount)
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();

                RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(9, stack))
                    .notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                    .outputs(OreDictUnifier.get(OrePrefix.block, smeltInto, amount))
                    .duration(5 * amount)
                    .EUt(4 * voltageMultiplier)
                    .buildAndRegister();
            }

            if (material != smeltInto) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_INGOT)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, smeltInto, amount))
                    .duration(10)
                    .EUt(4 * voltageMultiplier)
                    .buildAndRegister();
            }

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
                .outputs(OreDictUnifier.get(OrePrefix.plate, smeltInto, amount))
                .duration((int) Math.max(materialMass * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            if (amount * 2 <= 64) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                    .outputs(OreDictUnifier.get(OrePrefix.stick, smeltInto, amount * 2))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 2 <= 64) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_WIRE)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt01, smeltInto, amount * 2))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 8 <= 64) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_BOLT)
                    .outputs(OreDictUnifier.get(OrePrefix.bolt, smeltInto, amount * 8))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(8 * voltageMultiplier)
                    .buildAndRegister();
            }
            if (amount * 4 <= 64) {
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_RING)
                    .outputs(OreDictUnifier.get(OrePrefix.ring, smeltInto, amount * 4))
                    .duration((int) Math.max(materialMass * 2L * amount, amount))
                    .EUt(6 * voltageMultiplier)
                    .buildAndRegister();

                if (!material.hasFlag(NO_SMASHING)) {
                    ModHandler.addShapedRecipe("ring_" + material,
                        OreDictUnifier.get(OrePrefix.ring, material),
                        "h ",
                        " X",
                        'X', new UnificationEntry(OrePrefix.stick, material));
                }
            }
            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_SWORD)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadSword, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(3, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_PICKAXE)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, smeltInto, amount))
                .duration((int) Math.max(materialMass * 3L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_SHOVEL)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadShovel, smeltInto, amount))
                .duration((int) Math.max(materialMass * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(3, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_AXE)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadAxe, smeltInto, amount))
                .duration((int) Math.max(materialMass * 3L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_HOE)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadHoe, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(6, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_HAMMER)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadHammer, smeltInto, amount))
                .duration((int) Math.max(materialMass * 6L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_FILE)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadFile, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_SAW)
                .outputs(OreDictUnifier.get(OrePrefix.toolHeadSaw, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(4, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_GEAR)
                .outputs(OreDictUnifier.get(OrePrefix.gear, smeltInto, amount))
                .duration((int) Math.max(materialMass * 5L * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(2, stack))
                .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                .outputs(OreDictUnifier.get(OrePrefix.plate, smeltInto, amount))
                .duration((int) Math.max(materialMass * 2L * amount, amount))
                .EUt(2 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(8, stack))
                .notConsumable(MetaItems.SHAPE_MOLD_GEAR)
                .outputs(OreDictUnifier.get(OrePrefix.gear, smeltInto, amount))
                .duration((int) Math.max(materialMass * 10L * amount, amount))
                .EUt(2 * voltageMultiplier)
                .buildAndRegister();

            if (material == Materials.Steel) {
//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.steel, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.steel, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (material == Materials.Iron || material == Materials.WroughtIron) {

//                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                    .inputs(GTUtility.copyAmount(1, stack))
//                    .notConsumable(MetaItems.SHAPE_EXTRUDER_CELL)
//                    .outputs(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, amount))
//                    .duration(amount * 128)
//                    .EUt(32)
//                    .buildAndRegister();
//
//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.iron, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.iron, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }
                if (amount * 31 <= 64) {
                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(31, stack))
                        .notConsumable(MetaItems.SHAPE_MOLD_ANVIL)
                        .outputs(new ItemStack(Blocks.ANVIL, 1, 0))
                        .duration(amount * 512)
                        .EUt(4 * voltageMultiplier)
                        .buildAndRegister();
                }

            } else if (material == Materials.Tin) {

//                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                    .inputs(GTUtility.copyAmount(2, stack))
//                    .notConsumable(MetaItems.SHAPE_EXTRUDER_CELL)
//                    .outputs(ModHandler.IC2.getIC2Item(ItemName.fluid_cell, amount))
//                    .duration(amount * 128)
//                    .EUt(32)
//                    .buildAndRegister();
//
//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.tin, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.tin, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (material == Materials.Lead) {

//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.lead, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.lead, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (material == Materials.Copper || material == Materials.AnnealedCopper) {

//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.copper, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.copper, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (material == Materials.Bronze) {

//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.bronze, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.bronze, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }

            } else if (material == Materials.Gold) {

//                if (amount * 2 <= 64) {
//                    RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(1, stack))
//                        .notConsumable(MetaItems.SHAPE_EXTRUDER_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.gold, amount * 2))
//                        .duration(amount * 32)
//                        .EUt(3 * voltageMultiplier)
//                        .buildAndRegister();
//                }
//                if (amount * 2 <= 64) {
//                    RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
//                        .inputs(GTUtility.copyAmount(2, stack))
//                        .notConsumable(MetaItems.SHAPE_MOLD_CASING)
//                        .outputs(ModHandler.IC2.getIC2Item(ItemName.casing, CasingResourceType.gold, amount * 3))
//                        .duration(amount * 128)
//                        .EUt(voltageMultiplier)
//                        .buildAndRegister();
//                }
            }
        } else if (material == Materials.Glass) {

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
                .outputs(OreDictUnifier.get(OrePrefix.plate, material, amount))
                .duration((int) Math.max(materialMass * amount, amount))
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();

            RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_EXTRUDER_BOTTLE)
                .outputs(new ItemStack(Items.GLASS_BOTTLE, 1))
                .duration(amount * 32)
                .EUt(16)
                .buildAndRegister();

            RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .notConsumable(MetaItems.SHAPE_MOLD_BOTTLE)
                .outputs(new ItemStack(Items.GLASS_BOTTLE, 1))
                .duration(amount * 64)
                .EUt(4)
                .buildAndRegister();
        }
    }
    private void processingStick(OrePrefix stickPrefix, Material material){
        ItemStack stack = OreDictUnifier.get(stickPrefix, material);

        switch (stickPrefix) {
            case stick:
                if (!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {

                    if (material instanceof SolidMaterial) {
                        RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)
                                ? OreDictUnifier.get(OrePrefix.gem, material)
                                : OreDictUnifier.get(OrePrefix.ingot, material))
                            .outputs(OreDictUnifier.get(OrePrefix.stick, material), OreDictUnifier.get(OrePrefix.dustSmall, ((SolidMaterial) material).macerateInto, 2))
                            .duration((int) Math.max(material.getMass() * 5L, 1L))
                            .EUt(16)
                            .buildAndRegister();
                    }

                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.bolt, material, 4))
                        .duration((int) Math.max(material.getMass() * 2L, 1L))
                        .EUt(4)
                        .buildAndRegister();

                    ModHandler.addShapedRecipe("stick_fls_" + material,
                        OreDictUnifier.get(OrePrefix.stick, material, 2),
                        "s",
                        "X",
                        'X', new UnificationEntry(OrePrefix.stickLong, material));

                    ModHandler.addShapedRecipe("stick_" + material,
                        OreDictUnifier.get(OrePrefix.stick, material, 1),
                        "f ",
                        " X",
                        'X', new UnificationEntry(OrePrefix.ingot, material));

                }
                if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(2, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.stickLong, material))
                        .duration((int) Math.max(material.getMass(), 1L))
                        .EUt(16)
                        .buildAndRegister();
                }
                break;
            case stickLong:
                if (!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.stick, material, 2))
                        .duration((int) Math.max(material.getMass(), 1L))
                        .EUt(4)
                        .buildAndRegister();

                    ModHandler.addShapedRecipe("lstick_ffg_" + material,
                        OreDictUnifier.get(OrePrefix.stickLong, material, 1),
                        "sf",
                        "G ",
                        'G', new UnificationEntry(OrePrefix.gemFlawless, material));

                    ModHandler.addShapedRecipe("lstick_feg_" + material,
                        OreDictUnifier.get(OrePrefix.stickLong, material, 2),
                        "sf",
                        "G ",
                        'G', new UnificationEntry(OrePrefix.gemExquisite, material));
                }
                if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.spring, material))
                        .duration(200)
                        .EUt(16)
                        .buildAndRegister();

                    ModHandler.addShapedRecipe("lstick_fs_",
                        OreDictUnifier.get(OrePrefix.stickLong, material),
                        "ShS",
                        'S', new UnificationEntry(OrePrefix.stick, material));
                }
                break;
        }
    }

    private void processingStone(OrePrefix stonePrefix, Material material){
        ItemStack stack = OreDictUnifier.get(stonePrefix, material);

        switch (stonePrefix) {
            case stoneCobble:
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.stick, Materials.Wood))
                    .outputs(new ItemStack(Blocks.LEVER, 1))
                    .duration(400)
                    .EUt(1)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(8, stack))
                    .circuitMeta(8)
                    .outputs(new ItemStack(Blocks.FURNACE, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(7, stack), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone))
                    .outputs(new ItemStack(Blocks.DROPPER, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(7, stack), new ItemStack(Items.BOW, 1, 0)).fluidInputs(Materials.Redstone.getFluid(144))
                    .outputs(new ItemStack(Blocks.DISPENSER, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
                break;
            case stoneSmooth:
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .circuitMeta(1)
                    .outputs(new ItemStack(Blocks.STONE_BUTTON, 1))
                    .duration(100)
                    .EUt(4)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(2, stack))
                    .circuitMeta(2)
                    .outputs(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1))
                    .duration(200)
                    .EUt(4)
                    .buildAndRegister();
                break;
            case stone:

                if (material == null) {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(3, stack), new ItemStack(Blocks.REDSTONE_TORCH, 2))
                        .fluidInputs(Materials.Redstone.getFluid(144))
                        .outputs(new ItemStack(Items.REPEATER, 1))
                        .duration(100)
                        .EUt(4)
                        .buildAndRegister();
                }  else if (material == Materials.Endstone) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Endstone, 1))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Tungsten, 1), 500)
                        .buildAndRegister();
                } else if (material == Materials.Netherrack) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Netherrack, 1))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold, 1), 500)
                        .buildAndRegister();
                } else if (material == Materials.Obsidian) {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone), GTUtility.copyAmount(5, stack))
                        .fluidInputs(Materials.Glass.getFluid(L / 2))
                        .outputs(ModHandler.getModItem("Forestry", "thermionicTubes", 4, 6))
                        .duration(64)
                        .EUt(32)
                        .buildAndRegister();

                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.gem, Materials.NetherStar), GTUtility.copyAmount(3, stack))
                        .fluidInputs(Materials.Glass.getFluid(720)).outputs(new ItemStack(Blocks.BEACON, 1, 0))
                        .duration(32)
                        .EUt(16)
                        .buildAndRegister();

                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(8, stack), OreDictUnifier.get(OrePrefix.gem, Materials.EnderEye))
                        .outputs(new ItemStack(Blocks.ENDER_CHEST, 1))
                        .duration(400)
                        .EUt(4)
                        .buildAndRegister();

                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.plate,material, 1))
                        .duration(200)
                        .EUt(32)
                        .buildAndRegister();

                } else if (material == Materials.Concrete) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dust, material))
                        .buildAndRegister();
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material, 1))
                        .duration(200)
                        .EUt(32)
                        .buildAndRegister();
                } else if (material == Materials.Soapstone) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Talc, 1))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Chromite, 1), 1000)
                        .buildAndRegister();
                } else if (material == Materials.Redrock
                    || material == Materials.Marble
                    || material == Materials.Basalt
                    || material == Materials.Quartzite) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, material, 1))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dust, material, 1), 1000)
                        .buildAndRegister();
                } else if (material == Materials.Flint) {
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, material, 2))
                        .chancedOutput(new ItemStack(Items.FLINT, 1), 5000)
                        .buildAndRegister();
                } else if (material == Materials.GraniteBlack) {
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material, 1))
                        .duration(200)
                        .EUt(32)
                        .buildAndRegister();
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, material, 1))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Thorium, 1), 100)
                        .buildAndRegister();
                } else if (material == Materials.GraniteRed) {
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material, 1))
                        .duration(200)
                        .EUt(32)
                        .buildAndRegister();
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, material, 1))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Uranium, 1), 100)
                        .buildAndRegister();
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material, 1))
                        .duration(200)
                        .EUt(32)
                        .buildAndRegister();
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, material, 1))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Stone, 1), 100)
                        .buildAndRegister();
                } else if (material == Materials.Andesite || material == Materials.Diorite) {
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material, 1))
                        .duration(200)
                        .EUt(32)
                        .buildAndRegister();
                    RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.dustImpure, material, 1))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Stone, 1), 100)
                        .buildAndRegister();
                }
                break;
        }
    }

    private void processingToolHead(OrePrefix toolPrefix, Material material){
        boolean smashing = !material.hasFlag(NO_SMASHING);
        boolean working = !material.hasFlag(DustMaterial.MatFlags.NO_WORKING);

        if (material instanceof SolidMaterial) {
            SolidMaterial solidMaterial = (SolidMaterial) material;
            switch (toolPrefix) {
                case toolHeadAxe:
                    ModHandler.addShapelessRecipe("axe_" + solidMaterial + "_" + solidMaterial.handleMaterial,
                        MetaItems.AXE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe("head_axe_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadAxe, solidMaterial),
                            "PIh",
                            "P  ",
                            "f  ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe("head_axe_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadAxe, solidMaterial),
                            "GG ",
                            "G  ",
                            "f  ",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadBuzzSaw:
                    ModHandler.addShapedRecipe("bsaw_lith_" + solidMaterial,
                        MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                        "PBM",
                        "dXG",
                        "SGP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe("bsaw_cad_" + solidMaterial,
                        MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                        "PBM",
                        "dXG",
                        "SGP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe("bsaw_sod_" + solidMaterial,
                        MetaItems.BUZZSAW.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                        "PBM",
                        "dXG",
                        "SGP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

                    if (working)
                        ModHandler.addShapedRecipe("bsaw_head_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadBuzzSaw, solidMaterial),
                            "wXh",
                            "X X",
                            "fXx",
                            'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial));
                    break;
                case toolHeadChainsaw:
                    ModHandler.addShapedRecipe("chsaw_lv_lith_" + solidMaterial,
                        MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe("chsaw_mv_tit_lith_" + solidMaterial,
                        MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe("chsaw_hv_lith_" + solidMaterial,
                        MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe("chsaw_lv_cad_" + solidMaterial,
                        MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe("chsaw_mv_cad_" + solidMaterial,
                        MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe("chsaw_hv_cad_" + solidMaterial,
                        MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1200000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe("chsaw_lv_sod_" + solidMaterial,
                        MetaItems.CHAINSAW_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe("chsaw_mv_sod_" + solidMaterial,
                        MetaItems.CHAINSAW_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{200000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe("chsaw_hv_sod_" + solidMaterial,
                        MetaItems.CHAINSAW_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{800000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());

                    if (working)
                        ModHandler.addShapedRecipe("chsaw_head_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadChainsaw, solidMaterial),
                            "SRS",
                            "XhX",
                            "SRS",
                            'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel),
                            'R', OreDictUnifier.get(OrePrefix.ring, Materials.Steel));
                    break;
                case toolHeadDrill:
                    ModHandler.addShapedRecipe("drill_lv_lith_" + solidMaterial,
                        MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe("drill_lv_cad_" + solidMaterial,
                        MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe("drill_lv_sod_" + solidMaterial,
                        MetaItems.DRILL_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{50000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe("dill_mv_lith_" + solidMaterial,
                        MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe("drill_mv_cad_" + solidMaterial,
                        MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe("drill_mv_sod_" + solidMaterial,
                        MetaItems.DRILL_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{200000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe("drool_hv_lith_" + solidMaterial,
                        MetaItems.DRILL_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe("drill_hv_cad_" + solidMaterial,
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
                    ModHandler.addShapedRecipe("drill_hv_sod_" + solidMaterial,
                        MetaItems.DRILL_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{800000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe("jhammer_lith_" + solidMaterial,
                        MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{1600000L, 512L, 3L, -1L}),
                        "SXd",
                        "PRP",
                        "MPB",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());

                    ModHandler.addShapedRecipe("jhammer_cad_" + solidMaterial,
                        MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{1200000L, 512L, 3L, -1L}),
                        "SXd",
                        "PRP",
                        "MPB",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());

                    ModHandler.addShapedRecipe("jhammer_sod_" + solidMaterial,
                        MetaItems.JACKHAMMER.getStackForm(solidMaterial, Materials.Titanium), // new long[]{800000L, 512L, 3L, -1L}),
                        "SXd",
                        "PRP",
                        "MPB",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_PISTON_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'R', OreDictUnifier.get(OrePrefix.spring, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());

                    if (working)
                        ModHandler.addShapedRecipe("drill_head_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadDrill, solidMaterial),
                            "XSX",
                            "XSX",
                            "ShS",
                            'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel));
                    break;
                case toolHeadFile:
                    ModHandler.addShapelessRecipe("file_fh_" + solidMaterial,
                        MetaItems.FILE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material), OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));
                    if (!smashing) {
                        ModHandler.addMirroredShapedRecipe("file_",
                            MetaItems.FILE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                            "P",
                            "P",
                            "S",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));
                    }
                    break;
                case toolHeadHoe:
                    ModHandler.addShapelessRecipe("hoe_fh_" + solidMaterial,
                        MetaItems.HOE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                         OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));
                    if (smashing)
                        ModHandler.addShapedRecipe("hoe_head_m_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadHoe, solidMaterial),
                            "PIh",
                            "f  ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe("hoe_head_g_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadHoe, solidMaterial),
                            "GG",
                            "f ",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadPickaxe:
                    ModHandler.addShapelessRecipe("paxe_" + solidMaterial,
                        MetaItems.PICKAXE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe("paxe_head_m_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadPickaxe, solidMaterial),
                            "PII",
                            "f h",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe("paze_head_g_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadPickaxe, solidMaterial),
                            "GGG",
                            "f  ",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadPlow:
                    ModHandler.addShapelessRecipe("plow_" + solidMaterial,
                        MetaItems.PLOW.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe("plow_head_m_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadPlow, solidMaterial),
                            "PP",
                            "PP",
                            "hf",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe("plow_head_g_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadPlow, solidMaterial),
                            "GG",
                            "GG",
                            " f",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadSaw:
                    ModHandler.addShapelessRecipe("saw_" + solidMaterial,
                        MetaItems.SAW.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe("saw_head_m_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial),
                            "PP ",
                            "fh ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe("saw_head_g_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial),
                            "GGf",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadSense:
                    ModHandler.addShapelessRecipe("sense_" + solidMaterial,
                        MetaItems.SENSE.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe("sense_head_m_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadSense, solidMaterial),
                            "PPI",
                            "hf ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe("sense_head_g_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadSense, solidMaterial),
                            "GGG",
                            " f ",
                            "   ",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadShovel:
                    ModHandler.addShapelessRecipe("shovel_" + solidMaterial,
                        MetaItems.SHOVEL.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe("shovel_head_m_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial),
                            "fPh",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe("shovel_head_g_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial),
                            "fG",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadSword:
                    ModHandler.addShapelessRecipe("sword_" + solidMaterial,
                        MetaItems.SWORD.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing)
                        ModHandler.addShapedRecipe("sword_head_m_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadSword, solidMaterial),
                            " P ",
                            "fPh",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));
                    if (working)
                        ModHandler.addShapedRecipe("sword_head_g_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadSword, solidMaterial),
                            " G",
                            "fG",
                            'G', OreDictUnifier.get(OrePrefix.gem, solidMaterial));
                    break;
                case toolHeadUniversalSpade:
                    ModHandler.addShapelessRecipe("spade_" + solidMaterial,
                        MetaItems.UNIVERSALSPADE.getStackForm(solidMaterial, solidMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial),
                        new UnificationEntry(OrePrefix.screw, solidMaterial), ToolDictNames.craftingToolScrewdriver);

                    if (working)
                        ModHandler.addShapedRecipe("spade_head_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, solidMaterial),
                            "fX",
                            'X', OreDictUnifier.get(OrePrefix.toolHeadShovel, solidMaterial));
                    break;
                case toolHeadWrench:
                    ModHandler.addShapedRecipe("wrench_lv_lith_" + solidMaterial,
                        MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{100000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
                    ModHandler.addShapedRecipe("wrench_mv_lith_" + solidMaterial,
                        MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{400000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_LITHIUM.getStackForm());
                    ModHandler.addShapedRecipe("wrench_hv_lith_" + solidMaterial,
                        MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel), // new long[]{1600000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_LITHIUM.getStackForm());
                    ModHandler.addShapedRecipe("wrench_lv_cad_" + solidMaterial,
                        MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel), // new long[]{75000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe("wrench_mv_cad_" + solidMaterial,
                        MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium), // new long[]{300000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe("wrench_hv_cad_" + solidMaterial,
                        MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel),  //new long[]{1200000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe("wrench_lv_sod_" + solidMaterial,
                        MetaItems.WRENCH_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{50000L, 32L, 1L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());
                    ModHandler.addShapedRecipe("wrench_mv_sod_" + solidMaterial,
                        MetaItems.WRENCH_MV.getStackForm(solidMaterial, Materials.Titanium),  //new long[]{200000L, 128L, 2L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.Titanium),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.Titanium),
                        'B', MetaItems.BATTERY_RE_MV_SODIUM.getStackForm());
                    ModHandler.addShapedRecipe("wrench_hv_sod_" + solidMaterial,
                        MetaItems.WRENCH_HV.getStackForm(solidMaterial, Materials.TungstenSteel),  //new long[]{800000L, 512L, 3L, -1L}),
                        "SXd",
                        "GMG",
                        "PBP",
                        'X', new UnificationEntry(toolPrefix, material),
                        'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.Titanium),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.TungstenSteel),
                        'B', MetaItems.BATTERY_RE_HV_SODIUM.getStackForm());

                    ModHandler.addShapedRecipe("sdriver_lv_lith_" + solidMaterial,
                        MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{100000L, 32L, 1L, -1L}),
                        "PdX",
                        "MGS",
                        "GBP",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
                    ModHandler.addShapedRecipe("sdriver_lv_cad_" + solidMaterial,
                        MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{75000L, 32L, 1L, -1L}),
                        "PdX",
                        "MGS",
                        "GBP",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_CADMIUM.getStackForm());
                    ModHandler.addShapedRecipe("sdriver_lv_sod_" + solidMaterial,
                        MetaItems.SCREWDRIVER_LV.getStackForm(solidMaterial, Materials.StainlessSteel),  //new long[]{50000L, 32L, 1L, -1L}),
                        "PdX",
                        "MGS",
                        "GBP",
                        'X', OreDictUnifier.get(OrePrefix.stickLong, solidMaterial),
                        'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel),
                        'P', OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel),
                        'G', OreDictUnifier.get(OrePrefix.gearSmall, Materials.StainlessSteel),
                        'B', MetaItems.BATTERY_RE_LV_SODIUM.getStackForm());
                    if (working)
                        ModHandler.addShapedRecipe("whench_head_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadWrench, solidMaterial),
                            "hXW",
                            "XRX",
                            "WXd",
                            'X', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.plate, Materials.Steel),
                            'R', OreDictUnifier.get(OrePrefix.ring, Materials.Steel),
                            'W', OreDictUnifier.get(OrePrefix.screw, Materials.Steel));
                    break;
                case toolHeadHammer:
                    ModHandler.addShapelessRecipe("hammer_fh_" + solidMaterial,
                        MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        new UnificationEntry(toolPrefix, material),
                        OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    ModHandler.addShapedRecipe("hammer_m_" + solidMaterial,
                        MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        "XX ",
                        "XXS",
                        "XX ",
                        'X', OreDictUnifier.get(OrePrefix.ingot, solidMaterial),
                        'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    ModHandler.addShapedRecipe("hammer_g_" + solidMaterial,
                        MetaItems.HARDHAMMER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        "XX ",
                        "XXS",
                        "XX ",
                        'X',  OreDictUnifier.get(OrePrefix.gem, solidMaterial),
                        'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial.handleMaterial));

                    if (smashing) {
                        ModHandler.addShapedRecipe("hammer_head_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.toolHeadHammer, solidMaterial),
                            "II ",
                            "IIh",
                            "II ",
                            'P', OreDictUnifier.get(OrePrefix.plate, solidMaterial),
                            'I', OreDictUnifier.get(OrePrefix.ingot, solidMaterial));

                    }
                    break;
                case turbineBlade:
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 4), OreDictUnifier.get(OrePrefix.stickLong, Materials.Magnalium))
                        .outputs(MetaItems.TURBINE_SMALL.getStackForm(solidMaterial, solidMaterial))
                        .duration(160)
                        .EUt(100)
                        .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 8), OreDictUnifier.get(OrePrefix.stickLong, Materials.Titanium))
                        .outputs(MetaItems.TURBINE_NORMAL.getStackForm(solidMaterial, solidMaterial))
                        .duration(320)
                        .EUt(400)
                        .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 12), OreDictUnifier.get(OrePrefix.stickLong, Materials.TungstenSteel))
                        .outputs(MetaItems.TURBINE_LARGE.getStackForm(solidMaterial, solidMaterial))
                        .duration(640)
                        .EUt(1600)
                        .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial, 16), OreDictUnifier.get(OrePrefix.stickLong, Materials.Americium))
                        .outputs(MetaItems.TURBINE_HUGE.getStackForm(solidMaterial, solidMaterial))
                        .duration(1280)
                        .EUt(6400)
                        .buildAndRegister();
                    if (working) {
                        ModHandler.addShapedRecipe("blade_" + solidMaterial,
                            OreDictUnifier.get(OrePrefix.turbineBlade, solidMaterial),
                            "fPd",
                            "SPS",
                            " P ",
                            'P', OreDictUnifier.get(OrePrefix.ingot, solidMaterial),
                            'R', OreDictUnifier.get(OrePrefix.ring, solidMaterial),
                            'S', OreDictUnifier.get(OrePrefix.screw, solidMaterial));
                    }
                    break;
            }
        }
    }

    private void processingToolOther(OrePrefix toolPrefix, Material material){
        if (material != Materials.Stone && material != Materials.Flint) {
            if (material != Materials.Rubber) {
                ModHandler.addShapedRecipe("plunger_" + material + "_" + material,
                    MetaItems.PLUNGER.getStackForm(material, material),
                    "xRR",
                    " SR",
                    "S f",
                    'S', new UnificationEntry(OrePrefix.stick, material),
                    'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));
            }
            if (!ModHandler.isMaterialWood(material)
                && !material.hasFlag(NO_SMASHING)) {

                if (material instanceof SolidMaterial) {
                    SolidMaterial solidMaterial = (SolidMaterial) material;
                    ModHandler.addShapedRecipe("screwdriver_" + solidMaterial + "_" + solidMaterial.handleMaterial,
                        MetaItems.SCREWDRIVER.getStackForm(solidMaterial, solidMaterial.handleMaterial),
                        " fS",
                        " Sh",
                        "W  ",
                        'S', OreDictUnifier.get(OrePrefix.stick, solidMaterial),
                        'W', OreDictUnifier.get(OrePrefix.stick, solidMaterial));
                }
                ModHandler.addShapedRecipe("wrench_" + material + "_" + material,
                    MetaItems.WRENCH.getStackForm(material, material),
                    "IhI",
                    "III",
                    " I ",
                    'I', OreDictUnifier.get(OrePrefix.ingot, material));
                ModHandler.addShapedRecipe("crowbar_" + material + "_" + material,
                    MetaItems.CROWBAR.getStackForm(material, material),
                    "hDS",
                    "DSD",
                    "SDf",
                    'S', OreDictUnifier.get(OrePrefix.stick, material),
                    'D', EnumDyeColor.BLUE);
                ModHandler.addShapedRecipe("wirecutter_" + material + "_" + material,
                    MetaItems.WIRECUTTER.getStackForm(material, material),
                    "PfP",
                    "hPd",
                    "STS",
                    'S', OreDictUnifier.get(OrePrefix.stick, material),
                    'P', OreDictUnifier.get(OrePrefix.plate, material),
                    'T', OreDictUnifier.get(OrePrefix.screw, material));
                ModHandler.addShapedRecipe("scoop_" + material + "_" + material,
                    MetaItems.SCOOP.getStackForm(material, material),
                    "SWS",
                    "SSS",
                    "xSh",
                    'S', OreDictUnifier.get(OrePrefix.stick, material),
                    'W', new ItemStack(Blocks.WOOL, 1, 32767));
                ModHandler.addShapedRecipe("branchcutter_" + material + "_" + material,
                    MetaItems.BRANCHCUTTER.getStackForm(material, material),
                    "PfP",
                    "PdP",
                    "STS",
                    'S', OreDictUnifier.get(OrePrefix.stick, material),
                    'P', OreDictUnifier.get(OrePrefix.plate, material),
                    'T', OreDictUnifier.get(OrePrefix.screw, material));
                ModHandler.addShapedRecipe("knife_" + material + "_" + material,
                    MetaItems.KNIFE.getStackForm(material, material),
                    "fPh", " S ",
                    'S', OreDictUnifier.get(OrePrefix.stick, material),
                    'P', OreDictUnifier.get(OrePrefix.plate, material));
                ModHandler.addShapedRecipe("butchknife_" + material + "_" + material,
                    MetaItems.BUTCHERYKNIFE.getStackForm(material, material),
                    "PPf", "PP ", "Sh ",
                    'S', OreDictUnifier.get(OrePrefix.stick, material),
                    'P', OreDictUnifier.get(OrePrefix.plate, material));
                ModHandler.addShapedRecipe("soldiron_lv_" + material + "_" + Materials.Rubber,
                    MetaItems.SOLDERING_IRON_LV.getStackForm(material, Materials.Rubber/*, long[]{100000L, 32L, 1L, -1L}*/), // TODO electric tools
                    "LBf",
                    "Sd ",
                    "P  ",
                    'B', OreDictUnifier.get(OrePrefix.bolt, material),
                    'P', OreDictUnifier.get(OrePrefix.plate, material),
                    'S', OreDictUnifier.get(OrePrefix.stick, Materials.Iron),
                    'L', MetaItems.BATTERY_RE_LV_LITHIUM.getStackForm());
            }
        }
    }

    private void processinWire(OrePrefix wirePrefix, Material material){
        ItemStack stack = OreDictUnifier.get(wirePrefix, material);
        switch (wirePrefix) {
            case wireGt01:
                if (material == Materials.Cobalt
                    || material == Materials.Lead
                    || material == Materials.Tin
                    || material == Materials.Zinc
                    || material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(material + "_t_cable01", OreDictUnifier.get(OrePrefix.cableGt01, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt01, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt01, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else if (material == Materials.RedAlloy) {
                    ModHandler.addShapelessRecipe(material + "_t_cable01", OreDictUnifier.get(OrePrefix.cableGt01, material),
                        new UnificationEntry(wirePrefix, material),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt01, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt01, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt01, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt01, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.springSmall, material, 2))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.WIREMILL_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.wireFine, material, 4))
                        .duration(200)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.WIREMILL_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.ingot, material))
                        .outputs(GTUtility.copy(GTUtility.copyAmount(2, stack), OreDictUnifier.get(OrePrefix.wireFine, material, 8)))
                        .duration(100)
                        .EUt(4)
                        .buildAndRegister();
                    RecipeMap.WIREMILL_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.stick, material))
                        .outputs(GTUtility.copy(stack, OreDictUnifier.get(OrePrefix.wireFine, material, 4)))
                        .duration(50)
                        .EUt(4)
                        .buildAndRegister();
                }
                if (!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    ModHandler.addShapedRecipe(material + "_t_wire01", OreDictUnifier.get(OrePrefix.wireGt01, material),
                        "Xx",
                        'X', OreDictUnifier.get(OrePrefix.plate, material));
                }
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(2, stack))
                    .circuitMeta(2)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt02, material))
                    .duration(150)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(4, stack))
                    .circuitMeta(4)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt04, material))
                    .duration(200)
                    .EUt(8).buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(8, stack))
                    .circuitMeta(8)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt08, material))
                    .duration(300)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(12, stack))
                    .circuitMeta(12)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt12, material))
                    .duration(400)
                    .EUt(8)
                    .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(16)
                    .outputs(OreDictUnifier.get(OrePrefix.wireGt16, material))
                    .duration(500)
                    .EUt(8)
                    .buildAndRegister();
                break;
            case wireGt02:
                if (material == Materials.Cobalt
                    || material == Materials.Lead
                    || material == Materials.Tin
                    || material == Materials.Zinc
                    || material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(material + "_t_cable02", OreDictUnifier.get(OrePrefix.cableGt02, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt02, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt02, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 1, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else if (material == Materials.RedAlloy) {
                    ModHandler.addShapelessRecipe(material + "_t_cable02", OreDictUnifier.get(OrePrefix.cableGt02, material),
                        new UnificationEntry(wirePrefix, material),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt02, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt02, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt02, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt02, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                ModHandler.addShapelessRecipe(material + "_wire02_t_wire01", OreDictUnifier.get(OrePrefix.wireGt01, material, 2),
                    new UnificationEntry(wirePrefix, material));
                ModHandler.addShapelessRecipe(material + "_wire01_t_wire02", GTUtility.copyAmount(1, stack),
                    new UnificationEntry(OrePrefix.wireGt01, material),
                    new UnificationEntry(OrePrefix.wireGt01, material));

                break;
            case wireGt04:
                if (material == Materials.Cobalt
                    || material == Materials.Lead
                    || material == Materials.Tin
                    || material == Materials.Zinc
                    || material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(material + "_t_cable04", OreDictUnifier.get(OrePrefix.cableGt04, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 2, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt04, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt04, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 2, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else if (material == Materials.RedAlloy) {
                    ModHandler.addShapelessRecipe(material + "_t_cable04", OreDictUnifier.get(OrePrefix.cableGt04, material),
                        new UnificationEntry(wirePrefix, material),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt04, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt04, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(2 * L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt04, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt04, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 2))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                ModHandler.addShapelessRecipe(material + "_wire04_t_wire01", OreDictUnifier.get(OrePrefix.wireGt01, material, 4),
                    new UnificationEntry(wirePrefix, material));

                ModHandler.addShapelessRecipe(material + "_wire02_t_wire04", GTUtility.copyAmount(1, stack),
                    new UnificationEntry(OrePrefix.wireGt02, material),
                    new UnificationEntry(OrePrefix.wireGt02, material));

                break;
            case wireGt08:
                if (material == Materials.Cobalt
                    || material == Materials.Lead
                    || material == Materials.Tin
                    || material == Materials.Zinc
                    || material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(material + "_wire08_t_cable08", OreDictUnifier.get(OrePrefix.cableGt08, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 3, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt08, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt08, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 3, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                } else if (material == Materials.RedAlloy) {

                    ModHandler.addShapelessRecipe(material + "_wire08_t_cable08", OreDictUnifier.get(OrePrefix.cableGt08, material),
                        new UnificationEntry(wirePrefix, material),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt08, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt08, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(3 * L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt08, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt08, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 3))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                ModHandler.addShapelessRecipe(material + "_wire08_t_wire01", OreDictUnifier.get(OrePrefix.wireGt01, material, 8),
                    new UnificationEntry(wirePrefix, material));
                ModHandler.addShapelessRecipe(material + "_wire04_t_wire08", GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGt04, material),
                    OreDictUnifier.get(OrePrefix.wireGt04, material));

                break;
            case wireGt12:
                if (material == Materials.Cobalt
                    || material == Materials.Lead
                    || material == Materials.Tin
                    || material == Materials.Zinc
                    || material == Materials.SolderingAlloy) {

                    ModHandler.addShapelessRecipe(material + "_wire12_t_cable12", OreDictUnifier.get(OrePrefix.cableGt12, material),
                        new UnificationEntry(wirePrefix, material),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Blocks.CARPET, 1, 15),
                        new ItemStack(Items.STRING));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 4, 15))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt12, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt12, material))
                        .outputs(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.CARPET, 4, 15))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                } else if (material == Materials.RedAlloy) {

                    ModHandler.addShapelessRecipe(material + "_wire12_t_cable12", OreDictUnifier.get(OrePrefix.cableGt12, material),
                        new UnificationEntry(wirePrefix, material),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper),
                        new UnificationEntry(OrePrefix.plate, Materials.Paper));

                    RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt12, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt12, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                } else {
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .circuitMeta(24)
                        .fluidInputs(Materials.Rubber.getFluid(4 * L))
                        .outputs(OreDictUnifier.get(OrePrefix.cableGt12, material))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();

                    RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.cableGt12, material))
                        .outputs(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 4))
                        .duration(100)
                        .EUt(8)
                        .buildAndRegister();
                }
                ModHandler.addShapelessRecipe(material + "_wire12_t_wire01", OreDictUnifier.get(OrePrefix.wireGt01, material, 12),
                    new UnificationEntry(wirePrefix, material));

                ModHandler.addShapelessRecipe(material + "_wire12_t_wire08_04", GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGt08, material),
                    OreDictUnifier.get(OrePrefix.wireGt04, material));

                break;
            case wireGt16:
                ModHandler.addShapelessRecipe(material + "_wire01_t_wire16", OreDictUnifier.get(OrePrefix.wireGt01, material, 16),
                    new UnificationEntry(wirePrefix, material));

                ModHandler.addShapelessRecipe(material + "_wire08_t_wire16", GTUtility.copyAmount(1, stack),
                    OreDictUnifier.get(OrePrefix.wireGt08, material),
                    OreDictUnifier.get(OrePrefix.wireGt08, material));

                break;
        }
    }
}
