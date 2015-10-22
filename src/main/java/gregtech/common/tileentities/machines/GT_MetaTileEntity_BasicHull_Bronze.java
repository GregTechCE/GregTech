package gregtech.common.tileentities.machines;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull_NonElectric;
import gregtech.api.objects.GT_RenderedTexture;

public class GT_MetaTileEntity_BasicHull_Bronze
  extends GT_MetaTileEntity_BasicHull_NonElectric
{
  public GT_MetaTileEntity_BasicHull_Bronze(int aID, String aName, String aNameRegional, int aTier, String aDescription)
  {
    super(aID, aName, aNameRegional, aTier, aDescription);
  }
  
  public GT_MetaTileEntity_BasicHull_Bronze(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
  {
    super(aName, aTier, aDescription, aTextures);
  }
  
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
  {
    return new GT_MetaTileEntity_BasicHull_Bronze(this.mName, this.mTier, this.mDescription, this.mTextures);
  }
  
  public ITexture[][][] getTextureSet(ITexture[] aTextures)
  {
    ITexture[][][] rTextures = new ITexture[3][17][];
    for (byte i = -1; i < 16; i = (byte)(i + 1))
    {
      rTextures[0][(i + 1)] = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZE_BOTTOM, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
      rTextures[1][(i + 1)] = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZE_TOP, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
      rTextures[2][(i + 1)] = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZE_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
    }
    return rTextures;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.GT_MetaTileEntity_BasicHull_Bronze
 * JD-Core Version:    0.7.0.1
 */