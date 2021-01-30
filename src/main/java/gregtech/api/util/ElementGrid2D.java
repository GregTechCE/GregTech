package gregtech.api.util;

import com.google.common.base.Preconditions;

import java.util.Arrays;

public class ElementGrid2D<T> {

    private int gridSizeX;
    private int gridSizeY;
    private T[][] elementGrid;
    private final T defaultElement;

    public ElementGrid2D() {
        this(0, 0, null);
    }

    public ElementGrid2D(int gridSizeX, int gridSizeY, T defaultElement) {
        this.gridSizeX = 0;
        this.gridSizeY = 0;
        this.defaultElement = defaultElement;
        resizeGrid(gridSizeX, gridSizeY);
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public T getDefaultElement() {
        return defaultElement;
    }

    public void setElement(int posX, int posY, T newElement) {
        if (newElement == null) {
            newElement = defaultElement;
        }
        this.elementGrid[posY][posX] = newElement;
    }

    public T getElement(int posX, int posY) {
        return elementGrid[posY][posX];
    }

    @SuppressWarnings("unchecked")
    public void resizeGrid(int newSizeX, int newSizeY) {
        T[][] newElementGrid = (T[][]) new Object[newSizeY][newSizeX];

        for (T[] gridRow : newElementGrid) {
            Arrays.fill(gridRow, defaultElement);
        }

        for (int i = 0; i < Math.min(gridSizeY, newSizeY); i++) {
            if (Math.min(gridSizeX, newSizeX) >= 0) {
                System.arraycopy(elementGrid[i], 0, newElementGrid[i], 0, Math.min(gridSizeX, newSizeX));
            }
        }

        this.gridSizeX = newSizeX;
        this.gridSizeY = newSizeY;
        this.elementGrid = newElementGrid;
    }

    public boolean areGridsSizedTheSame(ElementGrid2D<T> otherGrid) {
        return getGridSizeY() == otherGrid.getGridSizeY() &&
            getGridSizeX() == otherGrid.getGridSizeX();
    }

    public boolean isElementPositionValid(int posX, int posY) {
        return posX >= 0 && posY >= 0 && posX < gridSizeX && posY < gridSizeY;
    }
}
