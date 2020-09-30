package gregtech.common.covers;

import net.minecraft.util.IStringSerializable;

public enum ManualImportExportMode implements IStringSerializable {

    DISABLED("cover.conveyor.manualimportexportmode.mode.disabled"),
    FILTERED("cover.conveyor.manualimportexportmode.mode.filtered"),
    UNFILTERED("cover.conveyor.manualimportexportmode.mode.unfiltered");

    public final String localeName;

    ManualImportExportMode(String localeName) {
        this.localeName = localeName;
    }

    @Override
    public String getName() {
            return localeName;
    }
}