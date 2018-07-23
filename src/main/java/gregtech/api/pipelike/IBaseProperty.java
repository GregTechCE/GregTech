package gregtech.api.pipelike;

import gregtech.api.unification.ore.OrePrefix;

public interface IBaseProperty {
    OrePrefix getOrePrefix();
    float getThickness();
    boolean isColorable();
}