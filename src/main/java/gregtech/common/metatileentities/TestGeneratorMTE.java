package gregtech.common.metatileentities;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.factory.TieredMetaTileEntityFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TestGeneratorMTE extends TieredMetaTileEntity {

    public TestGeneratorMTE(TieredMetaTileEntityFactory factory) {
        super(factory);
    }

    @Override
    public void onPostTick(long tickTimer) {
        super.onPostTick(tickTimer);
        if (tickTimer % 10 == 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                EnumFacing opposite = facing.getOpposite();
                TileEntity tileEntity = getWorld().getTileEntity(getPos().offset(facing));
                IEnergyContainer container = tileEntity == null ? null : tileEntity.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, opposite);
                if(container != null && container.inputsEnergy(opposite)) {
                    container.acceptEnergyFromNetwork(opposite, getOutputVoltage(), getOutputAmperage());
                }
            }
        }
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
        return new ItemStackHandler(1);
    }

    @Override
    public IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(0);
    }

    @Override
    public FluidTankHandler createImportFluidHandler() {
        return new FluidTankHandler();
    }

    @Override
    public FluidTankHandler createExportFluidHandler() {
        return new FluidTankHandler();
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

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return false;
    }

    @Override
    public long getInputAmperage() {
        return 0;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public long getOutputAmperage() {
        return 1;
    }

    @Override
    public long getEnergyCapacity() {
        return 0;
    }
}
