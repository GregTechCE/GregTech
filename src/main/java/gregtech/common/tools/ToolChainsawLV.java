package gregtech.common.tools;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ToolChainsawLV extends ToolSaw {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 8;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public void convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, List<ItemStack> dropList, ItemStack toolStack) {
        super.convertBlockDrops(world, blockPos, blockState, player, dropList, toolStack);
        ToolUtility.applyTimberAxe(toolStack, world, blockPos, player, blockState);
    }

}
