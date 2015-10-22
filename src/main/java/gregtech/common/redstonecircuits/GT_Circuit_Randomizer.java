package gregtech.common.redstonecircuits;

import gregtech.api.interfaces.IRedstoneCircuitBlock;
import gregtech.api.util.GT_CircuitryBehavior;

public class GT_Circuit_Randomizer
        extends GT_CircuitryBehavior {
    public GT_Circuit_Randomizer(int aIndex) {
        super(aIndex);
    }

    public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        aCircuitData[0] = 1;
        aCircuitData[4] = 0;
    }

    public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[0] < 1) {
            aCircuitData[0] = 1;
        }
        if (aCircuitData[3] < 0) {
            aCircuitData[3] = 0;
        }
        if (aCircuitData[3] > 1) {
            aCircuitData[3] = 1;
        }
        if (aCircuitData[4] < 0) {
            aCircuitData[4] = 0;
        }
    }

    public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[3] == 1) {
            if (getAnyRedstone(aRedstoneCircuitBlock)) {
                aCircuitData[4] += 1;
            } else {
                aCircuitData[4] = 0;
            }
        } else if (getAnyRedstone(aRedstoneCircuitBlock)) {
            aCircuitData[4] = 0;
        } else {
            aCircuitData[4] += 1;
        }
        if (aCircuitData[4] >= aCircuitData[0]) {
            aCircuitData[4] = 0;
            aRedstoneCircuitBlock.setRedstone((byte) aRedstoneCircuitBlock.getRandom(16), aRedstoneCircuitBlock.getOutputFacing());
        }
    }

    public String getName() {
        return "Randomizer";
    }

    public String getDescription() {
        return "Randomizes Redstone";
    }

    public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex) {
        switch (aCircuitDataIndex) {
            case 0:
                return "Delay";
            case 3:
                return aCircuitData[aCircuitDataIndex] == 1 ? "RS => ON" : "RS => OFF";
            case 4:
                return "Status";
        }
        return "";
    }

    public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex) {
        return false;
    }

    public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex) {
        if (aCircuitDataIndex != 0) {
            return "";
        }
        return null;
    }
}
