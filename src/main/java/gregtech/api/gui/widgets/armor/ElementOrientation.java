package gregtech.api.gui.widgets.armor;

public enum ElementOrientation {
    TOP(0, 0, -1),
    RIGHT(1, 1, 0),
    BOTTOM(2, 0, 1),
    LEFT(3, -1, 0);

    static {
        TOP.opposite = BOTTOM;
        BOTTOM.opposite = TOP;
        RIGHT.opposite = LEFT;
        LEFT.opposite = RIGHT;
    }

    public final int rotationValue;
    public final int offsetX;
    public final int offsetY;
    private ElementOrientation opposite;

    ElementOrientation(int rotationValue, int offsetX, int offsetY) {
        this.rotationValue = rotationValue;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public int getRotationValue() {
        return rotationValue;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public ElementOrientation getOpposite() {
        return opposite;
    }
}
