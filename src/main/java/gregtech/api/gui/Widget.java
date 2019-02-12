package gregtech.api.gui;

import gregtech.api.gui.widgets.WidgetUIAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Widget is functional element of ModularUI
 * It can draw, perform actions, react to key press and mouse
 * It's information is also synced to client
 */
public abstract class Widget {

    protected ModularUI gui;
    protected SizeProvider sizes;
    protected WidgetUIAccess uiAccess;

    public Widget() {
    }

    public void setGui(ModularUI gui) {
        this.gui = gui;
    }

    public void setSizes(SizeProvider sizes) {
        this.sizes = sizes;
    }

    public void setUiAccess(WidgetUIAccess uiAccess) {
        this.uiAccess = uiAccess;
    }

    public static boolean isMouseOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && x + width >= mouseX && y + height >= mouseY;
    }

    /**
     * Called on both sides to initialize widget data
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

    public List<INativeWidget> getNativeWidgets() {
        if(this instanceof INativeWidget) {
            return Collections.singletonList((INativeWidget) this);
        }
        return Collections.emptyList();
    }

    /**
     * Writes data to be sent to client's {@link #readUpdateInfo}
     */
    protected final void writeUpdateInfo(int id, Consumer<PacketBuffer> packetBufferWriter) {
        if(uiAccess != null && gui != null) {
            uiAccess.writeUpdateInfo(this, id, packetBufferWriter);
        }
    }

    @SideOnly(Side.CLIENT)
    protected final void writeClientAction(int id, Consumer<PacketBuffer> packetBufferWriter) {
        if(uiAccess != null) {
            uiAccess.writeClientAction(this, id, packetBufferWriter);
        }
    }

    @SideOnly(Side.CLIENT)
    protected void drawHoveringText(ItemStack itemStack, List<String> tooltip, int maxTextWidth, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiUtils.drawHoveringText(itemStack, tooltip,  mouseX, mouseY,
            sizes.getScreenWidth() - sizes.getGuiLeft(),
            sizes.getScreenHeight() - sizes.getGuiTop(), maxTextWidth, mc.fontRenderer);
    }

    @SideOnly(Side.CLIENT)
    protected void playButtonClickSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
