package gregtech.api.util;

import net.minecraft.util.Tuple;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GTFluidUtils {

    public static int transferFluids(@Nonnull IFluidHandler sourceHandler, @Nonnull IFluidHandler destHandler, int transferLimit) {
        return transferFluids(sourceHandler, destHandler, transferLimit, fluidStack -> true);
    }

    public static int transferFluids(@Nonnull IFluidHandler sourceHandler, @Nonnull IFluidHandler destHandler, int transferLimit, @Nonnull Predicate<FluidStack> fluidFilter) {

        List<Tuple<IFluidHandler, Predicate<FluidStack>>> transferSet = new ArrayList<>();
        transferSet.add(new Tuple<>(destHandler, fluidFilter));
        return transferFluidsToMultipleHandlers(sourceHandler, transferSet, transferLimit);
    }

    /**
    Used to void fluids through the fluid voiding filter before transferring any non voided fluids into a neighboring fluid handler
    destHandler is an array of fluid handlers, with the handler of the voiding fluid cover first
     **/
    public static int transferFluidsToMultipleHandlers(@Nonnull IFluidHandler sourceHandler, @Nonnull List<Tuple<IFluidHandler, Predicate<FluidStack>>> transferTuple, int transferLimit) {


        int fluidLeftToTransfer = transferLimit;

        for(Tuple<IFluidHandler, Predicate<FluidStack>> transfer : transferTuple) {

            IFluidHandler destination = transfer.getFirst();
            Predicate<FluidStack> filter = transfer.getSecond();

            //Check the contents of the source provider
            for (IFluidTankProperties tankProperties : sourceHandler.getTankProperties()) {
                FluidStack currentFluid = tankProperties.getContents();
                if (currentFluid == null || currentFluid.amount == 0 || (!filter.test(currentFluid))) {
                    continue;
                }

                //Set the amount to be drained on the fluid to the maximum amount
                currentFluid.amount = fluidLeftToTransfer;
                //Simulate the drain to find the resultant Fluid and amount
                FluidStack fluidStack = sourceHandler.drain(currentFluid, false);
                if (fluidStack == null || fluidStack.amount == 0) {
                    continue;
                }

                int canInsertAmount = destination.fill(fluidStack, false);
                if (canInsertAmount > 0) {
                    fluidStack.amount = canInsertAmount;
                    fluidStack = sourceHandler.drain(fluidStack, true);
                    if (fluidStack != null && fluidStack.amount > 0) {
                        destination.fill(fluidStack, true);

                        fluidLeftToTransfer -= fluidStack.amount;
                        if (fluidLeftToTransfer == 0) {
                            break;
                        }
                    }
                }

            }
        }

        return transferLimit - fluidLeftToTransfer;
    }
}
