package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
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
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops, boolean recursive) {
        int shearableResult = ToolUtility.applyShearable(world, blockPos, blockState, drops, harvester);
        if(shearableResult > 0) {
            //if shearing was successful, then just return it's result
            return shearableResult;
        }
        if(blockState.getMaterial() == Material.PACKED_ICE || blockState.getMaterial() == Material.ICE) {
            int stackMetadata = blockState.getBlock().getMetaFromState(blockState);
            ItemStack dropStack = new ItemStack(blockState.getBlock(), 1, stackMetadata);
            if(!dropStack.isEmpty()) {
                world.setBlockToAir(blockPos); //because ice sets water
                //do not set damage for non-subtype items
                //good example here would be frosted ice
                if(!dropStack.getItem().getHasSubtypes())
                    dropStack.setItemDamage(0);
                //only add drop stack if actual block has item form
                drops.clear();
                drops.add(dropStack);
                return 1;
            }
        }
        return 0;
    }




}
