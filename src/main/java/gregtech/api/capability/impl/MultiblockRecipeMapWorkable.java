package gregtech.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.function.BiFunction;

public class MultiblockRecipeMapWorkable extends RecipeMapWorkableHandler {

    private IItemHandlerModifiable importItemsInventory;
    private IMultipleTankHandler importFluidsInventory;
    private IItemHandlerModifiable exportItemsInventory;
    private IMultipleTankHandler exportFluidsInventory;
    private IEnergyContainer energyContainer;
    private BiFunction<Recipe, Boolean, Boolean> recipeChecker;

    public MultiblockRecipeMapWorkable(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, BiFunction<Recipe, Boolean, Boolean> recipeChecker) {
        super(tileEntity, recipeMap);
        this.recipeChecker = recipeChecker;
    }

    @Override
    public void update() {
    }

    public void reinitializeAbilities(IItemHandlerModifiable importItemsInventory, IMultipleTankHandler importFluidsInventory,
                                      IItemHandlerModifiable exportItemsInventory, IMultipleTankHandler exportFluidsInventory,
                                      IEnergyContainer energyContainer) {
        this.importItemsInventory = importItemsInventory;
        this.importFluidsInventory = importFluidsInventory;
        this.exportItemsInventory = exportItemsInventory;
        this.exportFluidsInventory = exportFluidsInventory;
        this.energyContainer = energyContainer;
    }

    public void resetAbilities() {
        this.importItemsInventory = null;
        this.importFluidsInventory = null;
        this.exportItemsInventory = null;
        this.exportFluidsInventory = null;
        this.energyContainer = null;
    }

    public void updateWorkable() {
        super.update();
    }

    @Override
    protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
        if(recipeChecker.apply(recipe, false) &&
            super.setupAndConsumeRecipeInputs(recipe)) {
            recipeChecker.apply(recipe, true);
            return true;
        } else return false;
    }

    @Override
    protected long getEnergyStored() {
        return energyContainer.getEnergyStored();
    }

    @Override
    protected long getEnergyCapacity() {
        return energyContainer.getEnergyCapacity();
    }

    @Override
    protected boolean drawEnergy(int recipeEUt) {
        long resultEnergy = getEnergyStored() - recipeEUt;
        if(resultEnergy >= 0L && resultEnergy <= getEnergyCapacity()) {
            energyContainer.addEnergy(-recipeEUt);
            return true;
        } else return false;
    }

    @Override
    protected long getMaxVoltage() {
        return Math.max(energyContainer.getInputVoltage(), energyContainer.getOutputVoltage());
    }

    @Override
    protected IItemHandlerModifiable getImportItemsInventory() {
        return this.importItemsInventory;
    }

    @Override
    protected IMultipleTankHandler getImportFluidsInventory() {
        return this.importFluidsInventory;
    }

    @Override
    protected IItemHandlerModifiable getExportItemsInventory() {
        return this.exportItemsInventory;
    }

    @Override
    protected IMultipleTankHandler getExportFluidsInventory() {
        return this.exportFluidsInventory;
    }
}
