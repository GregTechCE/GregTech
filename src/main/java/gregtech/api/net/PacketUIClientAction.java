package gregtech.api.net;

import gregtech.api.net.NetworkHandler.Packet;
import net.minecraft.network.PacketBuffer;

public class PacketUIClientAction implements Packet {

    public final int windowId;
    public final int widgetId;
    public final PacketBuffer updateData;

    public PacketUIClientAction(int windowId, int widgetId, PacketBuffer updateData) {
        this.windowId = windowId;
        this.widgetId = widgetId;
        this.updateData = updateData;
    }

}
