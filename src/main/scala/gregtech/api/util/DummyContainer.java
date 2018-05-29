package gregtech.api.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class DummyContainer extends Container {

    public DummyContainer() {
    }

    @Override
    public void detectAndSendChanges() {
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
