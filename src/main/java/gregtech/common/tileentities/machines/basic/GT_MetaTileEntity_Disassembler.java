package gregtech.common.tileentities.machines.basic;

import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_Disassembler
  extends GT_MetaTileEntity_BasicMachine
{
  public GT_MetaTileEntity_Disassembler(int aID, String aName, String aNameRegional, int aTier)
  {
    super(aID, aName, aNameRegional, aTier, 1, "Disassembles Machines at " + (50 + 10 * aTier) + "% Efficiency", 1, 9, "Disassembler.png", "", new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_DISASSEMBLER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_DISASSEMBLER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_DISASSEMBLER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_DISASSEMBLER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_DISASSEMBLER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_DISASSEMBLER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_DISASSEMBLER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_DISASSEMBLER) });
  }
  
  public GT_MetaTileEntity_Disassembler(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
  {
    super(aName, aTier, 1, aDescription, aTextures, 1, 9, aGUIName, aNEIName);
  }
  
  public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
  {
    return new GT_MetaTileEntity_Disassembler(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
  }
  
  public int checkRecipe()
  {
    if ((getInputAt(0) != null) && (isOutputEmpty()))
    {
      NBTTagCompound tNBT = getInputAt(0).getTagCompound();
      if (tNBT != null)
      {
        tNBT = tNBT.getCompoundTag("GT.CraftingComponents");
        if (tNBT != null)
        {
          getInputAt(0).stackSize -= 1;
          this.mEUt = (16 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
          this.mMaxProgresstime = 80;
          for (int i = 0; i < this.mOutputItems.length; i++) {
            if (getBaseMetaTileEntity().getRandomNumber(100) < 50 + 10 * this.mTier)
            {
              this.mOutputItems[i] = GT_Utility.loadItem(tNBT, "Ingredient." + i);
              if (this.mOutputItems[i] != null) {
                this.mMaxProgresstime *= 1.7;
              }
            }
          }
          return 2;
        }
      }
    }
    return 0;
  }
  
  public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
  {
    return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (aStack.getTagCompound() != null) && (aStack.getTagCompound().getCompoundTag("GT.CraftingComponents") != null);
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Disassembler
 * JD-Core Version:    0.7.0.1
 */