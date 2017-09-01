package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

public class ProcessingNugget implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.nugget.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        if (entry.material instanceof MetalMaterial) {
            RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(9, stack))
                    .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, ((MetalMaterial) entry.material).smeltInto))
                    .duration(200)
                    .EUt(2)
                    .buildAndRegister();
        }

        if (entry.material instanceof FluidMaterial && ((FluidMaterial) entry.material).isFluid())
            RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                    .notConsumable(MetaItems.SHAPE_MOLD_NUGGET)
                    .fluidInputs(((FluidMaterial) entry.material).getFluid(16))
                    .outputs(OreDictUnifier.get(OrePrefix.nugget, entry.material))
                    .duration(16)
                    .EUt(4)
                    .buildAndRegister();


//        RecipeRegistrator.registerReverseFluidSmelting(stack, entry.material, entry.orePrefix.materialAmount, null);
//        RecipeRegistrator.registerReverseMacerating(stack, entry.material, entry.orePrefix.materialAmount, null, null, null, false);

        if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING)) {

            RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .inputs(OreDictUnifier.get(OrePrefix.ingot, entry.material))
                    .notConsumable(MetaItems.SHAPE_MOLD_NUGGET)
                    .outputs(GTUtility.copyAmount(9, stack))
                    .duration(100)
                    .EUt(1)
                    .buildAndRegister();

            if (ModHandler.getSmeltingOutput(OreDictUnifier.get(OrePrefix.ingot, entry.material), false, null) == null) {
                if (entry.material instanceof MetalMaterial && OreDictUnifier.get(OrePrefix.nugget, ((MetalMaterial) entry.material).smeltInto) != null) {
                    ModHandler.addSmeltingRecipe(OreDictUnifier.get(OrePrefix.ingot, entry.material), GTUtility.copyAmount(9, stack));
                }
            }
        }
    }
}
