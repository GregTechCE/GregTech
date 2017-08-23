package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class ProcessingCrop implements IOreRegistrationHandler {
    public ProcessingCrop() {
        OrePrefix.crop.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        ModHandler.addCompressionRecipe(gregtech.api.util.GT_Utility.copyAmount(8, stack), ItemList.IC2_PlantballCompressed.get(1));
        if (aOreDictName.equals("cropTea")) {
            GTValues.RA.addBrewingRecipe(stack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.tea"), false);
            GTValues.RA.addBrewingRecipe(stack, ModHandler.getDistilledWater(1).getFluid(), FluidRegistry.getFluid("potion.tea"), false);
        } else if (aOreDictName.equals("cropGrape")) {
            GTValues.RA.addBrewingRecipe(stack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.grapejuice"), false);
            GTValues.RA.addBrewingRecipe(stack, ModHandler.getDistilledWater(1).getFluid(), FluidRegistry.getFluid("potion.grapejuice"), false);
        } else if (aOreDictName.equals("cropChilipepper")) {
            ModHandler.addPulverisationRecipe(stack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Chili, 1));
        } else if (aOreDictName.equals("cropCoffee")) {
            ModHandler.addPulverisationRecipe(stack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Coffee, 1));
        } else if (aOreDictName.equals("cropPotato")) {
            GTValues.RA.addSlicerRecipe(stack, ItemList.Shape_Slicer_Flat.get(0), ItemList.Food_Raw_PotatoChips.get(1), 64, 4);
            GTValues.RA.addSlicerRecipe(stack, ItemList.Shape_Slicer_Stripes.get(0), ItemList.Food_Raw_Fries.get(1), 64, 4);
            GTValues.RA.addBrewingRecipe(stack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.potatojuice"), true);
            GTValues.RA.addBrewingRecipe(stack, ModHandler.getDistilledWater(1).getFluid(), FluidRegistry.getFluid("potion.potatojuice"), true);
        } else if (aOreDictName.equals("cropLemon")) {
            GTValues.RA.addSlicerRecipe(stack, ItemList.Shape_Slicer_Flat.get(0), ItemList.Food_Sliced_Lemon.get(4), 64, 4);
            GTValues.RA.addBrewingRecipe(stack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.lemonjuice"), false);
            GTValues.RA.addBrewingRecipe(stack, ModHandler.getDistilledWater(1).getFluid(), FluidRegistry.getFluid("potion.lemonjuice"), false);
            GTValues.RA.addBrewingRecipe(stack, FluidRegistry.getFluid("potion.vodka"), FluidRegistry.getFluid("potion.leninade"), true);
        } else if (aOreDictName.equals("cropTomato")) {
            GTValues.RA.addSlicerRecipe(stack, ItemList.Shape_Slicer_Flat.get(0), ItemList.Food_Sliced_Tomato.get(4), 64, 4);
        } else if (aOreDictName.equals("cropCucumber")) {
            GTValues.RA.addSlicerRecipe(stack, ItemList.Shape_Slicer_Flat.get(0), ItemList.Food_Sliced_Cucumber.get(4), 64, 4);
        } else if (aOreDictName.equals("cropOnion")) {
            GTValues.RA.addSlicerRecipe(stack, ItemList.Shape_Slicer_Flat.get(0), ItemList.Food_Sliced_Onion.get(4), 64, 4);
        }
    }
}
