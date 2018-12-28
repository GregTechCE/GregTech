package gregtech.integration.jei.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public abstract class MultiblockInfoPage {

    public abstract MultiblockControllerBase getController();

    public Vec3d getDisplayOffset() {
        return Vec3d.ZERO;
    }

    public abstract List<MultiblockShapeInfo> getMatchingShapes();

    public abstract String[] getDescription();

}
