package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;

public class GT_Cover_Fluidfilter extends GT_CoverBehavior {

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        return aCoverVariable;
    }

    public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        //System.out.println("rightclick");
        if (((aX > 0.375D) && (aX < 0.625D)) || ((aSide > 3) && (((aY > 0.375D) && (aY < 0.625D)) || ((aSide < 2) && (((aZ > 0.375D) && (aZ < 0.625D)) || (aSide == 2) || (aSide == 3)))))) {
            ItemStack tStack = aPlayer.inventory.getCurrentItem();
            if (tStack != null) {
                FluidStack tFluid = FluidContainerRegistry.getFluidForFilledItem(tStack);
                if (tFluid != null) {
                    //System.out.println(tFluid.getLocalizedName()+" "+tFluid.getFluidID());
                    aCoverVariable = FluidRegistry.getFluidID(tFluid.getFluid());
                    aTileEntity.setCoverDataAtSide(aSide, aCoverVariable);
                    FluidStack sFluid = new FluidStack(FluidRegistry.getFluid(aCoverVariable), 1000);
                    GT_Utility.sendChatToPlayer(aPlayer, "Filter Fluid: " + sFluid.getLocalizedName());
                } else if (tStack.getItem() instanceof IFluidContainerItem) {
                    IFluidContainerItem tContainer = (IFluidContainerItem) tStack.getItem();
                    if (tContainer.getFluid(tStack) != null) {
                        aCoverVariable = FluidRegistry.getFluidID(tContainer.getFluid(tStack).getFluid());
                        aTileEntity.setCoverDataAtSide(aSide, aCoverVariable);
                        //System.out.println("fluidcontainer " + aCoverVariable);
                        FluidStack sFluid = new FluidStack(FluidRegistry.getFluid(aCoverVariable), 1000);
                        GT_Utility.sendChatToPlayer(aPlayer, "Filter Fluid: " + sFluid.getLocalizedName());
                    }
                }
            }
            return true;
        }
        return false;
    }


    
    @Override
    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return aFluid == null || FluidRegistry.getFluidID(aFluid) == aCoverVariable;
    }
    
    @Override
    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return aFluid == null || FluidRegistry.getFluidID(aFluid) == aCoverVariable;
    }
    
    public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 0;
    }
}
