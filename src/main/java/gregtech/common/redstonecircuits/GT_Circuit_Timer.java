package gregtech.common.redstonecircuits;

import gregtech.api.interfaces.IRedstoneCircuitBlock;
import gregtech.api.util.GT_CircuitryBehavior;

public class GT_Circuit_Timer
        extends GT_CircuitryBehavior {
    public GT_Circuit_Timer(int aIndex) {
        super(aIndex);
    }

    public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        aCircuitData[0] = 2;
        aCircuitData[1] = 1;
        aCircuitData[2] = 2;
        aCircuitData[4] = 0;
    }

    public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[0] < 2) {
            aCircuitData[0] = 2;
        }
        if (aCircuitData[1] < 1) {
            aCircuitData[1] = 1;
        }
        if (aCircuitData[2] < 2) {
            aCircuitData[2] = 2;
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
            if (aCircuitData[1] > 1) {
                if (aCircuitData[4] >= aCircuitData[0] + (aCircuitData[1] - 1) * aCircuitData[2]) {
                    aRedstoneCircuitBlock.setRedstone((byte) 15, aRedstoneCircuitBlock.getOutputFacing());
                    aCircuitData[4] = 0;
                } else {
                    aRedstoneCircuitBlock.setRedstone((byte) ((aCircuitData[4] - aCircuitData[0]) % aCircuitData[2] == 0 ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
                }
            } else {
                aRedstoneCircuitBlock.setRedstone((byte) 15, aRedstoneCircuitBlock.getOutputFacing());
                aCircuitData[4] = 0;
            }
        } else {
            aRedstoneCircuitBlock.setRedstone((byte) 0, aRedstoneCircuitBlock.getOutputFacing());
        }
    }

    public String getName() {
        return "Timer";
    }

    public String getDescription() {
        return "Pulses Redstone";
    }

    public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex) {
        switch (aCircuitDataIndex) {
            case 0:
                return "Delay";
            case 1:
                return "Pulses";
            case 2:
                return "Length";
            case 3:
                return aCircuitData[aCircuitDataIndex] == 1 ? "RS => ON" : "RS => OFF";
            case 4:
                return "Time";
        }
        return "";
    }

    public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex) {
        return false;
    }

    public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex) {
        if (aCircuitDataIndex == 3) {
            return "";
        }
        return null;
    }
}
