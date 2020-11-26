package gregtech.api;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.GTControlledRegistry;
import gregtech.api.util.GTLog;

public class SituationalStatus {

    public static GTControlledRegistry<SituationalStatus, Integer> SITUATIONAL_STATUS_REGISTRY = new GTControlledRegistry<>(Short.MAX_VALUE);

    private static int registryIdIndex = 0;
    public TextureArea iconTexture;
    public int code;
    public String localeName;

    public static int WORKING;
    public static int IDLE;
    public static int EXPECTED_CAPABILITY_UNAVAILABLE;
    public static int EMPTY_SOURCE;
    public static int INSUFFICIENT_POWER;
    public static int NO_MATCHING_RECIPE;
    public static int TARGET_INVENTORY_FULL;


    public SituationalStatus(String localeName, TextureArea errorGroup,int code) {
        this.localeName = localeName;
        this.iconTexture = errorGroup;
        this.code = code;
    }

    public static void init() {
        GTLog.logger.info("Registering SituationalStatus");

        WORKING = registerSituationalStatus(GuiTextures.STATUS_WORKING, "gregtech.situational_status.working");
        IDLE = registerSituationalStatus(GuiTextures.STATUS_IDLING, "gregtech.situational_status.idle");
        EXPECTED_CAPABILITY_UNAVAILABLE = registerSituationalStatus(GuiTextures.STATUS_ISSUE, "gregtech.situational_status.null_capability");
        EMPTY_SOURCE = registerSituationalStatus(GuiTextures.STATUS_WARNING, "gregtech.situational_status.empty_source");
        INSUFFICIENT_POWER = registerSituationalStatus(GuiTextures.STATUS_WARNING, "gregtech.situational_status.insufficient_power");
        NO_MATCHING_RECIPE = registerSituationalStatus(GuiTextures.STATUS_WARNING, "gregtech.situational_status.no_matching_recipe");
        TARGET_INVENTORY_FULL = registerSituationalStatus(GuiTextures.STATUS_ISSUE, "gregtech.situational_status.target_inventory_full");
    }

    public static int registerSituationalStatus(TextureArea iconTexture, String localeName){
        SituationalStatus situationalStatus = new SituationalStatus(localeName, iconTexture, registryIdIndex);
        SITUATIONAL_STATUS_REGISTRY.register(registryIdIndex, situationalStatus, registryIdIndex);
        int currentRegistryIdIndex = registryIdIndex;
        registryIdIndex += 1;
        return currentRegistryIdIndex;
    }

    public static SituationalStatus getSituationalStatusFromId(int id) {
        return SITUATIONAL_STATUS_REGISTRY.getNameForObject(id);
    }

    public static int getIdFromSituationalStatus(SituationalStatus situationalStatus) {
        return SITUATIONAL_STATUS_REGISTRY.getIdByObjectName(situationalStatus);
    }
}
