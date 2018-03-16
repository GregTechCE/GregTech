package gregtech.api.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.capability.impl.SteamRecipeMapWorkableHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.gui.resources.TextureArea;
import net.minecraftforge.fluids.FluidTank;

public abstract class SteamMetaTileEntity extends MetaTileEntity {

    public final TextureArea BRONZE_BACKGROUND_TEXTURE = getGuiTexture("%s_gui");
    public final TextureArea BRONZE_SLOT_BACKGROUND_TEXTURE = getGuiTexture("slot_%s");

    public final TextureArea SLOT_FURNACE_BACKGROUND = getGuiTexture("slot_%s_furnace_background");
    public final TextureArea SLOT_HAMMER_BACKGROUND = getGuiTexture("slot_%s_hammer_background");
    public final TextureArea SLOT_MACERATOR_BACKGROUND = getGuiTexture("slot_%s_macerator_background");
    public final TextureArea SLOT_COMPRESSOR_BACKGROUND = getGuiTexture("slot_%s_compressor_background");
    public final TextureArea SLOT_EXTRACTOR_BACKGROUND = getGuiTexture("slot_%s_extractor_background");

    protected SteamRecipeMapWorkableHandler workableHandler;
    protected FluidTank steamFluidTank;
    protected final boolean isHighPressure;

    public SteamMetaTileEntity(RecipeMap<?> recipeMap, boolean isHighPressure) {
        this.workableHandler = addTrait(new SteamRecipeMapWorkableHandler(
            recipeMap, GTValues.V[isHighPressure ? 1 : 0], steamFluidTank, 1.0));
        this.isHighPressure = isHighPressure;
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
    
    protected TextureArea getGuiTexture(String pathTemplate) {
        String type = isHighPressure ? "steel" : "bronze";
        return TextureArea.fullImage(String.format("gregtech:textures/gui/steam/%s/%s.png",
            type, pathTemplate.replace("%s", type)));
    }
}
