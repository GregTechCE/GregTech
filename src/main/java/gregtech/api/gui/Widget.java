package gregtech.api.gui;

import gregtech.api.net.NetworkHandler;
import gregtech.api.net.PacketUIWidgetUpdate;
import gregtech.api.util.GTLog;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    public abstract void initWidget();

    /**
     * Called each draw tick if drawPriority > Widget.SLOT_DRAW_PRIORITY to draw this widget in GUI
     */
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
    }

    /**
     * Called each draw tick if drawPriority <= Widget.SLOT_DRAW_PRIORITY to draw this widget in GUI
     */
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int guiLeft, int guiTop, float partialTicks, int mouseX, int mouseY) {
    }

    protected void drawInBackgroundInternal(int guiLeft, int guiTop, Runnable runnable){
//        RenderHelper.enableGUIStandardItemLighting();
//        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(guiLeft, guiTop, 0.0F);
            runnable.run();
        }
        GlStateManager.popMatrix();
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
     * Write initial server -> client sync data in this method
     */
    public abstract void writeInitialSyncInfo(PacketBuffer buffer);

    /**
     * Read data received from server to client in this method
     */
    @SideOnly(Side.CLIENT)
    public abstract void readInitialSyncInfo(PacketBuffer buffer);

    /**
     * Read data received from server's {@link #writeUpdateInfo(PacketBuffer)}
     */
    @SideOnly(Side.CLIENT)
    public abstract void readUpdateInfo(PacketBuffer buffer);

    /**
     * Writes data to be sent to client's {@link #readUpdateInfo(PacketBuffer)}
     */
    protected final void writeUpdateInfo(PacketBuffer packetBuffer) {
        int widgetId = gui.guiWidgets.inverse().get(this);
        if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            PacketUIWidgetUpdate widgetUpdate = new PacketUIWidgetUpdate(widgetId, packetBuffer);
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
