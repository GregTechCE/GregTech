package gregtech.common.items.behaviors;

import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Behaviour_Crowbar
        extends Behaviour_None {
    private final int mVanillaCosts;
    private final int mEUCosts;

    public Behaviour_Crowbar(int aVanillaCosts, int aEUCosts) {
        this.mVanillaCosts = aVanillaCosts;
        this.mEUCosts = aEUCosts;
    }

    @Override
    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if(!aWorld.isAirBlock(blockPos) && !aWorld.isRemote) {
            IBlockState blockState = aWorld.getBlockState(blockPos);
            if(blockState.getBlock() instanceof BlockRailBase) {
                if(aPlayer.isSneaking()) {
                    if(aWorld.canMineBlockBody(aPlayer, blockPos)) {
                        if(blockState.getBlock().canHarvestBlock(aWorld, blockPos, aPlayer)) {
                            if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts / 2, this.mEUCosts / 2, aPlayer)) {
                                for (ItemStack drops : blockState.getBlock().getDrops(aWorld, blockPos, blockState, 0)) {
                                    Block.spawnAsEntity(aWorld, blockPos, drops);
                                }
                                blockState.getBlock().onBlockDestroyedByPlayer(aWorld, blockPos, blockState);
                                blockState.getBlock().onBlockHarvested(aWorld, blockPos, blockState, aPlayer);
                                blockState.getBlock().breakBlock(aWorld, blockPos, blockState);
                                aWorld.setBlockToAir(blockPos);
                                return true;
                            }
                        }
                    }
                } else {
                    if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts, this.mEUCosts, aPlayer)) {
                        BlockRailBase blockRailBase = (BlockRailBase) blockState.getBlock();
                        int rotation = blockState.getValue(blockRailBase.getShapeProperty()).ordinal() + 1;
                        if (rotation >= BlockRailBase.EnumRailDirection.values().length) {
                            rotation = 0;
                        }
                        BlockRailBase.EnumRailDirection newDirection = BlockRailBase.EnumRailDirection.values()[rotation];
                        return aWorld.setBlockState(blockPos, blockState.withProperty(blockRailBase.getShapeProperty(), newDirection));
                    }
                }
            }
        }
        return false;
    }
}
