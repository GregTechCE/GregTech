package gregtech.common.metatileentities.electric.multiblockpart;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityLargeTurbine;
import net.minecraft.entity.player.EntityPlayer;

public class MetaTileEntityRotorHolder extends MetaTileEntityMultiblockPart {

    private static final int MAXIMUM_SPEED = 6000;
    private int currentSpeed = 0;

    public MetaTileEntityRotorHolder(String metaTileEntityId) {
        super(metaTileEntityId, 0);
    }

    @Override
    public void update() {
        super.update();
        if(((MetaTileEntityLargeTurbine) getController()).isActive()) {

        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityRotorHolder(metaTileEntityId);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
            .bindPlayerInventory(entityPlayer.inventory)
            .build(getHolder(), entityPlayer);
    }
}
