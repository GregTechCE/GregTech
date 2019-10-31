package gregtech.api.gui.widgets.armor;

import com.google.common.base.Preconditions;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AbstractWidgetGroup;
import gregtech.api.util.Position;
import gregtech.api.util.Size;

public class ComponentGridWidget extends AbstractWidgetGroup {

    private final int gridSizeHorizontal;
    private final int gridSizeVertical;
    private final int slotSize;
    private final int offset;
    private int gridColor = 0xFFFFFFFF;
    private TIntObjectMap<Widget> widgetBySlotIndex = new TIntObjectHashMap<>();
    private TObjectIntMap<Widget> originByWidget = new TObjectIntHashMap<>();

    public ComponentGridWidget(int x, int y, int slotSize, int offset, int gridSizeHorizontal, int gridSizeVertical) {
        super(new Position(x, y), computeGridSize(slotSize, offset, gridSizeHorizontal, gridSizeVertical));
        this.gridSizeHorizontal = gridSizeHorizontal;
        this.gridSizeVertical = gridSizeVertical;
        this.slotSize = slotSize;
        this.offset = offset;
    }

    public Position getWidgetOrigin(Widget widget) {
        int result = originByWidget.get(widget);
        return new Position(result % gridSizeHorizontal, result / gridSizeHorizontal);
    }

    public Widget getWidgetAt(int x, int y) {
        return widgetBySlotIndex.get(index(x, y));
    }

    public void placeWidgetAt(int x, int y, GridElementDef def, ElementOrientation orientation) {
        Preconditions.checkArgument(canPlaceWidgetAt(x, y, def, orientation), "Cannot place widget there!");
        GridElementWidget elementWidget = def.createWidget(orientation, slotSize);
        super.addWidget(elementWidget);
        elementWidget.setParentPosition(computeWidgetPosition(x, y));
        elementWidget.setParentWidget(this);
        setWidgetMapData(x, y, def.getSizeWithOrientation(orientation), elementWidget);
    }

    @Override
    public void removeWidget(Widget widget) {
        super.removeWidget(widget);
        if (widget instanceof GridElementWidget) {
            clearWidgetFromMap(widget);
            ((GridElementWidget) widget).setParentWidget(null);
        }
    }

    private Position computeWidgetPosition(int x, int y) {
        Position position = getPosition().add(new Position(offset, offset));
        return new Position(position.x + slotSize * x, position.y + slotSize * y);
    }

    public boolean canPlaceWidgetAt(int x, int y, GridElementDef elementDef, ElementOrientation orientation) {
        Size actualSize = elementDef.getSizeWithOrientation(orientation);
        for (int i = 0; i < actualSize.width; i++) {
            for (int j = 0; j < actualSize.height; j++) {
                int positionX = x + i;
                int positionY = y + j;
                if (widgetBySlotIndex.containsKey(index(positionX, positionY)) ||
                    !isValidPosition(positionX, positionY)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < gridSizeHorizontal && y < gridSizeVertical;
    }

    private int index(int x, int y) {
        return y * gridSizeHorizontal + x;
    }

    private void setWidgetMapData(int x, int y, Size actualSize, Widget widget) {
        this.originByWidget.put(widget, index(x, y));
        for (int i = 0; i < actualSize.width; i++) {
            for (int j = 0; j < actualSize.height; j++) {
                int positionX = x + i;
                int positionY = y + j;
                this.widgetBySlotIndex.put(index(positionX, positionY), widget);
            }
        }
    }

    private void clearWidgetFromMap(Widget widget) {
        this.originByWidget.remove(widget);
        TIntObjectIterator<Widget> it = this.widgetBySlotIndex.iterator();
        while (it.hasNext()) {
            it.advance();
            if (it.value() == widget) {
                it.remove();
            }
        }
    }

    @Override
    protected void clearAllWidgets() {
        this.widgets.forEach(it -> ((GridElementWidget) it).setParentWidget(null));
        super.clearAllWidgets();
        this.widgetBySlotIndex.clear();
        this.originByWidget.clear();
    }

    public ComponentGridWidget setGridColor(int gridColor) {
        this.gridColor = gridColor;
        return this;
    }

    private static Size computeGridSize(int slotSize, int offset, int slotsX, int slotsY) {
        return new Size(offset * 2 + slotSize * slotsX, offset * 2 + slotSize * slotsY);
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY, IRenderContext context) {
        drawComponentGrid();
        super.drawInBackground(mouseX, mouseY, context);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOverElement(mouseX, mouseY)) {
            int mouseSlotX = (mouseX - getPosition().x - offset) / slotSize;
            int mouseSlotY = (mouseY - getPosition().y - offset) / slotSize;
            if (isValidPosition(mouseSlotX, mouseSlotY)) {
                Widget widget = getWidgetAt(mouseSlotX, mouseSlotY);
                if (button == 1) {
                    if (widget != null) {
                        removeWidget(widget);
                        return true;
                    }
                } else if (button == 0 && widget == null) {
                    GridElementDef elementDef = new GridElementDef(1, 1, GridElementCable::new);
                    if (canPlaceWidgetAt(mouseSlotX, mouseSlotY, elementDef, ElementOrientation.TOP)) {
                        placeWidgetAt(mouseSlotX, mouseSlotY, elementDef, ElementOrientation.TOP);
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawComponentGrid() {
        for (int lineIndexX = 0; lineIndexX <= gridSizeHorizontal; lineIndexX++) {
            int lineX = getPosition().x - 1 + offset + lineIndexX * slotSize;
            int lineStartY = getPosition().y;
            drawSolidRect(lineX, lineStartY, 1, getSize().height, gridColor);
        }
        for (int lineIndexY = 0; lineIndexY <= gridSizeVertical; lineIndexY++) {
            int lineY = getPosition().y + offset + lineIndexY * slotSize;
            int lineStartX = getPosition().x;
            drawSolidRect(lineStartX, lineY, getSize().width, 1, gridColor);
        }
    }
}
