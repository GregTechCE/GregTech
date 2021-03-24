package gregtech.integration.jei.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;

import java.util.List;

public interface IMultiblockInfoPage {

    MultiblockControllerBase getController();

    List<MultiblockShapeInfo> getMatchingShapes();

    String[] getDescription();

    /**
     * Used to set the Zoom of the JEI Multiblock Preview.
     * @return The zoom as a float, in the range (0.0f, 1.0f]
     */
    default float getZoom() {
        return 1.0f;
    }
}
