package gregtech.api.interfaces.tileentity;

import cofh.api.energy.IEnergyReceiver;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_Utility;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * THIS IS GOING TO BE USED IN 1.8
 * <p/>
 * Interface for getting Connected to the GregTech Energy Network.
 * <p/>
 * This is all you need to connect to the GT Network.
 * IColoredTileEntity is needed for not connecting differently coloured Blocks to each other.
 * IHasWorldObjectAndCoords is needed for the InWorld related Stuff. @BaseTileEntity does implement most of that Interface.
 */
public interface IExperimentalEnergyTileEntity extends IColoredTileEntity, IHasWorldObjectAndCoords {
    /**
     * Inject Energy Call for Electricity. Gets called by EnergyEmitters to inject Energy into your Block
     * <p/>
     * Note: you have to check for @inputEnergyFrom because the Network won't check for that by itself.
     *
     * @param aSide 0 - 5 = Vanilla Directions of YOUR Block the Energy gets inserted to. 6 = No specific Side (don't do Side checks for this Side)
     * @return amount of used Amperes. 0 if not accepted anything.
     */
    public long injectEnergy(SubTag aEnergyType, byte aSide, long aPrimary, long aSecondary);

    /**
     * Sided Energy Input
     */
    public boolean inputEnergyFrom(SubTag aEnergyType, byte aSide);

    /**
     * Sided Energy Output
     */
    public boolean outputsEnergyTo(SubTag aEnergyType, byte aSide);

    /**
     * Utility for the Network
     */
    public static class Util {
        public static int RF_PER_EU = 4;
        private static boolean RF_ENERGY = false, IC_ENERGY = false, CHECK_ALL = true;

        private static void checkAvailabilities() {
            if (CHECK_ALL) {
                try {
                    Class tClass = cofh.api.energy.IEnergyReceiver.class;
                    tClass.getCanonicalName();
                    RF_ENERGY = true;
                } catch (Throwable e) {/**/}
                try {
                    Class tClass = ic2.api.energy.tile.IEnergySink.class;
                    tClass.getCanonicalName();
                    IC_ENERGY = true;
                } catch (Throwable e) {/**/}
                CHECK_ALL = false;
            }
        }

        /**
         * Emits Energy to the adjacent Blocks. Also compatible with adjacent IC2 TileEntities when electric and RF TileEntities when RedstoneFlux.
         *
         * @return the amount of used secondary value.
         */
        public static final long emitEnergyToNetwork(SubTag aEnergyType, long aPrimary, long aSecondary, IExperimentalEnergyTileEntity aEmitter) {
            long rUsedSecondary = 0;
            checkAvailabilities();
            for (byte i = 0, j = 0; i < 6 && aSecondary > rUsedSecondary; i++)
                if (aEmitter.outputsEnergyTo(aEnergyType, i)) {
                    j = GT_Utility.getOppositeSide(i);
                    TileEntity tTileEntity = aEmitter.getTileEntityAtSide(i);
                    if (tTileEntity instanceof IExperimentalEnergyTileEntity) {
                        if (aEmitter.getColorization() >= 0) {
                            byte tColor = ((IExperimentalEnergyTileEntity) tTileEntity).getColorization();
                            if (tColor >= 0 && tColor != aEmitter.getColorization()) continue;
                        }
                        rUsedSecondary += ((IExperimentalEnergyTileEntity) tTileEntity).injectEnergy(aEnergyType, j, aPrimary, aSecondary - rUsedSecondary);
                    } else if (IC_ENERGY && aEnergyType == SubTag.ENERGY_ELECTRICITY && tTileEntity instanceof IEnergySink) {
                        if (((IEnergySink) tTileEntity).acceptsEnergyFrom((TileEntity) aEmitter, ForgeDirection.getOrientation(j))) {
                            while (aSecondary > rUsedSecondary && ((IEnergySink) tTileEntity).getDemandedEnergy() > 0 && ((IEnergySink) tTileEntity).injectEnergy(ForgeDirection.getOrientation(j), aPrimary, aPrimary) < aPrimary)
                                rUsedSecondary++;
                        }
                    } else if (RF_ENERGY && aEnergyType == SubTag.ENERGY_REDSTONE_FLUX && tTileEntity instanceof IEnergyReceiver && ((IEnergyReceiver) tTileEntity).canConnectEnergy(ForgeDirection.getOrientation(j))) {
                        rUsedSecondary += ((IEnergyReceiver) tTileEntity).receiveEnergy(ForgeDirection.getOrientation(j), (int) aSecondary, false);
                    }
                }
            return rUsedSecondary;
        }
    }
}