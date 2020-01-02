package gregtech.api.items.toolitem;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

class DummyToolStats implements IToolStats {
    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 0;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 0;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canMineBlock(IBlockState block, ItemStack stack) {
        return false;
    }
}
