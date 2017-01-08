package gregtech.api.interfaces.tileentity;

import cofh.api.energy.IEnergyReceiver;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Utility;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tile.IEnergyStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static gregtech.api.enums.GT_Values.V;

/**
 * Interface for getting Connected to the GregTech Energy Network.
 * <p/>
 * This is all you need to connect to the GT Network.
 * IColoredTileEntity is needed for not connecting differently coloured Blocks to each other.
 * IHasWorldObjectAndCoords is needed for the InWorld related Stuff. @BaseTileEntity does implement most of that Interface.
 */
public interface IEnergyConnected extends IColoredTileEntity, IHasWorldObjectAndCoords, IEnergyAcceptor, IEnergyEmitter {
    /**
     * Inject Energy Call for Electricity. Gets called by EnergyEmitters to inject Energy into your Block
     * <p/>
     * Note: you have to check for @inputEnergyFrom because the Network won't check for that by itself.
     *
     * @param aSide 0 - 5 = Vanilla Directions of YOUR Block the Energy gets inserted to. 6 = No specific Side (don't do Side checks for this Side)
     * @return amount of used Amperes. 0 if not accepted anything.
     */
    public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperage);

    /**
     * Sided Energy Input
     */
    public boolean inputEnergyFrom(byte aSide);

    /**
     * Sided Energy Output
     */
    public boolean outputsEnergyTo(byte aSide);

    /**
     * Utility for the Network
     */
    public static class Util {
        /**
         * Emits Energy to the E-net. Also compatible with adjacent IC2 TileEntities.
         *
         * @return the used Amperage.
         */
        public static final long emitEnergyToNetwork(long aVoltage, long aAmperage, IEnergyConnected aEmitter) {
            long rUsedAmperes = 0;
            for (byte i = 0, j = 0; i < 6 && aAmperage > rUsedAmperes; i++)
                if (aEmitter.outputsEnergyTo(i)) {
                    j = GT_Utility.getOppositeSide(i);
                    TileEntity tTileEntity = aEmitter.getTileEntityAtSide(i);
                    if (tTileEntity instanceof IEnergyConnected) {
                        if (aEmitter.getColorization() >= 0) {
                            byte tColor = ((IEnergyConnected) tTileEntity).getColorization();
                            if (tColor >= 0 && tColor != aEmitter.getColorization()) continue;
                        }
                        rUsedAmperes += ((IEnergyConnected) tTileEntity).injectEnergyUnits(j, aVoltage, aAmperage - rUsedAmperes);
                    } else if (tTileEntity instanceof IEnergySink) {
                        if (((IEnergySink) tTileEntity).acceptsEnergyFrom(aEmitter, EnumFacing.VALUES[j])) {
                            while (aAmperage > rUsedAmperes && ((IEnergySink) tTileEntity).getDemandedEnergy() > 0 && ((IEnergySink) tTileEntity).injectEnergy(EnumFacing.VALUES[j], aVoltage, aVoltage) < aVoltage)
                                rUsedAmperes++;
                        }
                    } else if (tTileEntity instanceof IEnergyReceiver) {
                        EnumFacing tDirection = EnumFacing.VALUES[i].getOpposite();
                        int rfOut = (int) (aVoltage * GregTech_API.mEUtoRF / 100);
                        if (((IEnergyReceiver) tTileEntity).receiveEnergy(tDirection, rfOut, true) == rfOut) {
                            ((IEnergyReceiver) tTileEntity).receiveEnergy(tDirection, rfOut, false);
                            rUsedAmperes++;
                        }
                        if (GregTech_API.mRFExplosions && GregTech_API.sMachineExplosions && ((IEnergyReceiver) tTileEntity).getMaxEnergyStored(tDirection) < rfOut * 600) {
                            if (rfOut > 32 * GregTech_API.mEUtoRF / 100) {
                                int aExplosionPower = rfOut;
                                float tStrength = aExplosionPower < V[0] ? 1.0F : aExplosionPower < V[1] ? 2.0F : aExplosionPower < V[2] ? 3.0F : aExplosionPower < V[3] ? 4.0F : aExplosionPower < V[4] ? 5.0F : aExplosionPower < V[4] * 2 ? 6.0F : aExplosionPower < V[5] ? 7.0F : aExplosionPower < V[6] ? 8.0F : aExplosionPower < V[7] ? 9.0F : 10.0F;
                                BlockPos position = tTileEntity.getPos();
                                World tWorld = tTileEntity.getWorld();
                                GT_Utility.sendSoundToPlayers(tWorld, GregTech_API.sSoundList.get(209), 1.0F, -1, position.getX(), position.getY(), position.getZ());
                                tWorld.setBlockToAir(position);
                                if (GregTech_API.sMachineExplosions)
                                    tWorld.createExplosion(null, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5, tStrength, true);
                            }
                        }
                    }
                }
            return rUsedAmperes;
        }
    }
}