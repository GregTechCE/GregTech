package gregtech.api.net;

import net.minecraft.network.PacketBuffer;

public class PacketUIWidgetUpdate implements NetworkHandler.Packet {

    public final int windowId;
    public final int widgetId;
    public final PacketBuffer updateData;

    public PacketUIWidgetUpdate(int windowId, int widgetId, PacketBuffer updateData) {
        this.windowId = windowId;
        this.widgetId = widgetId;
        this.updateData = updateData;
    }

}
