package gregtech.api.recipes.machines;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.items.IItemHandlerModifiable;

public class RecipeMapCrackerUnit<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapCrackerUnit(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, R defaultRecipe, boolean isHidden) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, defaultRecipe, isHidden);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.defaultBuilder(yOffset);
        builder.widget(new RecipeProgressWidget(200, 78, 23 + yOffset, 20, 20, progressBarTexture, moveType, this));
        addSlot(builder, 52, 24 + yOffset, 0, importItems, importFluids, true, false);
        addSlot(builder, 34, 24 + yOffset, 1, importItems, importFluids, true, false);
        addSlot(builder, 16, 24 + yOffset, 0, importItems, importFluids, false, false);
        addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        if (this.specialTexture != null && this.specialTexturePosition != null) {
            addSpecialTexture(builder);
        }
        return builder;
    }
}
