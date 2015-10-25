package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_ItemMeter
        extends GT_CoverBehavior {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        int[] tSlots;
        if (aCoverVariable < 2) {
            tSlots = aTileEntity.getAccessibleSlotsFromSide(aSide);
        } else {
            tSlots = new int[]{aCoverVariable - 2};
        }
        int tAll = 0;
        int tFull = 0;
        for (int i : tSlots) {
            if ((i > 0) && (i < aTileEntity.getSizeInventory())) {
                tAll += 64;
                ItemStack tStack = aTileEntity.getStackInSlot(i);
                if (tStack != null) {
                    tFull += tStack.stackSize * 64 / tStack.getMaxStackSize();
                }
            }
        }
        tAll /= 14;
        if (tAll > 0) {
            aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable != 1 ? 0 : tFull > 0 ? (byte) (tFull / tAll + 1) : (byte) (15 - (tFull > 0 ? tFull / tAll + 1 : 0)));
        } else {
            aTileEntity.setOutputRedstoneSignal(aSide, (byte) (aCoverVariable != 1 ? 0 : 15));
        }
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + 1) % (2 + aTileEntity.getSizeInventory());
        switch(aCoverVariable) {
            case 0: GT_Utility.sendChatToPlayer(aPlayer, "Normal"); break;
            case 1: GT_Utility.sendChatToPlayer(aPlayer, "Inverted"); break;
            default: GT_Utility.sendChatToPlayer(aPlayer, "Slot: " + (aCoverVariable - 2)); break;
        }
        return aCoverVariable;
    }

    public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 5;
    }
}
