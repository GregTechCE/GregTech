package gregtech.api.metatileentity.multiblock;

public interface IMultiblockPart {

    void addToMultiBlock(MultiblockControllerBase controllerBase, Object attachmentData);

    void removeFromMultiBlock(MultiblockControllerBase controllerBase);

}
