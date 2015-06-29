package gregtech.common.tileentities.machines.multi;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;

public class GT_MetaTileEntity_LargeTurbine_HPSteam extends GT_MetaTileEntity_LargeTurbine{

	public GT_MetaTileEntity_LargeTurbine_HPSteam(int aID, String aName, String aNameRegional){super(aID, aName, aNameRegional);}
	public GT_MetaTileEntity_LargeTurbine_HPSteam(String aName){super(aName);}

	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[1][aColorIndex+1], aFacing == aSide ? aActive ? new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_ACTIVE5):new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE5) : Textures.BlockIcons.CASING_BLOCKS[57]};
	}
	
	   
	   public String[] getDescription()
	   {
	     return new String[] { 
	    		 "Controller Block for the Large High Pressure Steam Turbine",
	    		 "Size: 3x3x4 (Hollow)", "Controller (front centered)",
	    		 "1x Input Hatch (side centered)", "1x Output Hatch(side centered)",
	    		 "1x Dynamo Hatch (back centered)", 
	    		 "1x Maintenance Hatch (side centered)", 
	    		 "Turbine Casings for the rest (24 at least!)",
	    		 "Needs a Turbine Item (inside controller GUI)" };
	   }
	   
		@Override
		public boolean checkRecipe(ItemStack aStack) {
			ArrayList<FluidStack> steams = getStoredFluids();
		    if (steams.size()>0)
		    {if(baseEff==0 || optFlow == 0 || counter >= 1000 || this.getBaseMetaTileEntity().hasWorkJustBeenEnabled() || this.getBaseMetaTileEntity().hasInventoryBeenModified()){
		    		counter = 0;
		    		baseEff = (int) ((50.0F+(10.0F*((GT_MetaGenerated_Tool)aStack.getItem()).getToolCombatDamage(aStack)))*100);
		    		optFlow = (int) Math.max(Float.MIN_NORMAL, ((GT_MetaGenerated_Tool)aStack.getItem()).getToolStats(aStack).getSpeedMultiplier() * ((GT_MetaGenerated_Tool)aStack.getItem()).getPrimaryMaterial(aStack).mToolSpeed*50);
		    	}
		    int tEU=0;
		    int distOut=0;
		    for(int i=0;i<steams.size();i++){
		    	if(steams.get(i).getFluid().getUnlocalizedName(steams.get(i)).equals("ic2.fluidSuperheatedSteam")){
		    		int out = Math.min((int)(optFlow*1.5f),steams.get(i).amount);
		    		depleteInput(new FluidStack(steams.get(i),out));
		    		distOut += out;
		    		tEU += steams.get(i).amount;
		    	}
		    }
		      if(tEU<optFlow/10){tEU=0;}
		      if(tEU>optFlow) {tEU = optFlow;}
		      float tEff = tEU/(optFlow);
		      this.mEUt = (int) (tEff*tEU*baseEff/10000);
		      this.mMaxProgresstime = 1;
//		      System.out.println("Eff: "+baseEff+" optFlow: "+optFlow+" tEff: "+tEff+" eut: "+mEUt+" out: "+distOut);
		      this.mEfficiencyIncrease = (this.mMaxProgresstime * 10);
		      if(mEUt==0){return false;}
		      addOutput(GT_ModHandler.getSteam(distOut));
		      return true;
		    }
		    if(this.mEfficiency>50){
		    	this.mEfficiency = this.mEfficiency-50;
		    	return true;
		    }else{return false;}
		  }
		
		@Override
		public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {return new GT_MetaTileEntity_LargeTurbine_HPSteam(mName);}
		@Override
		public Block getCasingBlock() {
			return GregTech_API.sBlockCasings4;
		}
		@Override
		public byte getCasingMeta() {
			return 9;
		}
		@Override
		public byte getCasingTextureIndex() {
			return 46;
		}


}
