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

public class GT_MetaTileEntity_LargeTurbine_Steam extends GT_MetaTileEntity_LargeTurbine{

	public GT_MetaTileEntity_LargeTurbine_Steam(int aID, String aName, String aNameRegional){super(aID, aName, aNameRegional);}
	public GT_MetaTileEntity_LargeTurbine_Steam(String aName){super(aName);}

	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[1][aColorIndex+1], aFacing == aSide ? aActive ? new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_ACTIVE5):new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE5) : Textures.BlockIcons.CASING_BLOCKS[57]};
	}
	
	   
	   public String[] getDescription()
	   {
	     return new String[] { 
	    		 "Controller Block for the Large Steam Turbine",
	    		 "Size: 3x3x4 (Hollow)", "Controller (front centered)",
	    		 "1x Input Hatch (side centered)", "1x Output Hatch(side centered)",
	    		 "1x Dynamo Hatch (back centered)", 
	    		 "1x Maintenance Hatch (side centered)", 
	    		 "Turbine Casings for the rest (24 at least!)",
	    		 "Needs a Turbine Item (inside controller GUI)" };
	   } 
		
		@Override
		public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {return new GT_MetaTileEntity_LargeTurbine_Steam(mName);}
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
		
		private float water;
		private int useWater(float input){
			   water = water + input;
			   int usage = (int)water;
			   water = water - (int)usage;
			   return  usage;
		}
		
		@Override
		int fluidIntoPower(ArrayList<FluidStack> aFluids, int aOptFlow,	int aBaseEff) {
		    int tEU=0;
		    int averageFlow = 0; // To prevent closed water loops from breaking.  EU is based on average flow
		    int totalFlow = 0; // Byproducts are based on actual flow
		    int flow = 0;
		    int remainingFlow = (int)(aOptFlow * 1.25f);  // Allowed to use up to 125% of optimal flow.  Variable required outside of loop for multi-hatch scenarios.

		    for(int i=0;i<aFluids.size() && remainingFlow > 0;i++){ // loop through each hatch; extract inputs and track totals.
		    	if(aFluids.get(i).getFluid().getUnlocalizedName(aFluids.get(i)).equals("fluid.steam")||aFluids.get(i).getFluid().getUnlocalizedName(aFluids.get(i)).equals("ic2.fluidSteam")){
		    		flow = aFluids.get(i).amount;  // Get all (steam) in hatch
		    		flow = Math.min(flow, Math.min(remainingFlow, (int)( aOptFlow * 1.25f)));  // try to use up to 125% of optimal flow w/o exceeding remainingFlow
		    		depleteInput(new FluidStack(aFluids.get(i), flow)); // deplete that amount
		    		remainingFlow -= flow; // track amount we're allowed to continue depleting from hatches
		    		totalFlow += flow; // track total input used
		    	}
		    }
		    averageFlow = getAverage(totalFlow); // calculate recent average usage for power output purposes but NOT byproduct generation.  We used what we used, and get byproducts from that.
		    
		    tEU = Math.min(aOptFlow, averageFlow); 
		    addOutput(GT_ModHandler.getDistilledWater(useWater(totalFlow/160.0f)));
		    if(averageFlow > 0 && averageFlow != aOptFlow){
			  float efficiency =  1.0f - Math.abs(((averageFlow - (float)aOptFlow) / aOptFlow));
			  tEU *= efficiency;
			  tEU = Math.max(1, tEU * aBaseEff / 20000);
		    }
		    else {
		    	tEU = tEU * aBaseEff / 20000;
		    }
	            return  tEU;
		}
}
