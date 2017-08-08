package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerTileFourSlots extends ContainerMetaTileEntity {

    public ContainerTileFourSlots(InventoryPlayer playerInventory, IGregTechTileEntity tileEntity) {
        super(playerInventory, tileEntity);
        addSlotToContainer(new Slot(tileEntity, 0, 71, 26));
        addSlotToContainer(new Slot(tileEntity, 1, 89, 26));
        addSlotToContainer(new Slot(tileEntity, 2, 71, 44));
        addSlotToContainer(new Slot(tileEntity, 3, 89, 44));
    }
}
