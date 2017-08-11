package gregtech.api.gui;

import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;

public class ContainerGTTileEntity extends Container {

    public IGregTechTileEntity tileEntity;

    public InventoryPlayer playerInventory;

    public ContainerGTTileEntity(InventoryPlayer playerInventory, IGregTechTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        this.playerInventory = playerInventory;
        if(doesBindPlayerInventory()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
                }
            }
            for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.canPlayerAccess(player) && player.getDistanceSq(tileEntity.getWorldPos()) <= 64*64;
    }

    /**
     * Return true to add player inventory slots
     */
    public boolean doesBindPlayerInventory() {
        return true;
    }


}