package gregtech.api.net;

import net.minecraft.network.PacketBuffer;

import java.util.List;

public class PacketUIOpen implements NetworkHandler.Packet {

    public final int uiFactoryId;
    public final PacketBuffer serializedHolder;
    public final int windowId;
    public final List<PacketUIWidgetUpdate> initialWidgetUpdates;

    public PacketUIOpen(int uiFactoryId, PacketBuffer serializedHolder, int windowId, List<PacketUIWidgetUpdate> initialWidgetUpdates) {
        this.uiFactoryId = uiFactoryId;
        this.serializedHolder = serializedHolder;
        this.windowId = windowId;
        this.initialWidgetUpdates = initialWidgetUpdates;
    }
}
