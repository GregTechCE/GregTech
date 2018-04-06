package gregtech.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.capability.impl.EnergyRecipeMapWorkableHandler;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.render.OrientedOverlayRenderer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashSet;
import java.util.Set;

public abstract class WorkableTieredMetaTileEntity extends TieredMetaTileEntity {

    protected final EnergyRecipeMapWorkableHandler workable;
    protected final OrientedOverlayRenderer renderer;

    public WorkableTieredMetaTileEntity(String metaTileEntityId, RecipeMap<?> recipeMap, OrientedOverlayRenderer renderer, int tier) {
        super(metaTileEntityId, tier);
        this.renderer = renderer;
        this.workable = addTrait(new EnergyRecipeMapWorkableHandler(energyContainer, recipeMap));
        initializeInventory();
        initializeEnergyContainer();
    }

    @Override
    protected long getMaxInputOutputAmperage() {
        return workable == null ? 1L : workable.recipeMap.getAmperage();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        renderer.render(renderState, pipeline, getFrontFacing(), workable.isActive());
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        if(workable == null) return new ItemStackHandler(0);
        return new ItemStackHandler(workable.recipeMap.getMaxInputs());
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        if(workable == null) return new ItemStackHandler(0);
        return new ItemStackHandler(workable.recipeMap.getMaxOutputs());
    }

    @Override
    protected FluidTankHandler createImportFluidHandler() {
        if(workable == null) return new FluidTankHandler();
        FilteredFluidHandler[] fluidImports = new FilteredFluidHandler[workable.recipeMap.getMaxFluidInputs()];
        for(int i = 0; i < fluidImports.length; i++) {
            FilteredFluidHandler filteredFluidHandler = new FilteredFluidHandler(getInputTankCapacity(i));
            filteredFluidHandler.setFillPredicate(fluid -> canInputFluid(fluid.getFluid()));
            fluidImports[i] = filteredFluidHandler;
        }
        return new FluidTankHandler(fluidImports);
    }

    @Override
    protected FluidTankHandler createExportFluidHandler() {
        if(workable == null) return new FluidTankHandler();
        FluidTank[] fluidExports = new FluidTank[workable.recipeMap.getMaxFluidOutputs()];
        for(int i = 0; i < fluidExports.length; i++) {
            fluidExports[i] = new FluidTank(getOutputTankCapacity(i));
        }
        return new FluidTankHandler(fluidExports);
    }

    protected boolean canInputFluid(Fluid inputFluid) {
        RecipeMap<?> recipeMap = workable.recipeMap;
        Set<Recipe> matchingRecipes = null;
        for(IFluidTank fluidTank : importFluids) {
            Fluid fluidInTank = fluidTank.getFluid() == null ? null : fluidTank.getFluid().getFluid();
            if(fluidInTank != null) {
                if (matchingRecipes == null) {
                    //if we didn't have a list of recipes with any fluids, obtain it from first tank with fluid
                    matchingRecipes = new HashSet<>(recipeMap.getRecipesForFluid(fluidInTank));
                } else {
                    //else, remove recipes that don't contain fluid in this tank from list
                    matchingRecipes.removeIf(recipe -> !recipe.hasInputFluid(fluidInTank));
                }
            }
        }
        if(matchingRecipes == null) {
            //if all tanks are empty, generally fluid can be inserted if there are recipes for it
            return !recipeMap.getRecipesForFluid(inputFluid).isEmpty();
        } else {
            //otherwise, we can insert fluid only if one of recipes accept it as input
            return matchingRecipes.stream().anyMatch(recipe -> recipe.hasInputFluid(inputFluid));
        }
    }

    protected int getInputTankCapacity(int index) {
        return 16000;
    }

    protected int getOutputTankCapacity(int index) {
        return 16000;
    }

}
