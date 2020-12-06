package gregtech.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

import static gregtech.api.util.InventoryUtils.simulateItemStackMerge;

public class MultiblockRecipeLogic extends AbstractRecipeLogic {


    public MultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity, tileEntity.recipeMap);
    }

    @Override
    public void update() {
    }

    public void updateWorkable() {
        super.update();
    }

    public IEnergyContainer getEnergyContainer() {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        return controller.getEnergyContainer();
    }

    @Override
    protected IItemHandlerModifiable getInputInventory() {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        return controller.getInputInventory();
    }

    @Override
    protected IItemHandlerModifiable getOutputInventory() {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        return controller.getOutputInventory();
    }

    @Override
    protected IMultipleTankHandler getInputTank() {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        return controller.getInputFluidInventory();
    }

    @Override
    protected IMultipleTankHandler getOutputTank() {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        return controller.getOutputFluidInventory();
    }

    @Override
    protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        if (controller.checkRecipe(recipe, false) &&
            multiBlockSetupAndConsumeRecipeInputs(recipe)) {
            controller.checkRecipe(recipe, true);
            return true;
        } else return false;
    }

    //Logic Mostly copied from AbstractRecipeLogic, but with some additional checking for Multiblock output spacing to prevent voiding
    protected boolean multiBlockSetupAndConsumeRecipeInputs(Recipe recipe) {
        int[] resultOverclock = calculateOverclock(recipe.getEUt(), getMaxVoltage(), recipe.getDuration());
        int totalEUt = resultOverclock[0] * resultOverclock[1];
        IItemHandlerModifiable importInventory = getInputInventory();
        IItemHandlerModifiable exportInventory = getOutputInventory();
        IMultipleTankHandler importFluids = getInputTank();
        IMultipleTankHandler exportFluids = getOutputTank();
        List<ItemStack> itemOutputs = recipe.getAllItemOutputs(exportInventory.getSlots());
        return (totalEUt >= 0 ? getEnergyStored() >= (totalEUt > getEnergyCapacity() / 2 ? resultOverclock[0] : totalEUt) :
            (getEnergyStored() - resultOverclock[0] <= getEnergyCapacity())) &&
            MetaTileEntity.addItemsToItemHandler(exportInventory, true, itemOutputs) &&
            simulateItemStackMerge(itemOutputs, exportInventory) &&
            MetaTileEntity.addFluidsToFluidHandler(exportFluids, true, recipe.getFluidOutputs()) &&
            recipe.matches(true, importInventory, importFluids);
    }

    @Override
    protected long getEnergyStored() {
        return getEnergyContainer().getEnergyStored();
    }

    @Override
    protected long getEnergyCapacity() {
        return getEnergyContainer().getEnergyCapacity();
    }

    @Override
    protected boolean drawEnergy(int recipeEUt) {
        long resultEnergy = getEnergyStored() - recipeEUt;
        if (resultEnergy >= 0L && resultEnergy <= getEnergyCapacity()) {
            getEnergyContainer().changeEnergy(-recipeEUt);
            return true;
        } else return false;
    }

    @Override
    protected long getMaxVoltage() {
        return Math.max(getEnergyContainer().getInputVoltage(), getEnergyContainer().getOutputVoltage());
    }
}
