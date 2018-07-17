package gregtech.api.worldobject;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gregtech.api.pipelike.IBaseProperty;
import gregtech.api.pipelike.IPipeLikeTileProperty;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.pipelike.PipeLikeObjectFactory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

@SuppressWarnings("unchecked")
public class WorldPipeNet extends WorldSavedData {

    public static final String DATA_ID = "gregtech.pipe_net";
    private World world;
    private final Multimap<String, PipeNet> pipeNets = HashMultimap.create();
    private final Multimap<PipeLikeObjectFactory, BlockPos> scheduledCheck = HashMultimap.create();

    public static WorldPipeNet getWorldPipeNet(World world) {
        WorldPipeNet nets = (WorldPipeNet) world.loadData(WorldPipeNet.class, DATA_ID);
        if (nets == null) {
            nets = new WorldPipeNet(DATA_ID);
            world.setData(DATA_ID, nets);
        }
        nets.world = world;
        return nets;
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        switch (event.phase) {
            case START: getWorldPipeNet(event.world).onPreTick();
            case END: getWorldPipeNet(event.world).onPostTick();
        }
    }

    private WorldPipeNet(String name) {
        super(name);
    }

    public World getWorld() {
        return world;
    }

    @Nullable
    public <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C>
    PipeNet<Q, P, C> getPipeNetFromPos(BlockPos pos, PipeLikeObjectFactory<Q, P, C> factory) {
        for (PipeNet net : pipeNets.get(factory.name)) if (net.containsNode(pos)) {
            return net;
        }
        return null;
    }

    public <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C>
    PipeNet<Q, P, C> addNodeToAdjacentOrNewNet(BlockPos pos, ITilePipeLike<Q, P> tile, PipeLikeObjectFactory<Q, P, C> factory) {
        List<PipeNet<Q, P, C>> result = new ArrayList<>();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
        next:for (PipeNet net : pipeNets.get(factory.name)) {
            if (net.containsNode(mutablePos)) {
                return net;
            }
            for (EnumFacing facing : EnumFacing.VALUES) {
                mutablePos.move(facing);
                if (net.containsNode(mutablePos)) {
                    result.add(net);
                    mutablePos.move(facing.getOpposite());
                    continue next;
                }
                mutablePos.move(facing.getOpposite());
            }
        }
        if (result.isEmpty()) {
            PipeNet<Q, P, C> net = factory.createPipeNet(this);
            addPipeNet(net);
            result.add(net);
        }
        result.get(0).addNode(pos, tile);
        return PipeNet.mergeNets(result);
    }

    public void removeNodeFromNet(BlockPos pos, PipeLikeObjectFactory factory) {
        for (PipeNet net : pipeNets.get(factory.name)) {
            if (net.removeNode(pos)) break;
        }
    }

    protected void addPipeNet(PipeNet net) {
        pipeNets.put(net.getFactory().name, net);
    }

    protected void removePipeNet(PipeNet net) {
        pipeNets.remove(net.getFactory().name, net);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        pipeNets.clear();
        NBTTagCompound worldNets = nbt.getCompoundTag("GTPipeNets");
        PipeLikeObjectFactory.allFactories.forEach((name, factory) -> {
            worldNets.getTagList(name, TAG_COMPOUND).forEach(nbtBase -> {
                if (nbtBase.getId() == TAG_COMPOUND) {
                    PipeNet net = factory.createPipeNet(this);
                    net.deserializeNBT((NBTTagCompound) nbtBase);
                    pipeNets.put(name, net);
                }
            });
        });
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound worldNets = new NBTTagCompound();
        for (String name : pipeNets.keySet()) {
            NBTTagList nets = new NBTTagList();
            pipeNets.get(name).forEach(net -> nets.appendTag(net.serializeNBT()));
            worldNets.setTag(name, nets);
        }
        compound.setTag("GTPipeNets", worldNets);
        return compound;
    }

    public void addScheduledCheck(PipeLikeObjectFactory factory, BlockPos pos) {
        scheduledCheck.put(factory, pos);
    }

    public void onPreTick() {
        scheduledCheck.forEach((factory, pos) -> {
            ITilePipeLike tile = factory.getTile(world, pos);
            if (tile != null) {
                PipeNet net = addNodeToAdjacentOrNewNet(pos, tile, factory);
                tile.updateInternalConnection();
                net.updateNode(pos, tile);
            } else {
                removeNodeFromNet(pos, factory);
            }
        });
        scheduledCheck.clear();
        for (PipeNet net : pipeNets.values()) {
            if (net.allNodes.isEmpty()) removePipeNet(net);
            else {
                if (net.isAnyAreaLoaded()) net.onPreTick();
            }
        }
    }

    public void onPostTick() {
        for (PipeNet net : pipeNets.values()) {
            if (net.isAnyAreaLoaded()) net.onPostTick();
        }
    }

}
