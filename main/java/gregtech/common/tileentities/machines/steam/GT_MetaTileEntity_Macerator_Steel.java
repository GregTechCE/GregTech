package gregtech.common.tileentities.machines.steam;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.gui.GT_GUIContainer_BasicMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine_Steel;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;

import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GT_MetaTileEntity_Macerator_Steel
  extends GT_MetaTileEntity_BasicMachine_Steel
{
  public GT_MetaTileEntity_Macerator_Steel(int aID, String aName, String aNameRegional)
  {
    super(aID, aName, aNameRegional, "Macerating your Ores", 1, 1, false);
  }
  
  public GT_MetaTileEntity_Macerator_Steel(String aName, String aDescription, ITexture[][][] aTextures)
  {
    super(aName, aDescription, aTextures, 1, 1, false);
  }
  
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
  {
    return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "SteelMacerator.png", "ic2.macerator");
  }
  
  public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
  {
    return new GT_MetaTileEntity_Macerator_Steel(this.mName, this.mDescription, this.mTextures);
  }
  
  public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick)
  {
    super.onPreTick(aBaseMetaTileEntity, aTick);
    if ((aBaseMetaTileEntity.isClientSide()) && (aBaseMetaTileEntity.isActive()) && (aBaseMetaTileEntity.getFrontFacing() != 1) && (aBaseMetaTileEntity.getCoverIDAtSide((byte)1) == 0) && (!aBaseMetaTileEntity.getOpacityAtSide((byte)1)))
    {
      Random tRandom = aBaseMetaTileEntity.getWorld().rand;
      aBaseMetaTileEntity.getWorld().spawnParticle("smoke", aBaseMetaTileEntity.getXCoord() + 0.8F - tRandom.nextFloat() * 0.6F, aBaseMetaTileEntity.getYCoord() + 0.9F + tRandom.nextFloat() * 0.2F, aBaseMetaTileEntity.getZCoord() + 0.8F - tRandom.nextFloat() * 0.6F, 0.0D, 0.0D, 0.0D);
    }
  }
  
  public int checkRecipe()
  {
    if (null != (this.mOutputItems[0] = GT_ModHandler.getMaceratorOutput(getInputAt(0), true, getOutputAt(0))))
    {
      this.mEUt = 6;
      this.mMaxProgresstime = 400;
      return 2;
    }
    return 0;
  }
  
  public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
  {
    if (!super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) {return false;}
    return GT_ModHandler.getMaceratorOutput(GT_Utility.copyAmount(64L, new Object[] { aStack }), false, null) != null;
  }
  
  public void startSoundLoop(byte aIndex, double aX, double aY, double aZ)
  {
    super.startSoundLoop(aIndex, aX, aY, aZ);
    if (aIndex == 1) {
      GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(201)), 10, 1.0F, aX, aY, aZ);
    }
  }
  
  public void startProcess()
  {
    sendLoopStart((byte)1);
  }
  
  public ITexture[] getSideFacingActive(byte aColor)
  {
    return new ITexture[] { super.getSideFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_MACERATOR_ACTIVE) };
  }
  
  public ITexture[] getSideFacingInactive(byte aColor)
  {
    return new ITexture[] { super.getSideFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_MACERATOR) };
  }
  
  public ITexture[] getFrontFacingActive(byte aColor)
  {
    return new ITexture[] { super.getFrontFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_MACERATOR_ACTIVE) };
  }
  
  public ITexture[] getFrontFacingInactive(byte aColor)
  {
    return new ITexture[] { super.getFrontFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_MACERATOR) };
  }
  
  public ITexture[] getTopFacingActive(byte aColor)
  {
    return new ITexture[] { super.getTopFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_MACERATOR_ACTIVE) };
  }
  
  public ITexture[] getTopFacingInactive(byte aColor)
  {
    return new ITexture[] { super.getTopFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_MACERATOR) };
  }
  
  public ITexture[] getBottomFacingActive(byte aColor)
  {
    return new ITexture[] { super.getBottomFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_MACERATOR_ACTIVE) };
  }
  
  public ITexture[] getBottomFacingInactive(byte aColor)
  {
    return new ITexture[] { super.getBottomFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_MACERATOR) };
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.steam.GT_MetaTileEntity_Macerator_Steel
 * JD-Core Version:    0.7.0.1
 */