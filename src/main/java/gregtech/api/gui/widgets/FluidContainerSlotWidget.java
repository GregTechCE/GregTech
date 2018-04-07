package gregtech.api.gui.widgets;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandlerModifiable;

public class FluidContainerSlotWidget extends SlotWidget {

    public FluidContainerSlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition) {
        super(itemHandler, slotIndex, xPosition, yPosition, true, true);
    }

    @Override
    public boolean canPutStack(ItemStack stack) {
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        return fluidHandlerItem != null && fluidHandlerItem.getTankProperties()[0].getContents() != null;
    }
}
