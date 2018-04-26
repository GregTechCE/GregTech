package gregtech.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMap;

public class EnergyRecipeMapWorkableHandler extends RecipeMapWorkableHandler {

    private final IEnergyContainer energyContainer;

    public EnergyRecipeMapWorkableHandler(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, IEnergyContainer energyContainer) {
        super(tileEntity, recipeMap);
        this.energyContainer = energyContainer;
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
        return Math.max(energyContainer.getInputVoltage(),
            energyContainer.getOutputVoltage());
    }

}
