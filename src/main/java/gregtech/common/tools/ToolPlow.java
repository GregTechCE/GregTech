package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolPlow extends ToolBase {

    private ThreadLocal<Object> sIsHarvestingRightNow = new ThreadLocal();

    @Override
    public float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        return entity instanceof EntitySnowman ? 4.0F : 1.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && tool.equals("plow") ||
            block.getMaterial() == Material.SNOW ||
            block.getMaterial() == Material.CRAFTED_SNOW;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, NonNullList<ItemStack> drops) {
        int conversions = 0;
        if (this.sIsHarvestingRightNow.get() == null && harvester instanceof EntityPlayerMP) {
            this.sIsHarvestingRightNow.set(this);
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    for (int k = -1; k < 2; k++) {
                        BlockPos block = blockPos.add(i, j, k);
                        IBlockState state = harvester.getEntityWorld().getBlockState(block);
                        if ((i != 0 || j != 0 || k != 0) && ((EntityPlayerMP) harvester).interactionManager.tryHarvestBlock(block)) {
                              conversions++;
                        }
                    }
                }
            }
            this.sIsHarvestingRightNow.set(null);
        }
        return conversions;
    }
}
