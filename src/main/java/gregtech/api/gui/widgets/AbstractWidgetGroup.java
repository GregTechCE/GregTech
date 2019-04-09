package gregtech.api.gui.widgets;

import gregtech.api.gui.INativeWidget;
import gregtech.api.gui.Widget;
import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AbstractWidgetGroup extends Widget {

    private List<Widget> widgets = new ArrayList<>();
    private WidgetGroupUIAccess groupUIAccess = new WidgetGroupUIAccess();
    private boolean isInitialized;
    private boolean isVisible = true;

    public AbstractWidgetGroup() {
        super();
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        widgets.stream().flatMap(it -> it.getNativeWidgets().stream()).forEach(it -> it.setEnabled(visible));
    }

    public boolean isVisible() {
        return isVisible;
    }

    protected void addWidget(Widget widget) {
        if (isInitialized) {
            throw new IllegalStateException("Cannot add widgets after initialization!");
        }
        if (widget == this) {
            throw new IllegalArgumentException("Cannot add self");
        }
        if (!widgets.contains(widget)) {
            this.widgets.add(widget);
            widget.setUiAccess(groupUIAccess);
        }
    }

    public boolean isWidgetVisible(Widget widget) {
        return this.isVisible;
    }

    public boolean isWidgetClickable(Widget widget) {
        return isWidgetVisible(widget);
    }

    @Override
    public void initWidget() {
        this.isInitialized = true;
        for (Widget widget : widgets) {
            widget.setGui(gui);
            widget.setSizes(sizes);
            widget.initWidget();
        }
    }

    @Override
    public List<INativeWidget> getNativeWidgets() {
        ArrayList<INativeWidget> nativeWidgets = new ArrayList<>();
        for (Widget widget : widgets) {
            nativeWidgets.addAll(widget.getNativeWidgets());
        }
        if (this instanceof INativeWidget) {
            nativeWidgets.add((INativeWidget) this);
        }
        return nativeWidgets;
    }

    @Override
    public void detectAndSendChanges() {
        for (Widget widget : widgets) {
            widget.detectAndSendChanges();
        }
    }

    @Override
    public void updateScreen() {
        for (Widget widget : widgets) {
            widget.updateScreen();
        }
    }

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        for (Widget widget : widgets) {
            if (isWidgetVisible(widget)) {
                widget.drawInForeground(mouseX, mouseY);
            }
        }
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY) {
        for (Widget widget : widgets) {
            if (isWidgetVisible(widget)) {
                widget.drawInBackground(mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        for (Widget widget : widgets) {
            if (isWidgetClickable(widget)) {
                if (widget.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY, int button, long timeDragged) {
        for (Widget widget : widgets) {
            if (isWidgetClickable(widget)) {
                if (widget.mouseDragged(mouseX, mouseY, button, timeDragged)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        for (Widget widget : widgets) {
            if (isWidgetClickable(widget)) {
                if (widget.mouseReleased(mouseX, mouseY, mouseX)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char charTyped, int keyCode) {
        for (Widget widget : widgets) {
            if (isWidgetClickable(widget)) {
                if (widget.keyTyped(charTyped, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        if (id == 1) {
            int widgetIndex = buffer.readVarInt();
            int widgetUpdateId = buffer.readVarInt();
            Widget widget = widgets.get(widgetIndex);
            widget.readUpdateInfo(widgetUpdateId, buffer);
        }
    }

    @Override
    public void handleClientAction(int id, PacketBuffer buffer) {
        if (id == 1) {
            int widgetIndex = buffer.readVarInt();
            int widgetUpdateId = buffer.readVarInt();
            Widget widget = widgets.get(widgetIndex);
            widget.handleClientAction(widgetUpdateId, buffer);
        }
    }

    private class WidgetGroupUIAccess implements WidgetUIAccess {

        @Override
        public void writeClientAction(Widget widget, int updateId, Consumer<PacketBuffer> dataWriter) {
            AbstractWidgetGroup.this.writeClientAction(1, buffer -> {
                buffer.writeVarInt(widgets.indexOf(widget));
                buffer.writeVarInt(updateId);
                dataWriter.accept(buffer);
            });
        }

        @Override
        public void writeUpdateInfo(Widget widget, int updateId, Consumer<PacketBuffer> dataWriter) {
            AbstractWidgetGroup.this.writeUpdateInfo(1, buffer -> {
                buffer.writeVarInt(widgets.indexOf(widget));
                buffer.writeVarInt(updateId);
                dataWriter.accept(buffer);
            });
        }

    }
}
