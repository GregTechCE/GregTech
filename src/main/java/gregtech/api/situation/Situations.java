package gregtech.api.situation;

import gregtech.api.util.GTLog;

public class Situations {
    public static Situation WORKING = new Situation(1, "working", SituationTypes.WORKING);

    public static Situation IDLE = new Situation(2, "idle", SituationTypes.IDLE);
    public static Situation DISABLED_BY_CONTROLLER = new Situation(3, "disabled_by_controller", SituationTypes.IDLE);

    public static Situation EMPTY_SOURCE = new Situation(100, "empty_source", SituationTypes.WARNING);
    public static Situation INSUFFICIENT_POWER = new Situation(101, "insufficient_power", SituationTypes.WARNING);
    public static Situation INSUFFICIENT_POWER_TO_START = new Situation(102, "insufficient_power_to_start", SituationTypes.WARNING);
    public static Situation NO_MATCHING_RECIPE = new Situation(103, "no_matching_recipe", SituationTypes.WARNING);
    public static Situation OUTPUT_SLOTS_FULL = new Situation(104, "output_slots_full", SituationTypes.WARNING);
    public static Situation OUTPUT_TANKS_FULL = new Situation(105, "output_tanks_full", SituationTypes.WARNING);
    public static Situation OUTPUT_INVENTORY_FULL = new Situation(106, "output_inventory_full", SituationTypes.WARNING);
    public static Situation TARGET_INVENTORY_FULL = new Situation(107, "target_inventory_full", SituationTypes.WARNING);

    public static Situation NO_IMPORT_INVENTORY = new Situation(201, "no_import_inventory", SituationTypes.ERROR);
    public static Situation NO_EXPORT_INVENTORY = new Situation(202, "no_export_inventory", SituationTypes.ERROR);
    public static Situation NO_IMPORT_TANK = new Situation(203, "no_import_tank", SituationTypes.ERROR);
    public static Situation NO_EXPORT_TANK = new Situation(204, "no_export_tank", SituationTypes.ERROR);
    public static Situation EXPECTED_CAPABILITY_UNAVAILABLE = new Situation(205, "null_capability", SituationTypes.ERROR);
    public static Situation WATER_CHECK_FAILED = new Situation(206, "water_check_failed", SituationTypes.ERROR);
    public static Situation BLOCKED_INTAKES = new Situation(207, "blocked_intakes", SituationTypes.ERROR);
    public static Situation DIMENSION_LACKS_ATMOSPHERE = new Situation(208, "dimension_lacks_atmosphere", SituationTypes.ERROR);

    public static void init() {
        GTLog.logger.info("Registering situations...");
    }

}

