package gregtech.api.objects;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_Default extends GT_CoverBehavior {
    /**
     * This is the Dummy, if there is a generic Cover without behavior
     */
    public GT_Cover_Default() {
        super();
    }

    @Override
    public boolean isSimpleCover() {
        return true;
    }

    @Override
    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = ((aCoverVariable + 1) & 15);
        GT_Utility.sendChatToPlayer(aPlayer, ((aCoverVariable & 1) != 0 ? "Redstone " : "") + ((aCoverVariable & 2) != 0 ? "Energy " : "") + ((aCoverVariable & 4) != 0 ? "Fluids " : "") + ((aCoverVariable & 8) != 0 ? "Items " : ""));
        return aCoverVariable;
    }

    @Override
    public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return (aCoverVariable & 1) != 0;
    }

    @Override
    public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return (aCoverVariable & 1) != 0;
    }

    @Override
    public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return (aCoverVariable & 2) != 0;
    }

    @Override
    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return (aCoverVariable & 2) != 0;
    }

    @Override
    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return (aCoverVariable & 4) != 0;
    }

    @Override
    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return (aCoverVariable & 4) != 0;
    }

    @Override
    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return (aCoverVariable & 8) != 0;
    }

    @Override
    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return (aCoverVariable & 8) != 0;
    }
}