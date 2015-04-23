package gregtech.api.interfaces.tileentity;

import gregtech.api.util.GT_Utility;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Interface for getting Connected to the GregTech Energy Network.
 * 
 * This is all you need to connect to the GT Network.
 * IColoredTileEntity is needed for not connecting differently coloured Blocks to each other.
 * IHasWorldObjectAndCoords is needed for the InWorld related Stuff. @BaseTileEntity does implement most of that Interface.
 */
public interface IEnergyConnected extends IColoredTileEntity, IHasWorldObjectAndCoords {
	/**
	 * Inject Energy Call for Electricity. Gets called by EnergyEmitters to inject Energy into your Block
	 * 
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
	     * @return the used Amperage.
	     */
		public static final long emitEnergyToNetwork(long aVoltage, long aAmperage, IEnergyConnected aEmitter) {
			long rUsedAmperes = 0;
			for (byte i = 0, j = 0; i < 6 && aAmperage > rUsedAmperes; i++) if (aEmitter.outputsEnergyTo(i)) {
				j = GT_Utility.getOppositeSide(i);
				TileEntity tTileEntity = aEmitter.getTileEntityAtSide(i);
				if (tTileEntity instanceof IEnergyConnected) {
	    			if (aEmitter.getColorization() >= 0) {
						byte tColor = ((IEnergyConnected)tTileEntity).getColorization();
						if (tColor >= 0 && tColor != aEmitter.getColorization()) continue;
					}
					rUsedAmperes+=((IEnergyConnected)tTileEntity).injectEnergyUnits(j, aVoltage, aAmperage-rUsedAmperes);
//				} else if (tTileEntity instanceof IEnergySink) {
//	        		if (((IEnergySink)tTileEntity).acceptsEnergyFrom((TileEntity)aEmitter, ForgeDirection.getOrientation(j))) {
//	        			while (aAmperage > rUsedAmperes && ((IEnergySink)tTileEntity).demandedEnergyUnits() > 0 && ((IEnergySink)tTileEntity).injectEnergyUnits(ForgeDirection.getOrientation(j), aVoltage) < aVoltage) rUsedAmperes++;
//	        		}
	    		} else if (tTileEntity instanceof IEnergySink) {
	        		if (((IEnergySink)tTileEntity).acceptsEnergyFrom((TileEntity)aEmitter, ForgeDirection.getOrientation(j))) {
	        			while (aAmperage > rUsedAmperes && ((IEnergySink)tTileEntity).getDemandedEnergy() > 0 && ((IEnergySink)tTileEntity).injectEnergy(ForgeDirection.getOrientation(j), aVoltage, aVoltage) < aVoltage) rUsedAmperes++;
	        		}
	    		}
			}
			return rUsedAmperes;
		}
	}
}