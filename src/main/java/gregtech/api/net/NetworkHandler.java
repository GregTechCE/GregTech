package gregtech.api.net;

import gregtech.api.GTValues;
import gregtech.api.capability.internal.ICustomDataTile;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.UIFactory;
import gregtech.api.gui.impl.ModularUIGui;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

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
        MinecraftForge.EVENT_BUS.register(new ChunkWatchListener());

        registerPacket(1, PacketUIOpen.class, new PacketCodec<>(
                (packet, buf) -> {
                    buf.writeInt(packet.uiFactoryId);
                    buf.writeInt(packet.serializedHolder.readableBytes());
                    buf.writeBytes(packet.serializedHolder);
                    buf.writeInt(packet.widgetsInitData.readableBytes());
                    buf.writeBytes(packet.widgetsInitData);
                    buf.writeInt(packet.windowId);
                },
                (buf) -> new PacketUIOpen(
                        buf.readInt(),
                        new PacketBuffer(buf.readBytes(buf.readInt())),
                        new PacketBuffer(buf.readBytes(buf.readInt())),
                        buf.readInt()
                )
        ));

        registerPacket(2, PacketUIWidgetUpdate.class, new PacketCodec<>(
                (packet, buf) -> {
                    buf.writeInt(packet.widgetId);
                    buf.writeInt(packet.updateData.readableBytes());
                    buf.writeBytes(packet.updateData);
                },
                (buf) -> new PacketUIWidgetUpdate(
                        buf.readInt(),
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
            WorldClient world = Minecraft.getMinecraft().world;
            if (world == null) {
                return;
            }
            TileEntity tileEntity = world.getTileEntity(packet.tileEntityPos);
            if(tileEntity instanceof ICustomDataTile) {
                ((ICustomDataTile) tileEntity).receiveCustomData(packet.payload);
            }
        });
        registerClientExecutor(PacketUIOpen.class, (packet, handler) -> {
            UIFactory<?> uiFactory = UIFactory.FACTORY_REGISTRY.getObjectById(packet.uiFactoryId);
            uiFactory.initClientUI(packet.serializedHolder, packet.widgetsInitData, packet.windowId);
        });
        registerClientExecutor(PacketUIWidgetUpdate.class, (packet, handler) -> {
            GuiScreen activeGui = Minecraft.getMinecraft().currentScreen;
            if(activeGui instanceof ModularUIGui) {
                ModularUI<?> uiOpen = ((ModularUIGui) activeGui).getModularUI();
                uiOpen.guiWidgets.get(packet.widgetId).readUpdateInfo(packet.updateData);
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

    @SuppressWarnings("unchecked")
    public static FMLProxyPacket packet2proxy(Packet packet) {
        PacketCodec<Packet> codec = (PacketCodec<Packet>) codecMap.get(packet.getClass());
        PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        buf.writeInt(packetMap.getId(packet.getClass()));
        codec.encoder.encode(packet, buf);
        return new FMLProxyPacket(buf, GTValues.MODID);
    }

    @SuppressWarnings("unchecked")
    public static Packet proxy2packet(FMLProxyPacket packet) {
        PacketBuffer payload = (PacketBuffer) packet.payload();
        Class<Packet> packetClass = (Class<Packet>) packetMap.get(payload.readInt());
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
