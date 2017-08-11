package gregtech.api.gui;

import gregtech.api.capability.internal.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerTileNineSlots extends ContainerMetaTileEntity {

    public ContainerTileNineSlots(InventoryPlayer playerInventory, IGregTechTileEntity tileEntity) {
        super(playerInventory, tileEntity);
        addSlotToContainer(new Slot(tileEntity, 0, 62, 17));
        addSlotToContainer(new Slot(tileEntity, 1, 80, 17));
        addSlotToContainer(new Slot(tileEntity, 2, 98, 17));
        addSlotToContainer(new Slot(tileEntity, 3, 62, 35));
        addSlotToContainer(new Slot(tileEntity, 4, 80, 35));
        addSlotToContainer(new Slot(tileEntity, 5, 98, 35));
        addSlotToContainer(new Slot(tileEntity, 6, 62, 53));
        addSlotToContainer(new Slot(tileEntity, 7, 80, 53));
        addSlotToContainer(new Slot(tileEntity, 8, 98, 53));
    }

}
