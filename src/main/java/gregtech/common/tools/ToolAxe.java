package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ToolAxe extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 100;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return "axe".equals(tool) || (block.getMaterial() == Material.WOOD);
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, NonNullList<ItemStack> drops) {
        int amount = 0;
//        if (GregTechAPI.sTimber && !harvester.isSneaking() && OrePrefix.log.contains(getBlockStack(blockState))) {
//            int tY = blockPos.getY() + 1;
//            for (int tH = harvester.worldObj.getHeight(); tY < tH; tY++) {
//                BlockPos block = new BlockPos(blockPos.getX(), tY, blockPos.getZ());
//                if (!isStateEqual(harvester.worldObj.getBlockState(block), blockState) ||
//                        !harvester.worldObj.destroyBlock(block, true)) break;
//                amount++;
//            }
//        }
        return amount;
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "").appendSibling(entity.getDisplayName()).appendText(TextFormatting.WHITE + " has been chopped by " + TextFormatting.GREEN).appendSibling(player.getDisplayName());
//    }
}
