package gregtech.api.gui;

import net.minecraft.network.PacketBuffer;

public abstract class Widget implements Comparable<Widget> {

    public static final int SLOT_DRAW_PRIORITY = 1000;

    protected final ModularUI gui;
    public final int drawPriority;

    public Widget(ModularUI gui, int drawPriority) {
        this.gui = gui;
        this.drawPriority = drawPriority;
    }

    /**
     * Called on both sides to init widget data
     */
    public abstract void initWidget();

    /**
     * Called each draw tick to draw this widget in GUI
     */
    public abstract void draw(int mouseX, int mouseY);

    /**
     * Called when mouse is clicked in GUI
     */
    public void mouseClicked(int mouseX, int mouseY, int button) {
    }

    /**
     * Called when mouse is pressed and hold down in GUI
     */
    public void mouseDragged(int mouseX, int mouseY, int button, long timeDragged) {
    }

    /**
     * Called when mouse is released in GUI
     */
    public void mouseReleased(int mouseX, int mouseY, int button) {
    }

    /**
     * Called when key is typed in GUI
     */
    public void keyTyped(char charTyped, int keyCode) {
    }

    /**
     * Write initial server -> client sync data in this method
     */
    public abstract void writeInitialSyncInfo(PacketBuffer buffer);

    /**
     * Read data received from server to client in this method
     */
    public abstract void readInitialSyncInfo(PacketBuffer buffer);

    /**
     * Read data received from server's {@link #writeSyncInfo(PacketBuffer)}
     */
    public abstract void readSyncInfo(PacketBuffer buffer);

    /**
     * Writes data to be sent to client's {@link #readSyncInfo(PacketBuffer)}
     */
    protected final void writeSyncInfo(PacketBuffer packetBuffer) {
        //TODO
    }

    @Override
    public int compareTo(Widget widget) {
        return Integer.compare(drawPriority, widget.drawPriority);
    }

}
