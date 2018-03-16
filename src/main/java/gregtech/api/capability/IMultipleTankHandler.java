package gregtech.api.capability;


import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IMultipleTankHandler extends IFluidHandler {

    int getTanks();

    IFluidTank getTankAt(int index);
}
