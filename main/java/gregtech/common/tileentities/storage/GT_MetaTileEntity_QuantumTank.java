package gregtech.common.tileentities.storage;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.*;
import net.minecraft.entity.player.EntityPlayer;

public class GT_MetaTileEntity_QuantumTank
  extends GT_MetaTileEntity_BasicTank
{
  public GT_MetaTileEntity_QuantumTank(int aID, String aName, String aNameRegional)
  {
    super(aID, aName, aNameRegional, aID, aID, aNameRegional, null);
  }
  
  public boolean unbreakable()
  {
    return true;
  }
  
  public boolean isSimpleMachine()
  {
    return false;
  }
  
  public boolean isValidSlot(int aIndex)
  {
    return aIndex < 2;
  }
  
  public boolean isFacingValid(byte aFacing)
  {
    return false;
  }
  
  public void onRightclick(EntityPlayer aPlayer)
  {
    getBaseMetaTileEntity().openGUI(aPlayer, 114);
  }
  
  public boolean isAccessAllowed(EntityPlayer aPlayer)
  {
    return true;
  }
  
  public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
  {
    return new GT_MetaTileEntity_QuantumTank(mTier, mDescription, mDescription);
  }
  
  public boolean doesFillContainers()
  {
    return true;
  }
  
  public boolean doesEmptyContainers()
  {
    return true;
  }
  
  public boolean canTankBeFilled()
  {
    return true;
  }
  
  public boolean canTankBeEmptied()
  {
    return true;
  }
  
  public boolean displaysItemStack()
  {
    return true;
  }
  
  public boolean displaysStackSize()
  {
    return false;
  }
  
  public boolean isFluidChangingAllowed()
  {
    return getBaseMetaTileEntity().isAllowedToWork();
  }
  
  public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone)
  {
    if (aSide == 0) {
      return 38;
    }
    if (aSide == 1) {
      return 37;
    }
    return 36;
  }
  
  public String[] getDescription()
  {
    return new String[]{"With a capacity of 488.28125 Chunks!"};
  }
  
  public int getCapacity()
  {
    return 2000000000;
  }
  
  public int getTankPressure()
  {
    return 100;
  }

@Override
public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity,
		byte aSide, byte aFacing, byte aColorIndex, boolean aActive,
		boolean aRedstone) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ITexture[][][] getTextureSet(ITexture[] aTextures) {
	// TODO Auto-generated method stub
	return null;
}
}
