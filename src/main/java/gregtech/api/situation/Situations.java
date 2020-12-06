package gregtech.api.situation;

import gregtech.api.util.GTLog;

public class Situations {
    public static Situation WORKING = new Situation(0, "working", SituationTypes.WORKING);

    public static Situation IDLE = new Situation(1, "idle", SituationTypes.IDLE);
    public static Situation DISABLED_BY_CONTROLLER = new Situation(2, "disabled_by_controller", SituationTypes.IDLE);

    public static Situation EMPTY_SOURCE = new Situation(3, "empty_source", SituationTypes.WARNING);
    public static Situation INSUFFICIENT_POWER = new Situation(4, "insufficient_power", SituationTypes.WARNING);
    public static Situation NO_MATCHING_RECIPE = new Situation(5, "no_matching_recipe", SituationTypes.WARNING);
    public static Situation OUTPUT_INVENTORY_FULL = new Situation(6, "output_inventory_full", SituationTypes.WARNING);
    public static Situation TARGET_INVENTORY_FULL = new Situation(7, "target_inventory_full", SituationTypes.WARNING);

    public static Situation NO_IMPORT_INVENTORY = new Situation(8, "no_import_inventory", SituationTypes.ERROR);
    public static Situation NO_EXPORT_INVENTORY = new Situation(9, "no_export_inventory", SituationTypes.ERROR);
    public static Situation NO_IMPORT_TANK = new Situation(10, "no_import_tank", SituationTypes.ERROR);
    public static Situation NO_EXPORT_TANK = new Situation(11, "no_export_tank", SituationTypes.ERROR);
    public static Situation EXPECTED_CAPABILITY_UNAVAILABLE = new Situation(12, "null_capability", SituationTypes.ERROR);

    public static void init() {
        GTLog.logger.info("Registering situations...");
    }

}

