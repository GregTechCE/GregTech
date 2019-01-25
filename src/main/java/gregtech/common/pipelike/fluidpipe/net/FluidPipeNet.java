package gregtech.common.pipelike.fluidpipe.net;

import codechicken.multipart.TileMultipart;
import gregtech.api.GTValues;
import gregtech.api.pipenet.MonolithicPipeNet;
import gregtech.api.pipenet.Node;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.util.PerTickIntCounter;
import gregtech.common.multipart.FluidPipeMultiPart;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipe;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FluidPipeNet extends PipeNet<FluidPipeProperties> {

    protected final Map<BlockPos, NetworkSegment> segmentByNodeMap = new HashMap<>();
    protected final Map<BlockPos, FluidTank> activeNodeBufferTanks = new HashMap<>();
    protected FluidTank networkBufferTank;
    protected int maxNetworkCapacity;

    public FluidPipeNet(WorldPipeNet<FluidPipeProperties, FluidPipeNet> world) {
        super(world);
    }

    public FluidTank getFluidNetTank() {
        return fluidNetTank;
    }

    public int getMaxThroughput() {
        if (fluidNetTank.getCapacity() == 0)
            return 0;
        return nodeData.throughput;
    }

    @Override
    protected void onConnectionsUpdate() {
        super.onConnectionsUpdate();

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

    public class ComputedFluidPath {

        private final List<NetworkSegment> segments;
        public final BlockPos destination;
        public final int maxThroughput;
        public final boolean gasProof;
        public final int maxFluidTemperature;

        public ComputedFluidPath(List<NetworkSegment> segments, BlockPos destination) {
            this.segments = segments;
            this.destination = destination;
            int minThroughput = Integer.MAX_VALUE;
            boolean gasProof = true;
            int minTemperature = Integer.MAX_VALUE;
            for (NetworkSegment segment : segments) {
                minThroughput = Math.min(minThroughput, segment.maxThroughput);
                minTemperature = Math.min(minTemperature, segment.maxFluidTemperature);
                gasProof = gasProof && segment.gasProof;
            }
            this.maxThroughput = minThroughput;
            this.maxFluidTemperature = minTemperature;
            this.gasProof = gasProof;
        }

        public boolean canPassFluidSafely(FluidStack fluidStack) {
            return (gasProof || !fluidStack.getFluid().isGaseous(fluidStack)) &&
                (maxFluidTemperature >= fluidStack.getFluid().getTemperature(fluidStack));
        }

        public void destroyPath(FluidStack fluidStack) {
            boolean isGasFluid = fluidStack.getFluid().isGaseous(fluidStack);
            int fluidTemperature = fluidStack.getFluid().getTemperature(fluidStack);
            for (NetworkSegment segment : segments) {
                if((!segment.gasProof && isGasFluid) || fluidTemperature > segment.maxFluidTemperature) {
                    for (BlockPos nodePos : segment.blocks) {
                        Node<FluidPipeProperties> node = allNodes.get(nodePos);
                        FluidPipeProperties properties = node == null ? null : node.data;
                        if(properties == null) continue;
                        boolean shouldSetLeaking = !properties.gasProof && isGasFluid;
                        boolean shouldSetBurning = fluidTemperature > properties.maxFluidTemperature;
                        if(shouldSetBurning || shouldSetLeaking) {
                            destroyFluidPipe(worldData.getWorld(), nodePos, shouldSetBurning, shouldSetLeaking);
                        }
                    }
                }
            }
        }

        public int calculateTransferFluidAmount(int maxAmount, boolean simulate) {
            maxAmount = Math.min(maxAmount, maxThroughput);
            for (NetworkSegment networkSegment : segments) {
                int amountLeft = networkSegment.getThroughputLeftThisTick();
                if (amountLeft == 0) {
                    return 0;
                }
                maxAmount = Math.min(maxAmount, amountLeft);
            }
            if (maxAmount > 0 && !simulate) {
                for (NetworkSegment segment : segments) {
                    segment.incrementThisTickFluidPassed(maxAmount);
                }
            }
            return maxAmount;
        }
    }

    public class NetworkSegment {

        public final List<BlockPos> blocks;
        public final int maxThroughput;
        public final boolean gasProof;
        public final int maxFluidTemperature;
        private final PerTickIntCounter fluidPassedLastTick = new PerTickIntCounter(0);

        private NetworkSegment(List<BlockPos> blocks) {
            this.blocks = blocks;
            int minThroughput = Integer.MAX_VALUE;
            boolean gasProof = true;
            int minTemperature = Integer.MAX_VALUE;
            for (BlockPos blockPos : blocks) {
                FluidPipeProperties nodeData = allNodes.get(blockPos).data;
                minThroughput = Math.min(minThroughput, nodeData.throughput);
                minTemperature = Math.min(minTemperature, nodeData.maxFluidTemperature);
                gasProof = gasProof && nodeData.gasProof;
            }
            this.maxThroughput = minThroughput;
            this.maxFluidTemperature = minTemperature;
            this.gasProof = gasProof;
        }

        public int getThroughputLeftThisTick() {
            int currentPassed = fluidPassedLastTick.get(worldData.getWorld());
            return maxThroughput - currentPassed;
        }

        public void incrementThisTickFluidPassed(int fluidPassed) {
            fluidPassedLastTick.increment(worldData.getWorld(), fluidPassed);
        }
    }

    private static void destroyFluidPipe(World world, BlockPos nodePos, boolean setBurning, boolean setLeaking) {
        TileEntity tileEntity = world.getTileEntity(nodePos);
        if (tileEntity instanceof TileEntityFluidPipe) {
            if (setBurning) {
                world.setBlockState(nodePos, Blocks.FIRE.getDefaultState());
            } else if (setLeaking) {
                world.setBlockToAir(nodePos);
            }
        } else if (Loader.isModLoaded(GTValues.MODID_FMP)) {
            if (tileEntity instanceof TileMultipart) {
                FluidPipeMultiPart part = (FluidPipeMultiPart) getMultipartPipeTile(tileEntity);
                if (part != null) ((TileMultipart) tileEntity).remPart(part);
            }
        }
        Random random = world.rand;
        if (setBurning) {
            TileEntityFluidPipe.spawnParticles(world, nodePos, EnumFacing.UP,
                EnumParticleTypes.FLAME, 3 + random.nextInt(2), random);
            if (random.nextInt(4) == 0) {
                TileEntityFluidPipe.setNeighboursToFire(world, nodePos);
            }
        }
        if (setLeaking && world.rand.nextInt(setBurning ? 3 : 7) == 0) {
            world.createExplosion(null,
                nodePos.getX() + 0.5, nodePos.getY() + 0.5, nodePos.getZ() + 0.5,
                1.0f + world.rand.nextFloat(), false);
        }
    }

    private static IPipeTile<FluidPipeType, FluidPipeProperties> getMultipartPipeTile(TileEntity tileEntity) {
        if (tileEntity instanceof TileMultipart) {
            return (IPipeTile<FluidPipeType, FluidPipeProperties>) ((TileMultipart) tileEntity).jPartList().stream()
                .filter(part -> part instanceof IPipeTile)
                .findFirst().orElse(null);
        }
        return null;
    }
}
