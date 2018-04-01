package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolSense extends ToolBase {

    private ThreadLocal<ToolSense> isHarvestingRightNow = new ThreadLocal<>();

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && (tool.equals("sense") || tool.equals("scythe")) ||
                block.getMaterial() == Material.PLANTS ||
                block.getMaterial() == Material.LEAVES;
    }

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, NonNullList<ItemStack> drops) {
        int conversions = 0;
        ItemStack stack = harvester.getHeldItem(EnumHand.MAIN_HAND);
        if (this.isHarvestingRightNow.get() == null && !harvester.getEntityWorld().isRemote) {
            this.isHarvestingRightNow.set(this);
            for (int i = -2; i < 3; i++) {
                for (int j = -2; j < 3; j++) {
                    for (int k = -2; k < 3; k++) {
                        BlockPos block = blockPos.add(i, j, k);
                        IBlockState state = harvester.getEntityWorld().getBlockState(block);
                        if ((i != 0 || j != 0 || k != 0) && stack.getDestroySpeed(state) > 0.0F &&
                                ((EntityPlayerMP) harvester).interactionManager.tryHarvestBlock(block))
                            conversions++;
                    }
                }
            }
        }
        return conversions;
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.GREEN + "")
//                .appendSibling(player.getDisplayName())
//                .appendText(TextFormatting.WHITE + " has taken the Soul of " + TextFormatting.RED)
//                .appendSibling(entity.getDisplayName());
//    }
}
