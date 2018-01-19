package gregtech.common.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.api.util.IBlockOre;
import gregtech.common.blocks.properties.PropertyStoneType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOre extends BlockFalling implements IBlockOre {

    public static final PropertyBool SMALL = PropertyBool.create("small");
    public final PropertyStoneType STONE_TYPE;

    public final DustMaterial material;

    public BlockOre(DustMaterial material, StoneType[] allowedValues) {
        super(net.minecraft.block.material.Material.ROCK);
        setUnlocalizedName("ore_block");
        setSoundType(SoundType.STONE);
        setHardness(3.0f);
        setResistance(5.0f);
        setCreativeTab(GregTechAPI.TAB_GREGTECH_ORES);
        this.material = material;
        STONE_TYPE = PropertyStoneType.create("stone_type", allowedValues);
        initBlockState();
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    protected void initBlockState() {
        BlockStateContainer stateContainer = createStateContainer();
        ObfuscationReflectionHelper.setPrivateValue(Block.class, this, stateContainer, 21); //this.stateContainer
        setDefaultState(stateContainer.getBaseState());
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        StoneType stoneType = state.getValue(STONE_TYPE);
        return stoneType.harvestTool;
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        StoneType stoneType = state.getValue(STONE_TYPE);
        boolean small = state.getValue(SMALL);
        if(material instanceof SolidMaterial) {
            int toolQuality = ((SolidMaterial) material).harvestLevel;
            return Math.max(stoneType.stoneMaterial.harvestLevel, toolQuality > 1 ? toolQuality - 1 : toolQuality);
        }
        return 1;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STONE_TYPE, STONE_TYPE.getAllowedValues().get(meta % 8)).withProperty(SMALL, meta / 8 > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return STONE_TYPE.getAllowedValues().indexOf(state.getValue(STONE_TYPE)) + (state.getValue(SMALL) ? 8 : 0);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return blockState.getValue(STONE_TYPE).unbreakable ? -1.0f : this.blockHardness;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion)
    {
        return world.getBlockState(pos).getValue(STONE_TYPE).unbreakable ? 1200000.0F : getExplosionResistance(exploder);
    }

    @SuppressWarnings("deprecation")
    public ItemStack getItem(IBlockState blockState) {
        return new ItemStack(this, 1, getMetaFromState(blockState));
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        blockState.getValidStates().stream()
            .filter(blockState -> blockState.getValue(STONE_TYPE) != StoneTypes._NULL)
            .forEach(blockState -> list.add(getItem(blockState)));
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    private BlockStateContainer createStateContainer() {
        return new BlockStateContainer(this, STONE_TYPE, SMALL);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getValue(STONE_TYPE).falling)
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (state.getValue(STONE_TYPE).falling)
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (state.getValue(STONE_TYPE).falling)
            super.updateTick(worldIn, pos, state, rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(STONE_TYPE).falling)
            super.randomDisplayTick(stateIn, worldIn, pos, rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getDustColor(IBlockState state) {
        return this.material.materialRGB;
    }

    @Override
    public IBlockState getOreBlock(StoneType stoneType, boolean small) {
        return this.getDefaultState().withProperty(this.STONE_TYPE, stoneType).withProperty(SMALL, small);
    }
}
