package gregtech.common.multipart;

import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipeActive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public class FluidPipeActiveMultiPart extends FluidPipeMultiPart implements ITickable {

    private boolean isActivePart = false;

    FluidPipeActiveMultiPart() {
    }

    public FluidPipeActiveMultiPart(IPipeTile<FluidPipeType, FluidPipeProperties> sourceTile) {
        super(sourceTile);
    }

    public boolean isActivePart() {
        return isActivePart;
    }

    @Override
    public void transferDataFrom(IPipeTile<FluidPipeType, FluidPipeProperties> sourceTile) {
        super.transferDataFrom(sourceTile);
        if(sourceTile instanceof TileEntityFluidPipeActive) {
            setActivePart(((TileEntityFluidPipeActive) sourceTile).isActive());
        } else if(sourceTile instanceof FluidPipeActiveMultiPart) {
            setActivePart(((FluidPipeActiveMultiPart) sourceTile).isActivePart());
        }
    }

    public void setActivePart(boolean activePart) {
        isActivePart = activePart;
    }

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.FLUID_PIPE_ACTIVE_PART_KEY;
    }

    @Override
    public void update() {
        getCoverableImplementation().update();
        if(isActivePart) {
            TileEntityFluidPipeActive.pushFluidsFromTank(this);
        }
    }

    @Override
    public boolean supportsTicking() {
        return true;
    }

    @Override
    public void save(NBTTagCompound tag) {
        super.save(tag);
        tag.setBoolean("ActiveNode", isActivePart);
    }

    @Override
    public void load(NBTTagCompound tag) {
        super.load(tag);
        this.isActivePart = tag.getBoolean("ActiveNode");
    }
}
