package gregtech.api.net;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gregtech.api.GT_Values;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

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

    public static final class PacketCodec<T extends Packet> {

        public final PacketEncoder<T> encoder;
        public final PacketDecoder<T> decoder;

        public PacketCodec(PacketEncoder<T> encoder, PacketDecoder<T> decoder) {
            this.encoder = encoder;
            this.decoder = decoder;
        }
    }

    public static final HashMap<Class<? extends Packet>, PacketCodec<? extends Packet>> codecMap = new HashMap<>();
    public static final HashMap<Class<? extends Packet>, Consumer<? extends Packet>> clientExecutors = new HashMap<>();
    public static final HashMap<Class<? extends Packet>, Consumer<? extends Packet>> serverExecutors = new HashMap<>();
    public static final IntIdentityHashBiMap<Class<? extends Packet>> packetMap = new IntIdentityHashBiMap<>(10);

    public static FMLEventChannel channel;

    private NetworkHandler() {}

    public static void init() {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(GT_Values.MODID);
        channel.register(new NetworkHandler());
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
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        Packet packet = proxy2packet(event.getPacket());
        if(clientExecutors.containsKey(packet.getClass())) {
            Consumer<Packet> c = (Consumer<Packet>) clientExecutors.get(packet.getClass());
            c.accept(packet);
        }
    }

    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        Packet packet = proxy2packet(event.getPacket());
        if(serverExecutors.containsKey(packet.getClass())) {
            Consumer<Packet> c = (Consumer<Packet>) serverExecutors.get(packet.getClass());
            c.accept(packet);
        }
    }

}
