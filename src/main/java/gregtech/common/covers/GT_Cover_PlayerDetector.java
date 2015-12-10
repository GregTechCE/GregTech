package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_PlayerDetector extends GT_CoverBehavior {

    private String placer = "";
    private int range = 8;

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        boolean playerDetected = false;

        if (aTileEntity instanceof IGregTechTileEntity) {
            if (aTileEntity.isUniversalEnergyStored(20)) {
                aTileEntity.decreaseStoredEnergyUnits(20, true);
                range = 32;
            } else {
                range = 8;
            }
            placer = ((IGregTechTileEntity) aTileEntity).getOwnerName();
        }
        for (Object tObject : aTileEntity.getWorld().playerEntities) {
            if ((tObject instanceof EntityPlayerMP)) {
                EntityPlayerMP tEntity = (EntityPlayerMP) tObject;
                int dist = Math.max(1, (int) tEntity.getDistance(aTileEntity.getXCoord() + 0.5D, aTileEntity.getYCoord() + 0.5D, aTileEntity.getZCoord() + 0.5D));
                if (dist < range) {
                    if (aCoverVariable == 0) {
                        playerDetected = true;
                        break;
                    }
                    if (tEntity.getDisplayName().equalsIgnoreCase(placer)) {
                        if (aCoverVariable == 1) {
                            playerDetected = true;
                            break;
                        }
                    } else if (aCoverVariable == 2) {
                        playerDetected = true;
                        break;
                    }
                }
            }
        }


        aTileEntity.setOutputRedstoneSignal(aSide, (byte) (playerDetected ? 15 : 0));
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + (aPlayer.isSneaking()? -1 : 1)) % 3;
        if(aCoverVariable <0){aCoverVariable = 2;}
        switch(aCoverVariable) {
            case 0: GT_Utility.sendChatToPlayer(aPlayer, "Emit if any Player is close"); break;
            case 1: GT_Utility.sendChatToPlayer(aPlayer, "Emit if you are close"); break;
            case 2: GT_Utility.sendChatToPlayer(aPlayer, "Emit if other player is close"); break;
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
        return 20;
    }

}
