package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ToolShovel extends ToolBase {

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SHOVEL);
    }


    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.5F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack, String tool) {
        return (tool != null && tool.equals("shovel")) ||
            block.getMaterial() == Material.SAND ||
            block.getMaterial() == Material.GRASS ||
            block.getMaterial() == Material.GROUND ||
            block.getMaterial() == Material.SNOW ||
            block.getMaterial() == Material.CLAY;
    }

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }

}
