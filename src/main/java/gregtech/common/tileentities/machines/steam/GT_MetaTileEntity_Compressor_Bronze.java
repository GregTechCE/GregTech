package gregtech.common.tileentities.machines.steam;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_BasicMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine_Bronze;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class GT_MetaTileEntity_Compressor_Bronze
        extends GT_MetaTileEntity_BasicMachine_Bronze {
    public GT_MetaTileEntity_Compressor_Bronze(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, "Compressing Items", 1, 1, false);
    }

    public GT_MetaTileEntity_Compressor_Bronze(String aName, String aDescription, ITexture[][][] aTextures) {
        super(aName, aDescription, aTextures, 1, 1, false);
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "BronzeCompressor.png", "ic2.compressor");
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Compressor_Bronze(this.mName, this.mDescription, this.mTextures);
    }

    public int checkRecipe() {
        if (null != (this.mOutputItems[0] = GT_ModHandler.getCompressorOutput(getInputAt(0), true, getOutputAt(0)))) {
            this.mEUt = 2;
            this.mMaxProgresstime = 800;
            return 2;
        }
        return 0;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if (!super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) {
            return false;
        }
        return GT_ModHandler.getCompressorOutput(GT_Utility.copyAmount(64L, new Object[]{aStack}), false, null) != null;
    }

    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ) {
        super.startSoundLoop(aIndex, aX, aY, aZ);
        if (aIndex == 1) {
            GT_Utility.doSoundAtClient((String) GregTech_API.sSoundList.get(Integer.valueOf(203)), 10, 1.0F, aX, aY, aZ);
        }
    }

    public void startProcess() {
        sendLoopStart((byte) 1);
    }

    public ITexture[] getSideFacingActive(byte aColor) {
        return new ITexture[]{super.getSideFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_COMPRESSOR_ACTIVE)};
    }

    public ITexture[] getSideFacingInactive(byte aColor) {
        return new ITexture[]{super.getSideFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_COMPRESSOR)};
    }

    public ITexture[] getFrontFacingActive(byte aColor) {
        return new ITexture[]{super.getFrontFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_COMPRESSOR_ACTIVE)};
    }

    public ITexture[] getFrontFacingInactive(byte aColor) {
        return new ITexture[]{super.getFrontFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_COMPRESSOR)};
    }

    public ITexture[] getTopFacingActive(byte aColor) {
        return new ITexture[]{super.getTopFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_COMPRESSOR_ACTIVE)};
    }

    public ITexture[] getTopFacingInactive(byte aColor) {
        return new ITexture[]{super.getTopFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_COMPRESSOR)};
    }

    public ITexture[] getBottomFacingActive(byte aColor) {
        return new ITexture[]{super.getBottomFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_COMPRESSOR_ACTIVE)};
    }

    public ITexture[] getBottomFacingInactive(byte aColor) {
        return new ITexture[]{super.getBottomFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_COMPRESSOR)};
    }
}
