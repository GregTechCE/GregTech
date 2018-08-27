package gregtech.common.pipelike.fluidpipe.tile;

import gregtech.api.pipenet.tile.IPipeTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityFluidPipeActive extends TileEntityFluidPipe implements ITickable {

    private static final MutableBlockPos cachePos = new MutableBlockPos();

    @Override
    public void update() {
        pushFluidsFromTank(this, getFluidHandler());
    }

    public static void pushFluidsFromTank(IPipeTile<?, ?> pipeTile, IFluidHandler fluidHandler) {
        FluidStack drainStack = fluidHandler.drain(Integer.MAX_VALUE, false);
        int totalAmountDrained = 0;
        if(drainStack == null) {
            return; //we have nothing to drain
        }
        cachePos.setPos(pipeTile.getPipePos());
        for(EnumFacing side : EnumFacing.VALUES) {
            cachePos.move(side);
            TileEntity tileEntity = pipeTile.getPipeWorld().getTileEntity(cachePos);
            IFluidHandler receiverHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
            if(receiverHandler != null && pipeTile.getPipeBlock().getPipeTileEntity(tileEntity) == null) {
                int canFillAmount = receiverHandler.fill(drainStack, true);
                totalAmountDrained += canFillAmount;
                drainStack.amount -= canFillAmount;
                if(drainStack.amount == 0) {
                    break; //if we have nothing to fill, break
                }
            }
            cachePos.move(side.getOpposite());
        }
        if(totalAmountDrained > 0) {
            //if we drained something, call real drain from fluid handler
            fluidHandler.drain(totalAmountDrained, true);
        }
    }

}
