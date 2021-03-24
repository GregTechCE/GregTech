package gregtech.integration.jei.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;

import java.util.List;

/**
 * Old method of displaying Multiblocks in JEI.
 *
 * Use {@link IMultiblockInfoPage} instead.
 */
@Deprecated
public abstract class MultiblockInfoPage {

    @Deprecated
    public abstract MultiblockControllerBase getController();

    @Deprecated
    public abstract List<MultiblockShapeInfo> getMatchingShapes();

    @Deprecated
    public abstract String[] getDescription();
}
