package gregtech.common.covers;

public enum FluidFilterMode {

    FILTER_FILL("cover.fluid_filter.mode.filter_fill"),
    FILTER_DRAIN("cover.fluid_filter.mode.filter_drain");

    public final String localeName;

    FluidFilterMode(String localeName) {
        this.localeName = localeName;
    }
}
