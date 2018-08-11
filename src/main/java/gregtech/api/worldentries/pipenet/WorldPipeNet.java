package gregtech.api.worldentries.pipenet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import gregtech.api.net.NetworkHandler;
import gregtech.api.net.PacketPipeNetUpdate;
import gregtech.api.pipelike.IBaseProperty;
import gregtech.api.pipelike.IPipeLikeTileProperty;
import gregtech.api.pipelike.ITilePipeLike;
import gregtech.api.pipelike.PipeFactory;
import gregtech.api.util.XSTR;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

@SuppressWarnings("unchecked")
public class WorldPipeNet extends WorldSavedData {

    public static final String DATA_ID = "gregtech.pipe_net";
    private World world;
    private final Multimap<String, PipeNet> pipeNets = HashMultimap.create();
    private final Multimap<PipeFactory, BlockPos> scheduledCheck = HashMultimap.create();

    public static WorldPipeNet getWorldPipeNet(World world) {
        WorldPipeNet nets = (WorldPipeNet) world.getPerWorldStorage().getOrLoadData(WorldPipeNet.class, DATA_ID);
        if (nets == null) {
            nets = new WorldPipeNet(DATA_ID);
            world.getPerWorldStorage().setData(DATA_ID, nets);
        }
        nets.world = world;
        return nets;
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            World world = event.world;
            getWorldPipeNet(world).update();
        }
    }

    public WorldPipeNet(String name) {
        super(name);
    }

    public World getWorld() {
        return world;
    }

    @Nullable
    private PipeNet getPipeNetFromPos(BlockPos pos, String name) {
        if (!pipeNets.containsKey(name)) return null;
        for (PipeNet net : pipeNets.get(name)) if (net.containsNode(pos)) {
            return net;
        }
        return null;
    }

    @Nullable
    public <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C>
    PipeNet<Q, P, C> getPipeNetFromPos(BlockPos pos, PipeFactory<Q, P, C> factory) {
        return getPipeNetFromPos(pos, factory.name);
    }

    public <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C>
    PipeNet<Q, P, C> addNodeToAdjacentOrNewNet(BlockPos pos, ITilePipeLike<Q, P> tile, PipeFactory<Q, P, C> factory) {
        List<PipeNet<Q, P, C>> result = new ArrayList<>();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
        if (pipeNets.containsKey(factory.name)) {
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
        }
        if (result.isEmpty()) {
            PipeNet<Q, P, C> net = factory.createPipeNet(this);
            addPipeNet(net);
            result.add(net);
        }
        result.get(0).addNode(pos, tile);
        return PipeNet.mergeNets(result);
    }

    public void removeNodeFromNet(BlockPos pos, PipeFactory factory) {
        if (!pipeNets.containsKey(factory.name)) return;
        for (PipeNet net : pipeNets.get(factory.name)) {
            if (net.removeNode(pos)) break;
        }
    }

    protected PipeNet addPipeNet(PipeNet net) {
        pipeNets.put(net.factory.name, net);
        return net;
    }

    protected void removePipeNet(PipeNet net) {
        pipeNets.remove(net.getFactory().name, net);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        pipeNets.clear();
        NBTTagCompound worldNets = nbt.getCompoundTag("GTPipeNets");
        PipeFactory.allFactories.forEach((name, factory) ->
            worldNets.getTagList(name, TAG_COMPOUND).forEach(nbtBase -> {
            if (nbtBase.getId() == TAG_COMPOUND) {
                PipeNet net = factory.createPipeNet(this);
                net.deserializeNBT((NBTTagCompound) nbtBase);
                addPipeNet(net);
            }
        }));
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

    public void addScheduledCheck(PipeFactory factory, BlockPos pos) {
        scheduledCheck.put(factory, pos);
    }

    public void update() {
        scheduledCheck.forEach((factory, pos) -> {
            ITilePipeLike tile = factory.getTile(world, pos);
            if (tile != null) {
                factory.addToPipeNet(world, pos, tile);
                tile.updateInternalConnection();
                tile.updateRenderMask();
            } else {
                removeNodeFromNet(pos, factory);
            }
        });
        scheduledCheck.clear();

        Collection<PipeNet> nets = pipeNets.values();

        Sets.newHashSet(nets).forEach(PipeNet::trySplitPipeNet);
        nets.removeIf(net -> net.allNodes.isEmpty());

        nets.forEach(net -> {
            if (net.isAnyAreaLoaded()) net.onPreTick();
        });

        nets.forEach(net -> { if (net.isAnyAreaLoaded()) net.update(); });

        Sets.newHashSet(nets).forEach(PipeNet::trySplitPipeNet);
        nets.removeIf(net -> net.allNodes.isEmpty());

        nets.forEach(net -> {
            if (net.isAnyAreaLoaded()) net.onPostTick();
        });

        if (world instanceof WorldServer) {
            nets.forEach(net -> {
                if (net.needsClientSync()) {
                    NBTTagCompound updateTag = net.getTagForPacket();
                    if (updateTag != null) {
                        world.playerEntities.stream()
                            .map(EntityPlayerMP.class::cast)
                            .filter(net::isPlayerWatching)
                            .forEach(player -> NetworkHandler.channel
                                .sendTo(NetworkHandler.packet2proxy(new PacketPipeNetUpdate(net.factory.name, net.uid,
                                    new PacketBuffer(Unpooled.buffer()).writeCompoundTag(updateTag))), player));
                    }
                    net.clientSync = false;
                }
            });
        }
    }

    final WeakHashMap<PipeNet, Long> uids = new WeakHashMap<>();
    public static Random rnd = new XSTR();

    private Long tempUID = null;
    long getUID() {
        long uid;
        if (tempUID != null) {
            uid = tempUID;
            tempUID = null;
        } else {
            do {
                uid = rnd.nextLong();
            } while (uids.containsValue(uid));
        }
        return uid;
    }

    private WorldPipeNet withTempUID(long uid) {
        tempUID = uid;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public static void onServerPacket(PacketPipeNetUpdate packet) {
        WorldPipeNet pipeNets = getWorldPipeNet(Minecraft.getMinecraft().world);
        PipeNet net = pipeNets.pipeNets.values().stream().filter(n -> n.uid == packet.uid).findAny()
            .orElseGet(() -> Optional.ofNullable(PipeFactory.allFactories.get(packet.pipeNetName))
                .map(factory -> pipeNets.addPipeNet(factory.createPipeNet(pipeNets.withTempUID(packet.uid))))
                .orElse(null));
        if (net != null) {
            try {
                net.onPacketTag(packet.updateData.readCompoundTag());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
