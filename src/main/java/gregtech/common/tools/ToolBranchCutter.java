package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ToolBranchCutter extends ToolBase {

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.5F;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        return 0.25F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 0.25F;
    }

    @Override
    public boolean isGrafter(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canMineBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return (tool != null && tool.equals("grafter")) || block.getMaterial() == Material.LEAVES;
    }
}
