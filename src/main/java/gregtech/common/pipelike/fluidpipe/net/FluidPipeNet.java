package gregtech.common.pipelike.fluidpipe.net;

import gregtech.api.pipenet.MonolithicPipeNet;
import gregtech.api.pipenet.Node;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.Map;

public class FluidPipeNet extends MonolithicPipeNet<FluidPipeProperties> {

    private final FluidNetTank fluidNetTank = new FluidNetTank(this);

    public FluidPipeNet(WorldPipeNet<FluidPipeProperties, FluidPipeNet> world) {
        super(world);
    }

    public FluidTank getFluidNetTank() {
        return fluidNetTank;
    }

    public int getMaxThroughput() {
        if(fluidNetTank.getCapacity() == 0)
            return 0;
        int maxThroughput = nodeData.throughput;
        float fillPercentage = fluidNetTank.getFluidAmount() / fluidNetTank.getCapacity();
        if(fillPercentage < 0.25) {
            //if filled less than 1/4, use 1/4 input rate
            return (int) (maxThroughput * 0.25f);
        } else if(fillPercentage < 0.5f) {
            //if filled less than 1/2, use 1/2 input rate
            return (int) (maxThroughput * 0.5f);
        } else if(fillPercentage < 0.75f) {
            //if filled less than 3/4, use 3/4 input rate
            return (int) (maxThroughput * 0.75);
        } else {
            //otherwise, use full input rate
            return maxThroughput;
        }
    }

    @Override
    protected void onConnectionsUpdate() {
        super.onConnectionsUpdate();
        //monolithic net always contains exactly one kind of nodes, so this is always safe
        int newTankCapacity = nodeData.capacity * allNodes.size();
        fluidNetTank.updateTankCapacity(newTankCapacity);
    }

    @Override
    protected void transferNodeData(Map<BlockPos, Node<FluidPipeProperties>> transferredNodes, PipeNet<FluidPipeProperties> parentNet1) {
        super.transferNodeData(transferredNodes, parentNet1);
        FluidPipeNet parentNet = (FluidPipeNet) parentNet1;
        FluidStack parentFluid = parentNet.getFluidNetTank().getFluid();
        if(parentFluid != null && parentFluid.amount > 0) {
            if(parentNet.getAllNodes().isEmpty()) {
                //if this is merge of pipe nets, just add all fluid to our internal tank
                //use fillInternal to ignore throughput restrictions
                getFluidNetTank().fillInternal(parentFluid, true);
            } else {
                //otherwise, it is donating of some nodes to our net in result of split
                //so, we should estabilish equal amount of fluid in networks
                int firstNetCapacity = getAllNodes().size() * getNodeData().capacity;
                int secondNetCapacity = parentNet.getAllNodes().size() * parentNet.getNodeData().capacity;
                int totalFluidAmount = getFluidNetTank().getFluidAmount() + parentFluid.amount;
                int fluidAmount1 = totalFluidAmount * firstNetCapacity / (firstNetCapacity + secondNetCapacity);
                int fluidAmount2 = totalFluidAmount - fluidAmount1;

                if (fluidAmount1 > 0) {
                    FluidStack fluidStack1 = parentFluid.copy();
                    fluidStack1.amount = fluidAmount1;
                    fluidNetTank.setFluid(fluidStack1);
                } else fluidNetTank.setFluid(null);

                if (fluidAmount2 > 0) {
                    FluidStack fluidStack2 = parentFluid.copy();
                    fluidStack2.amount = fluidAmount2;
                    parentNet.getFluidNetTank().setFluid(fluidStack2);
                } else parentNet.getFluidNetTank().setFluid(null);
            }
        }
    }

    @Override
    protected boolean areNodesCustomContactable(FluidPipeProperties first, FluidPipeProperties second, PipeNet<FluidPipeProperties> secondNodeNet) {
        FluidPipeNet fluidPipeNet = (FluidPipeNet) secondNodeNet;
        return super.areNodesCustomContactable(first, second, secondNodeNet) &&
            (secondNodeNet == null || getFluidNetTank().getFluid() == null || fluidPipeNet.getFluidNetTank().getFluid() == null ||
                getFluidNetTank().getFluid().isFluidEqual(fluidPipeNet.getFluidNetTank().getFluid()));
    }

    @Override
    protected void writeNodeData(FluidPipeProperties nodeData, NBTTagCompound tagCompound) {
        tagCompound.setInteger("capacity", nodeData.capacity);
        tagCompound.setInteger("max_temperature", nodeData.maxFluidTemperature);
        tagCompound.setInteger("throughput", nodeData.throughput);
        tagCompound.setBoolean("opaque", nodeData.opaque);
    }

    @Override
    protected FluidPipeProperties readNodeData(NBTTagCompound tagCompound) {
        int capacity = tagCompound.getInteger("capacity");
        int maxTemperature = tagCompound.getInteger("max_temperature");
        int throughput = tagCompound.getInteger("throughput");
        boolean opaque = tagCompound.getBoolean("opaque");
        return new FluidPipeProperties(capacity, maxTemperature, throughput, opaque);
    }

}
