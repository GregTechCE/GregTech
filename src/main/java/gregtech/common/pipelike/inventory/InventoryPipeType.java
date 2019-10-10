package gregtech.common.pipelike.inventory;

import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.pipenet.block.simple.EmptyNodeData;

public enum InventoryPipeType implements IPipeType<EmptyNodeData> {
    NORMAL("normal", 0.4f, true);

    private final String name;
    private final float thickness;
    private final boolean isPaintable;

    InventoryPipeType(String name, float thickness, boolean isPaintable) {
        this.name = name;
        this.thickness = thickness;
        this.isPaintable = isPaintable;
    }

    @Override
    public float getThickness() {
        return thickness;
    }

    @Override
    public EmptyNodeData modifyProperties(EmptyNodeData baseProperties) {
        return baseProperties;
    }

    @Override
    public boolean isPaintable() {
        return isPaintable;
    }

    @Override
    public String getName() {
        return name;
    }
}
