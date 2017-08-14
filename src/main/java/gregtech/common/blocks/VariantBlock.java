package gregtech.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.List;

public class VariantBlock<T extends Enum<T> & IStringSerializable> extends Block {

    public final PropertyEnum<T> VARIANT;
    private final T[] VALUES;

    public VariantBlock(Material materialIn, Class<T> enumClass) {
        super(materialIn);
        this.VARIANT = PropertyEnum.create("variant", enumClass);
        this.VALUES = enumClass.getEnumConstants();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for(T variant : VALUES) {
            list.add(getItemVariant(variant));
        }
    }

    public T getVariant(IBlockState blockState) {
        return blockState.getValue(VARIANT);
    }

    public IBlockState withVariant(T variant) {
        return getDefaultState().withProperty(VARIANT, variant);
    }

    public ItemStack getItemVariant(T variant) {
        return new ItemStack(this, 1, variant.ordinal());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

}
