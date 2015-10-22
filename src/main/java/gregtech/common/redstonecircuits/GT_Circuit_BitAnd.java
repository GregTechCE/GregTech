package gregtech.common.redstonecircuits;

import gregtech.api.interfaces.IRedstoneCircuitBlock;
import gregtech.api.util.GT_CircuitryBehavior;

public class GT_Circuit_BitAnd
        extends GT_CircuitryBehavior {
    public GT_Circuit_BitAnd(int aIndex) {
        super(aIndex);
    }

    public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        aCircuitData[0] = 0;
        aCircuitData[1] = 0;
        aCircuitData[2] = 0;
        aCircuitData[3] = 0;
    }

    public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[0] < 0) {
            aCircuitData[0] = 0;
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
        if (aCircuitData[0] > 1) {
            aCircuitData[0] = 1;
        }
        if (aCircuitData[1] > 1) {
            aCircuitData[1] = 1;
        }
        if (aCircuitData[2] > 1) {
            aCircuitData[2] = 1;
        }
        if (aCircuitData[3] > 1) {
            aCircuitData[3] = 1;
        }
    }

    public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        aRedstoneCircuitBlock.setRedstone((byte) ((getStrongestRedstone(aRedstoneCircuitBlock) & (aCircuitData[0] | aCircuitData[1] << 1 | aCircuitData[2] << 2 | aCircuitData[3] << 3)) != 0 ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
    }

    public String getName() {
        return "Hardcode Bit-AND";
    }

    public String getDescription() {
        return "( signal & this ) != 0";
    }

    public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex) {
        return "Bit " + aCircuitDataIndex + ":";
    }

    public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex) {
        return false;
    }

    public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex) {
        return aCircuitData[aCircuitDataIndex] == 0 ? "OFF" : "ON";
    }
}
