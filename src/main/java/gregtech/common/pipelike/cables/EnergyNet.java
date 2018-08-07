package gregtech.common.pipelike.cables;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.worldentries.pipenet.PipeNet;
import gregtech.api.worldentries.pipenet.RoutePath;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class EnergyNet extends PipeNet<Insulation, WireProperties, IEnergyContainer> {

    private Map<BlockPos, EnergyPacket> tickCount = Maps.newHashMap();
    private Map<BlockPos, Statistics> statistics = Maps.newHashMap();
    private Collection<BlockPos> burntBlock = Sets.newHashSet();

    public EnergyNet(WorldPipeNet worldNet) {
        super(CableFactory.INSTANCE, worldNet);
    }

    @Override
    protected void onPreTick(long tickTimer) {
        if (!worldNets.getWorld().isRemote) {
            if (!statistics.isEmpty()) {
                for (Iterator<Map.Entry<BlockPos, Statistics>> itr = statistics.entrySet().iterator(); itr.hasNext();) {
                    Map.Entry<BlockPos, Statistics> e = itr.next();
                    Statistics stat = e.getValue();
                    stat.addData(tickCount.get(e.getKey()));
                    if (stat.isRemovable()) itr.remove();
                }
            }
        }
        tickCount.clear();
    }

    @Override
    protected void update(long tickTimer) {
        World world = worldNets.getWorld();
        if (!world.isRemote) {
            burntBlock.forEach(pos -> {
                world.setBlockToAir(pos);
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    5 + world.rand.nextInt(3), 0.0, 0.0, 0.0, 0.1);
            });
            burntBlock.clear();
        }
    }

    @Override
    protected void transferNodeDataTo(Collection<? extends BlockPos> nodeToTransfer, PipeNet<Insulation, WireProperties, IEnergyContainer> toNet) {
        EnergyNet net = (EnergyNet) toNet;
        for (BlockPos pos : nodeToTransfer) {
            if (tickCount.containsKey(pos)) net.tickCount.put(pos, tickCount.get(pos));
            if (burntBlock.contains(pos)) net.burntBlock.add(pos);
            if (statistics.containsKey(pos)) net.statistics.put(pos, statistics.get(pos));
        }
    }

    @Override
    protected void removeData(BlockPos pos) {
        tickCount.remove(pos);
        //burntBlock.remove(pos);
        statistics.remove(pos);
    }

    public long acceptEnergy(CableEnergyContainer energyContainer, long voltage, long amperage, EnumFacing ignoredFacing) {
        if (energyContainer.pathsCache == null || energyContainer.lastCachedPathsTime < lastUpdate) {
            energyContainer.lastCachedPathsTime = lastUpdate;
            energyContainer.lastWeakUpdate = lastWeakUpdate;
            energyContainer.pathsCache = computeRoutePaths(energyContainer.tileEntityCable.getTilePos(), checkConnectionMask(),
                Collectors.summingLong(node -> node.property.getLossPerBlock()), null);
        } else if (energyContainer.lastWeakUpdate < lastWeakUpdate) {
            energyContainer.lastWeakUpdate = lastWeakUpdate;
            updateNodeChain(energyContainer.pathsCache);
        }
        long amperesUsed = 0L;
        for (RoutePath<WireProperties, ?, Long> path : energyContainer.pathsCache) {
            if (path.getAccumulatedVal() >= voltage) continue; //do not emit if loss is too high
            amperesUsed += dispatchEnergyToNode(path, voltage, amperage - amperesUsed, ignoredFacing);
            if (amperesUsed == amperage) break; //do not continue if all amperes are exhausted
        }
        return amperesUsed;
    }

    public long dispatchEnergyToNode(RoutePath<WireProperties, ?, Long> path, long voltage, long amperage, EnumFacing ignoredFacing) {
        long amperesUsed = 0L;
        Node<WireProperties> destination = path.getEndNode();
        int tileMask = destination.getActiveMask();
        if (ignoredFacing != null && destination.equals(path.getStartNode())) tileMask &= ~(1 << ignoredFacing.getIndex());
        BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();
        World world = worldNets.getWorld();
        int[] indices = {0, 1, 2, 3, 4, 5};
        for (int i = 6, r; i > 0 && amperesUsed < amperage; indices[r] = indices[--i]) { // shuffle & traverse
            r = WorldPipeNet.rnd.nextInt(i);
            EnumFacing facing = EnumFacing.VALUES[indices[r]];
            if (0 != (tileMask & 1 << facing.getIndex())) {
                pos.setPos(destination).move(facing);
                if (!world.isBlockLoaded(pos)) continue; //do not allow cables to load chunks
                TileEntity tile = world.getTileEntity(pos);
                if (tile == null) continue;
                IEnergyContainer energyContainer = tile.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, facing.getOpposite());
                if (energyContainer == null) continue;
                amperesUsed += onEnergyPacket(path, voltage,
                    energyContainer.acceptEnergyFromNetwork(facing.getOpposite(), voltage - path.getAccumulatedVal(), amperage - amperesUsed));
            }
        }
        pos.release();
        return amperesUsed;
    }

    private long onEnergyPacket(RoutePath<WireProperties, ?, Long> path, long voltage, long amperage) {
        for (Node<WireProperties> node : path) {
            onEnergyPacket(node, voltage, amperage);
            voltage -= node.property.getLossPerBlock();
        }
        return amperage;
    }

    private void onEnergyPacket(Node<WireProperties> node, long voltage, long amperage) {
        WireProperties prop = node.property;
        long amp = tickCount.compute(node, (pos, ePacket) -> ePacket == null ? EnergyPacket.create(amperage, voltage) : ePacket.accumulate(amperage, voltage)).amperage;
        if (voltage > prop.getVoltage() || amp > prop.getAmperage()) burntBlock.add(node);
    }

    // amperage, energy
    public double[] getStatisticData(BlockPos pos) {
        return statistics.computeIfAbsent(pos, p -> new Statistics()).getData();
    }

    public static final int STATISTIC_COUNT = 20;

    public static class EnergyPacket {
        public long amperage;
        public double energy;

        EnergyPacket(long amperage, double energy) {
            this.amperage = amperage;
            this.energy = energy;
        }

        static EnergyPacket create(long amperage, long voltage) {
            return new EnergyPacket(amperage, (double) amperage * (double) voltage);
        }

        EnergyPacket accumulate(long amperage, long voltage) {
            this.amperage += amperage;
            this.energy += (double) amperage * (double) voltage;
            return this;
        }
    }

    public static final double[] NO_DATA = {0.0, 0.0};

    static class Statistics {
        long[] amperes = new long[STATISTIC_COUNT];
        double[] energies = new double[STATISTIC_COUNT];
        int pointer = 0;
        int dataCount = 0;
        int countDown = STATISTIC_COUNT * 5;

        Statistics addData(EnergyPacket data) {
            if (data != null) {
                amperes[pointer] = data.amperage;
                energies[pointer] = data.energy;
            } else {
                amperes[pointer] = 0;
                energies[pointer] = 0;
            }
            pointer++;
            if (pointer >= STATISTIC_COUNT) pointer -= STATISTIC_COUNT;
            if (dataCount < STATISTIC_COUNT) dataCount++;
            if (countDown > 0) --countDown;
            return this;
        }

        boolean isRemovable() {
            return countDown == 0;
        }

        double[] getData() {
            if (dataCount == 0) return NO_DATA;
            double amperage = 0.0, energy = 0.0;
            for (int i = 0; i < STATISTIC_COUNT; i++) {
                amperage += amperes[i];
                energy += energies[i];
            }
            countDown = STATISTIC_COUNT * 5;
            return new double[]{amperage / dataCount, energy / amperage};
        }
    }
}
