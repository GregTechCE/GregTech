package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolBranchCutter extends ToolBase {

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.5F;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        return 0.25F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 0.25F;
    }

    @Override
    public boolean isGrafter(ItemStack stack) {
        return true;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, NonNullList<ItemStack> drops, boolean recursive) {
        if (blockState.getBlock().isLeaves(blockState, world, blockPos)) {
            drops.clear(); //clear previous drops to avoid possible issues
            blockState.getBlock().getDrops(drops, world, blockPos, blockState, 3);
        }
        return 0;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return (tool != null && tool.equals("grafter")) || block.getMaterial() == Material.LEAVES;
    }

}
