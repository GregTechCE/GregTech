package gregtech.api.gui;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import gregtech.api.gui.widgets.*;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * ModularUI is user-interface implementation concrete, based on widgets system
 * Each widget acts unique and manage different things
 * All widget information is synced to client from server for correct rendering
 * Widgets and UI are both-sided, so widgets should equal on both sides
 * However widget data will sync, widgets themself, background, sizes and other important info will not
 * To open and create ModularUI, see {@link UIFactory}
 *
 */
public final class ModularUI {

    public final ImmutableBiMap<Integer, Widget> guiWidgets;

    public final TextureArea backgroundPath;
    public final int width, height;

    /**
     * UIHolder of this modular UI
     * Can be tile entity in world impl or item impl
     */
    public final IUIHolder holder;
    public final EntityPlayer entityPlayer;
    public boolean isJEIHandled;

    public ModularUI(ImmutableBiMap<Integer, Widget> guiWidgets, TextureArea backgroundPath, int width, int height, IUIHolder holder, EntityPlayer entityPlayer) {
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

    public static Builder defaultBuilder() {
        return new Builder(GuiTextures.BACKGROUND, 176, 166);
    }

    public static Builder builder(TextureArea background, int width, int height) {
        return new Builder(background, width, height);
    }

    /**
     * Simple builder for  ModularUI objects
     */
    public static class Builder {

        private ImmutableBiMap.Builder<Integer, Widget> widgets = ImmutableBiMap.builder();
        private TextureArea background;
        private int width, height;
        private int nextFreeWidgetId = 1000;

        public Builder(TextureArea background, int width, int height) {
            Preconditions.checkNotNull(background);
            this.background = background;
            this.width = width;
            this.height = height;
        }

        public Builder widget(Widget widget) {
            return widget(nextFreeWidgetId++, widget);
        }

        public Builder label(int x, int y, String localizationKey) {
            return widget(new LabelWidget(x, y, localizationKey));
        }

        public Builder label(int x, int y, String localizationKey, int color) {
            return widget(new LabelWidget(x, y, localizationKey, color));
        }

        public Builder dynamicLabel(int x, int y, Supplier<String> text) {
            return widget(new DynamicLabelWidget(x, y, text));
        }

        public Builder dynamicLabel(int x, int y, Supplier<String> text, int color) {
            return widget(new DynamicLabelWidget(x, y, text, color));
        }

        public Builder slot(IItemHandlerModifiable itemHandler, int slotIndex, int x, int y, TextureArea... overlays) {
            return widget(new SlotWidget(itemHandler, slotIndex, x, y).setBackgroundTexture(overlays));
        }

        public Builder tank(IFluidTank fluidTank, int x, int y, int width, int height, TextureArea... backgrounds) {
            return widget(new TankWidget(fluidTank, x, y, width, height).setBackgroundTexture(backgrounds));
        }

        public Builder progressBar(DoubleSupplier progressSupplier, int x, int y, int width, int height, TextureArea texture, MoveType moveType) {
            return widget(new ProgressWidget(progressSupplier, x, y, width, height, texture, moveType));
        }

        public Builder bindPlayerInventory(InventoryPlayer inventoryPlayer) {
            bindPlayerInventory(inventoryPlayer, nextFreeWidgetId, GuiTextures.SLOT);
            nextFreeWidgetId += 36;
            return this;
        }

        public Builder widget(int id, Widget widget) {
            Preconditions.checkNotNull(widget);
            widgets.put(id, widget);
            return this;
        }

        public Builder bindPlayerInventory(InventoryPlayer inventoryPlayer, int startWidgetId) {
            return bindPlayerInventory(inventoryPlayer, startWidgetId, GuiTextures.SLOT);
        }

        public Builder bindPlayerInventory(InventoryPlayer inventoryPlayer, int startWidgetId, TextureArea imageLocation) {
            return bindPlayerInventory(inventoryPlayer, startWidgetId, imageLocation, 8, 84);
        }

        public Builder bindPlayerInventory(InventoryPlayer inventoryPlayer, int startWidgetId, TextureArea imageLocation, int x, int y) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 9; col++) {
                    this.widget(startWidgetId + col + (row + 1) * 9,
                        new SlotWidget(new PlayerMainInvWrapper(inventoryPlayer), col + (row + 1) * 9, x + col * 18, y + row * 18)
                            .setBackgroundTexture(imageLocation));
                }
            }
            return bindPlayerHotbar(inventoryPlayer, startWidgetId, imageLocation, x, y + 58);
        }

        public Builder bindPlayerHotbar(InventoryPlayer inventoryPlayer, int startWidgetId, TextureArea imageLocation, int x, int y) {
            for (int slot = 0; slot < 9; slot++) {
                this.widget(startWidgetId + slot,
                    new SlotWidget(new PlayerMainInvWrapper(inventoryPlayer), slot, x + slot * 18, y)
                        .setBackgroundTexture(imageLocation));
            }
            return this;
        }

        public ModularUI build(IUIHolder holder, EntityPlayer player) {
            return new ModularUI(widgets.build(), background, width, height, holder, player);
        }

    }

}
