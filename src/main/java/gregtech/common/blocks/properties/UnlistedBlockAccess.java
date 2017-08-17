package gregtech.common.blocks.properties;

import gregtech.api.util.GT_Utility;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.property.IUnlistedProperty;

public final class UnlistedBlockAccess implements IUnlistedProperty<IBlockAccess> {

	public static UnlistedBlockAccess BLOCK_ACCESS = new UnlistedBlockAccess();

	private UnlistedBlockAccess() {}

	@Override
	public String getName() {
		return "dimension";
	}

	@Override
	public boolean isValid(IBlockAccess value) {
		return true;
	}

	@Override
	public Class<IBlockAccess> getType() {
		return IBlockAccess.class;
	}

	@Override
	public String valueToString(IBlockAccess value) {
		World world = GT_Utility.getBlockAcessWorld(value);
		if(world == null) {
			return "unknown";
		}
		return world.provider.getDimensionType().getName();
	}

}