package gregtech.api.items.gui;

import gregtech.api.gui.ModularUI;
import net.minecraft.entity.player.EntityPlayer;

public interface ItemUIFactory {

    /**
     * Creates new UI basing on given holder. Holder contains information
     * about item stack and hand, and also player
     */
    ModularUI createUI(PlayerInventoryHolder holder, EntityPlayer entityPlayer);

}
