package gregtech.api.metatileentity.implementations;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.items.ItemList;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.gui.ContainerTileFourSlots;
import gregtech.api.gui.GT_Container_MaintenanceHatch;
import gregtech.api.gui.GT_GUIContainer_2by2;
import gregtech.api.gui.GT_GUIContainer_MaintenanceHatch;
import gregtech.api.interfaces.ITexture;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

import java.util.Arrays;
import java.util.List;

public class GT_MetaTileEntity_Hatch_Maintenance extends GT_MetaTileEntity_Hatch {
    public boolean mWrench = false, mScrewdriver = false, mSoftHammer = false, mHardHammer = false, mSolderingTool = false, mCrowbar = false, mAuto;

    public GT_MetaTileEntity_Hatch_Maintenance(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "For maintaining Multiblocks");
        mAuto = false;
    }

    public GT_MetaTileEntity_Hatch_Maintenance(int aID, String aName, String aNameRegional, int aTier, boolean aAuto) {
        super(aID, aName, aNameRegional, aTier, 4, "For automatically maintaining Multiblocks");
        mAuto = aAuto;
    }

    public GT_MetaTileEntity_Hatch_Maintenance(String aName, int aTier, String aDescription, ITexture[][][] aTextures, boolean aAuto) {
        super(aName, aTier, 1, aDescription, aTextures);
        mAuto = aAuto;
    }

    @Override
    public String[] getDescription() {
        if(mAuto)return new String[]{mDescription,"4 Ducttape, 2 Lubricant Cells","4 Steel Screws, 2 Adv Circuits","For each autorepair"};
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
        if(aTileEntity.getMetaTileID()==111) return new GT_MetaTileEntity_Hatch_Maintenance(mName, mTier, mDescription, mTextures, true);
        return new GT_MetaTileEntity_Hatch_Maintenance(mName, mTier, mDescription, mTextures, false);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ, EnumHand hand) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        if (aSide == aBaseMetaTileEntity.getFrontFacing()) aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        if(mAuto) return new ContainerTileFourSlots(aPlayerInventory, aBaseMetaTileEntity);
        return new GT_Container_MaintenanceHatch(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        if(mAuto) return new GT_GUIContainer_2by2(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
        return new GT_GUIContainer_MaintenanceHatch(aPlayerInventory, aBaseMetaTileEntity);
    }

    public boolean autoMaintainance() {
        boolean tSuccess = true;
        ItemStack[] mInputs = new ItemStack[]{ItemList.Duct_Tape.get(4, new Object[]{}), OreDictionaryUnifier.get(OrePrefixes.cell, Materials.Lubricant, 2), OreDictionaryUnifier.get(OrePrefixes.screw, Materials.Steel, 4), OreDictionaryUnifier.get(OrePrefixes.circuit, Materials.Advanced, 2)};
        List<ItemStack> aInputs = Arrays.asList(mInventory);
        if (mInputs.length > 0 && aInputs == null) tSuccess = false;
        int amt = 0;
        for (ItemStack tStack : mInputs) {
            if (tStack != null) {
                amt = tStack.stackSize;
                boolean temp = true;
                for (ItemStack aStack : aInputs) {
                    if ((GT_Utility.areUnificationsEqual(aStack, tStack, true) || GT_Utility.areUnificationsEqual(OreDictionaryUnifier.get(false, aStack), tStack, true))) {
                        amt -= aStack.stackSize;
                        if (amt < 1) {
                            temp = false;
                            break;
                        }
                    }
                }
                if (temp) tSuccess = false;
            }
        }
        if(tSuccess){
            for (ItemStack tStack : mInputs) {
                if (tStack != null) {
                    amt = tStack.stackSize;
                    for (ItemStack aStack : aInputs) {
                        if ((GT_Utility.areUnificationsEqual(aStack, tStack, true) || GT_Utility.areUnificationsEqual(OreDictionaryUnifier.get(false, aStack), tStack, true))) {
                            if (aStack.stackSize < amt){
                                amt -= aStack.stackSize;
                                aStack.stackSize = 0;
                            }else{
                                aStack.stackSize -= amt;
                                amt = 0;
                                break;
                            }
                        }
                    }
                }
            }
            this.mCrowbar = true;
            this.mHardHammer = true;
            this.mScrewdriver = true;
            this.mSoftHammer = true;
            this.mSolderingTool = true;
            this.mWrench = true;
            return true;
        }
        return false;
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
        if (OreDictionaryUnifier.isItemStackInstanceOf(aStack, "craftingDuctTape")) {
            mWrench = mScrewdriver = mSoftHammer = mHardHammer = mCrowbar = mSolderingTool = true;
            getBaseMetaTileEntity().setActive(false);
            aStack.stackSize--;
        }
        if (mSolderingTool && aPlayer instanceof EntityPlayerMP) {
            EntityPlayerMP tPlayer = (EntityPlayerMP) aPlayer;
            try {
                GT_Mod.achievements.issueAchievement(tPlayer, "maintainance");
            } catch (Exception e) {
            }
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