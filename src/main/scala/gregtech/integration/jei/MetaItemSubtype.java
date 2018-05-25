package gregtech.integration.jei;

import gregtech.api.capability.IElectricItem;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class MetaItemSubtype implements ISubtypeInterpreter {
    @Override
    public String apply(ItemStack itemStack) {
        IFluidHandlerItem fluidHandler = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(fluidHandler != null) {
            IFluidTankProperties fluidTankProperties = fluidHandler.getTankProperties()[0];
            FluidStack fluid = fluidTankProperties.getContents();
            return String.format("%d;f=%s", itemStack.getMetadata(), fluid == null ? "empty" : fluid.getFluid().getName());
        }
        IElectricItem electricItem = itemStack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
        if(electricItem != null) {
            long electricCharge = electricItem.discharge(Long.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
            return String.format("%d;f=%d", itemStack.getMetadata(), electricCharge);
        }
        return String.valueOf(itemStack.getMetadata());
    }
}
