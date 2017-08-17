package gregtech.loaders.oreprocessing;

import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingStone implements IOreRegistrationHandler {
    public ProcessingStone() {
        OrePrefix.stone.addProcessingHandler(this);
        OrePrefix.stoneCobble.addProcessingHandler(this);
        OrePrefix.stoneSmooth.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (uEntry.orePrefix == OrePrefix.stoneCobble) {
            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood))
                    .outputs(new ItemStack(Blocks.LEVER, 1))
                    .duration(400)
                    .EUt(1)
                    .buildAndRegister();
            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(8, stack), ItemList.Circuit_Integrated.getWithDamage(0, 8))
                    .outputs(new ItemStack(Blocks.FURNACE, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(7, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Redstone))
                    .outputs(new ItemStack(Blocks.DROPPER, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(7, stack), new ItemStack(Items.BOW, 1, 0)).fluidInputs(Materials.Redstone.getFluid(144))
                    .outputs(new ItemStack(Blocks.DISPENSER, 1))
                    .duration(400)
                    .EUt(4)
                    .buildAndRegister();
        } else if (uEntry.orePrefix == OrePrefix.stoneSmooth) {
            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(1, stack), ItemList.Circuit_Integrated.getWithDamage(0, 1))
                    .outputs(new ItemStack(Blocks.STONE_BUTTON, 1))
                    .duration(100)
                    .EUt(4)
                    .buildAndRegister();
            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(2, stack), ItemList.Circuit_Integrated.getWithDamage(0, 2))
                    .outputs(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1))
                    .duration(200)
                    .EUt(4)
                    .buildAndRegister();
        } else if (uEntry.orePrefix == OrePrefix.stone) {
            Block block = GT_Utility.getBlockFromStack(stack);
            switch (uEntry.material.toString()) {
                case "NULL":
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(3, stack), new ItemStack(Blocks.REDSTONE_TORCH, 2))
                            .fluidInputs(Materials.Redstone.getFluid(144))
                            .outputs(new ItemStack(Items.REPEATER, 1))
                            .duration(100)
                            .EUt(4)
                            .buildAndRegister();
                    break;
                case "Sand":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), new ItemStack(Blocks.SAND, 1, 0), null, 10, false);
                    break;
                case "Endstone":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, Materials.Endstone, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Tungsten, 1), 5, false);
                    break;
                case "Netherrack":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, Materials.Netherrack, 1), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Gold, 1), 5, false);
                    break;
                case "NetherBrick":
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack), ItemList.Circuit_Integrated.getWithDamage(0, 1))
                            .outputs(new ItemStack(Blocks.NETHER_BRICK_FENCE, 1))
                            .duration(100)
                            .EUt(4)
                            .buildAndRegister();
                    break;
                case "Obsidian":
                    if (block != Blocks.AIR) block.setResistance(20.0F);
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Redstone), GT_Utility.copyAmount(5, stack))
                            .fluidInputs(Materials.Glass.getFluid(72)).outputs(ModHandler.getModItem("Forestry", "thermionicTubes", 4, 6)).duration(64).EUt(32).buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(OreDictionaryUnifier.get(OrePrefix.gem, Materials.NetherStar), GT_Utility.copyAmount(3, stack))
                            .fluidInputs(Materials.Glass.getFluid(720)).outputs(new ItemStack(Blocks.BEACON, 1, 0))
                            .duration(32)
                            .EUt(16)
                            .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack), ItemList.IC2_Compressed_Coal_Ball.get(8))
                            .outputs(ItemList.IC2_Compressed_Coal_Chunk.get(1))
                            .duration(400)
                            .EUt(4)
                            .buildAndRegister();
                    RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(8, stack), OreDictionaryUnifier.get(OrePrefix.gem, Materials.EnderEye))
                            .outputs(new ItemStack(Blocks.ENDER_CHEST, 1)).duration(400).EUt(4);
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack)).outputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), ModHandler.getModItem("Railcraft", "cube.crushed.obsidian", 1, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1)), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material), 10, true);
                    break;
                case "Concrete":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1));
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    break;
                case "Rhyolite":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.PotassiumFeldspar, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Quartz, 1), 20, false);
                    break;
                case "Komatiite":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Biotite, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Uranium, 1), 5, false);
                    break;
                case "Dacite":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Obsidian, 1), 20, false);
                    break;
                case "Gabbro":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.PotassiumFeldspar, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Pyrite, 1), 20, false);
                    break;
                case "Eclogite":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Rutile, 1), 10, false);
                    break;
                case "Soapstone":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, Materials.Talc, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Chromite, 1), 10, false);
                    break;
                case "Greenschist":
                case "Blueschist":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Glauconite, 2), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Basalt, 1), 100, false);
                    break;
                case "Gneiss":
                case "Migmatite":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefix.dustImpure, Materials.GraniteBlack, 1), 50, false);
                    break;
                case "Redrock":
                case "Marble":
                case "Basalt":
                case "Quartzite":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), 10, false);
                    break;
                case "Flint":
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 2), new ItemStack(Items.FLINT, 1), 50, false);
                    break;
                case "GraniteBlack":
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Thorium, 1), 1, false);
                    break;
                case "GraniteRed":
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Uranium, 1), 1, false);
                case "Andesite":
                case "Diorite":
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                            .inputs(GT_Utility.copyAmount(1, stack))
                            .outputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1))
                            .duration(200)
                            .EUt(32)
                            .buildAndRegister();
                    ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Stone, 1), 1, false);
                    break;
            }
        }
    }
}
