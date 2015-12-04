package gregtech.common.tileentities.storage;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_Container_QuantumChest;
import gregtech.common.gui.GT_GUIContainer_QuantumChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_QuantumChest extends GT_MetaTileEntity_TieredMachineBlock {
    public int mItemCount = 0;
    public ItemStack mItemStack = null;
    public GT_MetaTileEntity_QuantumChest(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 3, "This Chest stores " + ((int) ((Math.pow(6, aTier)) * 270000)) + " Blocks");
    }

    public GT_MetaTileEntity_QuantumChest(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 3, aDescription, aTextures);
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
        return true;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_QuantumChest(mName, mTier, mDescription, mTextures);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

//	  public void onRightclick(EntityPlayer aPlayer)
//	  {
//	    ItemStack tPlayerItem = aPlayer.inventory.getCurrentItem();
//	    if (tPlayerItem == null)
//	    {
//	      if (this.mItemID > 0)
//	      {
//	        for (int i = 0; (this.mItemCount < getMaxItemCount()) && (i < aPlayer.field_71071_by.func_70302_i_()); i++)
//	        {
//	          if ((aPlayer.field_71071_by.func_70301_a(i) != null) && (aPlayer.field_71071_by.func_70301_a(i).field_77993_c == this.mItemID) && (aPlayer.field_71071_by.func_70301_a(i).func_77960_j() == this.mItemMeta) && (!aPlayer.field_71071_by.func_70301_a(i).func_77942_o()))
//	          {
//	            this.mItemCount += aPlayer.field_71071_by.func_70301_a(i).field_77994_a;
//	            if (aPlayer.field_71071_by.func_70301_a(i).field_77994_a == 111)
//	            {
//	              this.mItemCount = (getMaxItemCount() + 192 - (this.mItemCount + (this.mInventory[0] == null ? 0 : this.mInventory[0].field_77994_a) + (this.mInventory[1] == null ? 0 : this.mInventory[1].field_77994_a) + (this.mInventory[2] == null ? 0 : this.mInventory[2].field_77994_a)));
//	            }
//	            else if (this.mItemCount > getMaxItemCount())
//	            {
//	              aPlayer.field_71071_by.func_70301_a(i).field_77994_a = (this.mItemCount - getMaxItemCount());
//	              this.mItemCount = getMaxItemCount();
//	            }
//	            else
//	            {
//	              aPlayer.field_71071_by.func_70301_a(i).field_77994_a = 0;
//	            }
//	          }
//	          if ((aPlayer.field_71071_by.func_70301_a(i) != null) && (aPlayer.field_71071_by.func_70301_a(i).field_77994_a <= 0)) {
//	            aPlayer.field_71071_by.func_70299_a(i, null);
//	          }
//	        }
//	        GT_Utility.sendChatToPlayer(aPlayer, this.mItemCount + (this.mInventory[0] == null ? 0 : this.mInventory[0].field_77994_a) + (this.mInventory[1] == null ? 0 : this.mInventory[1].field_77994_a) + (this.mInventory[2] == null ? 0 : this.mInventory[2].field_77994_a) + " of " + new ItemStack(this.mItemID, 1, this.mItemMeta).func_82833_r());
//	      }
//	    }
//	    if (aPlayer.field_71069_bz != null) {
//	      aPlayer.field_71069_bz.func_75142_b();
//	    }
//	  }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_QuantumChest(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_QuantumChest(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {

        if (getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().isAllowedToWork()) {
//            	if(mInventory[0]!=null)System.out.println("input: "+mInventory[0].stackSize);
//            	System.out.println("store: "+mItemCount);
//            	if(mInventory[0]!=null)System.out.println("output: "+mInventory[2].stackSize);
            if ((getItemCount() <= 0)) {
                this.mItemStack = null;
                this.mItemCount = 0;
            }
            if (this.mItemStack == null && this.mInventory[0] != null) {
                this.mItemStack = mInventory[0].copy();
            }
            if ((this.mInventory[0] != null) && (this.mItemCount < getMaxItemCount()) && GT_Utility.areStacksEqual(this.mInventory[0], this.mItemStack)) {
                this.mItemCount += this.mInventory[0].stackSize;
                if (this.mItemCount > getMaxItemCount()) {
                    this.mInventory[0].stackSize = (this.mItemCount - getMaxItemCount());
                    this.mItemCount = getMaxItemCount();
                } else {
                    this.mInventory[0] = null;
                }
            }
            if (this.mInventory[1] == null && mItemStack != null) {
                this.mInventory[1] = mItemStack.copy();
                this.mInventory[1].stackSize = Math.min(mItemStack.getMaxStackSize(), this.mItemCount);
                this.mItemCount -= this.mInventory[1].stackSize;
            } else if ((this.mItemCount > 0) && GT_Utility.areStacksEqual(this.mInventory[1], this.mItemStack) && this.mInventory[1].getMaxStackSize() > this.mInventory[1].stackSize) {
                int tmp = Math.min(this.mItemCount, this.mInventory[1].getMaxStackSize() - this.mInventory[1].stackSize);
                this.mInventory[1].stackSize += tmp;
                this.mItemCount -= tmp;
            }
            if (this.mItemStack != null) {
                this.mInventory[2] = this.mItemStack.copy();
                this.mInventory[2].stackSize = Math.min(mItemStack.getMaxStackSize(), this.mItemCount);
            } else {
                this.mInventory[2] = null;
            }
        }
    }

    private int getItemCount() {
        return this.mItemCount;
    }

    public void setItemCount(int aCount) {
        this.mItemCount = aCount;
    }

    public int getProgresstime() {
        return this.mItemCount + (this.mInventory[0] == null ? 0 : this.mInventory[0].stackSize) + (this.mInventory[1] == null ? 0 : this.mInventory[1].stackSize);
    }

    public int maxProgresstime() {
        return getMaxItemCount();
    }

    public int getMaxItemCount() {
        return (int) (((Math.pow(6, mTier)) * 270000) - 128);
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return aIndex==1;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return aIndex==0&&(mInventory[0]==null||GT_Utility.areStacksEqual(this.mInventory[0], aStack));
    }

    @Override
    public String[] getInfoData() {

        if (mItemStack == null) {
            return new String[]{
                    "Quantum Chest",
                    "Stored Items:",
                    "No Items",
                    Integer.toString(0),
                    Integer.toString(getMaxItemCount())};
        }
        return new String[]{
                "Quantum Chest",
                "Stored Items:",
                mItemStack.getDisplayName(),
                Integer.toString(mItemCount),
                Integer.toString(getMaxItemCount())};
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("mItemCount", this.mItemCount);
        if (this.mItemStack != null)
            aNBT.setTag("mItemStack", this.mItemStack.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        if (aNBT.hasKey("mItemCount"))
            this.mItemCount = aNBT.getInteger("mItemCount");
        if (aNBT.hasKey("mItemStack"))
            this.mItemStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) aNBT.getTag("mItemStack"));
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aBaseMetaTileEntity.getFrontFacing() == 0 && aSide == 4) {
            return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_QCHEST)};
        }
        return aSide == aBaseMetaTileEntity.getFrontFacing() ? new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_QCHEST)} : new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]};//aSide != aFacing ? mMachineBlock != 0 ? new ITexture[] {Textures.BlockIcons.CASING_BLOCKS[mMachineBlock]} : new ITexture[] {Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex+1]} : mMachineBlock != 0 ? aActive ? getTexturesActive(Textures.BlockIcons.CASING_BLOCKS[mMachineBlock]) : getTexturesInactive(Textures.BlockIcons.CASING_BLOCKS[mMachineBlock]) : aActive ? getTexturesActive(Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex+1]) : getTexturesInactive(Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex+1]);
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return new ITexture[0][0][0];
    }
}