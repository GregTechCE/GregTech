package gregtech.common.tileentities.automation;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.common.gui.GT_Container_SuperBuffer;
import gregtech.common.gui.GT_GUIContainer_SuperBuffer;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_MetaTileEntity_SuperBuffer
        extends GT_MetaTileEntity_ChestBuffer {
    public GT_MetaTileEntity_SuperBuffer(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 257, "Buffering up to 256 Stacks");
    }

    public GT_MetaTileEntity_SuperBuffer(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_SuperBuffer(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
    }

    public ITexture getOverlayIcon() {
        return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_SUPERBUFFER);
    }

    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_SuperBuffer(aPlayerInventory, aBaseMetaTileEntity);
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_SuperBuffer(aPlayerInventory, aBaseMetaTileEntity);
    }
}
