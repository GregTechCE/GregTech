package gregtech.common.redstonecircuits;

import gregtech.api.interfaces.IRedstoneCircuitBlock;
import gregtech.api.util.GT_CircuitryBehavior;

public class GT_Circuit_Repeater
        extends GT_CircuitryBehavior {
    public GT_Circuit_Repeater(int aIndex) {
        super(aIndex);
    }

    public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        aCircuitData[0] = 1;
        aCircuitData[4] = 0;
        aCircuitData[5] = -1;
    }

    public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[0] < 1) {
            aCircuitData[0] = 1;
        }
        if (aCircuitData[4] < 0) {
            aCircuitData[4] = 0;
        }
        if (aCircuitData[5] < -1) {
            aCircuitData[5] = -1;
        }
    }

    public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (getAnyRedstone(aRedstoneCircuitBlock)) {
            aCircuitData[4] += 1;
            if (aCircuitData[5] < 0) {
                aCircuitData[5] = 0;
            }
        }
        if ((aCircuitData[5] >= 0) && (aCircuitData[5] < aCircuitData[0])) {
            aCircuitData[5] += 1;
        }
        if (aCircuitData[4] > 0) {
            if (aCircuitData[5] >= aCircuitData[0]) {
                aCircuitData[4] -= 1;
                aRedstoneCircuitBlock.setRedstone((byte) 15, aRedstoneCircuitBlock.getOutputFacing());
            } else {
                aRedstoneCircuitBlock.setRedstone((byte) 0, aRedstoneCircuitBlock.getOutputFacing());
            }
        } else {
            aRedstoneCircuitBlock.setRedstone((byte) 0, aRedstoneCircuitBlock.getOutputFacing());
            aCircuitData[5] = -1;
        }
    }

    public String getName() {
        return "Repeater";
    }

    public String getDescription() {
        return "Delays RS-Signal";
    }

    public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex) {
        switch (aCircuitDataIndex) {
            case 0:
                return "Delay";
        }
        return "";
    }

    public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex) {
        return false;
    }

    public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex) {
        if (aCircuitDataIndex > 0) {
            return "";
        }
        return null;
    }
}
