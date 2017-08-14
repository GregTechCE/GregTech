package gregtech.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class VariantItemBlock extends ItemBlock {

    private PropertyEnum<?> variant;

    public VariantItemBlock(Block block) {
        super(block);
        this.variant = (PropertyEnum<?>) block.getBlockState().getProperty("variant");
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @SuppressWarnings("deprecation")
    protected IBlockState getBlockState(ItemStack stack) {
        return block.getStateFromMeta(getMetadata(stack.getItemDamage()));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + '.' + getBlockState(stack).getValue(variant).getName();
    }

}
