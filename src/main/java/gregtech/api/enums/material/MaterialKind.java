package gregtech.api.enums.material;

public enum MaterialKind {
    /**
     * Solid material forms
     */
    ROUGH, DUST, METAL, GEM,
    /**
     * Liquid material forms
     */
    LIQUID, GAS;

    public boolean isSolid() {
        return this == ROUGH || this == METAL || this == GEM || this == DUST;
    }

    public boolean isLiquid() {
        return this == LIQUID || this == GAS;
    }

}
