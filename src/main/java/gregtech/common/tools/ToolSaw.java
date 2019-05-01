package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ToolSaw extends ToolBase {

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_AXE);
    }

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
        return 3.0F;
    }

    @Override
    public boolean canMineBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return (tool != null && (tool.equals("axe") || tool.equals("saw"))) ||
            block.getMaterial() == Material.LEAVES ||
            block.getMaterial() == Material.VINE ||
            block.getMaterial() == Material.WOOD ||
            block.getMaterial() == Material.CACTUS ||
            block.getMaterial() == Material.ICE ||
            block.getMaterial() == Material.PACKED_ICE;
    }

    @Override
    public boolean onBlockPreBreak(ItemStack stack, BlockPos blockPos, EntityPlayer player) {
        if (player.world.isRemote || player.capabilities.isCreativeMode) {
            return false;
        }
        return ToolUtility.applyShearBehavior(stack, blockPos, player);
    }

    @Override
    public void convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, List<ItemStack> dropList, ItemStack toolStack) {
        if (blockState.getMaterial() == Material.PACKED_ICE || blockState.getMaterial() == Material.ICE) {
            Item item = Item.getItemFromBlock(blockState.getBlock());
            ItemStack dropStack = new ItemStack(item, 1);
            world.setBlockToAir(blockPos);
            dropList.add(dropStack);
        }
    }


}
