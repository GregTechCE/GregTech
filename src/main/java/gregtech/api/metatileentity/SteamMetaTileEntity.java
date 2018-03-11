package gregtech.api.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.capability.impl.SteamRecipeMapWorkableHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.fluids.FluidTank;

public abstract class SteamMetaTileEntity extends MetaTileEntity {

    protected SteamRecipeMapWorkableHandler workableHandler;
    protected FluidTank steamFluidTank;

    public SteamMetaTileEntity(RecipeMap<?> recipeMap) {
        this.workableHandler = addTrait(new SteamRecipeMapWorkableHandler(
            recipeMap, GTValues.V[1], steamFluidTank, 1.0));
    }

    @Override
    public FluidTankHandler createImportFluidHandler() {
        this.steamFluidTank = new FilteredFluidHandler(getSteamCapacity())
            .setFillPredicate(ModHandler::isSteam);
        return new FluidTankHandler(steamFluidTank);
    }

    @Override
    public FluidTankHandler createExportFluidHandler() {
        return new FluidTankHandler();
    }

    public int getSteamCapacity() {
        return 16000;
    }
}
