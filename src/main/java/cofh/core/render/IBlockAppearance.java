package cofh.core.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Implement this interface on blocks that can mimic the appearance of other blocks. Note that this is meant to be available server-side, so ensure the code is
 * server-safe and doesn't use client-side code.
 */
public interface IBlockAppearance {

	/**
	 * This function returns the state of the block that is being shown on a given side.
	 *
	 * @param world Reference to the world.
	 * @param pos   The Position of the block.
	 * @param side  The side of the block.
	 */
	IBlockState getVisualState(IBlockAccess world, BlockPos pos, EnumFacing side);

	/**
	 * This function returns whether the block's renderer will visually connect to other blocks implementing IBlockAppearance.
	 */
	boolean supportsVisualConnections();

}