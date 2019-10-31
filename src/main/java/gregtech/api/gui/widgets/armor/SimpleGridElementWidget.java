package gregtech.api.gui.widgets.armor;

import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.Position;
import gregtech.api.util.PositionedRect;
import gregtech.api.util.Size;

import java.util.ArrayList;
import java.util.List;

public class SimpleGridElementWidget extends GridElementWidget {

    private final TextureArea backgroundTexture;
    private final PositionedRect elementRect;
    private List<GridConnection> gridConnections = new ArrayList<>();

    public SimpleGridElementWidget(Size elementSize, int slotSize, TextureArea backgroundTexture, PositionedRect elementRect) {
        super(elementSize, slotSize);
        this.backgroundTexture = backgroundTexture;
        this.elementRect = elementRect;
    }

    public void addConnection(int cellIndex, ConnectionType connectionType, ElementOrientation side) {
        GridConnection connection = new GridConnection(connectionType, slotSize, initialElementSize, elementRect, side, cellIndex);
        this.gridConnections.add(connection);
    }

    public List<GridConnection> getGridConnections() {
        return gridConnections;
    }

    @Override
    public boolean canConnect(ElementOrientation side, Position relativeSlotPos, ConnectionType connectionType) {
        Position relativePos = new Position(relativeSlotPos.x * slotSize, relativeSlotPos.y * slotSize);
        for (GridConnection gridConnection : gridConnections) {
            Position position = gridConnection.getSelfPosition();
            if (isMouseOver(relativePos.x, relativePos.y, slotSize, slotSize, position.x, position.y) &&
                gridConnection.getOrientation() == side &&
                gridConnection.getConnectionType() == connectionType) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPositionUpdate() {
        super.onPositionUpdate();
        this.gridConnections.forEach(it -> it.setParentPosition(getPosition()));
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY, IRenderContext context) {
        super.drawInBackground(mouseX, mouseY, context);
        this.backgroundTexture.drawRotated(getPosition().x, getPosition().y, initialElementSize, elementRect, orientation.rotationValue);
        this.gridConnections.forEach(GridConnection::draw);
    }

    @Override
    public void setOrientation(ElementOrientation orientation) {
        super.setOrientation(orientation);
        this.gridConnections.forEach(it -> it.updateParentOrientation(orientation));
    }
}
