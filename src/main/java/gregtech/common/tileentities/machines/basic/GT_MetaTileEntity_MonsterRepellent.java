package gregtech.common.tileentities.machines.basic;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_SpawnEventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static gregtech.api.enums.GT_Values.V;

public class GT_MetaTileEntity_MonsterRepellent extends GT_MetaTileEntity_TieredMachineBlock {

    public int mRange = 16;

    public GT_MetaTileEntity_MonsterRepellent(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0, "Repels nasty Creatures. Range: " + (4 + (12 * aTier)) + " unpowered / " + (16 + (48 * aTier)) + " powered");
    }

    public GT_MetaTileEntity_MonsterRepellent(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_MonsterRepellent(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], (aSide != 1) ? null : aActive ? new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TELEPORTER_ACTIVE) : new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TELEPORTER)};
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
        if (aBaseMetaTileEntity.isAllowedToWork() && aBaseMetaTileEntity.isServerSide()) {
            int[] tCoords = new int[]{aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord(), aBaseMetaTileEntity.getWorld().provider.dimensionId};
            if ((aTimer % 600 == 0) && !GT_SpawnEventHandler.mobReps.contains(tCoords)) {
                GT_SpawnEventHandler.mobReps.add(tCoords);
            }
            if (aBaseMetaTileEntity.isUniversalEnergyStored(getMinimumStoredEU()) && aBaseMetaTileEntity.decreaseStoredEnergyUnits(1 << (this.mTier * 2), false)) {
                mRange = 16 + (48 * mTier);
            } else {
                mRange = 4 + (12 * mTier);
            }
        }
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        int[] tCoords = new int[]{aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord(), aBaseMetaTileEntity.getWorld().provider.dimensionId};
        GT_SpawnEventHandler.mobReps.add(tCoords);
    }

    @Override
    public void onRemoval() {
        int[] tCoords = new int[]{this.getBaseMetaTileEntity().getXCoord(), this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord(), this.getBaseMetaTileEntity().getWorld().provider.dimensionId};
        GT_SpawnEventHandler.mobReps.remove(tCoords);
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return true;
    }

    @Override
    public boolean isTeleporterCompatible() {
        return false;
    }

    @Override
    public long getMinimumStoredEU() {
        return 512;
    }

    @Override
    public long maxEUStore() {
        return 512 + V[mTier] * 50;
    }

    @Override
    public long maxEUInput() {
        return V[mTier];
    }

    @Override
    public long maxAmperesIn() {
        return 2;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return null;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
    }
}
