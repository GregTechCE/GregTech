package gregtech.api.objects;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Because Forge fucked this one up royally.
 */
public class GT_FluidStack extends FluidStack {
    private static final Collection<GT_FluidStack> sAllFluidStacks = new ArrayList<GT_FluidStack>(5000);
    private static volatile boolean lock = false;
    private Fluid mFluid;

    public GT_FluidStack(Fluid aFluid, int aAmount) {
        super(aFluid, aAmount);
        mFluid = aFluid;
        if(!GregTech_API.mServerStarted){sAllFluidStacks.add(this);}
    }

    public GT_FluidStack(FluidStack aFluid) {
        this(aFluid.getFluid(), aFluid.amount);
    }

    public static final synchronized void fixAllThoseFuckingFluidIDs() {
        if (ForgeVersion.getBuildVersion() < 1355) {
            while (lock) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
            lock = true;
            for (GT_FluidStack tFluid : sAllFluidStacks) tFluid.fixFluidIDForFucksSake();
            for (Map<Fluid, ?> tMap : GregTech_API.sFluidMappings)
                try {
                    GT_Utility.reMap(tMap);
                } catch (Throwable e) {
                    e.printStackTrace(GT_Log.err);
                }
            lock = false;
        }
    }

    public final void fixFluidIDForFucksSake() {
        if (ForgeVersion.getBuildVersion() < 1355) {
            int fluidID;
            try {
                fluidID = this.getFluid().getID();
            } catch (Throwable e) {
                System.err.println(e);
            }
            try {
                fluidID = mFluid.getID();
            } catch (Throwable e) {
                fluidID = -1;
            }
        }
    }

    @Override
    public FluidStack copy() {
        if (ForgeVersion.getBuildVersion() < 1355) {
            fixFluidIDForFucksSake();
        }
        return new GT_FluidStack(this);
    }
}