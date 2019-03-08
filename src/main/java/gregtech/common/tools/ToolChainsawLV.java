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
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops, boolean recursive, ItemStack toolStack) {
        int superResult = super.convertBlockDrops(world, blockPos, blockState, harvester, drops, recursive, toolStack);
        if(superResult > 0) {
            //we already harvested block and converted blocks in saw class
            return superResult;
        }
        //if not, try to apply timber axe mechanic
        return ToolUtility.applyTimberAxe(world, blockPos, blockState, harvester, drops);
    }

}
