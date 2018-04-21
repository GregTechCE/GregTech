package gregtech.api.metatileentity.multiblock;

import java.util.List;

public interface IMultiblockPart<T> {

    MultiblockAbility<T> getAbility();

    void addToMultiBlock(MultiblockControllerBase controllerBase, List<T> abilityList);

    void removeFromMultiblock(MultiblockControllerBase controllerBase);

}
