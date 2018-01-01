package gregtech.api.gui;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import gregtech.api.gui.widgets.SlotWidget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

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

        public Builder<T> bindPlayerInventory(InventoryPlayer inventoryPlayer, int startWidgetId, ResourceLocation imageLocation) {
            return bindPlayerInventory(inventoryPlayer, startWidgetId, imageLocation, 8, 84);
        }

        public Builder<T> bindPlayerInventory(InventoryPlayer inventoryPlayer, int startWidgetId, ResourceLocation imageLocation, int x, int y) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 9; col++) {
                    this.widget(startWidgetId + col + (row + 1) * 9,
                        new SlotWidget<T>(new PlayerMainInvWrapper(inventoryPlayer), col + (row + 1) * 9, x + col * 18, y + row * 18)
                            .setImageLocation(imageLocation));
                }
            }
            return bindPlayerHotbar(inventoryPlayer, startWidgetId, imageLocation, x, y + 58);
        }

        public Builder<T> bindPlayerHotbar(InventoryPlayer inventoryPlayer, int startWidgetId, ResourceLocation imageLocation, int x, int y) {
            for (int slot = 0; slot < 9; slot++) {
                this.widget(startWidgetId + slot,
                    new SlotWidget<T>(new PlayerMainInvWrapper(inventoryPlayer), slot, x + slot * 18, y)
                        .setImageLocation(imageLocation));
            }
            return this;
        }

        public ModularUI<T> build(T holder, EntityPlayer player) {
            return new ModularUI<>(widgets.build(), background, width, height, holder, player);
        }

    }

}
