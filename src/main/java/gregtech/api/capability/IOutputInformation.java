package gregtech.api.capability;

import net.minecraft.util.EnumFacing;

public interface IOutputInformation {

    EnumFacing getOutputSide();

    boolean isOutputEnabled();

    boolean isInputAllowedFromOutput();
}
