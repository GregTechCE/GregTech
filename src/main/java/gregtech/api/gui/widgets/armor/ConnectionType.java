package gregtech.api.gui.widgets.armor;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;

public enum ConnectionType {
    POWER(GuiTextures.CONNECTION_TYPE_POWER, 10);

    public final TextureArea icon;
    public final int thickness;

    ConnectionType(TextureArea icon, int thickness) {
        this.icon = icon;
        this.thickness = thickness;
    }
}
