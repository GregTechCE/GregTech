package gregtech.common.cable.data;

import net.minecraft.util.IStringSerializable;

public enum Insulation implements IStringSerializable {

    X1("1x", 0.2f, 1), X2("2x", 0.4f, 2), X4("4x", 0.6f, 4), X8("8x", 0.8f, 8), X16("16x", 1.0f, 16);

    public final String name;
    public final float thickness;
    public final int amperage;

    Insulation(String name, float thickness, int amperage) {
        this.name = name;
        this.thickness = thickness;
        this.amperage = amperage;
    }

    @Override
    public String getName() {
        return name;
    }
}
