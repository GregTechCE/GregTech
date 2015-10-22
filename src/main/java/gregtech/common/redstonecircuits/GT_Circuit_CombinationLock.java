package gregtech.common.redstonecircuits;

import gregtech.api.interfaces.IRedstoneCircuitBlock;
import gregtech.api.util.GT_CircuitryBehavior;

public class GT_Circuit_CombinationLock
        extends GT_CircuitryBehavior {
    public GT_Circuit_CombinationLock(int aIndex) {
        super(aIndex);
    }

    public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        aCircuitData[0] = 1;
        aCircuitData[1] = 0;
        aCircuitData[2] = 0;
        aCircuitData[3] = 0;
        aCircuitData[4] = 0;
        aCircuitData[5] = 0;
    }

    public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[0] < 1) {
            aCircuitData[0] = 1;
        }
        if (aCircuitData[1] < 0) {
            aCircuitData[1] = 0;
        }
        if (aCircuitData[2] < 0) {
            aCircuitData[2] = 0;
        }
        if (aCircuitData[3] < 0) {
            aCircuitData[3] = 0;
        }
        if (aCircuitData[0] > 15) {
            aCircuitData[0] = 15;
        }
        if (aCircuitData[1] > 15) {
            aCircuitData[1] = 15;
        }
        if (aCircuitData[2] > 15) {
            aCircuitData[2] = 15;
        }
        if (aCircuitData[3] > 15) {
            aCircuitData[3] = 15;
        }
        if (aCircuitData[4] < 0) {
            aCircuitData[4] = 0;
        }
        if (aCircuitData[4] > 3) {
            aCircuitData[4] = 3;
        }
        if (aCircuitData[5] < 0) {
            aCircuitData[5] = 0;
        }
    }

    public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        while ((aCircuitData[aCircuitData[4]] == 0) && (aCircuitData[4] < 4)) {
            aCircuitData[4] += 1;
        }
        if (aCircuitData[4] < 4) {
            int tRedstone = getStrongestRedstone(aRedstoneCircuitBlock);
            if (tRedstone > 0) {
                if (aCircuitData[5] == 0) {
                    if (tRedstone == aCircuitData[aCircuitData[4]]) {
                        aCircuitData[4] += 1;
                    } else {
                        aCircuitData[4] = 0;
                    }
                }
                aCircuitData[5] = 1;
            } else {
                aCircuitData[5] = 0;
            }
            aRedstoneCircuitBlock.setRedstone((byte) 0, aRedstoneCircuitBlock.getOutputFacing());
        } else {
            aRedstoneCircuitBlock.setRedstone((byte) 15, aRedstoneCircuitBlock.getOutputFacing());
            aCircuitData[4] = 0;
        }
    }

    public String getName() {
        return "Combination Lock";
    }

    public String getDescription() {
        return "Checks Combinations";
    }

    public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex) {
        return "Power " + aCircuitDataIndex;
    }

    public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex) {
        return false;
    }

    public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex) {
        return null;
    }
}
