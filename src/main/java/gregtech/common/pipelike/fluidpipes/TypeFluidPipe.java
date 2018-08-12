package gregtech.common.pipelike.fluidpipes;

import gregtech.api.pipelike.IBaseProperty;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.util.IStringSerializable;

import static gregtech.api.unification.ore.OrePrefix.*;

public enum TypeFluidPipe implements IBaseProperty, IStringSerializable {
    PIPE_TINY("fluid_pipe_tiny", 1, 1, pipeTiny, 0.25F, 0),
    PIPE_SMALL("fluid_pipe_small", 2, 1, pipeSmall, 0.375F, 1),
    PIPE_MEDIUM("fluid_pipe_medium", 6, 1, pipeMedium, 0.5F, 2),
    PIPE_LARGE("fluid_pipe_large", 12, 1, pipeLarge, 0.75F, 3),
    PIPE_HUGE("fluid_pipe_huge", 24, 1, pipeHuge, 0.875F, 4),

    PIPE_QUADRUPLE("fluid_pipe_quadruple", 6, 4, pipeQuadruple, 0.9375F, 5),
    PIPE_NONUPLE("fluid_pipe_nonuple", 2, 9, pipeNonuple, 0.9375F, 6),
    PIPE_SEXDECUPLE("fluid_pipe_sexdecuple", 1, 16, pipeSexdecuple, 0.875F, 7);

    public final String name;
    public final OrePrefix orePrefix;
    public final float thickness;
    public final int index;
    public final int fluidCapacityMultiplier;
    public final int multiple;

    TypeFluidPipe(String name, int fluidCapacityMultiplier, int multiple, OrePrefix orePrefix, float thickness, int index) {
        this.name = name;
        this.fluidCapacityMultiplier = fluidCapacityMultiplier;
        this.multiple = multiple;
        this.orePrefix = orePrefix;
        this.thickness = thickness;
        this.index = index;
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
