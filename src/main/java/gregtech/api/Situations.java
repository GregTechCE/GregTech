package gregtech.api;

public class Situations {
    public static Situation WORKING = new Situation(0, "working", SituationsTypes.WORKING, "gregtech.situation.working");

    public static Situation IDLE = new Situation(1, "idle", SituationsTypes.IDLE, "gregtech.situation.idle");

    public static Situation EMPTY_SOURCE = new Situation(2, "emptysource", SituationsTypes.WARNING, "gregtech.situation.empty_source");
    public static Situation INSUFFICIENT_POWER = new Situation(3, "nopower", SituationsTypes.WARNING, "gregtech.situation.insufficient_power");
    public static Situation NO_MATCHING_RECIPE = new Situation(4, "norecipe", SituationsTypes.WARNING, "gregtech.situation.no_matching_recipe");
    public static Situation OUTPUT_INVENTORY_FULL = new Situation(5, "outputfull", SituationsTypes.WARNING, "gregtech.situation.output_inventory_full");
    public static Situation TARGET_INVENTORY_FULL = new Situation(6, "targetfull", SituationsTypes.WARNING, "gregtech.situation.target_inventory_full");

    public static Situation EXPECTED_CAPABILITY_UNAVAILABLE = new Situation(7, "nullcapability", SituationsTypes.ERROR, "gregtech.situation.null_capability");

    public enum SituationsTypes {
        WORKING,
        IDLE,
        ERROR,
        WARNING;
    }
}

