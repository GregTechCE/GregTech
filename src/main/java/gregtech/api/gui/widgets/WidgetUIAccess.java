package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import net.minecraft.network.PacketBuffer;

import java.util.function.Consumer;

public interface WidgetUIAccess {

    void writeClientAction(Widget widget, int id, Consumer<PacketBuffer> payloadWriter);

    void writeUpdateInfo(Widget widget, int id, Consumer<PacketBuffer> payloadWriter);

}
