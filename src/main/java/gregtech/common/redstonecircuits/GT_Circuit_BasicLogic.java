package gregtech.common.redstonecircuits;

import gregtech.api.interfaces.IRedstoneCircuitBlock;
import gregtech.api.util.GT_CircuitryBehavior;

public class GT_Circuit_BasicLogic
        extends GT_CircuitryBehavior {
    public GT_Circuit_BasicLogic(int aIndex) {
        super(aIndex);
    }

    public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        aCircuitData[0] = 0;
    }

    public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[0] < 0) {
            aCircuitData[0] = 0;
        }
        if (aCircuitData[0] > 13) {
            aCircuitData[0] = 13;
        }
    }

    public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
        if (aCircuitData[0] < 2) {
            aRedstoneCircuitBlock.setRedstone((byte) (aCircuitData[0] % 2 == (getAnyRedstone(aRedstoneCircuitBlock) ? 0 : 1) ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
        } else if (aCircuitData[0] < 4) {
            aRedstoneCircuitBlock.setRedstone((byte) (aCircuitData[0] % 2 == (getOneRedstone(aRedstoneCircuitBlock) ? 0 : 1) ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
        } else if (aCircuitData[0] < 6) {
            aRedstoneCircuitBlock.setRedstone((byte) (aCircuitData[0] % 2 == (getAllRedstone(aRedstoneCircuitBlock) ? 0 : 1) ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
        } else if (aCircuitData[0] < 7) {
            aRedstoneCircuitBlock.setRedstone((byte) (15 - getStrongestRedstone(aRedstoneCircuitBlock)), aRedstoneCircuitBlock.getOutputFacing());
        } else if (aCircuitData[0] < 9) {
            aRedstoneCircuitBlock.setRedstone((byte) ((aCircuitData[0] % 2 == 0 ? 15 : 0) ^ (getStrongestRedstone(aRedstoneCircuitBlock) | getWeakestRedstone(aRedstoneCircuitBlock))), aRedstoneCircuitBlock.getOutputFacing());
        } else if (aCircuitData[0] < 11) {
            aRedstoneCircuitBlock.setRedstone((byte) ((aCircuitData[0] % 2 == 0 ? 15 : 0) ^ getStrongestRedstone(aRedstoneCircuitBlock) ^ getWeakestRedstone(aRedstoneCircuitBlock)), aRedstoneCircuitBlock.getOutputFacing());
        } else if (aCircuitData[0] < 13) {
            aRedstoneCircuitBlock.setRedstone((byte) ((aCircuitData[0] % 2 == 0 ? 15 : 0) ^ getStrongestRedstone(aRedstoneCircuitBlock) & getWeakestRedstone(aRedstoneCircuitBlock)), aRedstoneCircuitBlock.getOutputFacing());
        } else if (aCircuitData[0] < 14) {
            aRedstoneCircuitBlock.setRedstone((byte) (getStrongestRedstone(aRedstoneCircuitBlock) ^ 0xF), aRedstoneCircuitBlock.getOutputFacing());
        }
    }

    public String getName() {
        return "Basic Logic";
    }

    public String getDescription() {
        return "Regular Logic Gates";
    }

    public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex) {
        if (aCircuitDataIndex == 0) {
            switch (aCircuitData[0]) {
                case 0:
                    return "OR";
                case 1:
                    return "NOR";
                case 2:
                    return "XOR";
                case 3:
                    return "XNOR";
                case 4:
                    return "AND";
                case 5:
                    return "NAND";
                case 6:
                    return "INVERT";
                case 7:
                    return "BIT_OR";
                case 8:
                    return "BIT_NOR";
                case 9:
                    return "BIT_XOR";
                case 10:
                    return "BIT_XNOR";
                case 11:
                    return "BIT_AND";
                case 12:
                    return "BIT_NAND";
                case 13:
                    return "BIT_INVERT";
            }
        }
        return "";
    }

    public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex) {
        return false;
    }

    public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex) {
        return "";
    }
}
