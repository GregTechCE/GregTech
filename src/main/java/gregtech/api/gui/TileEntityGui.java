package gregtech.api.gui;

import com.google.common.collect.ImmutableBiMap;
import net.minecraft.util.ResourceLocation;

public class TileEntityGui {

    public final ImmutableBiMap<String, Widget> guiWidgets;

    public final ResourceLocation backgroundPath;
    public final int width, height;

    private TileEntityGui(ImmutableBiMap<String, Widget> guiWidgets, ResourceLocation backgroundPath, int width, int height) {
        this.guiWidgets = guiWidgets;
        this.backgroundPath = backgroundPath;
        this.width = width;
        this.height = height;
    }

}
