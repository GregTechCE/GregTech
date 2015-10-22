package gregtech.common.tileentities.automation;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Buffer;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_Container_ChestBuffer;
import gregtech.common.gui.GT_GUIContainer_ChestBuffer;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_MetaTileEntity_ChestBuffer
        extends GT_MetaTileEntity_Buffer {
    public GT_MetaTileEntity_ChestBuffer(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 28, "Buffering lots of incoming Items");
    }

    public GT_MetaTileEntity_ChestBuffer(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String aDescription) {
        super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription);
    }

    public GT_MetaTileEntity_ChestBuffer(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_ChestBuffer(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
    }

    public ITexture getOverlayIcon() {
        return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_CHESTBUFFER);
    }

    public boolean isValidSlot(int aIndex) {
        return aIndex < this.mInventory.length - 1;
    }

    protected void moveItems(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
        fillStacksIntoFirstSlots();
        super.moveItems(aBaseMetaTileEntity, aTimer);
        fillStacksIntoFirstSlots();
    }

    protected void fillStacksIntoFirstSlots() {
        for (int i = 0; i < this.mInventory.length - 1; i++) {
            for (int j = i + 1; j < this.mInventory.length - 1; j++) {
                if ((this.mInventory[j] != null) && ((this.mInventory[i] == null) || (GT_Utility.areStacksEqual(this.mInventory[i], this.mInventory[j])))) {
                    GT_Utility.moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), j, i, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
                }
            }
        }
    }

    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_ChestBuffer(aPlayerInventory, aBaseMetaTileEntity);
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_ChestBuffer(aPlayerInventory, aBaseMetaTileEntity);
    }
}
