package gregtech.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockWireCoil extends Block {

    public static final PropertyEnum<CoilType> VARIANT = PropertyEnum.create("variant", CoilType.class);

    public BlockWireCoil() {
        super(Material.IRON);
        setHardness(7.0f);
        setResistance(5.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, CoilType.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for(CoilType heatingCoilType : CoilType.values()) {
            list.add(getItem(heatingCoilType));
        }
    }

    public ItemStack getItem(CoilType coilType) {
        return new ItemStack(this, 1, coilType.ordinal());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack stack) {
        return getDefaultState().withProperty(VARIANT, CoilType.values()[stack.getItemDamage()]);
    }

    public enum CoilType implements IStringSerializable {

        CUPRONICKEL("cupronickel"),
        KANTHAL("kanthal"),
        NICHROME("nichrome"),
        TUNGSTENSTEEL("tungstensteel"),
        HSS_G("hss_g"),
        NAQUADAH("naquadah"),
        NAQUADAH_ALLOY("naquadah_alloy"),
        SUPERCONDUCTOR("superconductor"),
        FUSION("fusion");

        private final String name;

        CoilType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }

}
