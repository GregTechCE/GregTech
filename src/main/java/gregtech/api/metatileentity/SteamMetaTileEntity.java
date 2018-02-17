package gregtech.api.metatileentity;

import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.recipes.ModHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class SteamMetaTileEntity extends PaintableMetaTileEntity {

    protected FluidTank steamFluidTank;

    public SteamMetaTileEntity(IMetaTileEntityFactory factory) {
        super(factory);
    }

    @Override
    public FluidTankHandler createImportFluidHandler() {
        return new FluidTankHandler();
    }

    @Override
    public FluidTankHandler createExportFluidHandler() {
        return new FluidTankHandler();
    }

    @Override
    public IFluidHandler createFluidHandler() {
        this.steamFluidTank = new FilteredFluidHandler(getSteamCapacity()).setFillPredicate(ModHandler::isSteam);
        return new FluidHandlerProxy(this.steamFluidTank, this.exportFluids);
    }

    @Override
    public int getComparatorValue() {
        return 0;
    }

    @Override
    public void saveNBTData(NBTTagCompound data) {
        super.saveNBTData(data);
        data.setTag("SteamFluidTank", steamFluidTank.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        super.loadNBTData(data);
        steamFluidTank.readFromNBT(data.getCompoundTag("SteamFluidTank"));
    }

    public int getSteamCapacity() {
        return 16000;
    }
}
