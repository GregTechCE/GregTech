package gregtech.api.net;

import net.minecraft.network.PacketBuffer;

public class PacketUIOpen implements NetworkHandler.Packet {

    public final int uiFactoryId;
    public final PacketBuffer serializedHolder;
    public final PacketBuffer widgetsInitData;
    public final int windowId;

    public PacketUIOpen(int uiFactoryId, PacketBuffer serializedHolder, PacketBuffer widgetsInitData, int windowId) {
        this.uiFactoryId = uiFactoryId;
        this.serializedHolder = serializedHolder;
        this.widgetsInitData = widgetsInitData;
        this.windowId = windowId;
    }

}
