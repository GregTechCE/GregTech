package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;

public class AbstractPositionedWidget extends Widget {

    protected int xPosition;
    protected int yPosition;

    public AbstractPositionedWidget(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}
