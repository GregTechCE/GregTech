package gregtech.api.util;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.objects.GT_ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import static gregtech.api.enums.GT_Values.E;

/**
 * For Covers with a special behavior.
 */
public abstract class GT_CoverBehavior {
    /**
     * Called by updateEntity inside the covered TileEntity. aCoverVariable is the Value you returned last time.
     */
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        return aCoverVariable;
    }

    /**
     * Called when someone rightclicks this Cover.
     * <p/>
     * return true, if something actually happens.
     */
    public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        return false;
    }

    /**
     * Called when someone rightclicks this Cover Client Side
     * <p/>
     * return true, if something actually happens.
     */
    public boolean onCoverRightclickClient(byte aSide, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        return false;
    }

    /**
     * Called when someone rightclicks this Cover with a Screwdriver. Doesn't call @onCoverRightclick in this Case.
     * <p/>
     * return the new Value of the Cover Variable
     */
    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        return aCoverVariable;
    }

    /**
     * Checks if the Cover can be placed on this.
     */
    public boolean isCoverPlaceable(byte aSide, GT_ItemStack aStack, ICoverable aTileEntity) {
        return true;
    }

    /**
     * Removes the Cover if this returns true, or if aForced is true.
     * Doesn't get called when the Machine Block is getting broken, only if you break the Cover away from the Machine.
     */
    public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced) {
        return true;
    }

    /**
     * Gives a small Text for the status of the Cover.
     */
    public String getDescription(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return E;
    }

    /**
     * How Blast Proof the Cover is. 30 is normal.
     */
    public float getBlastProofLevel(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 10.0F;
    }

    /**
     * If it lets RS-Signals into the Block
     * <p/>
     * This is just Informative so that Machines know if their Redstone Input is blocked or not
     */
    public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets RS-Signals out of the Block
     */
    public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets Fibre-Signals into the Block
     * <p/>
     * This is just Informative so that Machines know if their Redstone Input is blocked or not
     */
    public boolean letsFibreGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets Fibre-Signals out of the Block
     */
    public boolean letsFibreGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets Energy into the Block
     */
    public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets Energy out of the Block
     */
    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets Liquids into the Block, aFluid can be null meaning if this is generally allowing Fluids or not.
     */
    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets Liquids out of the Block, aFluid can be null meaning if this is generally allowing Fluids or not.
     */
    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets Items into the Block, aSlot = -1 means if it is generally accepting Items (return false for no Interaction at all), aSlot = -2 means if it would accept for all Slots (return true to skip the Checks for each Slot).
     */
    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets Items out of the Block, aSlot = -1 means if it is generally accepting Items (return false for no Interaction at all), aSlot = -2 means if it would accept for all Slots (return true to skip the Checks for each Slot).
     */
    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return false;
    }

    /**
     * If it lets you rightclick the Machine normally
     */
    public boolean isGUIClickable(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * Needs to return true for Covers, which have a Redstone Output on their Facing.
     */
    public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * if this Cover should let Pipe Connections look connected even if it is not the case.
     */
    public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return false;
    }

    /**
     * Called to determine the incoming Redstone Signal of a Machine.
     * Returns the original Redstone per default.
     * The Cover should @letsRedstoneGoIn or the aInputRedstone Parameter is always 0.
     */
    public byte getRedstoneInput(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return letsRedstoneGoIn(aSide, aCoverID, aCoverVariable, aTileEntity) ? aInputRedstone : 0;
    }

    /**
     * Gets the Tick Rate for doCoverThings of the Cover
     * <p/>
     * 0 = No Ticks! Yes, 0 is Default, you have to override this
     */
    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 0;
    }

    /**
     * If this is a simple Cover, which can also be used on Bronze Machines and similar.
     */
    public boolean isSimpleCover() {
        return false;
    }

    /**
     * The MC Color of this Lens. -1 for no Color (meaning this isn't a Lens then).
     */
    public byte getLensColor(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return -1;
    }

    /**
     * @return the ItemStack dropped by this Cover
     */
    public ItemStack getDrop(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return GT_OreDictUnificator.get(true, aTileEntity.getCoverItemAtSide(aSide));
    }

    /**
     * @return sets the Cover upon placement.
     */
    public void placeCover(byte aSide, ItemStack aCover, ICoverable aTileEntity) {
        aTileEntity.setCoverIDAtSide(aSide, GT_Utility.stackToInt(aCover));
    }
}