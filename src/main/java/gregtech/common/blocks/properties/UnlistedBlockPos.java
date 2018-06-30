package gregtech.common.blocks.properties;

import net.minecraft.util.math.BlockPos;

import net.minecraftforge.common.property.IUnlistedProperty;

public final class UnlistedBlockPos implements IUnlistedProperty<BlockPos> {

	public static UnlistedBlockPos BLOCK_POS = new UnlistedBlockPos();

	private UnlistedBlockPos() {}

	@Override
	public String getName() {
		return "position";
	}

	@Override
	public boolean isValid(BlockPos value) {
		return true;
	}

	@Override
	public Class<BlockPos> getType() {
		return BlockPos.class;
	}

	@Override
	public String valueToString(BlockPos value) {
		return value.toString();
	}

}