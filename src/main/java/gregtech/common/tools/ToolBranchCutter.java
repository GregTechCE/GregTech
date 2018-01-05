package gregtech.common.tools;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ToolBranchCutter extends ToolBase {

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.5F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
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
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, NonNullList<ItemStack> drops) {
        ItemStack stack = harvester.getHeldItem(EnumHand.MAIN_HAND);
        if (blockState.getMaterial() == Material.LEAVES) {
//            aEvent.setDropChance(Math.min(1.0F, Math.max(aEvent.getDropChance(), (stack.getItem().getHarvestLevel(stack, "", harvester, blockState) + 1) * 0.2F)));
            if (blockState.getBlock().isLeaves(blockState, world, blockPos)) {
                blockState.getBlock().getDrops(drops, world, blockPos, blockState, 1);
            }
        }
        return 0;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && tool.equals("grafter") || block.getMaterial() == Material.LEAVES;
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(entity.getDisplayName())
//                .appendText(TextFormatting.WHITE + " has been trimmed by " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
