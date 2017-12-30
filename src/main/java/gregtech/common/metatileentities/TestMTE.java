package gregtech.common.metatileentities;

import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.metatileentity.WorkableMetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TestMTE extends WorkableMetaTileEntity<Recipe> {

    public TestMTE(IMetaTileEntityFactory factory, int tier, RecipeMap<Recipe, ?> recipeMap) {
        super(factory, tier, recipeMap);
    }

    @Override
    public boolean onScrewdriverRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public boolean onWrenchRightClick(EnumFacing side, EnumFacing wrenchingSide, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public IItemHandlerModifiable getImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public IItemHandlerModifiable getExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public FluidTankHandler getImportFluidHandler() {
        return new FluidTankHandler(0);
    }

    @Override
    public FluidTankHandler getExportFluidHandler() {
        return new FluidTankHandler(0);
    }

    @Override
    public ModularUI<? extends IMetaTileEntity> createUI(EntityPlayer player) {
        return ModularUI.<IMetaTileEntity>builder(new GTResourceLocation("textures/gui/basicmachines/bronze_furnace.png"), 176, 166)
            .widget(0, new SlotWidget<IMetaTileEntity>(this.importItems, 0, 53, 25).setOnSlotChanged(this::markDirty))
            .widget(1, new SlotWidget<IMetaTileEntity>(this.exportItems, 0, 107, 25, true, false).setOnSlotChanged(this::markDirty))
            .bindPlayerInventory(player.inventory, 2)
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
    public int getComparatorValue() {
        return 0;
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return false;
    }

    @Override
    public long getEnergyCapacity() {
        return 16000;
    }

    @Override
    public long getOutputAmperage() {
        return 0;
    }

    @Override
    public long getInputAmperage() {
        return 1;
    }
}
