package gregtech.api.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.capability.impl.SteamRecipeMapWorkableHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.TextureArea;
import net.minecraftforge.fluids.FluidTank;

public abstract class SteamMetaTileEntity extends MetaTileEntity {

    public static final TextureArea BRONZE_BACKGROUND_TEXTURE = TextureArea.fullImage("gregtech:textures/gui/bronze/bronze_gui.png");
    public static final TextureArea BRONZE_SLOT_BACKGROUND_TEXTURE = TextureArea.fullImage("gregtech:textures/gui/bronze/slot_bronze.png");

    public static final TextureArea SLOT_FURNACE_BACKGROUND = TextureArea.fullImage("gregtech:textures/gui/bronze/slot_bronze_furnace_background.png");
    public static final TextureArea SLOT_HAMMER_BACKGROUND = TextureArea.fullImage("gregtech:textures/gui/bronze/slot_bronze_hammer_background.png");
    public static final TextureArea SLOT_MACERATOR_BACKGROUND = TextureArea.fullImage("gregtech:textures/gui/bronze/slot_bronze_macerator_background.png");
    public static final TextureArea SLOT_COMPRESSOR_BACKGROUND = TextureArea.fullImage("gregtech:textures/gui/bronze/slot_bronze_compressor_background.png");
    public static final TextureArea SLOT_EXTRACTOR_BACKGROUND = TextureArea.fullImage("gregtech:textures/gui/bronze/slot_bronze_extractor_background.png");

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
