package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ToolSaw extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 2;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.75F;
    }

//    @Override
//    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
//        ItemStack stack = harvester.getHeldItem(EnumHand.MAIN_HAND);
//        if (blockState.getMaterial() == Material.LEAVES && blockState.getBlock() instanceof IShearable) {
//            IShearable shearable = (IShearable) blockState.getBlock();
//            if (shearable.isShearable(stack, harvester.worldObj, blockPos)) {
//                List<ItemStack> tDrops = shearable.onSheared(stack, harvester.worldObj, blockPos, aFortune);
//                drops.clear();
//                drops.addAll(tDrops);
//                aEvent.setDropChance(1.0F);
//            }
//            harvester.worldObj.setBlockToAir(blockPos);
//        } else if ((blockState.getMaterial() == Material.ICE ||
//                blockState.getMaterial() == Material.PACKED_ICE)
//                && drops.isEmpty()) {
//            drops.add(getBlockStack(blockState));
//            harvester.worldObj.setBlockToAir(blockPos);
//            aEvent.setDropChance(1.0F);
//            return 1;
//        }
//        return 0;
//    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && (tool.equals("axe") || tool.equals("saw")) ||
            block.getMaterial() == Material.LEAVES ||
            block.getMaterial() == Material.VINE ||
            block.getMaterial() == Material.WOOD ||
            block.getMaterial() == Material.CACTUS ||
            block.getMaterial() == Material.ICE ||
            block.getMaterial() == Material.PACKED_ICE;
    }

}
