package gregtech.api.cable;

import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.util.IStringSerializable;

public enum Insulation implements IStringSerializable {

    WIRE_SINGLE("wire_single", 0.17f, 1, 2, OrePrefix.wireGtSingle, false),
    WIRE_DOUBLE("wire_double", 0.37f, 2, 2, OrePrefix.wireGtDouble, false),
    WIRE_QUADRUPLE("wire_quadruple", 0.57f, 4, 3, OrePrefix.wireGtQuadruple, false),
    WIRE_OCTAL("wire_octal", 0.77f, 8, 3, OrePrefix.wireGtOctal, false),
    WIRE_TWELVE("wire_twelve", 0.85f, 6, 1, OrePrefix.wireGtTwelve, false),
    WIRE_HEX("wire_hex", 0.97f, 16, 3, OrePrefix.wireGtHex, false),

    CABLE_SINGLE("cable_single", 0.17f, 1, 1, OrePrefix.cableGtSingle, true),
    CABLE_DOUBLE("cable_double", 0.37f, 2, 1, OrePrefix.cableGtDouble, true),
    CABLE_QUADRUPLE("cable_quadruple", 0.57f, 4, 1, OrePrefix.cableGtQuadruple, true),
    CABLE_OCTAL("cable_octal", 0.77f, 8, 1, OrePrefix.cableGtOctal, true),
    CABLE_TWELVE("cable_twelve", 0.847f, 6, 1, OrePrefix.cableGtTwelve, true),
    CABLE_HEX("cable_hex", 0.97f, 16, 1, OrePrefix.cableGtHex, true);

    public final String name;
    public final float thickness;
    public final int amperage;
    public final int lossMultiplier;
    public final OrePrefix orePrefix;
    public final boolean insulated;

    Insulation(String name, float thickness, int amperage, int lossMultiplier, OrePrefix orePrefix, boolean insulated) {
        this.name = name;
        this.thickness = thickness;
        this.amperage = amperage;
        this.orePrefix = orePrefix;
        this.insulated = insulated;
        this.lossMultiplier = lossMultiplier;
    }

    @Override
    public String getName() {
        return name;
    }
}
