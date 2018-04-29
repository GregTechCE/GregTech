package gregtech.api.items.metaitem.stats;

import gregtech.api.gui.ModularUI;
import gregtech.api.items.HandUIWrapper;
import net.minecraft.entity.player.EntityPlayer;

public interface IUIManager extends IMetaItemStats {

    /**
     * Creates a UI instance for player opening inventory
     * @param holder wrapper around itemStack that will open ui
     * @param entityPlayer player opening inventory
     * @return freshly created UI instance
     */
    ModularUI createUI(HandUIWrapper holder, EntityPlayer entityPlayer);
}
