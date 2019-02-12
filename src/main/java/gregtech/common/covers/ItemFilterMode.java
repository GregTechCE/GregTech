package gregtech.common.covers;

public enum ItemFilterMode {

    FILTER_INSERT("cover.filter.mode.filter_insert"),
    FILTER_EXTRACT("cover.filter.mode.filter_extract");

    public final String localeName;

    ItemFilterMode(String localeName) {
        this.localeName = localeName;
    }
}
