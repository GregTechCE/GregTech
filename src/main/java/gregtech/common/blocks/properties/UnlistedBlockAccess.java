package gregtech.common.blocks.properties;

import net.minecraft.world.IBlockAccess;
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
		return "";
	}

}