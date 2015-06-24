package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;

public class GT_MetaTileEntity_LargeBoiler_TungstenSteel
  extends GT_MetaTileEntity_LargeBoiler
{
  public GT_MetaTileEntity_LargeBoiler_TungstenSteel(int aID, String aName, String aNameRegional)
  {
    super(aID, aName, aNameRegional);
  }
  
  public GT_MetaTileEntity_LargeBoiler_TungstenSteel(String aName)
  {
    super(aName);
  }
  
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
  {
    return new GT_MetaTileEntity_LargeBoiler_TungstenSteel(this.mName);
  }
  
  public Block getCasingBlock()
  {
    return GregTech_API.sBlockCasings4;
  }
  
  public byte getCasingMeta()
  {
    return 0;
  }
  
  public byte getCasingTextureIndex()
  {
    return 48;
  }
  
  public Block getPipeBlock()
  {
    return GregTech_API.sBlockCasings2;
  }
  
  public byte getPipeMeta()
  {
    return 15;
  }
  
  public Block getFireboxBlock()
  {
    return GregTech_API.sBlockCasings3;
  }
  
  public byte getFireboxMeta()
  {
    return 15;
  }
  
  public byte getFireboxTextureIndex()
  {
    return 47;
  }
  
  public int getEUt()
  {
    return 1000;
  }
  
  public int getEfficiencyIncrease()
  {
    return 4;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeBoiler_TungstenSteel
 * JD-Core Version:    0.7.0.1
 */