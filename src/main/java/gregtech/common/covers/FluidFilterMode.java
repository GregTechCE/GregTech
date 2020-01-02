package gregtech.common.covers;

public enum FluidFilterMode {

    FILTER_FILL("cover.fluid_filter.mode.filter_fill"),
    FILTER_DRAIN("cover.fluid_filter.mode.filter_drain"),
    FILTER_BOTH("cover.fluid_filter.mode.filter_both");

    public final String localeName;

    FluidFilterMode(String localeName) {
        this.localeName = localeName;
    }
}
