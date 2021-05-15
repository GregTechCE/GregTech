package gregtech.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.recipes.machines.FuelRecipeMap;
import gregtech.common.metatileentities.multi.ThrottleableMultiblockController;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Supplier;

public class ThrottableRecipeLogic extends FuelRecipeLogic {
    public ThrottableRecipeLogic(ThrottleableMultiblockController metaTileEntity, FuelRecipeMap recipeMap, Supplier<IEnergyContainer> energyContainer, Supplier<IMultipleTankHandler> fluidTank, long maxVoltage) {
        super(metaTileEntity, recipeMap, energyContainer, fluidTank, maxVoltage);
    }

    @Override
    protected int tryAcquireNewRecipe(FluidStack fluidStack) {
        int amount = (int) (super.tryAcquireNewRecipe(fluidStack) * ((ThrottleableMultiblockController) this.metaTileEntity).getThrottleMultiplier());
        if (amount > 0) {
            this.recipeOutputVoltage *= ((ThrottleableMultiblockController) this.metaTileEntity).getThrottleEfficiency();
        }
        return amount;
    }

}
