package gregtech.common.pipelike.fluidpipes.pipenet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.worldentries.pipenet.PipeNet;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import gregtech.common.pipelike.fluidpipes.FluidPipeFactory;
import gregtech.common.pipelike.fluidpipes.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipes.TypeFluidPipe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;

public class FluidPipeNet extends PipeNet<TypeFluidPipe, FluidPipeProperties, IFluidHandler> {
    public static final int TICK_RATE = 10;

    private Map<BlockPos, Pipe> pipes = Maps.newHashMap();

    public FluidPipeNet(WorldPipeNet worldNet) {
        super(FluidPipeFactory.INSTANCE, worldNet);
    }

    @Override
    public void updateNode(BlockPos pos, ITilePipeLike<TypeFluidPipe, FluidPipeProperties> tile) {
        super.updateNode(pos, tile);
        FluidPipeProperties properties = tile.getTileProperty();
        if (properties.getMultiple() > 1) {
            int[] entrance = new int[6];
            boolean create = false;
            for (EnumFacing facing : EnumFacing.VALUES) {
                int index = facing.getIndex();
                entrance[index] = FluidPipeFactory.INSTANCE.getMultiPipeAccessAtSide(tile, facing) - 1;
                if (entrance[index] >= 0) create = true;
            }
            if (create) {
                Pipe pipe = pipes.computeIfAbsent(pos, p -> Pipe.create(properties));
                if (pipe instanceof Pipe.MultiPipe) {
                    System.arraycopy(entrance, 0, ((Pipe.MultiPipe) pipe).entrances, 0, 6);
                }
            }
        }
    }

    @Override
    protected void transferNodeDataTo(Collection<? extends BlockPos> nodeToTransfer, PipeNet<TypeFluidPipe, FluidPipeProperties, IFluidHandler> toNet) {
        FluidPipeNet net = (FluidPipeNet) toNet;
        nodeToTransfer.forEach(node -> {
            if (pipes.containsKey(node)) net.pipes.put(node, pipes.get(node));
        });
    }

    @Override
    protected void removeData(BlockPos pos) {
        pipes.remove(pos);
    }

    public static final IFluidTankProperties[] EMPTY = new IFluidTankProperties[0];

    public IFluidTankProperties[] getTankProperties(BlockPos pos, EnumFacing facing) {
        Pipe pipe = pipes.get(pos);
        return pipe == null ? EMPTY : pipe.getTankProperties(facing);
    }

    public int fill(BlockPos pos, EnumFacing fromDir, FluidStack stack, boolean doFill) {
        if (!allNodes.containsKey(pos)) return 0;
        BufferTank tank = pipes.computeIfAbsent(pos, p -> Pipe.create(allNodes.get(p).property)).getAccessibleTank(fromDir);
        if (tank == null) return 0;
        int filled = tank.fill(stack, fromDir, doFill);
        if (doFill && filled > 0) {
            FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(new FluidStack(stack, filled), worldNets.getWorld(), pos, tank, filled));
        }
        return filled;
    }

    public FluidStack drain(BlockPos pos, EnumFacing fromDir, FluidStack stack, boolean doDrain) {
        return drain(pos, fromDir, stack, stack.amount, doDrain);
    }

    public FluidStack drain(BlockPos pos, EnumFacing fromDir, int amount, boolean doDrain) {
        return drain(pos, fromDir, null, amount, doDrain);
    }

    private FluidStack drain(BlockPos pos, EnumFacing fromDir, FluidStack stack, int amount, boolean doDrain) {
        if (!pipes.containsKey(pos)) return null;
        BufferTank tank = pipes.get(pos).getAccessibleTank(fromDir);
        if (tank == null || tank.getFluidAmount() <= 0 || (stack != null && !stack.isFluidEqual(tank.bufferedStack))) return null;
        FluidStack drained = tank.drain(amount, doDrain);
        if (doDrain && drained != null && drained.amount > 0) {
            FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(drained.copy(), worldNets.getWorld(), pos, tank, drained.amount));
        }
        return drained;
    }

    @Override
    protected void serializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {
        Pipe pipe = pipes.get(pos);
        if (pipe != null && !pipe.removable()) {
            nodeTag.setTag("Pipe", pipe.serializeNBT());
        }
    }

    @Override
    protected void deserializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {
        if (nodeTag.hasKey("Pipe")) {
            Pipe pipe = Pipe.create(allNodes.get(pos).property);
            pipe.deserializeNBT(nodeTag.getCompoundTag("Pipe"));
            pipes.put(pos, pipe);
        }
    }

    private Pipe getOrCreate(BlockPos pos) {
        return pipes.computeIfAbsent(pos.toImmutable(), p -> Pipe.create(allNodes.get(p).property));
    }

    private Map<Triple<BlockPos, BufferTank, FluidStack>, EnumMap<EnumFacing, BufferTank>> snapshots = Maps.newHashMap();

    @Override
    protected void onPreTick(long tickTimer) {
        if (!pipes.isEmpty()) {
            BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();
            Map<Pair<BufferTank, FluidStack>, EnumMap<EnumFacing, BufferTank>> toMove = Maps.newHashMap();
            EnumMap<EnumFacing, BufferTank> sideTanks = new EnumMap<>(EnumFacing.class);
            Maps.newHashMap(pipes).forEach((p, pipe) -> {
                Node<FluidPipeProperties> node = allNodes.get(p);
                boolean isActive = node.isActive();
                switch (pipe.getType()) {
                    case NORMAL:
                        BufferTank tank = ((Pipe.NormalPipe) pipe).tank;
                        if (tank.canFluidMove()) {
                            boolean move = false;
                            boolean active = false;
                            for (EnumFacing facing : EnumFacing.VALUES) if (tank.isFacingValid(facing.getOpposite())) {
                                pos.setPos(p).move(facing);
                                BufferTank sideTank = null;
                                if (isActive && (node.getActiveMask() & 1 << facing.getIndex()) != 0
                                    || allNodes.containsKey(pos)
                                    && PipeNet.<FluidPipeProperties>adjacent().test(node, facing, allNodes.get(pos))
                                    && (sideTank = getOrCreate(pos).getAccessibleTank(facing)) != null
                                    && sideTank.fill(tank.bufferedStack, facing, false) > 0) {
                                    sideTanks.put(facing, sideTank);
                                    move = true;
                                    if (sideTank == null) active = true;
                                }
                            }
                            if (move) {
                                if (active) {
                                    snapshots.put(Triple.of(p, tank, tank.bufferedStack.copy()), new EnumMap<>(sideTanks));
                                } else {
                                    toMove.put(Pair.of(tank, tank.bufferedStack.copy()), new EnumMap<>(sideTanks));
                                }
                            }
                            sideTanks.clear();
                        }
                        break;
                    case MULTIPLE:
                        BufferTank[] tanks = ((Pipe.MultiPipe) pipe).tanks;
                        for (int i = 0; i < tanks.length; i++) if (tanks[i].canFluidMove()) {
                            boolean move = false;
                            boolean active = false;
                            for (EnumFacing facing : EnumFacing.VALUES) {
                                boolean isOpen = i == ((Pipe.MultiPipe) pipe).entrances[facing.getOpposite().getIndex()];
                                pos.setPos(p).move(facing);
                                if (tanks[i].isFacingValid(facing)
                                    && (isActive && (node.getActiveMask() & 1 << facing.getIndex()) != 0
                                    || allNodes.containsKey(pos) && PipeNet.<FluidPipeProperties>adjacent().test(node, facing, allNodes.get(pos)))) {
                                    Pipe sidePipe = getOrCreate(pos);
                                    BufferTank sideTank = null;
                                    boolean flag = false;
                                    if (isActive && sidePipe == null) {
                                        flag = isOpen;
                                    } else switch (sidePipe.getType()) {
                                        case NORMAL:
                                            flag = isOpen && (sideTank = ((Pipe.NormalPipe) sidePipe).tank).fill(tanks[i].bufferedStack, facing, false) > 0;
                                            break;
                                        case MULTIPLE:
                                            flag = (sideTank = isOpen ? sidePipe.getAccessibleTank(facing)
                                                    : ((Pipe.MultiPipe) sidePipe).tanks.length == tanks.length ? ((Pipe.MultiPipe) sidePipe).tanks[i] : null) != null
                                                && sideTank.fill(tanks[i].bufferedStack, facing, false) > 0;
                                            break;
                                    }
                                    if (flag) {
                                        sideTanks.put(facing, sideTank);
                                        move = true;
                                        if (sideTank == null) active = true;
                                    }
                                }
                            }
                            if (move) {
                                if (active) {
                                    snapshots.put(Triple.of(p, tanks[i], tanks[i].bufferedStack.copy()), new EnumMap<>(sideTanks));
                                } else {
                                    toMove.put(Pair.of(tanks[i], tanks[i].bufferedStack.copy()), new EnumMap<>(sideTanks));
                                }
                            }
                            sideTanks.clear();
                        }
                        break;
                }
            });
            if (!toMove.isEmpty()) {
                toMove.forEach((pair, map) -> {
                    //Split the fluid stack evenly and then move it.
                    @SuppressWarnings("unchecked")
                    Map.Entry<EnumFacing, BufferTank>[] sideContainers = map.entrySet().toArray(new Map.Entry[0]);
                    if (sideContainers.length == 0) return;
                    BufferTank tank = pair.getLeft();
                    FluidStack stack = pair.getRight();
                    if (!stack.isFluidEqual(tank.bufferedStack)) return;
                    int amount = Math.min(stack.amount, tank.bufferedStack.amount);
                    int quotient = amount / sideContainers.length;
                    int remainder = amount % sideContainers.length;
                    int filled = 0;

                    int[] indices = new int[sideContainers.length];
                    for (int i = 0; i < sideContainers.length; i++) indices[i] = i;
                    for (int i = sideContainers.length, r; i > 0; indices[r] = indices[--i]) {
                        r = WorldPipeNet.rnd.nextInt(i);
                        Map.Entry<EnumFacing, BufferTank> p = sideContainers[indices[r]];
                        filled += p.getValue().fill(new FluidStack(stack, quotient + (remainder-- > 0 ? 1 : 0)), p.getKey(), true);
                    }
                    tank.bufferedStack.amount -= filled;
                });
            }
            pos.release();
        }
    }

    @Override
    protected void update(long tickTimer) {
        World world = worldNets.getWorld();
        if (!world.isRemote) {
            BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();
            if (!snapshots.isEmpty()) {
                EnumMap<EnumFacing, Object> sideTanks = new EnumMap<>(EnumFacing.class);
                snapshots.forEach((triple, sides) -> {
                    BufferTank tank = triple.getMiddle();
                    sides.forEach((facing, sideTank) -> {
                        if (sideTank != null) {
                            sideTanks.put(facing, sideTank);
                        } else {
                            pos.setPos(triple.getLeft()).move(facing);
                            if (!world.isBlockLoaded(pos)) return;
                            TileEntity tile = world.getTileEntity(pos);
                            if (tile == null) return;
                            IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                            if (handler == null) return;
                            if (handler.fill(tank.bufferedStack, false) > 0) {
                                sideTanks.put(facing, handler);
                            }
                        }
                    });
                    if (!sideTanks.isEmpty()) {
                        @SuppressWarnings("unchecked")
                        Map.Entry<EnumFacing, Object>[] sideContainers = sideTanks.entrySet().toArray(new Map.Entry[0]);
                        FluidStack stack = tank.bufferedStack;
                        int quotient = stack.amount / sideContainers.length;
                        int remainder = stack.amount % sideContainers.length;
                        int filled = 0;

                        int[] indices = new int[sideContainers.length];
                        for (int i = 0; i < sideContainers.length; i++) indices[i] = i;
                        for (int i = sideContainers.length, r; i > 0; indices[r] = indices[--i]) {
                            r = WorldPipeNet.rnd.nextInt(i);
                            Map.Entry<EnumFacing, Object> sideContainer = sideContainers[indices[r]];
                            FluidStack toFill = new FluidStack(stack, quotient + (remainder-- > 0 ? 1 : 0));
                            if (sideContainer.getValue() instanceof BufferTank) {
                                filled += ((BufferTank) sideContainer.getValue()).fill(toFill, sideContainer.getKey(), true);
                            } else {
                                filled += ((IFluidHandler) sideContainer.getValue()).fill(toFill, true);
                            }
                        }
                        tank.bufferedStack.amount -= filled;
                    }
                    sideTanks.clear();
                });
                snapshots.clear();
            }
            if (world.getTotalWorldTime() % 5 == 0) {
                List<BlockPos> burnt = Lists.newArrayList();
                pipes.forEach((p, pipe) -> {
                    List<FluidStack> leakedStacks = Lists.newArrayList();
                    FluidPipeProperties properties = allNodes.get(p).property;
                    boolean isGasProof = properties.isGasProof();
                    int heatLimit = properties.getHeatLimit();
                    switch (pipe.getType()) {
                        case NORMAL: {
                            BufferTank tank = ((Pipe.NormalPipe) pipe).tank;
                            if (!tank.isEmpty()) {
                                if (!isGasProof && tank.bufferedStack.getFluid().isGaseous(tank.bufferedStack)) {
                                    int leaked = Math.min(tank.capacity / 50, tank.bufferedStack.amount);
                                    tank.bufferedStack.amount -= leaked;
                                    leakedStacks.add(new FluidStack(tank.bufferedStack, leaked));
                                }
                                if (tank.bufferedStack.getFluid().getTemperature(tank.bufferedStack) > heatLimit) {
                                    burnt.add(p);
                                }
                            }
                        } break;
                        case MULTIPLE: {
                            BufferTank[] tanks = ((Pipe.MultiPipe) pipe).tanks;
                            for (BufferTank tank : tanks) if (!tank.isEmpty()) {
                                if (!isGasProof && tank.bufferedStack.getFluid().isGaseous(tank.bufferedStack)) {
                                    int leaked = Math.min(5, tank.bufferedStack.amount);
                                    tank.bufferedStack.amount -= leaked;
                                    leakedStacks.add(new FluidStack(tank.bufferedStack, leaked));
                                }
                                if (tank.bufferedStack.getFluid().getTemperature(tank.bufferedStack) > heatLimit) {
                                    burnt.add(p);
                                }
                            }
                        }
                    }
                    if (!leakedStacks.isEmpty()) {
                        ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                            p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5,
                            8 + world.rand.nextInt(3), 0.0, 0.0, 0.0, 0.1);
                        //TODO sound
                        for (EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(p).grow(4.0))) {
                            FluidPipeFactory.applyGasLeakingDamage(entity, leakedStacks);
                        }
                    }
                });
                burnt.forEach(p -> {
                    for (EnumFacing facing : EnumFacing.VALUES) if (WorldPipeNet.rnd.nextInt(10) == 0) {
                        pos.setPos(p).move(facing);
                        if (world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
                            world.setBlockToAir(pos);
                            world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                        }
                    }
                    if (WorldPipeNet.rnd.nextInt(100) == 0) {
                        world.setBlockToAir(p);
                        world.setBlockState(p, Blocks.FIRE.getDefaultState());
                    }
                });
            }
            pos.release();
        }
    }

    @Override
    protected void onPostTick(long tickTimer) {
        for (Iterator<Pipe> itr = pipes.values().iterator(); itr.hasNext();) {
            Pipe pipe = itr.next();
            pipe.tick();
            if (pipe.removable()) {
                itr.remove();
            }
        }
    }
}
