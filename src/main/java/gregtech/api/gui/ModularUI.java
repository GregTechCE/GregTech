package gregtech.api.gui;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModularUI is user-interface implementation concrete, based on widgets system
 * Each widget acts unique and manage different things
 * All widget information is synced to client from server for correct rendering
 * Widgets and UI are both-sided, so widgets should equal on both sides
 * However widget data will sync, widgets themself, background, sizes and other important info will not
 * To open and create ModularUI, see {@link UIFactory}
 *
 *
 * @param <H> type of modular UI holder
 */
public final class ModularUI<H extends IUIHolder> {

    public final ImmutableBiMap<Integer, Widget<H>> guiWidgets;

    public final ResourceLocation backgroundPath;
    public final int width, height;

    /**
     * UIHolder of this modular UI
     * Can be tile entity in world impl or item impl
     */
    public final H holder;
    public final EntityPlayer entityPlayer;

    public ModularUI(ImmutableBiMap<Integer, Widget<H>> guiWidgets, ResourceLocation backgroundPath, int width, int height, H holder, EntityPlayer entityPlayer) {
        this.guiWidgets = guiWidgets;
        this.backgroundPath = backgroundPath;
        this.width = width;
        this.height = height;
        this.holder = holder;
        this.entityPlayer = entityPlayer;
    }

    public void initWidgets() {
        guiWidgets.values().forEach(widget -> {
            widget.gui = this;
            widget.initWidget();
        });
    }

    public void writeWidgetData(PacketBuffer dataBuffer) {
        for(int guiWidgetId : guiWidgets.keySet()) {
            dataBuffer.writeInt(guiWidgetId);
            guiWidgets.get(guiWidgetId).writeInitialSyncInfo(dataBuffer);
        }
    }

    @SideOnly(Side.CLIENT)
    public void readWidgetData(PacketBuffer dataBuffer) {
        for(int i = 0; i < guiWidgets.size(); i++) {
            guiWidgets.get(dataBuffer.readInt()).readInitialSyncInfo(dataBuffer);
        }
    }

    public static <T extends IUIHolder> Builder<T> builder(ResourceLocation background, int width, int height) {
        return new Builder<>(background, width, height);
    }

    /**
     * Simple builder for  ModularUI objects
     * @param <T> UI holder type
     */
    public static class Builder<T extends IUIHolder> {

        private ImmutableBiMap.Builder<Integer, Widget<T>> widgets = ImmutableBiMap.builder();
        private ResourceLocation background;
        private int width, height;

        public Builder(ResourceLocation background, int width, int height) {
            Preconditions.checkNotNull(background);
            this.background = background;
            this.width = width;
            this.height = height;
        }

        public Builder<T> widget(int id, Widget<T> widget) {
            Preconditions.checkNotNull(widget);
            widgets.put(id, widget);
            return this;
        }

        public ModularUI<T> build(T holder, EntityPlayer player) {
            return new ModularUI<>(widgets.build(), background, width, height, holder, player);
        }

    }

}
