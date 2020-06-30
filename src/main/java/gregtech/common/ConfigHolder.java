package gregtech.common;

import gregtech.api.GTValues;
import net.minecraftforge.common.config.Config;

@Config(modid = GTValues.MODID)
public class ConfigHolder {

    @Config.Comment("Whether to enable more verbose logging. Default: false")
    public static boolean debug = false;

    @Config.Comment("Whether to increase number of rolls for dungeon chests. Increases dungeon loot drastically.")
    public static boolean increaseDungeonLoot = true;

    @Config.Comment("Whether to hide facades of all blocks in JEI and creative search menu. Default is true.")
    @Config.RequiresMcRestart
    public static boolean hideFacadesInJEI = true;

    @Config.Comment("Whether to hide filled cells in JEI and creative search menu. Default is true.")
    @Config.RequiresMcRestart
    public static boolean hideFilledCellsInJEI = true;

    @Config.Comment("Whether to hide filled tanks in JEI and creative search menu. Default is true.")
    @Config.RequiresMcRestart
    public static boolean hideFilledTanksInJEI = true;

    @Config.Comment("Specifies min amount of veins in section")
    public static int minVeinsInSection = 0;

    @Config.Comment("Specifies additional random amount of veins in section")
    public static int additionalVeinsInSection = 2;

    @Config.Comment("Whether veins should be generated in center of chunk. Default is false.")
    public static boolean generateVeinsInCenterOfChunk = false;

    @Config.Comment("Whether to disable vanilla ores generation in world. Default is false.")
    public static boolean disableVanillaOres = false;

    @Config.Comment("Whether to disable rubber tree world generation. Default is false.")
    @Config.RequiresMcRestart
    public static boolean disableRubberTreeGeneration = false;

    @Config.Comment("Whether machines should explode when overloaded with power. Default: true")
    public static boolean doExplosions = true;

    @Config.Comment("Energy use multiplier for electric items. Default: 100")
    public static int energyUsageMultiplier = 100;

    @Config.Comment("Chance of generating abandoned base in chunk = 1 / THIS_VALUE. 0 disables abandoned base generation")
    public static int abandonedBaseRarity = 1000;

    @Config.RangeInt(min = 0, max = 100)
    @Config.Comment("Chance with which flint and steel will create fire. Default: 50")
    public static int flintChanceToCreateFire = 50;

    @Config.Comment("Recipes for machine hulls use more materials. Default: false")
    @Config.RequiresMcRestart
    public static boolean harderMachineHulls = false;

    @Config.Comment("If true, insufficient energy supply will reset recipe progress to zero. If false, progress will slowly go back (with 2x speed)")
    @Config.RequiresWorldRestart
    public static boolean insufficientEnergySupplyWipesRecipeProgress = false;

    @Config.Comment("Whether to use modPriorities setting in config for prioritizing ore dictionary item registrations. " +
        "By default, GTCE will sort ore dictionary registrations alphabetically comparing their owner ModIDs.")
    public static boolean useCustomModPriorities = false;

    @Config.Comment("Specifies priorities of mods in ore dictionary item registration. First ModID has highest priority, last - lowest. " +
        "Unspecified ModIDs follow standard sorting, but always have lower priority than last specified ModID.")
    @Config.RequiresMcRestart
    public static String[] modPriorities = new String[0];

    @Config.Comment("Setting this to true makes GTCE ignore error and invalid recipes that would otherwise cause crash. Default to true.")
    @Config.RequiresMcRestart
    public static boolean ignoreErrorOrInvalidRecipes = true;

    @Config.Comment("Setting this to false causes GTCE to not register additional methane recipes for foods in the centrifuge.")
    @Config.RequiresMcRestart
    public static boolean addFoodMethaneRecipes = true;

    @Config.Comment("Category that contains configs for changing vanilla recipes")
    @Config.RequiresMcRestart
    public static VanillaRecipes vanillaRecipes = new VanillaRecipes();

    @Config.Comment("Category that contains configs for machines with specific behavior")
    public static MachineSpecificConfiguration machineSpecific = new MachineSpecificConfiguration();

    @Config.Comment("Sets the bonus EU output of Steam Turbines.")
    @Config.RequiresMcRestart
    public static int steamTurbineBonusOutput = 6144;

    @Config.Comment("Sets the bonus EU output of Plasma Turbines.")
    @Config.RequiresMcRestart
    public static int plasmaTurbineBonusOutput = 6144;

    @Config.Comment("Sets the bonus EU output of Gas Turbines.")
    @Config.RequiresMcRestart
    public static int gasTurbineBonusOutput = 6144;

    public static class VanillaRecipes {

        @Config.Comment("Whether to nerf paper crafting recipe. Default is true.")
        public boolean nerfPaperCrafting = true;

        @Config.Comment("Whether to make flint and steel recipe require steel nugget instead of iron one. Default is true")
        public boolean flintAndSteelRequireSteel = true;

        @Config.Comment("Whether to nerf wood crafting to 2 planks from 1 log. Default is false.")
        public boolean nerfWoodCrafting = false;

        @Config.Comment("Whether to nerf wood crafting to 2 sticks from 2 planks. Default is false.")
        public boolean nerfStickCrafting = false;

        @Config.Comment("Whether to make iron bucket recipe harder by requiring hammer and plates. Default is true.")
        public boolean bucketRequirePlatesAndHammer = true;

        @Config.Comment("Recipes for items like iron doors, trapdors, pressure plates, cauldron, hopper and iron bars require iron plates and hammer. Default is true")
        public boolean ironConsumingCraftingRecipesRequirePlates = true;

        @Config.Comment("Require a knife for bowl crafting instead of only plank? Default is true.")
        public boolean bowlRequireKnife = true;
    }

    public static class MachineSpecificConfiguration {
        @Config.Comment("Array of blacklisted dimension IDs in which Air Collector does not work.")
        public int[] airCollectorDimensionBlacklist = new int[]{};
    }
}
