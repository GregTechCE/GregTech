package gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.GT_Values.V;
import gregtech.api.enums.Textures;
import gregtech.api.gui.*;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * This is the main construct for my Basic Machines such as the Automatic Extractor
 * Extend this class to make a simple Machine
 */
public class GT_MetaTileEntity_BasicBatteryBuffer extends GT_MetaTileEntity_TieredMachineBlock {
	public boolean mCharge = false, mDecharge = false;
	public int mBatteryCount = 0, mChargeableCount = 0;
	
	public GT_MetaTileEntity_BasicBatteryBuffer(int aID, String aName, String aNameRegional, int aTier, String aDescription, int aSlotCount) {
		super(aID, aName, aNameRegional, aTier, aSlotCount, aDescription);
	}
	
	public GT_MetaTileEntity_BasicBatteryBuffer(String aName, int aTier, String aDescription, ITexture[][][] aTextures, int aSlotCount) {
		super(aName, aTier, aSlotCount, aDescription, aTextures);
	}
	
	@Override
	public String[] getDescription() {
		return new String[] {mDescription, mInventory.length + " Slots"};
	}
	
	@Override
	public ITexture[][][] getTextureSet(ITexture[] aTextures) {
		ITexture[][][] rTextures = new ITexture[2][17][];
		for (byte i = -1; i < 16; i++) {
			rTextures[ 0][i+1] = new ITexture[] {Textures.BlockIcons.MACHINE_CASINGS[mTier][i+1]};
			rTextures[ 1][i+1] = new ITexture[] {Textures.BlockIcons.MACHINE_CASINGS[mTier][i+1], mInventory.length > 4 ? Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier] : Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier]};
		}
		return rTextures;
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return mTextures[aSide == aFacing ? 1 : 0][aColorIndex+1];
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_BasicBatteryBuffer(mName, mTier, mDescription, mTextures, mInventory.length);
	}
	
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isElectric()							{return true;}
	@Override public boolean isValidSlot(int aIndex)				{return true;}
	@Override public boolean isFacingValid(byte aFacing)			{return true;}
	@Override public boolean isEnetInput() 							{return true;}
	@Override public boolean isEnetOutput() 						{return true;}
	@Override public boolean isInputFacing(byte aSide)				{return aSide!=getBaseMetaTileEntity().getFrontFacing();}
	@Override public boolean isOutputFacing(byte aSide)				{return aSide==getBaseMetaTileEntity().getFrontFacing();}
	@Override public boolean isTeleporterCompatible()				{return false;}
	@Override public long getMinimumStoredEU()						{return V[mTier]*16*mInventory.length;}
	@Override public long maxEUStore()								{return V[mTier]*64*mInventory.length;}
    @Override public long maxEUInput()								{return V[mTier];}
    @Override public long maxEUOutput()								{return V[mTier];}
    @Override public long maxAmperesIn()							{return mChargeableCount * 2;}
    @Override public long maxAmperesOut()							{return mBatteryCount;}
	@Override public int rechargerSlotStartIndex()					{return 0;}
	@Override public int dechargerSlotStartIndex()					{return 0;}
	@Override public int rechargerSlotCount()						{return mCharge?mInventory.length:0;}
	@Override public int dechargerSlotCount()						{return mDecharge?mInventory.length:0;}
	@Override public int getProgresstime()							{return (int)getBaseMetaTileEntity().getUniversalEnergyStored();}
	@Override public int maxProgresstime()							{return (int)getBaseMetaTileEntity().getUniversalEnergyCapacity();}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		//
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		//
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) return true;
		aBaseMetaTileEntity.openGUI(aPlayer);
		return true;
	}
	
	@Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		switch (mInventory.length) {
		case  1: return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
		case  4: return new GT_Container_2by2(aPlayerInventory, aBaseMetaTileEntity);
		case  9: return new GT_Container_3by3(aPlayerInventory, aBaseMetaTileEntity);
		case 16: return new GT_Container_4by4(aPlayerInventory, aBaseMetaTileEntity);
		}
		return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		switch (mInventory.length) {
		case  1: return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
		case  4: return new GT_GUIContainer_2by2(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
		case  9: return new GT_GUIContainer_3by3(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
		case 16: return new GT_GUIContainer_4by4(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
		}
		return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
	    if (aBaseMetaTileEntity.isServerSide()) {
	    	mCharge   = aBaseMetaTileEntity.getStoredEU() / 2 > aBaseMetaTileEntity.getEUCapacity() / 3;
			mDecharge = aBaseMetaTileEntity.getStoredEU()     < aBaseMetaTileEntity.getEUCapacity() / 3;
			mBatteryCount = 0;
			mChargeableCount = 0;
			for (ItemStack tStack : mInventory) if (GT_ModHandler.isElectricItem(tStack, mTier)) {
				if (GT_ModHandler.isChargerItem(tStack)) mBatteryCount++;
				mChargeableCount++;
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