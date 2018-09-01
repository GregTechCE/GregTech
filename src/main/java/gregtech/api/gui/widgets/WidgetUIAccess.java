package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import net.minecraft.network.PacketBuffer;

public interface WidgetUIAccess {

    void writeClientAction(Widget widget, PacketBuffer payload);

    void writeUpdateInfo(Widget widget, PacketBuffer payload);

}
