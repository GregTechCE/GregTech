package gregtech.api.metatileentity.implementations;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyStorage;
import crazypants.enderio.machine.capbank.TileCapBank;
import crazypants.enderio.machine.capbank.network.ICapBankNetwork;
import crazypants.enderio.power.IPowerContainer;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import static gregtech.api.enums.GT_Values.V;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main construct for my Basic Machines such as the Automatic Extractor
 * Extend this class to make a simple Machine
 */
public class GT_MetaTileEntity_Transformer extends GT_MetaTileEntity_TieredMachineBlock {
    public GT_MetaTileEntity_Transformer(int aID, String aName, String aNameRegional, int aTier, String aDescription) {
        super(aID, aName, aNameRegional, aTier, 0, aDescription);
    }

    public GT_MetaTileEntity_Transformer(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[12][17][];
        for (byte i = -1; i < 16; i++) {
            rTextures[0][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier]};
            rTextures[1][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier]};
            rTextures[2][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier]};
            rTextures[3][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI[mTier]};
            rTextures[4][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI[mTier]};
            rTextures[5][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI[mTier]};
            rTextures[6][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_IN[mTier]};
            rTextures[7][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_IN[mTier]};
            rTextures[8][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_IN[mTier]};
            rTextures[9][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier]};
            rTextures[10][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier]};
            rTextures[11][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier]};
        }
        return rTextures;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return mTextures[Math.min(2, aSide) + (aSide == aFacing ? 3 : 0) + (aActive ? 0 : 6)][aColorIndex + 1];
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Transformer(mName, mTier, mDescription, mTextures);
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
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
    public boolean isEnetOutput() {
        return true;
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return getBaseMetaTileEntity().isAllowedToWork() ? aSide == getBaseMetaTileEntity().getFrontFacing() : aSide != getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        return !isInputFacing(aSide);
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
        return 512 + V[mTier + 1] * 2;
    }

    @Override
    public long maxEUInput() {
        return V[getBaseMetaTileEntity().isAllowedToWork() ? mTier + 1 : mTier];
    }

    @Override
    public long maxEUOutput() {
        return V[getBaseMetaTileEntity().isAllowedToWork() ? mTier : mTier + 1];
    }

    @Override
    public long maxAmperesOut() {
        return getBaseMetaTileEntity().isAllowedToWork() ? V[mTier + 1] / V[mTier] : 1;
    }

    @Override
    public long maxAmperesIn() {
        return getBaseMetaTileEntity().isAllowedToWork() ? 1 : V[mTier + 1] / V[mTier];
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            aBaseMetaTileEntity.setActive(aBaseMetaTileEntity.isAllowedToWork());
            for (byte i = 0; i < 6 && aBaseMetaTileEntity.getStoredEU() < aBaseMetaTileEntity.getEUCapacity(); i++)
                if (aBaseMetaTileEntity.inputEnergyFrom(i)) {
                    TileEntity tTileEntity = aBaseMetaTileEntity.getTileEntityAtSide(i);
                    if (tTileEntity instanceof IEnergySource && ((IEnergySource) tTileEntity).emitsEnergyTo((TileEntity) aBaseMetaTileEntity, ForgeDirection.getOrientation(GT_Utility.getOppositeSide(i)))) {
                        long tEU = Math.min(maxEUInput(), (long) ((IEnergySource) tTileEntity).getOfferedEnergy());
                        ((IEnergySource) tTileEntity).drawEnergy(tEU);
                        aBaseMetaTileEntity.injectEnergyUnits((byte) 6, tEU, 1);
                    } else if (GregTech_API.mInputRF && tTileEntity instanceof IEnergyProvider && ((IEnergyProvider) tTileEntity).extractEnergy(ForgeDirection.getOrientation(GT_Utility.getOppositeSide(i)), 1, true) == 1) {
                        long tEU = (long) ((IEnergyProvider) tTileEntity).extractEnergy(ForgeDirection.getOrientation(GT_Utility.getOppositeSide(i)), (int) maxEUInput() * 100 / GregTech_API.mRFtoEU, false);
                        tEU = tEU * GregTech_API.mRFtoEU / 100;
                        aBaseMetaTileEntity.injectEnergyUnits((byte) 6, Math.min(tEU, maxEUInput()), 1);
                    } else if (GregTech_API.mInputRF && tTileEntity instanceof IEnergyStorage && ((IEnergyStorage) tTileEntity).extractEnergy(1, true) == 1) {
                        long tEU = (long) ((IEnergyStorage) tTileEntity).extractEnergy((int) maxEUInput() * 100 / GregTech_API.mRFtoEU, false);
                        tEU = tEU * GregTech_API.mRFtoEU / 100;
                        aBaseMetaTileEntity.injectEnergyUnits((byte) 6, Math.min(tEU, maxEUInput()), 1);
                    } else if (GregTech_API.mInputRF && GregTech_API.meIOLoaded && tTileEntity instanceof IPowerContainer && ((IPowerContainer) tTileEntity).getEnergyStored() > 0) {
                        int storedRF = ((IPowerContainer) tTileEntity).getEnergyStored();
                        int extractRF = (int) maxEUInput() * 100 / GregTech_API.mRFtoEU;
                        long tEU = 0;
                        if (tTileEntity instanceof TileCapBank) {
                            ICapBankNetwork network = ((TileCapBank) tTileEntity).getNetwork();
                            if (network != null && network.getEnergyStoredL() > 0) {
                                tEU = Math.min((Math.min(Math.min(network.getEnergyStoredL(), storedRF - extractRF), network.getMaxOutput())) * GregTech_API.mRFtoEU / 100, maxEUInput());
                                network.addEnergy((int) -(tEU * 100 / GregTech_API.mRFtoEU));
                            }
                        } else {
                            if (storedRF > extractRF) {
                                ((IPowerContainer) tTileEntity).setEnergyStored(storedRF - extractRF);
                                tEU = maxEUInput();
                            } else {
                                ((IPowerContainer) tTileEntity).setEnergyStored(0);
                                tEU = storedRF * GregTech_API.mRFtoEU / 100;
                            }
                        }
                        aBaseMetaTileEntity.injectEnergyUnits((byte) 6, Math.min(tEU, maxEUInput()), 1);
                    }
                }
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        //
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        //
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }
}