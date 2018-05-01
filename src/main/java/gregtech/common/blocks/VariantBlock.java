package gregtech.common.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class VariantBlock<T extends Enum<T> & IStringSerializable> extends Block {

    private PropertyEnum<T> VARIANT;
    private T[] VALUES;

    public VariantBlock(Material materialIn) {
        super(materialIn);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for(T variant : VALUES) {
            list.add(getItemVariant(variant));
        }
    }

    public IBlockState getState(T variant) {
        return getDefaultState().withProperty(VARIANT, variant);
    }

    public T getState(IBlockState blockState) {
        return blockState.getValue(VARIANT);
    }

    public ItemStack getItemVariant(T variant) {
        return new ItemStack(this, 1, variant.ordinal());
    }

    public ItemStack getItemVariant(T variant, int amount) {
        return new ItemStack(this, amount, variant.ordinal());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        Class<T> enumClass = GTUtility.getActualTypeParameter(getClass(), VariantBlock.class, 0);
        this.VARIANT = PropertyEnum.create("variant", enumClass);
        this.VALUES = enumClass.getEnumConstants();
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
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
