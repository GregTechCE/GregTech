package gregtech.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrowbarBehaviour implements IItemBehaviour {

    private final int cost;

    public CrowbarBehaviour(int cost) {
        this.cost = cost;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isAirBlock(blockPos) && !world.isRemote) {
            IBlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() instanceof BlockRailBase) {
                if (player.isSneaking()) {
                    if (world.canMineBlockBody(player, blockPos)) {
                        if (blockState.getBlock().canHarvestBlock(world, blockPos, player)) {
                            if (GTUtility.doDamageItem(stack, cost / 2, false)) {
                                for (ItemStack drops : blockState.getBlock().getDrops(world, blockPos, blockState, 0)) {
                                    Block.spawnAsEntity(world, blockPos, drops);
                                }
                                blockState.getBlock().onBlockDestroyedByPlayer(world, blockPos, blockState);
                                blockState.getBlock().onBlockHarvested(world, blockPos, blockState, player);
                                blockState.getBlock().breakBlock(world, blockPos, blockState);
                                world.setBlockToAir(blockPos);
                                return EnumActionResult.SUCCESS;
                            }
                        }
                    }
                } else {
                    if (GTUtility.doDamageItem(stack, cost, false)) {
                        BlockRailBase blockRailBase = (BlockRailBase) blockState.getBlock();
                        int rotation = blockState.getValue(blockRailBase.getShapeProperty()).ordinal() + 1;
                        if (rotation >= BlockRailBase.EnumRailDirection.values().length) {
                            rotation = 0;
                        }
                        if (world.setBlockState(blockPos, blockState.withProperty(blockRailBase.getShapeProperty(), BlockRailBase.EnumRailDirection.values()[rotation]))) {
                            return EnumActionResult.SUCCESS;
                        }
                        return EnumActionResult.FAIL;
                    }
                }
            }
        }
        return EnumActionResult.PASS;
    }
}
