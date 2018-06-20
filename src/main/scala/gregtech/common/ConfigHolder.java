package gregtech.common;

import gregtech.api.GTValues;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

@Config(modid = GTValues.MODID)
public class ConfigHolder {

    @Config.Comment("Whether to enable more verbose logging. Default: false")
    public static boolean debug = false;

    @Config.Comment("Whether to increase number of rolls for dungeon chests. Increases dungeon loot drastically.")
    public static boolean increaseDungeonLoot = true;

    @Config.Comment("Specifies min amount of veins in section")
    public static int minVeinsInSection = 3;

    @Config.Comment("Specifies additional random amount of veins in section")
    public static int additionalVeinsInSection = 2;

    @Config.Comment("True to enable surface rocks indicating vein under them")
    public static boolean enableOreVeinSurfaceRocks = true;

    @Config.Comment("Whether to disable vanilla ores generation in world. Default is false.")
    public static boolean disableVanillaOres = false;

    @Config.Comment("Whether to disable rubber tree world generation. Default is false.")
    public static boolean disableRubberTreeGeneration = false;

    @Config.Comment("Material flags in format material_name<->list of material flags strings")
    @Config.RequiresMcRestart
    public static Map<String, String[]> materialFlags = new HashMap<>();

    @Config.Comment("Whether machines should explode when overloaded with power. Default: true")
    public static boolean doExplosions = true;

    @Config.Comment("Energy use multiplier for electric items. Default: 100")
    public static int energyUsageMultiplier = 100;

    @Config.RangeInt(min = 0, max = 100)
    @Config.Comment("Chance with which flint and steel will create fire. Default: 50")
    public static int flintChanceToCreateFire = 50;

    @Config.Comment("Recipes for machine hulls use more materials. Default: false")
    @Config.RequiresMcRestart
    public static boolean harderMachineHulls = false;

    @Config.Comment("Flint tools (pickaxe, shovel, axe, sword, hoe) recipes will be disabled. Default is false.")
    @Config.RequiresMcRestart
    public static boolean disableFlintTools = false;

    @Config.Comment("Category that contains configs for changing vanilla recipes")
    @Config.RequiresMcRestart
    public static VanillaRecipes vanillaRecipes = new VanillaRecipes();

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

        @Config.Comment("Require steel wheels to craft minecarts? Default is true.")
        public boolean harderMinecarts = true;

    }

}
