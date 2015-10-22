package gregtech.common.redstonecircuits;

import gregtech.api.interfaces.IRedstoneCircuitBlock;
import gregtech.api.util.GT_CircuitryBehavior;

public class GT_Circuit_RedstoneMeter
        extends GT_CircuitryBehavior {
    public GT_Circuit_RedstoneMeter(int aIndex) {
        super(aIndex);
    }

    public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        aCircuitData[0] = 1;
        aCircuitData[1] = 15;
        aCircuitData[2] = 0;
        aCircuitData[3] = 15;
    }

    public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[0] < 0) {
            aCircuitData[0] = 0;
        }
        if (aCircuitData[0] > 15) {
            aCircuitData[0] = 15;
        }
        if (aCircuitData[1] < 0) {
            aCircuitData[1] = 0;
        }
        if (aCircuitData[1] > 15) {
            aCircuitData[1] = 15;
        }
        if (aCircuitData[1] < aCircuitData[0]) {
            aCircuitData[1] = aCircuitData[0];
        }
        if (aCircuitData[2] < 0) {
            aCircuitData[2] = 0;
        }
        if (aCircuitData[2] > 1) {
            aCircuitData[2] = 1;
        }
        if (aCircuitData[3] < 0) {
            aCircuitData[3] = 0;
        }
        if (aCircuitData[3] > 15) {
            aCircuitData[3] = 15;
        }
    }

    public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        byte tRedstone = getStrongestRedstone(aRedstoneCircuitBlock);
        aRedstoneCircuitBlock.setRedstone((byte) (((tRedstone >= aCircuitData[0]) && (tRedstone <= aCircuitData[1]) ? 1 : 0) != (aCircuitData[2] != 0 ? 1 : 0) ? (byte) aCircuitData[3] : 0), aRedstoneCircuitBlock.getOutputFacing());
    }

    public String getName() {
        return "Redstone Meter";
    }

    public String getDescription() {
        return "Checks Boundaries";
    }

    public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex) {
        switch (aCircuitDataIndex) {
            case 0:
                return "Lower";
            case 1:
                return "Upper";
            case 2:
                return "Invert:";
            case 3:
                return "RS Out:";
        }
        return "";
    }

    public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex) {
        return false;
    }

    public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex) {
        if (aCircuitDataIndex == 2) {
            return aCircuitData[2] == 0 ? "OFF" : "ON";
        }
        return null;
    }
}
