package gregtech.api.util;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.function.Predicate;

public class FluidTankUtils {

    public static int moveHandlerFluids(IFluidHandler sourceHandler, IFluidHandler destHandler, int transferLimit) {
        int fluidLeftToTransfer = transferLimit;
        for (IFluidTankProperties tankProperties : sourceHandler.getTankProperties()) {
            FluidStack currentFluid = tankProperties.getContents();
            if (currentFluid == null || currentFluid.amount == 0) continue;
            currentFluid.amount = fluidLeftToTransfer;
            FluidStack fluidStack = sourceHandler.drain(currentFluid, false);
            if (fluidStack == null || fluidStack.amount == 0) continue;
            int canInsertAmount = destHandler.fill(fluidStack, false);
            if (canInsertAmount > 0) {
                fluidStack.amount = canInsertAmount;
                fluidStack = sourceHandler.drain(fluidStack, true);
                if (fluidStack != null && fluidStack.amount > 0) {
                    destHandler.fill(fluidStack, true);

                    fluidLeftToTransfer -= fluidStack.amount;
                    if (fluidLeftToTransfer == 0) break;
                }
            }
        }
        return transferLimit - fluidLeftToTransfer;
    }

}
