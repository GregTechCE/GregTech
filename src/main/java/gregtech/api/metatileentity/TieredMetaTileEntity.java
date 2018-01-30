package gregtech.api.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.factory.TieredMetaTileEntityFactory;

public abstract class TieredMetaTileEntity extends EnergyMetaTileEntity {

    public final int tier;

    public TieredMetaTileEntity(TieredMetaTileEntityFactory<?> factory) {
        super(factory);
        this.tier = factory.getTier();
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
