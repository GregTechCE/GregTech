package gregtech.common.multipart;

import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.common.pipelike.cable.Insulation;
import gregtech.common.pipelike.cable.WireProperties;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public class CableMultiPartTickable extends CableMultiPart implements ITickable {

    CableMultiPartTickable() {}

    public CableMultiPartTickable(IPipeTile<Insulation, WireProperties> sourceTile) {
        super(sourceTile);
    }

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.CABLE_PART_TICKABLE_KEY;
    }

    @Override
    public void update() {
        getCoverableImplementation().update();
    }

    @Override
    public boolean supportsTicking() {
        return true;
    }
}
