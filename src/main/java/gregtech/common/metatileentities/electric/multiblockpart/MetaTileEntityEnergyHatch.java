package gregtech.common.metatileentities.electric.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class MetaTileEntityEnergyHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IEnergyContainer> {

    private final boolean isExportHatch;
    private final IEnergyContainer energyContainer;

    public MetaTileEntityEnergyHatch(String metaTileEntityId, int tier, boolean isExportHatch) {
        super(metaTileEntityId, tier);
        this.isExportHatch = isExportHatch;
        if(isExportHatch) {
            this.energyContainer = EnergyContainerHandler.emitterContainer(this, GTValues.V[tier] * 16L, GTValues.V[tier], 4);
        } else {
            this.energyContainer = EnergyContainerHandler.receiverContainer(this, GTValues.V[tier] * 16L, GTValues.V[tier], 2);
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityEnergyHatch(metaTileEntityId, getTier(), isExportHatch);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        (isExportHatch ? Textures.ENERGY_OUT_MULTI : Textures.ENERGY_IN_MULTI).renderSided(getFrontFacing(), renderState, pipeline);
    }

    @Override
    public MultiblockAbility<IEnergyContainer> getAbility() {
        return isExportHatch ? MultiblockAbility.OUTPUT_ENERGY : MultiblockAbility.INPUT_ENERGY;
    }

    @Override
    public void registerAbilities(List<IEnergyContainer> abilityList) {
        abilityList.add(energyContainer);
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }
}
