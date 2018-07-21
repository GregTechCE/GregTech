package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ToolSword extends ToolBase {

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SWORD);
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 2;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 5.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack, String tool) {
        return (tool != null && tool.equals("sword")) ||
            block.getMaterial() == Material.LEAVES ||
            block.getMaterial() == Material.GOURD ||
            block.getMaterial() == Material.VINE ||
            block.getMaterial() == Material.WEB ||
            block.getMaterial() == Material.CLOTH ||
            block.getMaterial() == Material.CARPET ||
            block.getMaterial() == Material.PLANTS ||
            block.getMaterial() == Material.CACTUS ||
            block.getMaterial() == Material.CAKE ||
            block.getMaterial() == Material.TNT ||
            block.getMaterial() == Material.SPONGE;
    }

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }
}
