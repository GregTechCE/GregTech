package gregtech.common.blocks;

import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class VariantBlock<T extends Enum<T> & IStringSerializable> extends Block {

    private PropertyEnum<T> VARIANT;
    private T[] VALUES;

    public VariantBlock(Material materialIn) {
        super(materialIn);
        setCreativeTab(GregTech_API.TAB_GREGTECH);
    }

    public void registerVariantBlock(String blockName) {
        setUnlocalizedName(blockName);
        setRegistryName(GT_Values.MODID, blockName);
        GameRegistry.register(this);
        VariantItemBlock itemBlock = new VariantItemBlock<>(this);
        itemBlock.setRegistryName(GT_Values.MODID, blockName);
        GameRegistry.register(itemBlock);
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
        Class<T> enumClass = GT_Utility.getActualTypeParameter(getClass(), VariantBlock.class, 0);
        this.VARIANT = PropertyEnum.create("variant", enumClass);
        this.VALUES = enumClass.getEnumConstants();
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
