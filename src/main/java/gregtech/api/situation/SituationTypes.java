package gregtech.api.situation;

import net.minecraft.util.IStringSerializable;

public enum SituationTypes implements IStringSerializable {
    WORKING("gregtech.situation.level.warning"),
    INFO("gregtech.situation.level.idle"),
    ERROR("gregtech.situation.level.error"),
    WARNING("gregtech.situation.level.warning");

    public final String errorLevelName;

    SituationTypes(String errorLevelName) {this.errorLevelName = errorLevelName;}

    @Override
    public String getName() {
        return errorLevelName;
    }
}


