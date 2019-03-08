package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ToolSense extends ToolBase {

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 5.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return (tool != null && (tool.equals("sense") || tool.equals("scythe"))) ||
                block.getMaterial() == Material.PLANTS ||
                block.getMaterial() == Material.LEAVES;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops, boolean recursive, ItemStack toolStack) {
        return ToolUtility.applyMultiBreak(world, blockPos, harvester, this, 3);
    }

}
