package gregtech.common.pipelike;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class EnergyNet extends PipeNet<Insulation, WireProperties, IEnergyContainer> {

    private Map<BlockPos, MutableLong> accumulated = Maps.newHashMap();
    private Queue<BlockPos> burntBlock = Lists.newLinkedList();

    public EnergyNet(WorldPipeNet worldNet) {
        super(CableFactory.INSTANCE, worldNet);
    }

    @Override
    protected void onPreTick(long tickTimer) {
        accumulated.clear();
    }

    @Override
    protected void onPostTick(long tickTimer) {
        World world = worldNets.getWorld();
        if (!world.isRemote) {
            if (!burntBlock.isEmpty()) {
                while (!burntBlock.isEmpty()) {
                    BlockPos pos = burntBlock.poll();
                    world.setBlockToAir(pos);
                    world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                    if (!world.isRemote) {
                        ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            5 + world.rand.nextInt(3), 0.0, 0.0, 0.0, 0.1);
                    }
                }
            }
        }
    }

    @Override
    protected void serializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {}

    @Override
    protected void deserializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {}

    @Override
    protected void transferNodeDataTo(Collection<? extends BlockPos> nodeToTransfer, PipeNet<Insulation, WireProperties, IEnergyContainer> toNet) {
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
            energyContainer.pathsCache = computeRoutePaths(energyContainer.tileEntityCable.getPos(), checkConnectionMask,
                Collectors.summingLong(node -> node.property.getLossPerBlock()), n -> false);
        }
        long amperesUsed = 0L;
        for (RoutePath<WireProperties, ?, Long> path : energyContainer.pathsCache) {
            if (path.getAccumulatedVal() >= voltage) continue; //do not emit if loss is too high
            amperesUsed += dispatchEnergyToNode(path, voltage, amperage - amperesUsed);
            if (amperesUsed == amperage) break; //do not continue if all amperes are exhausted
        }
        return amperesUsed;
    }

    public long dispatchEnergyToNode(RoutePath<WireProperties, ?, Long> path, long voltage, long amperage) {
        long amperesUsed = 0L;
        Node<WireProperties> destination = path.getEndNode();
        int tileMask = destination.getActiveMask();
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
                energyContainer.acceptEnergyFromNetwork(facing.getOpposite(), voltage - path.getAccumulatedVal(), amperage - amperesUsed));
            if(amperesUsed == amperage) break;
        }
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
        long amp = accumulated.compute(node, (pos, a) -> {
            if (a == null) {
                return new MutableLong(amperage);
            } else {
                a.add(amperage);
                return a;
            }
        }).getValue();
        if (voltage > prop.getVoltage() || amp > prop.getAmperage()) burntBlock.add(node);
    }
}
