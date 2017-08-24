package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GregTechAPI;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingBlock implements IOreRegistrationHandler {
    public ProcessingBlock() {
        OrePrefix.block.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        RecipeMap.CUTTER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(1, stack))
                .outputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 9))
                .duration((int) Math.max(uEntry.material.getMass() * 10L, 1L))
                .EUt(30)
                .buildAndRegister();

        ItemStack ingotStack = OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1);
        ItemStack gemStack = OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1);
        ItemStack dustStack = OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1);
        ModHandler.removeRecipe(GTUtility.copyAmount(1, stack));

        if (ingotStack != null)
            ModHandler.removeRecipe(ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack);
        if (gemStack != null)
            ModHandler.removeRecipe(gemStack, gemStack, gemStack, gemStack, gemStack, gemStack, gemStack, gemStack, gemStack);
        if (dustStack != null) {
            ModHandler.removeRecipe(dustStack, dustStack, dustStack, dustStack, dustStack, dustStack, dustStack, dustStack, dustStack);
        }
        if (uEntry.material instanceof FluidMaterial) {
            FluidMaterial fluidMaterial = (FluidMaterial) uEntry.material;
            RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                    .inputs(ItemList.Shape_Mold_Block.get(0))
                    .fluidInputs(fluidMaterial.getFluid(1296))
                    .outputs(OreDictionaryUnifier.get(OrePrefix.block, uEntry.material))
                    .duration(288)
                    .EUt(8)
                    .buildAndRegister();
        }
        if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.storageblockcrafting, OreDictionaryUnifier.get(OrePrefix.block, uEntry.material).toString(), false)) {
            if ((ingotStack == null) && (gemStack == null) && (dustStack != null))
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1), "XXX", "XXX", "XXX", 'X', OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material));
            if (gemStack != null)
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1), "XXX", "XXX", "XXX", 'X', OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material));
            if (ingotStack != null) {
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1), "XXX", "XXX", "XXX", 'X', OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material));
            }
        }
        if (ingotStack != null) ingotStack.stackSize = 9;
        if (gemStack != null) gemStack.stackSize = 9;
        if (dustStack != null) {
            dustStack.stackSize = 9;
        }
        RecipeMap.HAMMER_RECIPES.recipeBuilder()
                .inputs(stack).outputs(gemStack)
                .duration(100)
                .EUt(24)
                .buildAndRegister();

        if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.storageblockdecrafting, OreDictionaryUnifier.get(OrePrefix.block, uEntry.material).toString(), gemStack != null)) {
            if (dustStack != null)
                ModHandler.addShapelessCraftingRecipe(dustStack, OreDictionaryUnifier.get(OrePrefix.block, uEntry.material));
            if (gemStack != null)
                ModHandler.addShapelessCraftingRecipe(gemStack, OreDictionaryUnifier.get(OrePrefix.block, uEntry.material));
            if (ingotStack != null) {
                ModHandler.addShapelessCraftingRecipe(ingotStack, OreDictionaryUnifier.get(OrePrefix.block, uEntry.material));
            }
        }
        if (!OrePrefix.block.isIgnored(uEntry.material)) {
            ModHandler.addCompressionRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 9), OreDictionaryUnifier.get(OrePrefix.block, uEntry.material, 1));
        }
        switch (uEntry.material.toString()) {
            case "Mercury":
                System.err.println("'blockQuickSilver'?, In which Ice Desert can you actually place this as a solid Block?");
                break;
            case "Iron":
            case "WroughtIron":
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), ItemList.Shape_Extruder_Rod.get(0))
                        .outputs(ItemList.IC2_ShaftIron.get(1))
                        .duration(640)
                        .EUt(120)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(ItemList.IC2_Compressed_Coal_Ball.get(8), GTUtility.copyAmount(1, stack))
                        .outputs(ItemList.IC2_Compressed_Coal_Chunk.get(1))
                        .duration(400)
                        .EUt(4)
                        .buildAndRegister();
                break;
            case "Steel":
                RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack), ItemList.Shape_Extruder_Rod.get(0))
                        .outputs(ItemList.IC2_ShaftSteel.get(1))
                        .duration(1280)
                        .EUt(120)
                        .buildAndRegister();
                RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                        .inputs(ItemList.IC2_Compressed_Coal_Ball.get(8), GTUtility.copyAmount(1, stack))
                        .outputs(ItemList.IC2_Compressed_Coal_Chunk.get(1))
                        .duration(400)
                        .EUt(4)
                        .buildAndRegister();
        }
    }
}
