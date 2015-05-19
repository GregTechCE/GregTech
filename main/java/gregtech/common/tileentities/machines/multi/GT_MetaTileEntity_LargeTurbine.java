package gregtech.common.tileentities.machines.multi;

import net.minecraft.item.ItemStack;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.GT_MetaGenerated_Tool_01;

public class GT_MetaTileEntity_LargeTurbine extends GT_MetaTileEntity_MultiBlockBase{

	public GT_MetaTileEntity_LargeTurbine(int aID, String aName, String aNameRegional){super(aID, aName, aNameRegional);}
	public GT_MetaTileEntity_LargeTurbine(String aName){super(aName);}

	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[1][aColorIndex+1], aFacing == aSide ? aActive ? new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_ACTIVE5):new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE5) : Textures.BlockIcons.CASING_BLOCKS[57]};
	}
	
	   
	   public String[] getDescription()
	   {
	     return new String[] { 
	    		 "Controller Block for the Large Turbine",
	    		 "Size: 3x3x4 (Hollow)", "Controller (front centered)",
	    		 "1x Input Hatch (side centered)", "1x Output Hatch(side centered)",
	    		 "1x Dynamo Hatch (back centered)", 
	    		 "1x Maintenance Hatch (side centered)", 
	    		 "Turbine Casings for the rest (24 at least!)" };
	   }

	@Override
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return getMaxEfficiency(aStack) > 0;
	}

	@Override
	public boolean checkRecipe(ItemStack aStack) {
	    if (depleteInput(GT_ModHandler.getSteam(1600L)))
	    {
	      this.mEUt = 1000;
	      this.mMaxProgresstime = 1;
//	      if (ItemList.Component_Turbine_Bronze.isStackEqual(aStack, true, true)) {
//	        this.mEfficiencyIncrease = (this.mMaxProgresstime * 10);
//	      } else if (ItemList.Component_Turbine_Steel.isStackEqual(aStack, true, true)) {
//	        this.mEfficiencyIncrease = (this.mMaxProgresstime * 20);
//	      } else if (ItemList.Component_Turbine_Magnalium.isStackEqual(aStack, true, true)) {
//	        this.mEfficiencyIncrease = (this.mMaxProgresstime * 50);
//	      } else if (ItemList.Component_Turbine_TungstenSteel.isStackEqual(aStack, true, true)) {
//	        this.mEfficiencyIncrease = (this.mMaxProgresstime * 15);
//	      } else if (ItemList.Component_Turbine_Carbon.isStackEqual(aStack, true, true)) {
//	        this.mEfficiencyIncrease = (this.mMaxProgresstime * 100);
//	      } else {
	        this.mEfficiencyIncrease = (this.mMaxProgresstime * 20);
//	      }
	      addOutput(GT_ModHandler.getDistilledWater(10L));
	      return true;
	    }
	    return false;
	  }

	@Override
	public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
	    byte tSide = getBaseMetaTileEntity().getBackFacing();
	    if ((getBaseMetaTileEntity().getAirAtSideAndDistance(getBaseMetaTileEntity().getBackFacing(), 1)) && (getBaseMetaTileEntity().getAirAtSideAndDistance(getBaseMetaTileEntity().getBackFacing(), 2)))
	    {
	      int tAirCount = 0;
	      for (byte i = -1; i < 2; i = (byte)(i + 1)) {
	        for (byte j = -1; j < 2; j = (byte)(j + 1)) {
	          for (byte k = -1; k < 2; k = (byte)(k + 1)) {
	            if (getBaseMetaTileEntity().getAirOffset(i, j, k)) {
	              tAirCount++;
	            }
	          }
	        }
	      }
	      if (tAirCount != 10) {
	        return false;
	      }
	      for (byte i = 2; i < 6; i = (byte)(i + 1))
	      {
	        IGregTechTileEntity tTileEntity;
	        if ((null != (tTileEntity = getBaseMetaTileEntity().getIGregTechTileEntityAtSideAndDistance(i, 2))) && 
	          (tTileEntity.getFrontFacing() == getBaseMetaTileEntity().getFrontFacing()) && (tTileEntity.getMetaTileEntity() != null) && 
	          ((tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_LargeTurbine))) {
	          return false;
	        }
	      }
	      int tX = getBaseMetaTileEntity().getXCoord();int tY = getBaseMetaTileEntity().getYCoord();int tZ = getBaseMetaTileEntity().getZCoord();
	      for (byte i = -1; i < 2; i = (byte)(i + 1)) {
	        for (byte j = -1; j < 2; j = (byte)(j + 1)) {
	          if ((i != 0) || (j != 0)) {
	            for (byte k = 0; k < 4; k = (byte)(k + 1)) {
	              if (((i == 0) || (j == 0)) && ((k == 1) || (k == 2)))
	              {
	                if (getBaseMetaTileEntity().getBlock(tX + (tSide == 5 ? k : tSide < 4 ? i : -k), tY + j, tZ + (tSide < 4 ? -k : tSide == 3 ? k : i)) == GregTech_API.sBlockCasings4)
	                {
	                  if (getBaseMetaTileEntity().getMetaID(tX + (tSide == 5 ? k : tSide < 4 ? i : -k), tY + j, tZ + (tSide < 4 ? -k : tSide == 3 ? k : i)) == 9) {}
	                }
	                else if (!addToMachineList(getBaseMetaTileEntity().getIGregTechTileEntity(tX + (tSide == 5 ? k : tSide < 4 ? i : -k), tY + j, tZ + (tSide < 4 ? -k : tSide == 3 ? k : i)))) {
	                	return false;
	                }
	              }
	              else if (getBaseMetaTileEntity().getBlock(tX + (tSide == 5 ? k : tSide < 4 ? i : -k), tY + j, tZ + (tSide < 4 ? -k : tSide == 3 ? k : i)) == GregTech_API.sBlockCasings4)
	              {
	                if (getBaseMetaTileEntity().getMetaID(tX + (tSide == 5 ? k : tSide < 4 ? i : -k), tY + j, tZ + (tSide < 4 ? -k : tSide == 3 ? k : i)) == 9) {}
	              }
	              else {
	                return false;
	              }
	            }
	          }
	        }
	      }
	      this.mDynamoHatches.clear();
	      IGregTechTileEntity tTileEntity = getBaseMetaTileEntity().getIGregTechTileEntityAtSideAndDistance(getBaseMetaTileEntity().getBackFacing(), 3);
	      if ((tTileEntity != null) && (tTileEntity.getMetaTileEntity() != null)) {
	        if ((tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Dynamo)) {
	          this.mDynamoHatches.add((GT_MetaTileEntity_Hatch_Dynamo)tTileEntity.getMetaTileEntity());
	          ((GT_MetaTileEntity_Hatch)tTileEntity.getMetaTileEntity()).mMachineBlock = (byte)46;
	        } else {
	          return false;
	        }
	      }
	    }
	    else
	    {
	      return false;
	    }
	    return true;
	    }
	
	private boolean addToMachineList(IGregTechTileEntity tTileEntity){
		return ((addMaintenanceToMachineList(tTileEntity, 46)) || (addInputToMachineList(tTileEntity, 46)) || (addOutputToMachineList(tTileEntity, 46)));
	}
	
	@Override
	public int getDamageToComponent(ItemStack aStack) {
	    return 1;//GT_Utility.areStacksEqual(GT_ModHandler.getModItem("Railcraft","part.turbine.rotor", 1L, 32767), aStack) ? 2 : 1;
	  }

	
	 public int getMaxEfficiency(ItemStack aStack)
	  {
	    if (GT_Utility.isStackInvalid(aStack)) {
	      return 0;
	    }
	    if (aStack.getItem() instanceof GT_MetaGenerated_Tool_01) {
	      return 10000;
	    }
	    return 0;
	  }
	
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {return 0;}
	@Override
	public int getAmountOfOutputs() {return 0;}
	@Override
	public boolean explodesOnComponentBreak(ItemStack aStack) {return true;}
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {return new GT_MetaTileEntity_LargeTurbine(mName);}

}
