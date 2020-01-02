package gregtech.integration.jei.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;

import java.util.List;

public abstract class MultiblockInfoPage {

    public abstract MultiblockControllerBase getController();

    public abstract List<MultiblockShapeInfo> getMatchingShapes();

    public abstract String[] getDescription();

}
