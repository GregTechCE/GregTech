package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ProcessingDye implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.dye.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        ItemStack glassBlock = OreDictUnifier.get(OrePrefix.blockGlass, entry.material);
        ItemStack glassPane = OreDictUnifier.get(OrePrefix.paneGlass, entry.material);

        Fluid waterMixedDye = FluidRegistry.getFluid("dye.water_mixed." + entry.material.toString());
        Fluid chemicalDye = FluidRegistry.getFluid("dye.chemical." + entry.material.toString());

        RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(new ItemStack(Blocks.GLASS, 8), stack)
                .outputs(GTUtility.copyAmount(8, glassBlock))
                .duration(50)
                .EUt(8)
                .buildAndRegister();

        RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 8), stack)
                .outputs(GTUtility.copyAmount(8, glassBlock))
                .duration(50)
                .EUt(8)
                .buildAndRegister();

        RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(new ItemStack(Blocks.GLASS_PANE, 21), stack)
                .outputs(GTUtility.copyAmount(21, glassPane))
                .duration(60)
                .EUt(8)
                .buildAndRegister();

        RecipeMap.MIXER_RECIPES.recipeBuilder()
                .inputs(stack)
                .fluidInputs(ModHandler.getWater(216))
                .fluidOutputs(new FluidStack(waterMixedDye, 192))
                .duration(16)
                .EUt(4)
                .buildAndRegister();

        RecipeMap.MIXER_RECIPES.recipeBuilder()
                .inputs(stack)
                .fluidInputs(ModHandler.getWater(288))
                .fluidOutputs(new FluidStack(waterMixedDye, 216))
                .duration(16)
                .EUt(4)
                .buildAndRegister();

        RecipeMap.MIXER_RECIPES.recipeBuilder()
                .inputs(stack, OreDictUnifier.get(OrePrefix.dust, Materials.Salt, 2))
                .fluidInputs(Materials.SulfuricAcid.getFluid(432))
                .fluidOutputs(new FluidStack(chemicalDye, 216))
                .duration(32)
                .EUt(48)
                .buildAndRegister();
    }

}
