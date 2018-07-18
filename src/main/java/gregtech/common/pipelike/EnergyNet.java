package gregtech.common.pipelike;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.worldentries.PipeNet;
import gregtech.api.worldentries.WorldPipeNet;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.mutable.MutableLong;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EnergyNet extends PipeNet<Insulation, WireProperties, IEnergyContainer> {

    private Map<BlockPos, MutableLong> accumulated = Maps.newHashMap();
    private Collection<BlockPos> burntBlock = Sets.newHashSet();

    public EnergyNet(WorldPipeNet worldNet) {
        super(CableFactory.INSTANCE, worldNet);
    }

    @Override
    protected void onPreTick(long tickTimer) {
        accumulated.clear();
        burntBlock.clear();
    }

    @Override
    protected void onPostTick(long tickTimer) {
        World world = worldNets.getWorld();
        if (!world.isRemote) {
            if (!burntBlock.isEmpty()) {
                burntBlock.forEach(pos -> {
                    world.setBlockToAir(pos);
                    world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                    if (!world.isRemote) {
                        ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            5 + world.rand.nextInt(3), 0.0, 0.0, 0.0, 0.1);
                    }
                });
            }
        }
    }

    @Override
    protected void serializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {}

    @Override
    protected void deserializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {}

    @Override
    protected void transferNodeDataTo(Collection<BlockPos> nodeToTransfer, PipeNet<Insulation, WireProperties, IEnergyContainer> toNet) {
        EnergyNet net = (EnergyNet) toNet;
        for (BlockPos pos : nodeToTransfer) {
            if (accumulated.containsKey(pos)) net.accumulated.put(pos, accumulated.get(pos));
            if (burntBlock.contains(pos)) net.burntBlock.add(pos);
        }
    }

    @Override
    protected void removeData(BlockPos pos) {
        accumulated.remove(pos);
        burntBlock.remove(pos);
    }

    @Nullable
    @Override
    protected NBTTagCompound getUpdateTag() {
        return null;
    }

    @Override
    protected void onUpdate(NBTTagCompound nbt) {}

    public long acceptEnergy(CableEnergyContainer energyContainer, long voltage, long amperage) {
        if (energyContainer.pathsCache == null || energyContainer.lastCachedPathsTime < lastUpdate) {
            energyContainer.lastCachedPathsTime = lastUpdate;
            energyContainer.pathsCache = computeRoutePaths(energyContainer.tileEntityCable.getPos());
        }
        long amperesUsed = 0L;
        for (Map.Entry<LinkedList<BlockPos>, Long> path : energyContainer.pathsCache.entrySet()) {
            if (path.getValue() >= voltage) continue; //do not emit if loss is too high
            amperesUsed += dispatchEnergyToNode(path.getKey(), voltage, amperage - amperesUsed, path.getValue());
            if (amperesUsed == amperage) break; //do not continue if all amperes are exhausted
        }
        return amperesUsed;
    }

    /**
     * @param blockPos start pos
     * @return Linked Map of route paths, arranging by destinations from near to far;
     *              key, the list of route positions, from the start pos to the destination;
     *              value, the total loss of this route
     */
    public Map<LinkedList<BlockPos>, Long> computeRoutePaths(BlockPos blockPos) {
        List<BlockPos> actives = Lists.newArrayList();
        Map<BlockPos, BlockPos> net = bfs(blockPos, checkConnectionMask, actives::add, noshortCircuit);
        Map<LinkedList<BlockPos>, Long> results = Maps.newLinkedHashMap();
        actives.forEach(activePos -> {
            long loss = 0L;
            LinkedList<BlockPos> route = Lists.newLinkedList();
            for (BlockPos pos = activePos; pos != null; pos = net.get(pos)) {
                loss += allNodes.get(pos).left.getLossPerBlock();
                route.addFirst(pos);
            }
            results.put(route, loss);
        });
        return results;
    }

    public long dispatchEnergyToNode(LinkedList<BlockPos> path, long voltage, long amperage, long totalLoss) {
        long amperesUsed = 0L;
        BlockPos destination = path.getLast();
        int tileMask = activeNodes.get(destination);
        BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();
        World world = worldNets.getWorld();
        for (EnumFacing facing : EnumFacing.VALUES) if (0 != (tileMask & 1 << facing.getIndex())) {
            pos.setPos(destination).move(facing);
            if (!world.isBlockLoaded(pos)) continue; //do not allow cables to load chunks
            TileEntity tile = world.getTileEntity(pos);
            if (tile == null) continue;
            IEnergyContainer energyContainer = tile.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, facing.getOpposite());
            if (energyContainer == null) continue;
            amperesUsed += onEnergyPacket(path, voltage,
                energyContainer.acceptEnergyFromNetwork(facing.getOpposite(), voltage - totalLoss, amperage - amperesUsed));
            if(amperesUsed == amperage) break;
        }
        return amperesUsed;
    }

    private long onEnergyPacket(List<BlockPos> path, long voltage, long amperage) {
        for (BlockPos pos : path) {
            onEnergyPacket(pos, voltage, amperage);
            voltage -= allNodes.get(pos).left.getLossPerBlock();
        }
        return amperage;
    }

    private void onEnergyPacket(BlockPos blockPos, long voltage, long amperage) {
        WireProperties prop = allNodes.get(blockPos).left;
        long amp = accumulated.compute(blockPos, (pos, a) -> {
            if (a == null) {
                return new MutableLong(amperage);
            } else {
                a.add(amperage);
                return a;
            }
        }).getValue();
        if (voltage > prop.getVoltage() || amp > prop.getAmperage()) burntBlock.add(blockPos);
    }
}
