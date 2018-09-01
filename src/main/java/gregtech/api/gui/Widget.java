package gregtech.api.gui;

import gregtech.api.gui.widgets.WidgetUIAccess;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Consumer;

/**
 * Widget is functional element of ModularUI
 * It can draw, perform actions, react to key press and mouse
 * It's information is also synced to client
 */
public abstract class Widget implements Comparable<Widget> {

    public static final int SLOT_DRAW_PRIORITY = 1000;

    protected ModularUI gui;
    protected WidgetUIAccess uiAccess;

    public final int drawPriority;

    public Widget(int drawPriority) {
        this.drawPriority = drawPriority;
    }

    public void setUiAccess(WidgetUIAccess uiAccess) {
        this.uiAccess = uiAccess;
    }

    public static boolean isMouseOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && x + width >= mouseX && y + height >= mouseY;
    }

    /**
     * Called on both sides to freezeRegistry widget data
     */
    public void initWidget() {
    }

    /**
     * Called on serverside to detect changes and synchronize them with clients
     */
    public void detectAndSendChanges() {
    }

    /**
     * Called clientside every tick with this modular UI open
     */
    public void updateScreen() {
    }

    /**
     * Called each draw tick to draw this widget in GUI
     *
     * Note that current GL state is ALREADY translated to (guiLeft, guiTop, 0.0)!
     */
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
    }

    /**
     * Called each draw tick to draw this widget in GUI
     *
     * Note that current GL state is ALREADY translated to (guiLeft, guiTop, 0.0)!
     */
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int mouseX, int mouseY) {
    }

    /**
     * Called when mouse is clicked in GUI
     */
    @SideOnly(Side.CLIENT)
    public void mouseClicked(int mouseX, int mouseY, int button) {
    }

    /**
     * Called when mouse is pressed and hold down in GUI
     */
    @SideOnly(Side.CLIENT)
    public void mouseDragged(int mouseX, int mouseY, int button, long timeDragged) {
    }

    /**
     * Called when mouse is released in GUI
     */
    @SideOnly(Side.CLIENT)
    public void mouseReleased(int mouseX, int mouseY, int button) {
    }

    /**
     * Called when key is typed in GUI
     */
    @SideOnly(Side.CLIENT)
    public void keyTyped(char charTyped, int keyCode) {
    }
    /**
     * Read data received from server's {@link #writeUpdateInfo}
     */
    @SideOnly(Side.CLIENT)
    public void readUpdateInfo(int id, PacketBuffer buffer) {
    }

    public void handleClientAction(int id, PacketBuffer buffer) {
    }

    /**
     * Writes data to be sent to client's {@link #readUpdateInfo}
     */
    protected final void writeUpdateInfo(int id, Consumer<PacketBuffer> packetBufferWriter) {
        if(gui.isJEIHandled) return; //do not send packets on jei guis
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(id);
        packetBufferWriter.accept(packetBuffer);
        if(uiAccess != null) {
            uiAccess.writeUpdateInfo(this, packetBuffer);
        }
    }

    @SideOnly(Side.CLIENT)
    protected final void writeClientAction(int id, Consumer<PacketBuffer> packetBufferWriter) {
        if(gui.isJEIHandled) return; //do not send packets on jei guis
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(id);
        packetBufferWriter.accept(packetBuffer);
        if(uiAccess != null) {
            uiAccess.writeClientAction(this, packetBuffer);
        }
    }

    @Override
    public int compareTo(Widget widget) {
        return Integer.compare(drawPriority, widget.drawPriority);
    }

}
