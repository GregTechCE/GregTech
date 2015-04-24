package gregtech.api.objects;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Because Forge fucked this one up royally.
 */
public class GT_FluidStack extends FluidStack {
	private static final Collection<GT_FluidStack> sAllFluidStacks = new ArrayList<GT_FluidStack>(5000);
	private Fluid mFluid;
	
    public GT_FluidStack(Fluid aFluid, int aAmount) {
    	super(aFluid, aAmount);
    	mFluid = aFluid;
    	sAllFluidStacks.add(this);
    }
    
    public GT_FluidStack(FluidStack aFluid) {
    	this(aFluid.getFluid(), aFluid.amount);
    }
    
    public static void fixAllThoseFuckingFluidIDs() {
    	for (GT_FluidStack tFluid : sAllFluidStacks) tFluid.fixFluidIDForFucksSake();
    	for (Map<Fluid, ?> tMap : GregTech_API.sFluidMappings) try {GT_Utility.reMap(tMap);} catch(Throwable e) {e.printStackTrace(GT_Log.err);}
	}
    
    public void fixFluidIDForFucksSake() {
    	int fluidID = this.getFluid().getID();
    	try {fluidID = mFluid.getID();} catch(Throwable e) {fluidID = -1;}
    }
    
    @Override
	public FluidStack copy() {
    	fixFluidIDForFucksSake();
        return new GT_FluidStack(this);
    }
}