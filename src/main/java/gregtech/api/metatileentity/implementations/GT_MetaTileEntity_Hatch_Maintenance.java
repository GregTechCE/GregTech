package gregtech.api.metatileentity.implementations;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_MaintenanceHatch;
import gregtech.api.gui.GT_GUIContainer_MaintenanceHatch;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_Hatch_Maintenance extends GT_MetaTileEntity_Hatch {
    public boolean mWrench = false, mScrewdriver = false, mSoftHammer = false, mHardHammer = false, mSolderingTool = false, mCrowbar = false;

    public GT_MetaTileEntity_Hatch_Maintenance(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "For maintaining Multiblocks");
    }

    public GT_MetaTileEntity_Hatch_Maintenance(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 1, aDescription, aTextures);
    }

    @Override
    public String[] getDescription() {
        return new String[]{mDescription, "Cannot be shared between Multiblocks!"};
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_MAINTENANCE)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_MAINTENANCE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_DUCTTAPE)};
    }

    @Override
    public void initDefaultModes(NBTTagCompound aNBT) {
        getBaseMetaTileEntity().setActive(true);
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
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return false;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Hatch_Maintenance(mName, mTier, mDescription, mTextures);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        if (aSide == aBaseMetaTileEntity.getFrontFacing()) aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_MaintenanceHatch(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MaintenanceHatch(aPlayerInventory, aBaseMetaTileEntity);
    }

    public void onToolClick(ItemStack aStack, EntityLivingBase aPlayer) {
        if (aStack == null || aPlayer == null) return;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sWrenchList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mWrench = true;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sScrewdriverList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mScrewdriver = true;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sSoftHammerList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mSoftHammer = true;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sHardHammerList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mHardHammer = true;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sCrowbarList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mCrowbar = true;
        if (GT_ModHandler.useSolderingIron(aStack, aPlayer)) mSolderingTool = true;
        if (GT_OreDictUnificator.isItemStackInstanceOf(aStack, "craftingDuctTape")) {
            mWrench = mScrewdriver = mSoftHammer = mHardHammer = mCrowbar = mSolderingTool = true;
            getBaseMetaTileEntity().setActive(false);
            aStack.stackSize--;
        }
        if (mSolderingTool && aPlayer instanceof EntityPlayerMP) {
            EntityPlayerMP tPlayer = (EntityPlayerMP) aPlayer;
            try{GT_Mod.instance.achievements.issueAchievement(tPlayer, "maintainance");}catch(Exception e){}
        }
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
