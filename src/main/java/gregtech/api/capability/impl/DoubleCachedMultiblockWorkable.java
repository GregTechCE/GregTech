package gregtech.api.capability.impl;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Basically serves as a double-buffer for recipe map workables, who override findRecipe
 * to create temporary one-tick recipes from ones returned by recipe map
 *
 * This class optimizes this strategy, so when you call super.findRecipe, it will
 * try to return buffered recipe to you first, or else will try to find a new one,
 * so you won't need to call recipeMap.findRecipe again next tick when your temporary recipe ends
 */
public class DoubleCachedMultiblockWorkable extends MultiblockRecipeMapWorkable {

    protected Recipe doublePreviousRecipe;

    public DoubleCachedMultiblockWorkable(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    @Override
    protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
        Recipe currentRecipe;
        if(doublePreviousRecipe != null && doublePreviousRecipe.matches(false, false, inputs, fluidInputs)) {
            //if previous recipe still matches inputs, try to use it
            currentRecipe = doublePreviousRecipe;
        } else {
            //else, try searching new recipe for given inputs
            currentRecipe = recipeMap.findRecipe(maxVoltage, inputs, fluidInputs);
            //if we found recipe that can be buffered, buffer it
            if(currentRecipe != null && currentRecipe.canBeBuffered()) {
                this.doublePreviousRecipe = currentRecipe;
            }
        }
        return currentRecipe;
    }

}
