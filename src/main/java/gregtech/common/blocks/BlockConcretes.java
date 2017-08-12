package gregtech.common.blocks;

import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.ItemConcretes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

import javax.annotation.Nullable;

public class BlockConcretes extends BlockStonesAbstract {

    public static final AxisAlignedBB CONCRETE_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public static final PropertyEnum<BlockStonesAbstract.EnumStoneVariant> STONE_VARIANT = PropertyEnum.create("stone_variant", BlockStonesAbstract.EnumStoneVariant.class);
    public static final PropertyBool LIGHT_CONCRETE = PropertyBool.create("light_concrete");

    public BlockConcretes() {
        super("blockconcretes", ItemConcretes.class);
        setResistance(20.0F);
        this.slipperiness = 0.9F;

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(STONE_VARIANT, EnumStoneVariant.NORMAL)
                .withProperty(LIGHT_CONCRETE, false));

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Dark Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Dark Concrete Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Mossy Dark Concrete Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Dark Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Cracked Dark Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Mossy Dark Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Chiseled Dark Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Smooth Dark Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Light Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Light Concrete Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Mossy Light Concrete Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Light Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cracked Light Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Mossy Light Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Chiseled Light Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Smooth Light Concrete");
//        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, GT_Values.W));
    }

    /**
     * This block does not use material but extends BlockStonesAbstract
     */
    @Override
    public Material[] getMaterials() {
        return new Material[0];
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STONE_VARIANT, LIGHT_CONCRETE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(STONE_VARIANT, BlockStonesAbstract.EnumStoneVariant.byMetadata(meta & 0b0111))
                .withProperty(LIGHT_CONCRETE, (meta & 0b1000) == 0b1000);
    }

    /**
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        meta |= state.getValue(STONE_VARIANT).getMetadata();

        if (state.getValue(LIGHT_CONCRETE)){
            meta |= 1 << 3;
        }

        return meta;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity) {
        if (entity.onGround && !entity.isInWater()) {
            if (entity.isSneaking()) {
                entity.motionX *= 0.8999999761581421D;
                entity.motionZ *= 0.8999999761581421D;
            } else {
                if (entity.motionX < 6.0 && entity.motionZ < 6.0) {
                    entity.motionX *= 1.100000023841858D;
                    entity.motionZ *= 1.100000023841858D;
                }
            }
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos) {
        Block block = world.getBlockState(pos.up()).getBlock();
        if (block instanceof IFluidBlock || block instanceof BlockLiquid) {
            return Block.FULL_BLOCK_AABB;
        }
        return CONCRETE_BLOCK_AABB;
    }
}
