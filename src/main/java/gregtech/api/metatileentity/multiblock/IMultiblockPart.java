package gregtech.api.metatileentity.multiblock;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IMultiblockPart {

    @CapabilityInject(IMultiblockPart.class)
    Capability<IMultiblockPart> CAPABILITY_MULTIBLOCK_PART = null;

    void addToMultiBlock(MultiblockControllerBase controllerBase);

    void removeFromMultiblock(MultiblockControllerBase controllerBase);

}
