package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.material.type.DustMaterial.MatFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;

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

}
