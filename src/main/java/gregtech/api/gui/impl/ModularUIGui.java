package gregtech.api.gui.impl;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.io.IOException;

public class ModularUIGui extends GuiContainer {

    private final ModularUI<?> modularUI;

    public ModularUI<?> getModularUI() {
        return modularUI;
    }

    public ModularUIGui(ModularUI<?> modularUI) {
        super(new ModularUIContainer(modularUI));
        this.modularUI = modularUI;
    }

    @Override
    public void initGui() {
        xSize = modularUI.width;
        ySize = modularUI.height;
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        modularUI.guiWidgets.values().stream()
                .filter(widget -> widget.drawPriority > Widget.SLOT_DRAW_PRIORITY)
                .sorted()
                .forEach(widget -> widget.drawInForeground(mouseX, mouseY));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.renderEngine.bindTexture(modularUI.backgroundPath);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        modularUI.guiWidgets.values().stream()
                .filter(widget -> widget.drawPriority <= Widget.SLOT_DRAW_PRIORITY)
                .sorted()
                .forEach(widget -> widget.drawInBackground(guiLeft, guiTop, partialTicks,  mouseX, mouseY));
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
