package gregtech.common;

import gregtech.api.GTValues;
import net.minecraftforge.common.config.Config;

@Config(modid = GTValues.MODID)
public class ConfigHolder {

    @Config.Comment("Whether to enable more verbose logging. Default: false")
    public static boolean debug = false;

    @Config.Comment("Whether to add [GregTech] prefix in logger. Default: false")
    public static boolean useLoggerPrefix = false;

    @Config.Comment("Whether machines should explode when overloaded with power. Default: true")
    public static boolean doExplosions = true;

    @Config.Comment("Energy use multiplier for electric items. Default: 100")
    public static int energyUsageMultiplier = 100;

    @Config.RangeInt(min = 0, max = 100)
    @Config.Comment("Chance with which flint and steel will create fire. Default: 50")
    public static int flintChanceToCreateFire = 50;

    @Config.Comment("Recipes for machine hulls use more materials. Default: false")
    public static boolean harderMachineHulls = false;

    @Config.Comment("Category that contains configs for changing vanilla recipes")
    public static VanillaRecipes vanillaRecipes = new VanillaRecipes();

    public static class VanillaRecipes {

        @Config.Ignore
        public boolean steelRequireBlastFurnace = true;

        @Config.Comment("Default: true")
        public boolean nerfedDustCrafting = true;

        @Config.Comment("Default: true")
        public boolean nerfedPaperCrafting = true;

        @Config.Comment("Default: true")
        public boolean flintAndSteelRequireSteel = true;

        @Config.Comment("Default: true")
        public boolean nerfedStickCrafting = true;

        @Config.Comment("Default: true")
        public boolean bucketRequirePlatesAndHammer = true;

        @Config.Comment("Recipes for items like iron doors, trapdors, pressure plates, cauldron, hopper and iron bars require iron plates and hammer. Default: true")
        public boolean ironConsumingCraftingRecipesRequirePlates = true;

        @Config.Comment("Default: true")
        public boolean bowlRequireKnife = true;
    }

}
