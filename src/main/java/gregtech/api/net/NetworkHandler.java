package gregtech.api.net;

import gregtech.api.GT_Values;
import gregtech.api.interfaces.tileentity.ICustomDataTile;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.function.Consumer;

public class NetworkHandler {

    public interface Packet {}

    @FunctionalInterface
    public interface PacketEncoder<T extends Packet> {
        void encode(T packet, PacketBuffer byteBuf);
    }

    @FunctionalInterface
    public interface PacketDecoder<T extends Packet> {
        T decode(PacketBuffer byteBuf);
    }

    @FunctionalInterface
    public interface PacketExecutor<T, R extends INetHandler> {
        void execute(T packet, R handler);
    }

    public static final class PacketCodec<T extends Packet> {

        public final PacketEncoder<T> encoder;
        public final PacketDecoder<T> decoder;

        public PacketCodec(PacketEncoder<T> encoder, PacketDecoder<T> decoder) {
            this.encoder = encoder;
            this.decoder = decoder;
        }
    }

    private static final HashMap<Class<? extends Packet>, PacketCodec<? extends Packet>> codecMap = new HashMap<>();
    @SideOnly(Side.CLIENT) private static final HashMap<Class<? extends Packet>, PacketExecutor<? extends Packet, NetHandlerPlayClient>> clientExecutors = new HashMap<>();
    private static final HashMap<Class<? extends Packet>, PacketExecutor<? extends Packet, NetHandlerPlayServer>> serverExecutors = new HashMap<>();
    private static final IntIdentityHashBiMap<Class<? extends Packet>> packetMap = new IntIdentityHashBiMap<>(10);

    public static FMLEventChannel channel;

    private NetworkHandler() {}

    public static void init() {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(GT_Values.MODID);
        channel.register(new NetworkHandler());

        registerPacket(0, PacketCustomTileData.class, new PacketCodec<>(
                (packet, buf) -> {
                    buf.writeBlockPos(packet.tileEntityPos);
                    byte[] data = packet.payload.array();
                    buf.writeInt(data.length);
                    buf.writeBytes(data);
                },
                (buf) -> new PacketCustomTileData(
                        buf.readBlockPos(),
                        new PacketBuffer(buf.readBytes(buf.readInt()))
                )
        ));

        if(FMLCommonHandler.instance().getSide().isClient()) {
            initClient();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void initClient() {
        registerClientExecutor(PacketCustomTileData.class, (packet, handler) -> {
            TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(packet.tileEntityPos);
            if(tileEntity instanceof ICustomDataTile) {
                ((ICustomDataTile) tileEntity).receiveCustomData(packet.payload);
            }
        });
    }

    public static <T extends Packet> void registerPacket(int packetId, Class<T> packetClass, PacketCodec<T> codec) {
        packetMap.put(packetClass, packetId);
        codecMap.put(packetClass, codec);
    }

    @SideOnly(Side.CLIENT)
    public static <T extends Packet> void registerClientExecutor(Class<T> packet, PacketExecutor<T, NetHandlerPlayClient> executor) {
        clientExecutors.put(packet, executor);
    }

    public static <T extends Packet> void registerServerExecutor(Class<T> packet, PacketExecutor<T, NetHandlerPlayServer> executor) {
        serverExecutors.put(packet, executor);
    }

    public static FMLProxyPacket packet2proxy(Packet packet) {
        PacketCodec<Packet> codec = (PacketCodec<Packet>) codecMap.get(packet.getClass());
        PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        buf.writeInt(packetMap.getId(packet.getClass()));
        codec.encoder.encode(packet, buf);
        return new FMLProxyPacket(buf, GT_Values.MODID);
    }

    public static Packet proxy2packet(FMLProxyPacket packet) {
        PacketBuffer payload = (PacketBuffer) packet.payload();
        Class<Packet> packetClass = (Class<Packet>) packetMap.get(payload.readInt());
        PacketCodec<Packet> codec = (PacketCodec<Packet>) codecMap.get(packetClass);
        return codec.decoder.decode(payload);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        Packet packet = proxy2packet(event.getPacket());
        if(clientExecutors.containsKey(packet.getClass())) {
            PacketExecutor<Packet, NetHandlerPlayClient> executor = (PacketExecutor<Packet, NetHandlerPlayClient>) clientExecutors.get(packet.getClass());
            executor.execute(packet, (NetHandlerPlayClient) event.getHandler());
        }
    }

    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        Packet packet = proxy2packet(event.getPacket());
        if(serverExecutors.containsKey(packet.getClass())) {
            PacketExecutor<Packet, NetHandlerPlayServer> executor = (PacketExecutor<Packet, NetHandlerPlayServer>) serverExecutors.get(packet.getClass());
            executor.execute(packet, (NetHandlerPlayServer) event.getHandler());
        }
    }

}
