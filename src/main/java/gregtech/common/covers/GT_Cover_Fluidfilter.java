package gregtech.common.covers;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public class GT_Cover_Fluidfilter
        extends GT_CoverBehavior {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        return aCoverVariable;
    }

    public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
       //System.out.println("rightclick");
    	if (((aX > 0.375D) && (aX < 0.625D)) || ((aSide > 3) && (((aY > 0.375D) && (aY < 0.625D)) || ((aSide < 2) && (((aZ > 0.375D) && (aZ < 0.625D)) || (aSide == 2) || (aSide == 3)))))) {
            ItemStack tStack = aPlayer.inventory.getCurrentItem();
            if(tStack!=null){
            FluidStack tFluid = FluidContainerRegistry.getFluidForFilledItem(tStack);
            if(tFluid!=null){
            	//System.out.println(tFluid.getLocalizedName()+" "+tFluid.getFluidID());
            	aCoverVariable = tFluid.getFluidID();
                aTileEntity.setCoverDataAtSide(aSide, aCoverVariable);
        	FluidStack sFluid = new FluidStack(FluidRegistry.getFluid(aCoverVariable),1000);
                GT_Utility.sendChatToPlayer(aPlayer, "Filter Fluid: " + sFluid.getLocalizedName());
            }else if(tStack.getItem() instanceof IFluidContainerItem){
            	IFluidContainerItem tContainer = (IFluidContainerItem)tStack.getItem();
                if(tContainer.getFluid(tStack) != null) {
                    aCoverVariable = tContainer.getFluid(tStack).getFluidID();
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
    	if(aFluid==null){return true;}
    	return aFluid.getID() == aCoverVariable;
    }
    
    @Override
    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
    	if(aFluid==null) return false;
    	return aFluid.getID() == aCoverVariable;
    }
    
    public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 0;
    }
}
