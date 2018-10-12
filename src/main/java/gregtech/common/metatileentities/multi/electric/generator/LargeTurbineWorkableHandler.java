package gregtech.common.metatileentities.multi.electric.generator;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.FuelRecipeMapWorkableHandler;
import gregtech.api.recipes.machines.FuelRecipeMap;
import gregtech.api.recipes.recipes.FuelRecipe;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.common.MetaFluids;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityRotorHolder;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine.TurbineType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.function.Supplier;

public class LargeTurbineWorkableHandler extends FuelRecipeMapWorkableHandler {

    private static final int BASE_ROTOR_DAMAGE = 2;
    private static final int BASE_EU_OUTPUT = 2048;
    private static final int EU_OUTPUT_BONUS = 6144;

    private MetaTileEntityLargeTurbine largeTurbine;

    public LargeTurbineWorkableHandler(MetaTileEntityLargeTurbine metaTileEntity, FuelRecipeMap recipeMap, Supplier<IEnergyContainer> energyContainer, Supplier<IFluidHandler> fluidTank, long maxVoltage) {
        super(metaTileEntity, recipeMap, energyContainer, fluidTank, maxVoltage);
        this.largeTurbine = metaTileEntity;
    }

    public FluidStack getFuelStack() {
        if(previousRecipe == null)
            return null;
        FluidStack fuelStack = previousRecipe.getRecipeFluid();
        return fluidTank.get().drain(new FluidStack(fuelStack.getFluid(), Integer.MAX_VALUE), false);
    }

    @Override
    public boolean checkRecipe(FuelRecipe recipe) {
        MetaTileEntityRotorHolder rotorHolder = largeTurbine.getAbilities(MetaTileEntityLargeTurbine.ABILITY_ROTOR_HOLDER).get(0);
        int damageToBeApplied = (int) (BASE_ROTOR_DAMAGE * rotorHolder.getRelativeRotorSpeed()) + 1;
        return rotorHolder.applyDamageToRotor(damageToBeApplied, true);
    }

    @Override
    protected int calculateFuelAmount(FuelRecipe currentRecipe) {
        MetaTileEntityRotorHolder rotorHolder = largeTurbine.getAbilities(MetaTileEntityLargeTurbine.ABILITY_ROTOR_HOLDER).get(0);
        double relativeRotorSpeed = rotorHolder.getRelativeRotorSpeed();
        return (int) Math.floor(super.calculateFuelAmount(currentRecipe) * (relativeRotorSpeed * relativeRotorSpeed));
    }

    @Override
    protected long startRecipe(FuelRecipe currentRecipe, int fuelAmountUsed, int recipeDuration) {
        MetaTileEntityRotorHolder rotorHolder = largeTurbine.getAbilities(MetaTileEntityLargeTurbine.ABILITY_ROTOR_HOLDER).get(0);
        double relativeRotorSpeed = rotorHolder.getRelativeRotorSpeed();
        double rotorEfficiency = rotorHolder.getRotorEfficiency();
        double totalEnergyOutput = BASE_EU_OUTPUT + EU_OUTPUT_BONUS * rotorEfficiency;
        int damageToBeApplied = (int) (BASE_ROTOR_DAMAGE * relativeRotorSpeed) + 1;
        rotorHolder.applyDamageToRotor(damageToBeApplied, true);
        addOutputFluids(currentRecipe, fuelAmountUsed);
        return (int) Math.floor(totalEnergyOutput * relativeRotorSpeed * relativeRotorSpeed);
    }

    private void addOutputFluids(FuelRecipe currentRecipe, int fuelAmountUsed) {
        if(largeTurbine.turbineType == TurbineType.STEAM) {
            int waterFluidAmount = fuelAmountUsed / 15;
            if(waterFluidAmount > 0) {
                FluidStack waterStack = Materials.Water.getFluid(waterFluidAmount);
                largeTurbine.exportFluidHandler.fill(waterStack, true);
            }
        } else if(largeTurbine.turbineType == TurbineType.PLASMA) {
            FluidMaterial material = MetaFluids.getMaterialFromFluid(currentRecipe.getRecipeFluid().getFluid());
            if(material != null) {
                largeTurbine.exportFluidHandler.fill(material.getFluid(fuelAmountUsed), true);
            }
        }
    }

}
