package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import net.minecraft.network.PacketBuffer;

import java.util.function.Consumer;

/**
 * Provides access to UI's syncing mechanism, allowing to send arbitrary data and
 * receive it on the client or server side via callbacks provided to widgets
 * IDs are used only by your own widget, use IDs you like
 */
public interface WidgetUIAccess {

    /**
     * Sends action to the server with the ID and data payload supplied
     * Server will receive it in {@link Widget#handleClientAction(int, PacketBuffer)}
     */
    void writeClientAction(Widget widget, int id, Consumer<PacketBuffer> payloadWriter);

    /**
     * Sends update to the client from the server
     * Usually called when internal state changes in {@link Widget#detectAndSendChanges()}
     * Client will receive payload data in {@link Widget#readUpdateInfo(int, PacketBuffer)}
     */
    void writeUpdateInfo(Widget widget, int id, Consumer<PacketBuffer> payloadWriter);

}
