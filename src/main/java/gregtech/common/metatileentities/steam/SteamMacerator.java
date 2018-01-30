package gregtech.common.metatileentities.steam;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.WorkableSteamMetaTileEntity;
import gregtech.api.metatileentity.factory.WorkableSteamMetaTileEntityFactory;
import gregtech.api.util.GTResourceLocation;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class SteamMacerator extends WorkableSteamMetaTileEntity {

    public SteamMacerator(WorkableSteamMetaTileEntityFactory<SteamMacerator> factory) {
        super(factory);
    }

    @Override
    public IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public ModularUI<? extends IMetaTileEntity> createUI(EntityPlayer player) {
        GTResourceLocation slotImageLocation = new GTResourceLocation("textures/gui/bronze/slot_bronze.png");
        return ModularUI.<SteamMacerator>builder(new GTResourceLocation("textures/gui/bronze/bronze_gui.png"), 176, 166)
            .widget(0, new LabelWidget<>(6, 6, GTUtility.sided(() -> I18n.format(this.factory.getUnlocalizedName()), this.factory::getUnlocalizedName)))
            .widget(1, new SlotWidget<SteamMacerator>(this.importItems, 0, 53, 25)
                .setImageLocation(slotImageLocation)
                .setBackgroundLocation(new GTResourceLocation("textures/gui/bronze/slot_bronze_macerator_background.png"))
                .setOnSlotChanged(this::markDirty))
            .widget(2, new ProgressWidget<SteamMacerator>(78, 25, false,false)
                .setImageLocation(new GTResourceLocation("textures/gui/bronze/progress_bar_bronze_macerator.png"))
                .setFilledImageLocation(new GTResourceLocation("textures/gui/bronze/progress_bar_bronze_macerator_filled.png"))
                .setImageWidthHeight(20,18)
                .setImageUV(0,0))
            .widget(3, new SlotWidget<SteamMacerator>(this.exportItems, 0, 107, 25, true, false)
                .setImageLocation(slotImageLocation)
                .setOnSlotChanged(this::markDirty))
            .widget(4, new LabelWidget<>(8, 166 - 96 + 2, player.inventory.getDisplayName().getUnformattedText())) // 166 - gui height, 96 + 2 - from vanilla code
            .bindPlayerInventory(player.inventory, 5, slotImageLocation)
            .build(this, player);
    }
}
