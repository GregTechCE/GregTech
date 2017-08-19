package gregtech.api.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import java.io.IOException;

public class McGuiContainer extends GuiContainer {

    private final TileEntityGui tileEntityGui;

    public McGuiContainer(Container inventorySlotsIn, TileEntityGui tileEntityGui) {
        super(inventorySlotsIn);
        this.tileEntityGui = tileEntityGui;
    }

    @Override
    public void initGui() {
        xSize = tileEntityGui.width;
        ySize = tileEntityGui.height;
        super.initGui();
        tileEntityGui.guiWidgets.values().forEach(Widget::initWidget);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        tileEntityGui.guiWidgets.values().stream()
                .filter(widget -> widget.drawPriority >= Widget.SLOT_DRAW_PRIORITY)
                .sorted()
                .forEach(widget -> widget.draw(mouseX, mouseY));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        tileEntityGui.guiWidgets.values().stream()
                .filter(widget -> widget.drawPriority < Widget.SLOT_DRAW_PRIORITY)
                .sorted()
                .forEach(widget -> widget.draw(mouseX, mouseY));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        tileEntityGui.guiWidgets.values().forEach(Widget::updateWidget);
    }

}
