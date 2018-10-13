package gregtech.api.metatileentity.multiblock;

public interface IMultiblockPart {

    boolean isAttachedToMultiBlock();

    void addToMultiBlock(MultiblockControllerBase controllerBase, Object attachmentData);

    void removeFromMultiBlock(MultiblockControllerBase controllerBase);

}
