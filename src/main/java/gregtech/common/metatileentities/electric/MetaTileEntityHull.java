package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class MetaTileEntityHull extends TieredMetaTileEntity  {

    public MetaTileEntityHull(String metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
    }

    @Override
    public gregtech.api.metatileentity.MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityHull(metaTileEntityId, getTier());
    }

    @Override
    protected void reinitializeEnergyContainer() {
        long tierVoltage = GTValues.V[getTier()];
        this.energyContainer = new EnergyContainerHandler(tierVoltage * 16L, tierVoltage, 1L, tierVoltage, 1L);
        this.energyContainer.setSideOutputCondition(s -> s == getFrontFacing());
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        Textures.ENERGY_OUT.renderSided(getFrontFacing(), renderState, pipeline);
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return true;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(0);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(0);
    }

    @Override
    protected FluidTankHandler createImportFluidHandler() {
        return new FluidTankHandler();
    }

    @Override
    protected FluidTankHandler createExportFluidHandler() {
        return new FluidTankHandler();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }
}
