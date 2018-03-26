package gregtech.api.gui;

import gregtech.api.net.NetworkHandler;
import gregtech.api.net.PacketUIWidgetUpdate;
import gregtech.api.util.GTLog;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Consumer;

/**
 * Widget is functional element of ModularUI
 * It can draw, perform actions, react to key press and mouse
 * It's information is also synced to client
 * @param <T> type of UIHolder this widget requires
 */
public abstract class Widget<T extends IUIHolder> implements Comparable<Widget<T>> {

    public static final int SLOT_DRAW_PRIORITY = 1000;

    protected ModularUI<T> gui;
    public final int drawPriority;

    public Widget(int drawPriority) {
        this.drawPriority = drawPriority;
    }

    /**
     * Called on both sides to init widget data
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
     * Called each draw tick if drawPriority > Widget.SLOT_DRAW_PRIORITY to draw this widget in GUI
     *
     * Note that current GL state is ALREADY translated to (guiLeft, guiTop, 0.0)!
     */
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
    }

    /**
     * Called each draw tick if drawPriority <= Widget.SLOT_DRAW_PRIORITY to draw this widget in GUI
     *
     * Note that current GL state is ALREADY translated to (guiLeft, guiTop, 0.0)!
     */
    @SideOnly(Side.CLIENT)
    public void drawInBackground(float partialTicks, int mouseX, int mouseY) {
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

    /**
     * Writes data to be sent to client's {@link #readUpdateInfo}
     */
    protected final void writeUpdateInfo(int id, Consumer<PacketBuffer> packetBufferWriter) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(id);
        packetBufferWriter.accept(packetBuffer);
        int widgetId = gui.guiWidgets.inverse().get(this);
        if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            int currentWindowId = gui.entityPlayer.openContainer.windowId;
            PacketUIWidgetUpdate widgetUpdate = new PacketUIWidgetUpdate(currentWindowId, widgetId, packetBuffer);
            NetworkHandler.channel.sendTo(NetworkHandler.packet2proxy(widgetUpdate), (EntityPlayerMP) gui.entityPlayer);
        } else {
            GTLog.logger.warn("Attempt to call writeUpdateInfo on client side! WID: " + widgetId, new Error());
        }
    }

    @Override
    public int compareTo(Widget<T> widget) {
        return Integer.compare(drawPriority, widget.drawPriority);
    }

}
