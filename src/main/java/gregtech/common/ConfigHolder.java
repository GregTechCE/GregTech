package gregtech.common;

import gregtech.api.GTValues;
import net.minecraftforge.common.config.Config;

@Config(modid = GTValues.MODID)
public class ConfigHolder {

    @Config.Comment("Whether to enable more verbose logging. Default: false")
    public static boolean debug = false;

    @Config.Comment("Whether to add [GregTech] prefix in logger. Default: false")
    public static boolean useLoggerPrefix = false;

    @Config.Comment("Whether machines should explode when overloaded with power")
    public static boolean doExplosions = true;

    @Config.Comment("Energy use multiplier for items")
    @Config.RangeDouble(min = 0.1D, max = 10.0D)
    public static double toolEnergyMultiplier = 1.0D;
}
