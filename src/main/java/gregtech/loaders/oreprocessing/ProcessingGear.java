package gregtech.loaders.oreprocessing;

import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.GT_Proxy;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingGear implements IOreRegistrationHandler {
    public ProcessingGear() {
        OrePrefix.gearGt.addProcessingHandler(this);
        OrePrefix.gearGtSmall.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.orePrefix) {
            case gearGt:
                ModHandler.removeRecipeByOutput(stack);
                if (uEntry.material instanceof FluidMaterial)
                    RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                            .inputs(ItemList.Shape_Mold_Gear.get(0))
                            .fluidInputs(((FluidMaterial) uEntry.material).getFluid(576))
                            .outputs(GTUtility.copyAmount(1, stack))
                            .duration(128)
                            .EUt(8)
                            .buildAndRegister();
                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    switch (uEntry.material.defaultLocalName) {
                        case "Wood":
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, uEntry.material, 1), GT_Proxy.tBits, "SPS", "PsP", "SPS", Character.valueOf('P'), OreDictionaryUnifier.get(OrePrefix.plank, uEntry.material, 2), Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 2));
                            break;
                        case "Stone":
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, uEntry.material, 1), GT_Proxy.tBits, "SPS", "PfP", "SPS", Character.valueOf('P'), OrePrefix.stoneSmooth, Character.valueOf('S'), new ItemStack(Blocks.STONE_BUTTON, 1, 32767));
                            break;
                        default:
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, uEntry.material, 1), GT_Proxy.tBits, "SPS", "PwP", "SPS", Character.valueOf('P'), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 2), Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 2));
                    }
                }
                break;
            case gearGtSmall:
                if (uEntry.material instanceof FluidMaterial)
                    RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                            .inputs(ItemList.Shape_Mold_Gear_Small.get(0))
                            .fluidInputs(((FluidMaterial) uEntry.material).getFluid(144))
                            .outputs(GTUtility.copyAmount(1, stack))
                            .duration(16)
                            .EUt(8)
                            .buildAndRegister();
                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    switch (uEntry.material.defaultLocalName) {
                        case "Wood":
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, uEntry.material, 1), GT_Proxy.tBits, "P ", " s", Character.valueOf('P'), OreDictionaryUnifier.get(OrePrefix.plank, uEntry.material, 2));
                            break;
                        case "Stone":
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, uEntry.material, 1), GT_Proxy.tBits, "P ", " f", Character.valueOf('P'), OrePrefix.stoneSmooth);
                            break;
                        default:
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, uEntry.material, 1), GT_Proxy.tBits, "P ", uEntry.material.contains(SubTag.WOOD) ? " s" : " h", Character.valueOf('P'), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 2));
                    }
                }
                break;
        }
    }
}
