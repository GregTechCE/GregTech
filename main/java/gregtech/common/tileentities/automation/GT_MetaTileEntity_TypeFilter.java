package gregtech.common.tileentities.automation;

import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Buffer;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_Container_TypeFilter;
import gregtech.common.gui.GT_GUIContainer_TypeFilter;

import java.util.ArrayList;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_TypeFilter
  extends GT_MetaTileEntity_Buffer
{
  public boolean bNBTAllowed = false;
  public boolean bInvertFilter = false;
  public int mRotationIndex = 0;
  public OrePrefixes mPrefix = OrePrefixes.ore;
  
  public GT_MetaTileEntity_TypeFilter(int aID, String aName, String aNameRegional, int aTier)
  {
    super(aID, aName, aNameRegional, aTier, 11, "Filtering incoming Items by Type");
  }
  
  public GT_MetaTileEntity_TypeFilter(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures)
  {
    super(aName, aTier, aInvSlotCount, aDescription, aTextures);
  }
  
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
  {
    return new GT_MetaTileEntity_TypeFilter(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
  }
  
  public ITexture getOverlayIcon()
  {
    return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_TYPEFILTER);
  }
  
  public boolean isValidSlot(int aIndex)
  {
    return aIndex < 9;
  }
  
  public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
  {
    return new GT_Container_TypeFilter(aPlayerInventory, aBaseMetaTileEntity);
  }
  
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
  {
    return new GT_GUIContainer_TypeFilter(aPlayerInventory, aBaseMetaTileEntity);
  }
  
  public void clickTypeIcon(boolean aRightClick)
  {
    if (getBaseMetaTileEntity().isServerSide())
    {
      for (int i = 0; i < OrePrefixes.values().length; i++) {
        if (this.mPrefix == OrePrefixes.values()[i]) {
          for (this.mPrefix = null; this.mPrefix == null; this.mPrefix = OrePrefixes.values()[i])
          {
            if (aRightClick)
            {
              i--;
              if (i < 0) {
                i = OrePrefixes.values().length - 1;
              }
            }
            else
            {
              i++;
              if (i >= OrePrefixes.values().length) {
                i = 0;
              }
            }
            if(!OrePrefixes.values()[i].mPrefixedItems.isEmpty() && OrePrefixes.values()[i].mPrefixInto == OrePrefixes.values()[i])
    						mPrefix = OrePrefixes.values()[i];
          }
        }
      }
      this.mRotationIndex = 0;
    }
  }
  
  public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick)
  {
    super.onPreTick(aBaseMetaTileEntity, aTick);
    if ((getBaseMetaTileEntity().isServerSide()) && (aTick % 8L == 0L)) {
      if (this.mPrefix.mPrefixedItems.isEmpty())
      {
        this.mInventory[9] = null;
      }
      else
      {
        Object[] tmp63_60 = new Object[1]; int tmp90_89 = ((this.mRotationIndex + 1) % this.mPrefix.mPrefixedItems.size());this.mRotationIndex = tmp90_89;tmp63_60[0] = this.mPrefix.mPrefixedItems.get(tmp90_89);this.mInventory[9] = GT_Utility.copyAmount(1L, tmp63_60);
        if (this.mInventory[9].getItemDamage() == 32767) {
          this.mInventory[9].setItemDamage(0);
        }
        this.mInventory[9].setStackDisplayName(this.mPrefix.toString());
      }
    }
  }
  
  public void saveNBTData(NBTTagCompound aNBT)
  {
    super.saveNBTData(aNBT);
    aNBT.setString("mPrefix", this.mPrefix.toString());
    aNBT.setBoolean("bInvertFilter", this.bInvertFilter);
    aNBT.setBoolean("bNBTAllowed", this.bNBTAllowed);
  }
  
  public void loadNBTData(NBTTagCompound aNBT)
  {
    super.loadNBTData(aNBT);
    this.mPrefix = OrePrefixes.getPrefix(aNBT.getString("mPrefix"), this.mPrefix);
    this.bInvertFilter = aNBT.getBoolean("bInvertFilter");
    this.bNBTAllowed = aNBT.getBoolean("bNBTAllowed");
  }
  
  public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
  {
    return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && ((this.bNBTAllowed) || (!aStack.hasTagCompound())) && (this.mPrefix.contains(aStack) != this.bInvertFilter);
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.automation.GT_MetaTileEntity_TypeFilter
 * JD-Core Version:    0.7.0.1
 */