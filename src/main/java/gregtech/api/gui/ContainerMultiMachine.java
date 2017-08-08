package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerMultiMachine extends ContainerMetaTileEntity {

    public ContainerMultiMachine(InventoryPlayer playerInventory, IGregTechTileEntity tileEntity) {
        super(playerInventory, tileEntity);
        addSlotToContainer(new Slot(tileEntity, 1, 152, 5));
    }

}
