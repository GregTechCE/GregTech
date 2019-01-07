package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import net.minecraft.network.PacketBuffer;

import java.util.function.BooleanSupplier;

public class ServerWidgetGroup extends AbstractWidgetGroup {

    private BooleanSupplier isVisibleGetter;
    private Boolean lastIsVisible;

    public ServerWidgetGroup(BooleanSupplier isVisibleGetter) {
        this.isVisibleGetter = isVisibleGetter;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(lastIsVisible == null || isVisibleGetter.getAsBoolean() != lastIsVisible) {
            this.lastIsVisible = isVisibleGetter.getAsBoolean();
            writeUpdateInfo(2, buffer -> buffer.writeBoolean(lastIsVisible));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if(id == 2) {
            setVisible(buffer.readBoolean());
        }
    }

    @Override
    public void addWidget(Widget widget) {
        super.addWidget(widget);
    }
}
