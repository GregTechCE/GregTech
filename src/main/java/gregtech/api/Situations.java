package gregtech.api;

public class Situations {
    public static Situation WORKING = new Situation(0, "working", SituationsTypes.WORKING, "gregtech.situation.working");

    public static Situation IDLE = new Situation(1, "idle", SituationsTypes.IDLE, "gregtech.situation.idle");
    public static Situation DISABLED_BY_CONTROLLER = new Situation(2, "workingdisabled", SituationsTypes.IDLE, "gregtech.situation.disabled_by_controller");

    public static Situation EMPTY_SOURCE = new Situation(3, "emptysource", SituationsTypes.WARNING, "gregtech.situation.empty_source");
    public static Situation INSUFFICIENT_POWER = new Situation(4, "nopower", SituationsTypes.WARNING, "gregtech.situation.insufficient_power");
    public static Situation NO_MATCHING_RECIPE = new Situation(5, "norecipe", SituationsTypes.WARNING, "gregtech.situation.no_matching_recipe");
    public static Situation OUTPUT_INVENTORY_FULL = new Situation(6, "outputfull", SituationsTypes.WARNING, "gregtech.situation.output_inventory_full");
    public static Situation TARGET_INVENTORY_FULL = new Situation(7, "targetfull", SituationsTypes.WARNING, "gregtech.situation.target_inventory_full");

    public static Situation NO_IMPORT_INVENTORY = new Situation(8, "noimportinv", SituationsTypes.ERROR, "gregtech.situation.no_import_inventory");
    public static Situation NO_EXPORT_INVENTORY = new Situation(9, "noexportinv", SituationsTypes.ERROR, "gregtech.situation.no_export_inventory");
    public static Situation NO_IMPORT_TANK = new Situation(10, "noimporttank", SituationsTypes.ERROR, "gregtech.situation.no_import_tank");
    public static Situation NO_EXPORT_TANK = new Situation(11, "noexporttank", SituationsTypes.ERROR, "gregtech.situation.no_export_tank");
    public static Situation EXPECTED_CAPABILITY_UNAVAILABLE = new Situation(12, "nullcapability", SituationsTypes.ERROR, "gregtech.situation.null_capability");

    public enum SituationsTypes {
        WORKING,
        IDLE,
        ERROR,
        WARNING;
    }
}

