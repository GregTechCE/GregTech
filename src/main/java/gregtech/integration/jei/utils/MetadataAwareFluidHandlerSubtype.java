package gregtech.integration.jei.utils;

import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class MetadataAwareFluidHandlerSubtype implements ISubtypeInterpreter {
    @Override
    public String apply(ItemStack itemStack) {
        IFluidHandlerItem fluidHandler = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(fluidHandler != null) {
            IFluidTankProperties fluidTankProperties = fluidHandler.getTankProperties()[0];
            FluidStack fluid = fluidTankProperties.getContents();
            return String.format("%d;f=%s", itemStack.getMetadata(), fluid == null ? "empty" : fluid.getFluid().getName());
        }
        return String.valueOf(itemStack.getMetadata());
    }
}
