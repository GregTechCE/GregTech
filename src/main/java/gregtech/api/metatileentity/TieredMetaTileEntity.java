package gregtech.api.metatileentity;

import gregtech.api.GTValues;

public abstract class TieredMetaTileEntity extends EnergyMetaTileEntity {

    public final int tier;

    public TieredMetaTileEntity(IMetaTileEntityFactory factory, int tier) {
        super(factory);
        this.tier = tier;
    }

    @Override
    public long getOutputVoltage() {
        return GTValues.V[this.tier];
    }

    @Override
    public long getInputVoltage() {
        return GTValues.V[this.tier];
    }

}
