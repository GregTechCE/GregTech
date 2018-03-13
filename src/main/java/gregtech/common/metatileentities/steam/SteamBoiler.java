package gregtech.common.metatileentities.steam;

import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.IWorkable;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.factory.MetaTileEntityFactory;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class SteamBoiler extends SteamMetaTileEntity implements IWorkable {

    public SteamBoiler(MetaTileEntityFactory factory) {
        super(factory);
    }

    @Override
    public boolean onScrewdriverRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public boolean onWrenchRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(2);
    }

    @Override
    public IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(2);
    }

    @Override
    public IFluidHandler createFluidHandler() {
        this.steamFluidTank = new FilteredFluidHandler(16000).setFillPredicate(fluidStack -> false);
        return new FluidHandlerProxy(this.steamFluidTank, this.exportFluids);
    }

    @Override
    public ModularUI<? extends IMetaTileEntity> createUI(EntityPlayer player) {
        GTResourceLocation slotImageLocation = new GTResourceLocation("textures/gui/bronze/slot_bronze.png");
        return ModularUI.<SteamBoiler>builder(new GTResourceLocation("textures/gui/bronze/bronze_gui.png"), 176, 166)
            .widget(0, new LabelWidget<>(6, 6, GTUtility.sided(() -> I18n.format(this.factory.getUnlocalizedName()), this.factory::getUnlocalizedName)))
            .widget(1, new SlotWidget<SteamBoiler>(this.importItems, 0, 115, 54)
                .setImageLocation(slotImageLocation)
                .setBackgroundLocation(new GTResourceLocation("textures/gui/bronze/slot_bronze_furnace_background.png"))
                .setOnSlotChanged(this::markDirty))
            .widget(2, new ProgressWidget<SteamBoiler>(114, 35, true, true)
                .setImageLocation(new GTResourceLocation("textures/gui/bronze/boiler_bronze_fuel.png"))
                .setFilledImageLocation(new GTResourceLocation("textures/gui/bronze/boiler_bronze_fuel_full.png"))
                .setImageWidthHeight(18, 18)
                .setImageUV(0, 0))//optional but included anyway as a good example for new widgets
            .widget(3, new SlotWidget<SteamBoiler>(this.exportItems, 0, 115, 18, true, false)
                .setImageLocation(slotImageLocation)
                .setOnSlotChanged(this::markDirty))
            .widget(4, new ProgressWidget<SteamBoiler>(96, 18, true, true)
                .setImageLocation(new GTResourceLocation("textures/gui/bronze/bar_bronze_empty.png"))
                .setFilledImageLocation(new GTResourceLocation("textures/gui/bronze/bar_heat.png"))
                .setImageWidthHeight(10, 54)
                .setImageUV(0, 0))//optional but included anyway as a good example for new widgets
            .widget(5, new ProgressWidget<SteamBoiler>(83, 18, true, true)
                .setImageLocation(new GTResourceLocation("textures/gui/bronze/bar_bronze_empty.png"))
                .setFilledImageLocation(new GTResourceLocation("textures/gui/bronze/bar_water.png"))
                .setImageWidthHeight(10, 54)
                .setImageUV(0, 0))//optional but included anyway as a good example for new widgets
            .widget(6, new ProgressWidget<SteamBoiler>(70, 18, true, true)
                .setImageLocation(new GTResourceLocation("textures/gui/bronze/bar_bronze_empty.png"))
                .setFilledImageLocation(new GTResourceLocation("textures/gui/bronze/bar_steam.png"))
                .setImageWidthHeight(10, 54)
                .setImageUV(0, 0))//optional but included anyway as a good example for new widgets
            .widget(7, new SlotWidget<SteamBoiler>(this.importItems, 1, 43, 18)
                .setImageLocation(slotImageLocation)
                .setBackgroundLocation(new GTResourceLocation("textures/gui/bronze/overlay_bronze_in.png")))
            .widget(8, new SlotWidget<SteamBoiler>(this.exportItems, 1, 43, 54, true, false)
                .setImageLocation(slotImageLocation)
                .setBackgroundLocation(new GTResourceLocation("textures/gui/bronze/overlay_bronze_out.png"))
                .setOnSlotChanged(this::markDirty))
            .widget(9, new ImageWidget<SteamBoiler>(42, 35)
                .setImageLocation(new GTResourceLocation("textures/gui/bronze/overlay_bronze_fluid_container.png"))
//                .setFillPredicate(holder -> holder.importItems.getStackInSlot(0).getCount() > 0) // test
                .setImageWidthHeight(18, 18))
            .widget(10, new LabelWidget<>(8, 166 - 96 + 2, player.inventory.getDisplayName().getUnformattedText())) // 166 - gui imageHeight, 96 + 2 - from vanilla code
            .bindPlayerInventory(player.inventory, 11, slotImageLocation)
            .build(this, player);
    }

    @Override
    public boolean onRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        if (player instanceof EntityPlayerMP) {
            MetaTileEntityUIFactory.INSTANCE.openUI(this, (EntityPlayerMP) player);
        }
        return true;
    }

    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public int getMaxProgress() {
        return 0;
    }

    @Override
    public void increaseProgress(int progress) {

    }

    @Override
    public boolean hasWorkToDo() {
        return false;
    }

    @Override
    public void enableWorking() {

    }

    @Override
    public void disableWorking() {

    }

    @Override
    public boolean isAllowedToWork() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean active) {

    }
}
