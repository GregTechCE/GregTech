package gregtech.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.capability.impl.SteamRecipeMapWorkableHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.render.Textures;
import net.minecraftforge.fluids.FluidTank;
import org.apache.commons.lang3.ArrayUtils;

public abstract class SteamMetaTileEntity extends MetaTileEntity {

    public final TextureArea BRONZE_BACKGROUND_TEXTURE;
    public final TextureArea BRONZE_SLOT_BACKGROUND_TEXTURE;

    protected SteamRecipeMapWorkableHandler workableHandler;
    protected FluidTank steamFluidTank;
    protected final boolean isHighPressure;

    public SteamMetaTileEntity(RecipeMap<?> recipeMap, boolean isHighPressure) {
        this.workableHandler = addTrait(new SteamRecipeMapWorkableHandler(
            recipeMap, GTValues.V[isHighPressure ? 1 : 0], steamFluidTank, 1.0));
        this.isHighPressure = isHighPressure;
        BRONZE_BACKGROUND_TEXTURE = getFullGuiTexture("%s_gui");
        BRONZE_SLOT_BACKGROUND_TEXTURE = getFullGuiTexture("slot_%s");
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(paintingColor));
        if(isHighPressure) {
            Textures.STEAM_CASING_STEEL.render(renderState, colouredPipeline);
        } else Textures.STEAM_CASING_BRONZE.render(renderState, colouredPipeline);
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
    
    protected TextureArea getFullGuiTexture(String pathTemplate) {
        String type = isHighPressure ? "steel" : "bronze";
        return TextureArea.fullImage(String.format("gregtech:textures/gui/steam/%s/%s.png",
            type, pathTemplate.replace("%s", type)));
    }
}
