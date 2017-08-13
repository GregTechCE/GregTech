package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.items.ItemList;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import net.minecraftforge.fluids.FluidRegistry;

public class ProcessingCrop implements IOreRegistrationHandler {
    public ProcessingCrop() {
        OrePrefix.crop.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, net.minecraft.item.ItemStack aStack) {
        GT_ModHandler.addCompressionRecipe(gregtech.api.util.GT_Utility.copyAmount(8L, new Object[]{aStack}), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        if (aOreDictName.equals("cropTea")) {
            GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.tea"), false);
            GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.tea"), false);
        } else if (aOreDictName.equals("cropGrape")) {
            GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.grapejuice"), false);
            GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.grapejuice"), false);
        } else if (aOreDictName.equals("cropChilipepper")) {
            GT_ModHandler.addPulverisationRecipe(aStack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Chili, 1L));
        } else if (aOreDictName.equals("cropCoffee")) {
            GT_ModHandler.addPulverisationRecipe(aStack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Coffee, 1L));
        } else if (aOreDictName.equals("cropPotato")) {
            GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Raw_PotatoChips.get(1L, new Object[0]), 64, 4);
            GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Stripes.get(0L, new Object[0]), ItemList.Food_Raw_Fries.get(1L, new Object[0]), 64, 4);
            GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.potatojuice"), true);
            GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.potatojuice"), true);
        } else if (aOreDictName.equals("cropLemon")) {
            GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Lemon.get(4L, new Object[0]), 64, 4);
            GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.WATER, FluidRegistry.getFluid("potion.lemonjuice"), false);
            GT_Values.RA.addBrewingRecipe(aStack, GT_ModHandler.getDistilledWater(1L).getFluid(), FluidRegistry.getFluid("potion.lemonjuice"), false);
            GT_Values.RA.addBrewingRecipe(aStack, FluidRegistry.getFluid("potion.vodka"), FluidRegistry.getFluid("potion.leninade"), true);
        } else if (aOreDictName.equals("cropTomato")) {
            GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Tomato.get(4L, new Object[0]), 64, 4);
        } else if (aOreDictName.equals("cropCucumber")) {
            GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Cucumber.get(4L, new Object[0]), 64, 4);
        } else if (aOreDictName.equals("cropOnion")) {
            GT_Values.RA.addSlicerRecipe(aStack, ItemList.Shape_Slicer_Flat.get(0L, new Object[0]), ItemList.Food_Sliced_Onion.get(4L, new Object[0]), 64, 4);
        }
    }
}
