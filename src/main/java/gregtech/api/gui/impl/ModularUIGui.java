package gregtech.api.gui.impl;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.net.PacketUIWidgetUpdate;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModularUIGui extends GuiContainer {

    //we don't need CopyOnWriteArrayList here because list access is guarded by lock
    private static List<PacketUIWidgetUpdate> queuingWidgetUpdates = new ArrayList<>();
    private static final Object widgetUpdatesLock = new Object();

    public static void addWidgetUpdate(PacketUIWidgetUpdate packet) {
        synchronized (widgetUpdatesLock) {
            queuingWidgetUpdates.add(packet);
        }
    }

    private final ModularUI modularUI;

    public ModularUI getModularUI() {
        return modularUI;
    }

    public ModularUIGui(ModularUI modularUI) {
        super(new ModularUIContainer(modularUI));
        this.modularUI = modularUI;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.xSize = modularUI.getWidth();
        this.ySize = modularUI.getHeight();
        super.initGui();
        this.modularUI.updateScreenSize(width, height);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        processWidgetPackets();
        modularUI.guiWidgets.values().forEach(Widget::updateScreen);
    }

    private void processWidgetPackets() {
        synchronized (widgetUpdatesLock) {
            for(PacketUIWidgetUpdate packet : queuingWidgetUpdates) {
               handleWidgetUpdate(packet);
            }
            queuingWidgetUpdates.clear();
        }
    }

    public void handleWidgetUpdate(PacketUIWidgetUpdate packet) {
        if(packet.windowId == inventorySlots.windowId) {
            Widget widget = modularUI.guiWidgets.get(packet.widgetId);
            int updateId = packet.updateData.readVarInt();
            if(widget != null) {
                widget.readUpdateInfo(updateId, packet.updateData);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    //for foreground gl state is already translated
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        modularUI.guiWidgets.values().forEach(widget -> {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            widget.drawInForeground(mouseX - guiLeft, mouseY - guiTop);
            GlStateManager.popMatrix();
        });
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate(guiLeft, guiTop, 0.0);
        modularUI.backgroundPath.draw(0, 0, xSize, ySize);
        modularUI.guiWidgets.values().forEach(widget -> {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            widget.drawInBackground(mouseX - guiLeft, mouseY - guiTop);
            GlStateManager.popMatrix();
        });
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean result = modularUI.guiWidgets.values().stream().anyMatch(widget -> widget.mouseClicked(mouseX - guiLeft, mouseY - guiTop, mouseButton));
        if(!result) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        boolean result = modularUI.guiWidgets.values().stream().anyMatch(widget -> widget.mouseDragged(mouseX - guiLeft, mouseY - guiTop, clickedMouseButton, timeSinceLastClick));
        if(!result) {
            super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        boolean result = modularUI.guiWidgets.values().stream().anyMatch(widget -> widget.mouseReleased(mouseX - guiLeft, mouseY - guiTop, state));
        if(!result) {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        boolean result = modularUI.guiWidgets.values().stream().anyMatch(widget -> widget.keyTyped(typedChar, keyCode));
        if(!result) {
            super.keyTyped(typedChar, keyCode);
        }
    }

}
