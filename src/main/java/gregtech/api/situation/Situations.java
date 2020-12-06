package gregtech.api.situation;

import gregtech.api.util.GTLog;

public class Situations {
    public static Situation WORKING = new Situation(0, "working", SituationTypes.WORKING, "gregtech.situation.working");

    public static Situation IDLE = new Situation(1, "idle", SituationTypes.IDLE, "gregtech.situation.idle");
    public static Situation DISABLED_BY_CONTROLLER = new Situation(2, "workingdisabled", SituationTypes.IDLE, "gregtech.situation.disabled_by_controller");

    public static Situation EMPTY_SOURCE = new Situation(3, "emptysource", SituationTypes.WARNING, "gregtech.situation.empty_source");
    public static Situation INSUFFICIENT_POWER = new Situation(4, "nopower", SituationTypes.WARNING, "gregtech.situation.insufficient_power");
    public static Situation NO_MATCHING_RECIPE = new Situation(5, "norecipe", SituationTypes.WARNING, "gregtech.situation.no_matching_recipe");
    public static Situation OUTPUT_INVENTORY_FULL = new Situation(6, "outputfull", SituationTypes.WARNING, "gregtech.situation.output_inventory_full");
    public static Situation TARGET_INVENTORY_FULL = new Situation(7, "targetfull", SituationTypes.WARNING, "gregtech.situation.target_inventory_full");

    public static Situation NO_IMPORT_INVENTORY = new Situation(8, "noimportinv", SituationTypes.ERROR, "gregtech.situation.no_import_inventory");
    public static Situation NO_EXPORT_INVENTORY = new Situation(9, "noexportinv", SituationTypes.ERROR, "gregtech.situation.no_export_inventory");
    public static Situation NO_IMPORT_TANK = new Situation(10, "noimporttank", SituationTypes.ERROR, "gregtech.situation.no_import_tank");
    public static Situation NO_EXPORT_TANK = new Situation(11, "noexporttank", SituationTypes.ERROR, "gregtech.situation.no_export_tank");
    public static Situation EXPECTED_CAPABILITY_UNAVAILABLE = new Situation(12, "nullcapability", SituationTypes.ERROR, "gregtech.situation.null_capability");

    public static void init() {
        GTLog.logger.info("Registering situations...");
    }

}

