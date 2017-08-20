package gregtech.api.gui.impl;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import java.io.IOException;

public class McGuiContainer extends GuiContainer {

    private final ModularUI<?> modularUI;

    public McGuiContainer(Container inventorySlotsIn, ModularUI modularUI) {
        super(inventorySlotsIn);
        this.modularUI = modularUI;
    }

    @Override
    public void initGui() {
        xSize = modularUI.width;
        ySize = modularUI.height;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        modularUI.guiWidgets.values().stream()
                .filter(widget -> widget.drawPriority >= Widget.SLOT_DRAW_PRIORITY)
                .sorted()
                .forEach(widget -> widget.draw(mouseX, mouseY));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {



        modularUI.guiWidgets.values().stream()
                .filter(widget -> widget.drawPriority < Widget.SLOT_DRAW_PRIORITY)
                .sorted()
                .forEach(widget -> widget.draw(mouseX, mouseY));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        modularUI.guiWidgets.values().forEach(widget -> widget.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        modularUI.guiWidgets.values().forEach(widget -> widget.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        modularUI.guiWidgets.values().forEach(widget -> widget.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        modularUI.guiWidgets.values().forEach(widget -> widget.keyTyped(typedChar, keyCode));
    }

}
