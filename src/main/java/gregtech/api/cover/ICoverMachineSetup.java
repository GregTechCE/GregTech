package gregtech.api.cover;

import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;



public interface ICoverMachineSetup {

        default void setMachineToInputFromOutput(SimpleMachineMetaTileEntity simpleMachineMetaTileEntity, EntityPlayer playerIn) {
        EnumFacing facing = simpleMachineMetaTileEntity.getOutputFacing();
        if (simpleMachineMetaTileEntity.getCoverAtSide(facing) != null && !simpleMachineMetaTileEntity.isAllowInputFromOutputSide() &&
            (simpleMachineMetaTileEntity.getCoverAtSide(facing).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) != null ||
                simpleMachineMetaTileEntity.getCoverAtSide(facing).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null) != null)) {
            simpleMachineMetaTileEntity.setAllowInputFromOutputSide(true);
            playerIn.sendMessage(new TextComponentTranslation("cover.setup.input_from_output_side"));
        }
    }
}
