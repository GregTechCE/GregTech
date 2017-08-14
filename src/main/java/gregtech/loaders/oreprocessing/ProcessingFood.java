package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingFood implements IOreRegistrationHandler {
    public ProcessingFood() {
        OrePrefix.food.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (aOreDictName) {
            case "foodCheese":
                GT_Values.RA.addSlicerRecipe(stack, ItemList.Shape_Slicer_Flat.get(0), ItemList.Food_Sliced_Cheese.get(4), 64, 4);
                OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Cheese, 3628800, new MaterialStack[0]));
            case "foodDough":
                ModHandler.removeFurnaceSmelting(stack);
                GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1, stack), ItemList.Food_Flat_Dough.get(1), 16, 4);

                GT_Values.RA.addMixerRecipe(stack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sugar, 1), null, null, null, null, ItemList.Food_Dough_Sugar.get(2), 32, 8);
                GT_Values.RA.addMixerRecipe(stack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cocoa, 1), null, null, null, null, ItemList.Food_Dough_Chocolate.get(2), 32, 8);
                GT_Values.RA.addMixerRecipe(stack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Chocolate, 1), null, null, null, null, ItemList.Food_Dough_Chocolate.get(2), 32, 8);

                GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(1, stack), ItemList.Shape_Mold_Bun.get(0), ItemList.Food_Raw_Bun.get(1), 128, 4);
                GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(2, stack), ItemList.Shape_Mold_Bread.get(0), ItemList.Food_Raw_Bread.get(1), 256, 4);
                GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(3, stack), ItemList.Shape_Mold_Baguette.get(0), ItemList.Food_Raw_Baguette.get(1), 384, 4);
        }
    }
}
