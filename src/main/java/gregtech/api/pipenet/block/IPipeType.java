package gregtech.api.pipenet.block;

import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.util.IStringSerializable;

public interface IPipeType<NodeDataType> extends IStringSerializable {

    float getThickness();

    OrePrefix getOrePrefix();

    NodeDataType modifyProperties(NodeDataType baseProperties);

    boolean isPaintable();

}
