package gregtech.common.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.StoneType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class BlockOre extends Block {

    public static final PropertyBool SMALL = PropertyBool.create("small");
    public static final PropertyEnum<StoneType> STONE_TYPE = PropertyEnum.create("stone_type", StoneType.class);

    public final DustMaterial material;

    public BlockOre(DustMaterial material) {
        super(net.minecraft.block.material.Material.ROCK);
        setSoundType(SoundType.STONE);
        setHardness(3.0f);
        setResistance(5.0f);
        setCreativeTab(GregTechAPI.TAB_GREGTECH_MATERIALS);
        this.material = material;
    }

    public void registerBlock(String blockName) {
        setUnlocalizedName("unnamed");
        setRegistryName(blockName);
        GameRegistry.register(this);
        OreItemBlock itemBlock = new OreItemBlock(this);
        itemBlock.setRegistryName(blockName);
        GameRegistry.register(itemBlock);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        StoneType stoneType = state.getValue(STONE_TYPE);
        if(stoneType == StoneType.SANDSTONE) {
            return "shovel";
        }
        return "pickaxe";
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STONE_TYPE, SMALL);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STONE_TYPE, StoneType.values()[meta % 8]).withProperty(SMALL, meta / 8 > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STONE_TYPE).ordinal() + (state.getValue(SMALL) ? 8 : 0);
    }

    @SuppressWarnings("deprecation")
    public ItemStack getItem(IBlockState blockState) {
        return new ItemStack(this, 1, getMetaFromState(blockState));
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for(IBlockState blockState : blockState.getValidStates()) {
            list.add(getItem(blockState));
        }
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
