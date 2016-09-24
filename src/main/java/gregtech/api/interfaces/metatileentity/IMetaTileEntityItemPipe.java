package gregtech.api.interfaces.metatileentity;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.util.GT_Utility;

import java.util.Map;

public interface IMetaTileEntityItemPipe extends IMetaTileEntity {
    /**
     * @return if this Pipe can still be used.
     */
    public boolean pipeCapacityCheck();

    /**
     * @return if this Pipe can still be used.
     */
    public boolean incrementTransferCounter(int aIncrement);

    /**
     * Sends an ItemStack from aSender to the adjacent Blocks.
     *
     * @param aSender the BaseMetaTileEntity sending the Stack.
     * @return if it was able to send something
     */
    public boolean sendItemStack(Object aSender);

    /**
     * Executes the Sending Code for inserting Stacks into the TileEntities.
     *
     * @param aSender the BaseMetaTileEntity sending the Stack.
     * @param aSide   the Side of the PIPE facing the TileEntity.
     * @return if this Side was allowed to Output into the Block.
     */
    public boolean insertItemStackIntoTileEntity(Object aSender, byte aSide);

    /**
     * Can be used to make flow control Pipes, like Redpowers Restriction Tubes.
     * Every normal Pipe returns a Value of 32768, so you can easily insert lower Numbers to set Routing priorities.
     * Negative Numbers to "suck" Items into a certain direction are also possible.
     */
    public int getStepSize();

    /**
     * Utility for the Item Network
     */
    public static class Util {
        /**
         * @return a List of connected Item Pipes
         */
        public static Map<IMetaTileEntityItemPipe, Long> scanPipes(IMetaTileEntityItemPipe aMetaTileEntity, Map<IMetaTileEntityItemPipe, Long> aMap, long aStep, boolean aSuckItems, boolean aIgnoreCapacity) {
            aStep += aMetaTileEntity.getStepSize();
            if (aIgnoreCapacity || aMetaTileEntity.pipeCapacityCheck())
                if (aMap.get(aMetaTileEntity) == null || aMap.get(aMetaTileEntity) > aStep) {
                    IGregTechTileEntity aBaseMetaTileEntity = aMetaTileEntity.getBaseMetaTileEntity();
                    aMap.put(aMetaTileEntity, aStep);
                    for (byte i = 0, j = 0; i < 6; i++) {
                        j = GT_Utility.getOppositeSide(i);
                        if (aSuckItems) {
                            if (aBaseMetaTileEntity.getCoverBehaviorAtSide(i).letsItemsIn(i, aBaseMetaTileEntity.getCoverIDAtSide(i), aBaseMetaTileEntity.getCoverDataAtSide(i), -2, aBaseMetaTileEntity)) {
                                IGregTechTileEntity tItemPipe = aBaseMetaTileEntity.getIGregTechTileEntityAtSide(i);
                                if (aBaseMetaTileEntity.getColorization() >= 0) {
                                    byte tColor = tItemPipe.getColorization();
                                    if (tColor >= 0 && tColor != aBaseMetaTileEntity.getColorization()) {
                                        continue;
                                    }
                                }
                                if (tItemPipe instanceof BaseMetaPipeEntity) {
                                    IMetaTileEntity tMetaTileEntity = tItemPipe.getMetaTileEntity();
                                    if (tMetaTileEntity instanceof IMetaTileEntityItemPipe && tItemPipe.getCoverBehaviorAtSide(j).letsItemsOut(j, tItemPipe.getCoverIDAtSide(j), tItemPipe.getCoverDataAtSide(j), -2, tItemPipe)) {
                                        scanPipes((IMetaTileEntityItemPipe) tMetaTileEntity, aMap, aStep, aSuckItems, aIgnoreCapacity);
                                    }
                                }
                            }
                        } else {
                            if (aBaseMetaTileEntity.getCoverBehaviorAtSide(i).letsItemsOut(i, aBaseMetaTileEntity.getCoverIDAtSide(i), aBaseMetaTileEntity.getCoverDataAtSide(i), -2, aBaseMetaTileEntity)) {
                                IGregTechTileEntity tItemPipe = aBaseMetaTileEntity.getIGregTechTileEntityAtSide(i);
                                if (tItemPipe != null) {
                                    if (aBaseMetaTileEntity.getColorization() >= 0) {
                                        byte tColor = tItemPipe.getColorization();
                                        if (tColor >= 0 && tColor != aBaseMetaTileEntity.getColorization()) {
                                            continue;
                                        }
                                    }
                                    if (tItemPipe instanceof BaseMetaPipeEntity) {
                                        IMetaTileEntity tMetaTileEntity = tItemPipe.getMetaTileEntity();
                                        if (tMetaTileEntity instanceof IMetaTileEntityItemPipe && tItemPipe.getCoverBehaviorAtSide(j).letsItemsIn(j, tItemPipe.getCoverIDAtSide(j), tItemPipe.getCoverDataAtSide(j), -2, tItemPipe)) {
                                            scanPipes((IMetaTileEntityItemPipe) tMetaTileEntity, aMap, aStep, aSuckItems, aIgnoreCapacity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            return aMap;
        }
    }
}
