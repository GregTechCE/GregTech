package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.Materials;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

public class MetaTileEntityAirCollector extends TieredMetaTileEntity {

    public MetaTileEntityAirCollector(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityAirCollector(metaTileEntityId, getTier());
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, new FluidTank(32000));
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            long energyToConsume = GTValues.V[getTier()];
            if (checkOpenSides() && getTimer() % 20 == 0L && energyContainer.getEnergyStored() >= energyToConsume) {
                int fluidAmount = 500 * (1 << getTier());
                exportFluids.fill(Materials.Air.getFluid(fluidAmount), true);
                energyContainer.removeEnergy(energyToConsume);
            }
            if (getTimer() % 5 == 0) {
                pushFluidsIntoNearbyHandlers(getFrontFacing());
            }
        }
    }

    private boolean checkOpenSides() {
        EnumFacing frontFacing = getFrontFacing();
        for(EnumFacing side : EnumFacing.VALUES) {
            if(side == frontFacing) continue;
            if(getWorld().isAirBlock(getPos().offset(side)))
                return true;
        }
        return false;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        EnumFacing frontFacing = getFrontFacing();
        for(EnumFacing side : EnumFacing.VALUES) {
            Textures.FILTER_OVERLAY.renderSided(side, renderState, translation, pipeline);
        }
        Textures.PIPE_OUT_OVERLAY.renderSided(frontFacing, renderState, translation, pipeline);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }
}
