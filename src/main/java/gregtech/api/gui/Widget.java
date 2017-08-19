package gregtech.api.gui;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;

public abstract class Widget implements Comparable<Widget> {

    public static final int SLOT_DRAW_PRIORITY = 1000;

    protected final TileEntityGui gui;
    public final int drawPriority;

    public Widget(TileEntityGui gui, int drawPriority) {
        this.gui = gui;
        this.drawPriority = drawPriority;
    }

    public abstract void initWidget();

    public abstract void updateWidget();

    public abstract void draw(int mouseX, int mouseY);

    public abstract void writeInitialSyncInfo(PacketBuffer buffer);
    public abstract void readInitialSyncInfo(PacketBuffer buffer);
    public abstract void readSyncInfo(PacketBuffer buffer);

    @Override
    public int compareTo(Widget widget) {
        return Integer.compare(drawPriority, widget.drawPriority);
    }

}
