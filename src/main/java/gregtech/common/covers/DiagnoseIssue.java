package gregtech.common.covers;

import net.minecraft.util.IStringSerializable;

public enum DiagnoseIssue implements IStringSerializable {

    IDLING("cover.universal.diagnose.issue.idling"),
    WORKING("cover.universal.diagnose.issue.working"),
    EXPECTED_CAPABILITY_UNAVAILABLE("cover.universal.diagnose.issue.expected_capability_unavailable");

    public final String localeName;

    DiagnoseIssue(String localeName) {
        this.localeName = localeName;
    }

    @Override
    public String getName() {
        return localeName;
    }
}