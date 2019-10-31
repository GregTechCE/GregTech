package gregtech.api.gui.widgets.armor;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.Position;
import gregtech.api.util.PositionedRect;
import gregtech.api.util.Size;

import java.util.EnumMap;
import java.util.Map;

public abstract class PipeGridElementWidget extends GridElementWidget {

    private static Map<ConnectionType, TIntObjectMap<TextureHolder>> pipeIconCacheMap = new EnumMap<>(ConnectionType.class);
    protected final ConnectionType connectionType;

    public PipeGridElementWidget(Size elementSize, int slotSize, ConnectionType connectionType) {
        super(elementSize, slotSize);
        this.connectionType = connectionType;
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY, IRenderContext context) {
        super.drawInBackground(mouseX, mouseY, context);
        int conn = determineConnections();
        TextureHolder textureHolder = getIconMap().get(conn);
        if (textureHolder == null) {
            textureHolder = new TextureHolder(createSpriteMap().get(TextureSprite.STRAIGHT), orientation.ordinal());
        }
        textureHolder.sprite.drawRotated(getPosition().x, getPosition().y,
            new Size(slotSize, slotSize), new PositionedRect(0, 0, slotSize, slotSize), textureHolder.orientation);
    }

    @Override
    public boolean canConnect(ElementOrientation side, Position relativeSlotPos, ConnectionType connectionType) {
        return connectionType == this.connectionType;
    }

    private int determineConnections() {
        ComponentGridWidget gridWidget = parentWidget;
        if (gridWidget == null) {
            return 0;
        }
        Position self = gridWidget.getWidgetOrigin(this);
        int resultConnections = 0;
        for (ElementOrientation orientation : ElementOrientation.values()) {
            Position position = new Position(self.x + orientation.offsetX, self.y + orientation.offsetY);
            Widget widget = gridWidget.getWidgetAt(position.x, position.y);
            Position origin = gridWidget.getWidgetOrigin(widget);
            Position relativePos = position.subtract(origin);
            if (widget instanceof GridElementWidget &&
                ((GridElementWidget) widget).canConnect(orientation.getOpposite(), relativePos, connectionType)) {
                resultConnections |= 1 << orientation.ordinal();
            }
        }
        return resultConnections;
    }

    protected TIntObjectMap<TextureHolder> getIconMap() {
        return pipeIconCacheMap.computeIfAbsent(connectionType, k -> initializeIcons(createSpriteMap()));
    }

    protected abstract Map<TextureSprite, TextureArea> createSpriteMap();

    private static TIntObjectMap<TextureHolder> initializeIcons(Map<TextureSprite, TextureArea> sprite2TextureMap) {
        TIntObjectMap<TextureHolder> resultMap = new TIntObjectHashMap<>();
        for (int i = 0; i < 4; i++) {
            resultMap.put(pack(i, i + 1), new TextureHolder(sprite2TextureMap.get(TextureSprite.EDGE), i));
            resultMap.put(pack(i, i + 1, i + 2), new TextureHolder(sprite2TextureMap.get(TextureSprite.T_JOINT), i));
        }
        for (int i = 0; i < 2; i++) {
            resultMap.put(pack(i, i + 2), new TextureHolder(sprite2TextureMap.get(TextureSprite.STRAIGHT), i));
            resultMap.put(pack(i), new TextureHolder(sprite2TextureMap.get(TextureSprite.STRAIGHT), i));
            resultMap.put(pack(i + 2), new TextureHolder(sprite2TextureMap.get(TextureSprite.STRAIGHT), i));
        }
        resultMap.put(pack(0, 1, 2, 3), new TextureHolder(sprite2TextureMap.get(TextureSprite.CROSS), 0));
        return resultMap;
    }

    private static int pack(int... sides) {
        int result = 0;
        for (int i : sides) result |= 1 << (i % 4);
        return result;
    }

    protected enum TextureSprite {
        STRAIGHT, EDGE, T_JOINT, CROSS
    }

    private static class TextureHolder {
        private final TextureArea sprite;
        private final int orientation;

        public TextureHolder(TextureArea sprite, int orientation) {
            this.sprite = sprite;
            this.orientation = orientation;
        }
    }
}
