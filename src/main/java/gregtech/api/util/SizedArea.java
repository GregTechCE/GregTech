package gregtech.api.util;

public class SizedArea {

    public int xPos;
    public int yPos;
    public int width;
    public int height;

    public SizedArea(int xPos, int yPosition, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPosition;
        this.width = width;
        this.height = height;
    }

    public boolean isInside(int x, int y) {
        return x >= xPos &&
            y >= yPos &&
            x < xPos + width &&
            y < yPos + height;
    }
}
