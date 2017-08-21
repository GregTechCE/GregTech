package gregtech.api.net;

import net.minecraft.network.PacketBuffer;

public class PacketUIWidgetUpdate implements NetworkHandler.Packet {

    public final int widgetId;
    public final PacketBuffer updateData;

    public PacketUIWidgetUpdate(int widgetId, PacketBuffer updateData) {
        this.widgetId = widgetId;
        this.updateData = updateData;
    }

}
