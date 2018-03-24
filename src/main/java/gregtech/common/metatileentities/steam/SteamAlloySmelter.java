package gregtech.common.metatileentities.steam;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public abstract class SteamAlloySmelter extends SteamMetaTileEntity {

    public static final class Bronze extends SteamAlloySmelter { public Bronze() { super(false); } }
    public static final class Steel extends SteamAlloySmelter { public Steel() { super(true); } }

    public SteamAlloySmelter(boolean isHighPressure) {
        super(RecipeMap.ALLOY_SMELTER_RECIPES, isHighPressure);
    }

    @Override
    public IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(2);
    }

    @Override
    public IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public ModularUI<IUIHolder> createUI(EntityPlayer player) {
        TextureArea slotBackground = getFullGuiTexture("slot_%s_furnace_background");
        return ModularUI.builder(BRONZE_BACKGROUND_TEXTURE, 176, 166)
            .widget(0, new LabelWidget<>(6, 6, getMetaName()))
            .widget(1, new SlotWidget<>(this.importItems, 0, 60, 25)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, slotBackground))
            .widget(2, new SlotWidget<>(this.importItems, 1, 42, 25)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, slotBackground))
            .widget(3, new ProgressWidget<>(workableHandler::getProgressPercent, 82, 25, 20, 16)
                .setProgressBar(getFullGuiTexture("progress_bar_%s_furnace"),
                    getFullGuiTexture("progress_bar_%s_furnace_filled"),
                    MoveType.HORIZONTAL))
            .widget(4, new SlotWidget<>(this.exportItems, 0, 107, 25, true, false)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE))
            .widget(5, new LabelWidget<>(8, 166 - 96 + 2, player.inventory.getName())) // 166 - gui imageHeight, 96 + 2 - from vanilla code
            .bindPlayerInventory(player.inventory, 6, BRONZE_SLOT_BACKGROUND_TEXTURE)
            .build(this, player);
    }
}
