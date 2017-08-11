package gregtech.api.gui;

import gregtech.api.capability.internal.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerTileTwelveSlots extends ContainerMetaTileEntity {

    public ContainerTileTwelveSlots(InventoryPlayer playerInventory, IGregTechTileEntity tileEntity) {
        super(playerInventory, tileEntity);
        addSlotToContainer(new Slot(tileEntity, 0, 53, 8));
        addSlotToContainer(new Slot(tileEntity, 1, 71, 8));
        addSlotToContainer(new Slot(tileEntity, 2, 89, 8));
        addSlotToContainer(new Slot(tileEntity, 3, 107, 8));
        addSlotToContainer(new Slot(tileEntity, 4, 53, 26));
        addSlotToContainer(new Slot(tileEntity, 5, 71, 26));
        addSlotToContainer(new Slot(tileEntity, 6, 89, 26));
        addSlotToContainer(new Slot(tileEntity, 7, 107, 26));
        addSlotToContainer(new Slot(tileEntity, 8, 53, 44));
        addSlotToContainer(new Slot(tileEntity, 9, 71, 44));
        addSlotToContainer(new Slot(tileEntity, 10, 89, 44));
        addSlotToContainer(new Slot(tileEntity, 11, 107, 44));
        addSlotToContainer(new Slot(tileEntity, 12, 53, 62));
        addSlotToContainer(new Slot(tileEntity, 13, 71, 62));
        addSlotToContainer(new Slot(tileEntity, 14, 89, 62));
        addSlotToContainer(new Slot(tileEntity, 15, 107, 62));
    }
}
