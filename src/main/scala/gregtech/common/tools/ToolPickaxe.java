package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ToolPickaxe extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.5F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && tool.equals("pickaxe") ||
            block.getMaterial() == Material.ROCK ||
            block.getMaterial() == Material.IRON ||
            block.getMaterial() == Material.ANVIL ||
            block.getMaterial() == Material.GLASS;
    }

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }

}
