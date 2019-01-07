package gregtech.api.gui.widgets.tab;

import gregtech.api.gui.resources.TextureArea;

public interface ITabInfo {

    void renderTab(TextureArea tabTexture, int posX, int posY, int xSize, int ySize, boolean isSelected);

    void renderHoverText(int posX, int posY, int xSize, int ySize, int guiWidth, int guiHeight, boolean isSelected, int mouseX, int mouseY);

}
