package gregtech.common.covers;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

public abstract class GT_Cover_RedstoneWirelessBase
        extends GT_CoverBehavior {
    public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced) {
        GregTech_API.sWirelessRedstone.put(Integer.valueOf(aCoverVariable), Byte.valueOf((byte) 0));
        return true;
    }

    public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (((aX > 0.375D) && (aX < 0.625D)) || ((aSide > 3) && ((aY > 0.375D) && (aY < 0.625D)))) {
            GregTech_API.sWirelessRedstone.put(Integer.valueOf(aCoverVariable), Byte.valueOf((byte) 0));
            aCoverVariable = GT_Utility.stackToInt(aPlayer.inventory.getCurrentItem());
            aTileEntity.setCoverDataAtSide(aSide, aCoverVariable);
            GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + aCoverVariable);
            return true;
        }
        return false;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (((aX > 0.375D) && (aX < 0.625D)) || ((aSide <= 3) || (((aY > 0.375D) && (aY < 0.625D)) || ((((aZ <= 0.375D) || (aZ >= 0.625D))))))) {
            GregTech_API.sWirelessRedstone.put(Integer.valueOf(aCoverVariable), Byte.valueOf((byte) 0));
            float[] tCoords = GT_Utility.getClickedFacingCoords(aSide, aX, aY, aZ);
            switch ((byte) ((byte) (int) (tCoords[0] * 2.0F) + 2 * (byte) (int) (tCoords[1] * 2.0F))) {
                case 0:
                    aCoverVariable -= 32;
                    break;
                case 1:
                    aCoverVariable += 32;
                    break;
                case 2:
                    aCoverVariable -= 1024;
                    break;
                case 3:
                    aCoverVariable += 1024;
            }
        }
        GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + aCoverVariable);
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

    public String getDescription(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return "Frequency: " + aCoverVariable;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 1;
    }
}
