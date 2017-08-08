package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerTileSingleSlot extends ContainerMetaTileEntity {

    public ContainerTileSingleSlot(InventoryPlayer playerInventory, IGregTechTileEntity tileEntity) {
        super(playerInventory, tileEntity);
        addSlotToContainer(new Slot(tileEntity, 0, 80, 35));
    }

}
