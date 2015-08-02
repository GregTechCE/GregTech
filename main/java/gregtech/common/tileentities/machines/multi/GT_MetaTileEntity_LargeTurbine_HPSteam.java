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
		@Override
		public int getPollutionPerTick(ItemStack aStack) {
			return 0;
		}
		
		@Override
		int fluidIntoPower(ArrayList<FluidStack> aFluids, int aOptFlow,	int aBaseEff) {
		    int tEU=0;
		    int tOut=0;
		    for(int i=0;i<aFluids.size();i++){
		    	if(aFluids.get(i).getFluid().getUnlocalizedName(aFluids.get(i)).equals("ic2.fluidSuperheatedSteam")){
		    		tOut = Math.min((int)(aOptFlow*1.5f),aFluids.get(i).amount);
		    		depleteInput(new FluidStack(aFluids.get(i),tOut));
		    	}
		    }
		    tOut = getAverage(tOut);
		    tEU = Math.min(aOptFlow,tOut);
		      addOutput(GT_ModHandler.getSteam(tOut));
		      if(tOut>0&&tOut<aOptFlow){
		    	  tEU = tEU*(tOut*100/aOptFlow)+3;
		      }
			return tEU * aBaseEff / 10000;
		}


}
