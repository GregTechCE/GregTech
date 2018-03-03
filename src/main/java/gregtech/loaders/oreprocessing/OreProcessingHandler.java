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
}
