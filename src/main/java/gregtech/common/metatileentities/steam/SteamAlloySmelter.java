package gregtech.common.metatileentities.steam;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;

public class SteamAlloySmelter extends SteamMetaTileEntity {

    public SteamAlloySmelter(ResourceLocation metaTileEntityId, boolean isHighPressure) {
        super(metaTileEntityId, RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, isHighPressure);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new SteamAlloySmelter(metaTileEntityId, isHighPressure);
    }

    @Override
    protected boolean isBrickedCasing() {
        return true;
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(2, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(1, this, true);
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        TextureArea slotBackground = getFullGuiTexture("slot_%s_furnace_background");
        return createUITemplate(player)
            .widget(new SlotWidget(this.importItems, 0, 60, 25)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, slotBackground))
            .widget(new SlotWidget(this.importItems, 1, 42, 25)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, slotBackground))
            .widget(new ProgressWidget(workableHandler::getProgressPercent, 82, 25, 20, 16)
                .setProgressBar(getFullGuiTexture("progress_bar_%s_furnace"),
                    getFullGuiTexture("progress_bar_%s_furnace_filled"),
                    MoveType.HORIZONTAL))
            .widget(new SlotWidget(this.exportItems, 0, 107, 25, true, false)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE))
            .build(getHolder(), player);
    }
}
