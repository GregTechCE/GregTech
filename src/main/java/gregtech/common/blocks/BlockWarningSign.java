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

public class BlockWarningSign extends Block {

    public static final PropertyEnum<SignType> VARIANT = PropertyEnum.create("variant", SignType.class);

    public BlockWarningSign() {
        super(Material.IRON);
        setHardness(2.0f);
        setResistance(1.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, SignType.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for(SignType signType : SignType.values()) {
            list.add(getItem(signType));
        }
    }

    public ItemStack getItem(SignType signType) {
        return new ItemStack(this, 1, signType.ordinal());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack stack) {
        return getDefaultState().withProperty(VARIANT, SignType.values()[stack.getItemDamage()]);
    }

    public enum SignType implements IStringSerializable {

        YELLOW_STRIPES("yellow_stripes"),
        SMALL_YELLOW_STRIPES("small_yellow_stripes"),
        RADIOACTIVE_HAZARD("radioactive_hazard"),
        BIO_HAZARD("bio_hazard"),
        EXPLOSION_HAZARD("explosion_hazard"),
        FIRE_HAZARD("fire_hazard"),
        ACID_HAZARD("acid_hazard"),
        MAGIC_HAZARD("magic_hazard"),
        FROST_HAZARD("frost_hazard"),
        NOISE_HAZARD("noise_hazard");

        private final String name;

        SignType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }
    
}
