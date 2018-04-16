package gregtech.api.recipes.machines;

import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.DefaultRecipeBuilder;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.function.DoubleSupplier;

public class RecipeMapLiquidFuel extends RecipeMap<DefaultRecipeBuilder> {

    public RecipeMapLiquidFuel(String unlocalizedName, int minFluidOutputs, int maxFluidOutputs, int amperage, DefaultRecipeBuilder defaultRecipe) {
        super(unlocalizedName, 0, 0, 0, 0, 1, 1, minFluidOutputs, maxFluidOutputs, amperage, defaultRecipe);
    }

    @Override
    public Builder createUITemplate(DoubleSupplier progressSupplier, IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankHandler importFluids, FluidTankHandler exportFluids) {
        Builder builder = ModularUI.defaultBuilder();
        builder.image(7, 16, 81, 55, GuiTextures.DISPLAY);
        TankWidget tankWidget = new TankWidget(importFluids.getTankAt(0), 67, 50, 18, 18)
            .setHideTooltip(true).setAlwaysShowFull(true);
        builder.widget(tankWidget);
        builder.label(11, 20, "gregtech.gui.fuel_amount", 0xFFFFFF);
        builder.dynamicLabel(11, 30, tankWidget::getFormattedFluidAmount, 0xFFFFFF);
        builder.dynamicLabel(11, 40, tankWidget::getFluidLocalizedName, 0xFFFFFF);

        int fluidOutputsAmount = exportFluids.getTanks();
        int outputStartX = 172 - fluidOutputsAmount * 18 - ((fluidOutputsAmount - 1) * 4);
        int outputStartY = 16;
        for(int i = 0; i < fluidOutputsAmount; i++) {
            builder.tank(exportFluids.getTankAt(i), outputStartX, outputStartY, 18, 45, GuiTextures.FLUID_SLOT);
            outputStartX += 20;
        }
        return builder;
    }
}
