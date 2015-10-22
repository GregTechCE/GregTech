package gregtech.api.interfaces.tileentity;

public interface IGearEnergyTileEntity {
    /**
     * If Rotation Energy can be accepted on this Side.
     * This means that the Gear/Axle will connect to this Side, and can cause the Gear/Axle to stop if the Energy isn't accepted.
     */
    public boolean acceptsRotationalEnergy(byte aSide);

    /**
     * Inject Energy Call for Rotational Energy.
     * Rotation Energy can't be stored, this is just for things like internal Dynamos, which convert it into Energy, or into Progress.
     *
     * @param aSpeed Positive = Clockwise, Negative = Counterclockwise
     */
    public boolean injectRotationalEnergy(byte aSide, long aSpeed, long aEnergy);
}
