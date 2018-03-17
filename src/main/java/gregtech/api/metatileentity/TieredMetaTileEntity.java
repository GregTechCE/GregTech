package gregtech.api.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.EnergyContainerHandler;

public abstract class TieredMetaTileEntity extends MetaTileEntity {

    private final int tier;
    protected final EnergyContainerHandler energyContainer;

    public TieredMetaTileEntity(int tier) {
        this.tier = tier;
        long tierVoltage = GTValues.V[tier];
        if (isEnergyEmitter()) {
            this.energyContainer = addTrait(EnergyContainerHandler.emitterContainer(
                tierVoltage * 8L, tierVoltage, getMaxInputOutputAmperage()));
        } else this.energyContainer = addTrait(EnergyContainerHandler.receiverContainer(
            tierVoltage * 16L, tierVoltage, getMaxInputOutputAmperage()));
    }


    /**
     * Tier of machine determines it's input voltage, storage and generation rate
     * @return tier of this machine
     */
    public int getTier() {
        return tier;
    }

    /**
     * Determines max input or output amperage used by this meta tile entity
     * if emitter, it determines size of energy packets it will emit at once
     * if receiver, it determines max input energy per request
     * @return max amperage received or emitted by this machine
     */
    protected long getMaxInputOutputAmperage() {
        return 1L;
    }

    /**
     * Determines if this meta tile entity is in energy receiver or emitter mode
     * @return true if machine emits energy to network, false it it accepts energy from network
     */
    protected boolean isEnergyEmitter() {
        return false;
    }

}
