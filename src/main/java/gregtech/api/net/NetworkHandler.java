package gregtech.api.net;

import gregtech.api.GTValues;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.UIFactory;
import gregtech.api.gui.impl.ModularUIContainer;
import gregtech.api.gui.impl.ModularUIGui;
import gregtech.api.util.GTLog;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;

public class NetworkHandler {

    public interface Packet {
        default FMLProxyPacket toFMLPacket() {
            return packet2proxy(this);
        }
    }

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
    @SideOnly(Side.CLIENT) private static HashMap<Class<? extends Packet>, PacketExecutor<? extends Packet, NetHandlerPlayClient>> clientExecutors;
    private static final HashMap<Class<? extends Packet>, PacketExecutor<? extends Packet, NetHandlerPlayServer>> serverExecutors = new HashMap<>();
    private static final IntIdentityHashBiMap<Class<? extends Packet>> packetMap = new IntIdentityHashBiMap<>(10);

    static {
        if(FMLCommonHandler.instance().getSide().isClient()) {
            clientExecutors = new HashMap<>();
        }
    }

    public static FMLEventChannel channel;

    private NetworkHandler() {}

    public static void init() {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(GTValues.MODID);
        channel.register(new NetworkHandler());

        PacketEncoder<PacketUIWidgetUpdate> widgetUpdateEncoder =  (packet, buf) -> {
            buf.writeVarInt(packet.updateData.readableBytes());
            buf.writeBytes(packet.updateData);
            buf.writeVarInt(packet.windowId);
            buf.writeVarInt(packet.widgetId);
        };

        PacketDecoder<PacketUIWidgetUpdate> widgetUpdateDecoder =  (buf) -> {
            ByteBuf directSliceBuffer = buf.readBytes(buf.readVarInt());
            ByteBuf copiedDataBuffer = Unpooled.copiedBuffer(directSliceBuffer);
            directSliceBuffer.release();
            return new PacketUIWidgetUpdate(
                buf.readVarInt(),
                buf.readVarInt(),
                new PacketBuffer(copiedDataBuffer));
        };

        registerPacket(1, PacketUIOpen.class, new PacketCodec<>(
            (packet, buf) -> {
                buf.writeVarInt(packet.serializedHolder.readableBytes());
                buf.writeBytes(packet.serializedHolder);
                buf.writeVarInt(packet.uiFactoryId);
                buf.writeVarInt(packet.windowId);
                buf.writeVarInt(packet.initialWidgetUpdates.size());
                for(PacketUIWidgetUpdate widgetUpdate : packet.initialWidgetUpdates) {
                    widgetUpdateEncoder.encode(widgetUpdate, buf);
                }
            },
            (buf) -> {
                ByteBuf directSliceBuffer = buf.readBytes(buf.readVarInt());
                ByteBuf copiedDataBuffer = Unpooled.copiedBuffer(directSliceBuffer);
                directSliceBuffer.release();
                int uiFactoryId = buf.readVarInt();
                int windowId = buf.readVarInt();
                ArrayList<PacketUIWidgetUpdate> initialWidgetUpdates = new ArrayList<>();
                int initialWidgetUpdatesCount = buf.readVarInt();
                for(int i = 0; i < initialWidgetUpdatesCount; i++) {
                    initialWidgetUpdates.add(widgetUpdateDecoder.decode(buf));
                }
                return new PacketUIOpen(
                    uiFactoryId,
                    new PacketBuffer(copiedDataBuffer),
                    windowId,
                    initialWidgetUpdates);
            }
        ));

        registerPacket(2, PacketUIWidgetUpdate.class, new PacketCodec<>(
            (packet, buf) -> {
                buf.writeVarInt(packet.updateData.readableBytes());
                buf.writeBytes(packet.updateData);
                buf.writeVarInt(packet.windowId);
                buf.writeVarInt(packet.widgetId);
            },
            (buf) -> {
                ByteBuf directSliceBuffer = buf.readBytes(buf.readVarInt());
                ByteBuf copiedDataBuffer = Unpooled.copiedBuffer(directSliceBuffer);
                directSliceBuffer.release();
                return new PacketUIWidgetUpdate(
                    buf.readVarInt(),
                    buf.readVarInt(),
                    new PacketBuffer(copiedDataBuffer));
            }
        ));

        registerPacket(3, PacketUIClientAction.class, new PacketCodec<>(
            (packet, buf) -> {
                buf.writeVarInt(packet.updateData.readableBytes());
                buf.writeBytes(packet.updateData);
                buf.writeVarInt(packet.windowId);
                buf.writeVarInt(packet.widgetId);
            },
            (buf) -> {
                ByteBuf directSliceBuffer = buf.readBytes(buf.readVarInt());
                ByteBuf copiedDataBuffer = Unpooled.copiedBuffer(directSliceBuffer);
                directSliceBuffer.release();
                return new PacketUIClientAction(
                    buf.readVarInt(),
                    buf.readVarInt(),
                    new PacketBuffer(copiedDataBuffer));
            }
        ));

        registerServerExecutor(PacketUIClientAction.class, (packet, handler) -> {
            Container openContainer = handler.player.openContainer;
            if(openContainer instanceof ModularUIContainer &&
                openContainer.windowId == packet.windowId) {
                ModularUI modularUI = ((ModularUIContainer) openContainer).getModularUI();
                PacketBuffer buffer = packet.updateData;
                modularUI.guiWidgets.get(packet.widgetId).handleClientAction(buffer.readVarInt(), buffer);
            }
        });

        if (FMLCommonHandler.instance().getSide().isClient()) {
            initClient();
        }

    }

    @SideOnly(Side.CLIENT)
    private static void initClient() {
        registerClientExecutor(PacketUIOpen.class, (packet, handler) -> {
            UIFactory<?> uiFactory = UIFactory.FACTORY_REGISTRY.getObjectById(packet.uiFactoryId);
            if(uiFactory == null) {
                GTLog.logger.warn("Couldn't find UI Factory with id '{}'", packet.uiFactoryId);
            } else {
                uiFactory.initClientUI(packet.serializedHolder, packet.windowId, packet.initialWidgetUpdates);
            }
        });
        registerClientExecutor(PacketUIWidgetUpdate.class, (packet, handler) ->
            ModularUIGui.addWidgetUpdate(packet));
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

    @SuppressWarnings("unchecked")
    public static FMLProxyPacket packet2proxy(Packet packet) {
        PacketCodec<Packet> codec = (PacketCodec<Packet>) codecMap.get(packet.getClass());
        PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        buf.writeVarInt(packetMap.getId(packet.getClass()));
        codec.encoder.encode(packet, buf);
        return new FMLProxyPacket(buf, GTValues.MODID);
    }

    @SuppressWarnings("unchecked")
    public static Packet proxy2packet(FMLProxyPacket packet) {
        PacketBuffer payload = (PacketBuffer) packet.payload();
        Class<Packet> packetClass = (Class<Packet>) packetMap.get(payload.readVarInt());
        PacketCodec<Packet> codec = (PacketCodec<Packet>) codecMap.get(packetClass);
        return codec.decoder.decode(payload);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        Packet packet = proxy2packet(event.getPacket());
        if(clientExecutors.containsKey(packet.getClass())) {
            PacketExecutor<Packet, NetHandlerPlayClient> executor = (PacketExecutor<Packet, NetHandlerPlayClient>) clientExecutors.get(packet.getClass());
            executor.execute(packet, (NetHandlerPlayClient) event.getHandler());
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        Packet packet = proxy2packet(event.getPacket());
        if(serverExecutors.containsKey(packet.getClass())) {
            PacketExecutor<Packet, NetHandlerPlayServer> executor = (PacketExecutor<Packet, NetHandlerPlayServer>) serverExecutors.get(packet.getClass());
            executor.execute(packet, (NetHandlerPlayServer) event.getHandler());
        }
    }
}
