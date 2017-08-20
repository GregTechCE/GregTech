package gregtech.api.gui;

import com.google.common.collect.ImmutableBiMap;
import net.minecraft.util.ResourceLocation;

public class ModularUI<H> {

    public final ImmutableBiMap<Integer, Widget> guiWidgets;

    public final ResourceLocation backgroundPath;
    public final int width, height;

    /**
     * UIHolder of this modular UI
     * Can be tile entity in world impl or item impl
     */
    public final H holder;

    public ModularUI(ImmutableBiMap<Integer, Widget> guiWidgets, ResourceLocation backgroundPath, int width, int height, H holder) {
        this.guiWidgets = guiWidgets;
        this.backgroundPath = backgroundPath;
        this.width = width;
        this.height = height;
        this.holder = holder;
    }

}
