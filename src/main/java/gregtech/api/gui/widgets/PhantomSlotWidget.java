package gregtech.api.gui.widgets;

import gregtech.api.util.SlotUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class PhantomSlotWidget extends SlotWidget {

    public PhantomSlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition) {
        super(itemHandler, slotIndex, xPosition, yPosition, false, true);
    }

    @Override
    public ItemStack slotClick(int dragType, ClickType clickTypeIn, EntityPlayer player) {
        return SlotUtil.slotClickPhantom(slotReference, dragType, clickTypeIn, player);
    }
}
