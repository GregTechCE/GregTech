package gregtech.integration.jei.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;

import java.util.List;

public interface IMultiblockInfoPage {

    MultiblockControllerBase getController();

    List<MultiblockShapeInfo> getMatchingShapes();

    String[] getDescription();

    default float getZoom() {
        return 1.0f;
    }
}
