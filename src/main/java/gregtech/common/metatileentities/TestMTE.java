package gregtech.common.metatileentities;

import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TestMTE extends MetaTileEntity {

    public TestMTE(IMetaTileEntityFactory factory) {
        super(factory);
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
        return new ItemStackHandler(0);
    }

    @Override
    public IItemHandlerModifiable getExportItemHandler() {
        return new ItemStackHandler(0);
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
        return null;
    }

    @Override
    public boolean onRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public int getComparatorValue() {
        return 0;
    }
}
