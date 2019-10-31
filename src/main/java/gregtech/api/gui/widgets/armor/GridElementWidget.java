package gregtech.api.gui.widgets.armor;

import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.Position;
import gregtech.api.util.Size;

import javax.annotation.Nullable;

public abstract class GridElementWidget extends Widget {

    protected final int slotSize;
    protected ElementOrientation orientation = ElementOrientation.TOP;
    protected final Size initialElementSize;
    @Nullable
    protected ComponentGridWidget parentWidget;

    public GridElementWidget(Size elementSize, int slotSize) {
        super(Position.ORIGIN, elementSize);
        this.initialElementSize = elementSize;
        this.slotSize = slotSize;
    }

    public void setParentWidget(ComponentGridWidget parentWidget) {
        this.parentWidget = parentWidget;
    }

    public void setOrientation(ElementOrientation orientation) {
        this.orientation = orientation;
        setSize(TextureArea.transformSize(TextureArea.createOrientation(initialElementSize, orientation.rotationValue), initialElementSize));
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOverElement(mouseX, mouseY)) {
            setOrientation(ElementOrientation.values()[(orientation.ordinal() + 1) % 4]);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public abstract boolean canConnect(ElementOrientation side, Position relativeSlotPos, ConnectionType connectionType);
}
