package gregtech.api.gui.widgets.armor;

import codechicken.lib.vec.Transformation;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.Position;
import gregtech.api.util.PositionedRect;
import gregtech.api.util.Size;

public class GridConnection {

    private final ConnectionType connectionType;
    private final Size parentElementSize;
    private final PositionedRect parentElementRect;
    private final int connectionOffset;
    private final Size elementSize;
    private Position parentPosition = Position.ORIGIN;
    private Position selfPosition;
    private ElementOrientation parentOrientation = ElementOrientation.TOP;
    private ElementOrientation selfOrientation;

    public GridConnection(ConnectionType connectionType, int slotSize, Size elementSize, PositionedRect elementRect, ElementOrientation selfOrientation, int initialSlotIndex) {
        this.connectionOffset = initialSlotIndex * slotSize + (slotSize - connectionType.thickness) / 2;
        this.connectionType = connectionType;
        this.parentElementSize = elementSize;
        this.parentElementRect = elementRect;
        this.selfOrientation = selfOrientation;
        this.elementSize = computeConnectionSize();
        updateOrientation();
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private Size computeConnectionSize() {
        int thickness = connectionType.thickness;
        switch (selfOrientation) {
            case LEFT: return new Size(thickness, parentElementRect.position.x);
            case RIGHT: return new Size(thickness, parentElementSize.width - (parentElementRect.position.x + parentElementRect.size.width));
            case TOP: return new Size(thickness, parentElementRect.position.y);
            case BOTTOM: return new Size(thickness, parentElementSize.height - (parentElementRect.position.y + parentElementRect.size.height));
            default: return Size.ZERO;
        }
    }

    private Position computePosition() {
        Transformation orientation = TextureArea.createOrientation(parentElementSize, parentOrientation.rotationValue);
        PositionedRect parentRect = TextureArea.transformRect(orientation, parentElementRect);
        switch (getOrientation()) {
            case LEFT: return new Position(0, connectionOffset);
            case RIGHT: return new Position(parentRect.position.x + parentRect.size.width, connectionOffset);
            case TOP: return new Position(connectionOffset, 0);
            case BOTTOM: return new Position(connectionOffset, parentRect.position.y + parentRect.size.height);
            default: return Position.ORIGIN;
        }
    }

    public Position getSelfPosition() {
        return selfPosition;
    }

    public Position getPosition() {
        return parentPosition.add(selfPosition);
    }

    public ElementOrientation getOrientation() {
        return ElementOrientation.values()[(parentOrientation.ordinal() + selfOrientation.ordinal()) % 4];
    }

    public void updateParentOrientation(ElementOrientation parentOrientation) {
        this.parentOrientation = parentOrientation;
        updateOrientation();
    }

    private void updateOrientation() {
        this.selfPosition = computePosition();
    }

    public void setParentPosition(Position parentPosition) {
        this.parentPosition = parentPosition;
    }

    public void draw() {
        Position position = getPosition();
        ElementOrientation orientation = getOrientation();
        connectionType.icon.drawRotated(position.x, position.y, elementSize, new PositionedRect(Position.ORIGIN, elementSize), orientation.rotationValue);
    }
}
