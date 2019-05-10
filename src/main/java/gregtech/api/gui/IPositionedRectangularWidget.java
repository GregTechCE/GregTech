package gregtech.api.gui;

import java.awt.*;

/**
 * Implemented on a rectangular sized widgets and used for JEI integration,
 * ingredient picking and general information retrieval
 */
public interface IPositionedRectangularWidget {

    /**
     * @return current X position of the widget
     */
    int getXPosition();

    /**
     * @return current Y position of the widget
     */
    int getYPosition();

    /**
     * @return current width of the widget
     */
    int getWidth();

    /**
     * @return current height of the widget
     */
    int getHeight();

    default Rectangle toRectangleBox() {
        return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());
    }

    default boolean isMouseOver(int mouseX, int mouseY) {
        return Widget.isMouseOver(getXPosition(), getYPosition(), getWidth(), getHeight(), mouseX, mouseY);
    }
}
