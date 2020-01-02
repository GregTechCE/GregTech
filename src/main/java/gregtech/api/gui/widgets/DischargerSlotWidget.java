package gregtech.api.gui.widgets;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class DischargerSlotWidget extends SlotWidget {

    public DischargerSlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition) {
        super(itemHandler, slotIndex, xPosition, yPosition, true, true);
    }

    @Override
    public boolean canPutStack(ItemStack stack) {
        IElectricItem capability = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        return capability != null && capability.canProvideChargeExternally();
    }
}
