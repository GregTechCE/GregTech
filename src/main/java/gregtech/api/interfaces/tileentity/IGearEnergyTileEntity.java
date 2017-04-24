package gregtech.api.interfaces.tileentity;

import net.minecraft.util.EnumFacing;

public interface IGearEnergyTileEntity {
    /**
     * If Rotation Energy can be accepted on this Side.
     * This means that the Gear/Axle will connect to this Side, and can cause the Gear/Axle to stop if the Energy isn't accepted.
     */
    public boolean acceptsRotationalEnergy(EnumFacing aSide);

    /**
     * Inject Energy Call for Rotational Energy.
     * Rotation Energy can't be stored, this is just for things like internal Dynamos, which convert it into Energy, or into Progress.
     *
     * @param aSpeed Positive = Clockwise, Negative = Counterclockwise
     */
    public boolean injectRotationalEnergy(EnumFacing aSide, long aSpeed, long aEnergy);
}
