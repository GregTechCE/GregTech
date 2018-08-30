package gregtech.common.pipelike.fluidpipe.net;

import codechicken.multipart.TileMultipart;
import gregtech.api.GTValues;
import gregtech.api.pipenet.MonolithicPipeNet;
import gregtech.api.pipenet.Node;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.common.pipelike.fluidpipe.LeakableFluidPipeTile;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
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
        return nodeData.throughput;
    }

    public void markNodesAsLeaking(boolean markAsBurningInstead) {
        World world = worldData.getWorld();
        int nodesAmount = 3 + world.rand.nextInt(5);
        ArrayList<BlockPos> allNodes = new ArrayList<>(this.allNodes.keySet());
        for(int i = 0; i < nodesAmount; i++) {
            BlockPos nodePos = allNodes.get(world.rand.nextInt(allNodes.size()));
            allNodes.remove(nodePos);
            LeakableFluidPipeTile tile = getPipeTile(world, nodePos);
            if(tile != null) {
                if(markAsBurningInstead) {
                    tile.markAsBurning();
                } else {
                    tile.markAsLeaking();
                }
            }
            if(allNodes.isEmpty()) {
                //no more nodes left, break
                break;
            }
        }
    }

    private static LeakableFluidPipeTile getPipeTile(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof LeakableFluidPipeTile) {
            return (LeakableFluidPipeTile) tileEntity;
        } else if (Loader.isModLoaded(GTValues.MODID_FMP)) {
            return getMultipartPipeTile(tileEntity);
        }
        return null;
    }

    private static LeakableFluidPipeTile getMultipartPipeTile(TileEntity tileEntity) {
        if(tileEntity instanceof TileMultipart) {
            return (LeakableFluidPipeTile) ((TileMultipart) tileEntity).jPartList().stream()
                .filter(part -> part instanceof LeakableFluidPipeTile)
                .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    protected void onConnectionsUpdate() {
        super.onConnectionsUpdate();
        //monolithic net always contains exactly one kind of nodes, so this is always safe
        int newTankCapacity = nodeData.throughput * allNodes.size();
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
                int firstNetCapacity = getAllNodes().size() * getNodeData().throughput;
                int secondNetCapacity = parentNet.getAllNodes().size() * parentNet.getNodeData().throughput;
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
        tagCompound.setInteger("max_temperature", nodeData.maxFluidTemperature);
        tagCompound.setInteger("throughput", nodeData.throughput);
        tagCompound.setBoolean("gas_proof", nodeData.gasProof);
    }

    @Override
    protected FluidPipeProperties readNodeData(NBTTagCompound tagCompound) {
        int maxTemperature = tagCompound.getInteger("max_temperature");
        int throughput = tagCompound.getInteger("throughput");
        boolean gasProof = tagCompound.getBoolean("gas_proof");
        return new FluidPipeProperties(maxTemperature, throughput, gasProof);
    }

}
