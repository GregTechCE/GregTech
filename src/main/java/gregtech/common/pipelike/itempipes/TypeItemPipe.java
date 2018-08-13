package gregtech.common.pipelike.itempipes;

import gregtech.api.pipelike.IBaseProperty;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.util.IStringSerializable;

import static gregtech.api.unification.ore.OrePrefix.*;

public enum TypeItemPipe implements IBaseProperty, IStringSerializable {
    PIPE_TINY("item_pipe_tiny", 1, 4, 131072, pipeTiny, 0.25F, 0, false),
    PIPE_SMALL("item_pipe_small", 1, 2, 65536, pipeSmall, 0.375F, 1, false),
    PIPE_MEDIUM("item_pipe_medium", 1, 1, 32768, pipeMedium, 0.50F, 2, false),
    PIPE_LARGE("item_pipe_large", 2, 1, 16384, pipeLarge, 0.75F, 3, false),
    PIPE_HUGE("item_pipe_huge", 4, 1, 8192, pipeHuge, 0.875F, 4, false),

    PIPE_RESTRICTIVE_TINY("item_pipe_restrictive_tiny", 1, 4, 13107200, pipeRestrictiveTiny, 0.25F, 0, true),
    PIPE_RESTRICTIVE_SMALL("item_pipe_restrictive_small", 1, 2, 6553600, pipeRestrictiveSmall, 0.375F, 1, true),
    PIPE_RESTRICTIVE_MEDIUM("item_pipe_restrictive_medium", 1, 1, 3276800, pipeRestrictiveMedium, 0.50F, 2, true),
    PIPE_RESTRICTIVE_LARGE("item_pipe_restrictive_large", 2, 1, 1638400, pipeRestrictiveLarge, 0.75F, 3, true),
    PIPE_RESTRICTIVE_HUGE("item_pipe_restrictive_huge", 4, 1, 819200, pipeRestrictiveHuge, 0.875F, 4, true);

    public final String name;
    public final int transferCapacity;
    public final int tickRate;
    public final int baseRoutingValue;
    public final OrePrefix orePrefix;
    public final float thickness;
    public final int index;
    public final boolean isRestrictive;

    TypeItemPipe(String name, int transferCapacity, int tickRate, int baseRoutingValue, OrePrefix orePrefix, float thickness, int index, boolean isRestrictive) {
        this.name = name;
        this.transferCapacity = transferCapacity;
        this.tickRate = tickRate;
        this.baseRoutingValue = baseRoutingValue;
        this.orePrefix = orePrefix;
        this.thickness = thickness;
        this.index = index;
        this.isRestrictive = isRestrictive;
    }

    @Override
    public OrePrefix getOrePrefix() {
        return orePrefix;
    }

    @Override
    public float getThickness() {
        return thickness;
    }

    @Override
    public boolean isColorable() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }
}
