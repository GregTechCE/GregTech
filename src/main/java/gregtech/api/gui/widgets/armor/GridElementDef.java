package gregtech.api.gui.widgets.armor;

import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.Size;

import java.util.function.BiFunction;

public class GridElementDef {

    private final int sizeHorizontal;
    private final int sizeVertical;
    private final BiFunction<Size, Integer, GridElementWidget> supplier;

    public GridElementDef(int sizeHorizontal, int sizeVertical, BiFunction<Size, Integer, GridElementWidget> supplier) {
        this.sizeHorizontal = sizeHorizontal;
        this.sizeVertical = sizeVertical;
        this.supplier = supplier;
    }

    public Size getSizeWithOrientation(ElementOrientation orientation) {
        Size initialSize = new Size(sizeHorizontal, sizeVertical);
        return TextureArea.transformSize(TextureArea.createOrientation(initialSize, orientation.rotationValue), initialSize);
    }

    public GridElementWidget createWidget(ElementOrientation orientation, int slotSize) {
        GridElementWidget elementWidget = supplier.apply(new Size(sizeHorizontal * slotSize, sizeVertical * slotSize), slotSize);
        elementWidget.setOrientation(orientation);
        return elementWidget;
    }
}
