package gregtech.common.tools;

import gregtech.api.items.toolitem.IToolStats;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ToolBase implements IToolStats {

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 8;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 2;
    }

    @Override
    public ResourceLocation getUseSound(ItemStack stack) {
        return null;
    }
}
