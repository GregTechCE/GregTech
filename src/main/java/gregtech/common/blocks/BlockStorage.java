package gregtech.common.blocks;

import com.google.common.collect.Lists;
import gregtech.api.GregTech_API;
import gregtech.api.material.OrePrefixes;
import gregtech.api.material.type.Material;
import gregtech.api.items.GenericBlock;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.ItemStorage;
import gregtech.common.blocks.properties.PropertyMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class BlockStorage extends GenericBlock {

    private PropertyMaterial MATERIAL;

    public static Block createStorageBlock(String name, Material[] materials, OrePrefixes prefix){
        return new BlockStorage(name, prefix){
            @Override
            public Material[] getMaterials() {
                return materials;
            }
        };
    }

    public OrePrefixes prefix;

    private BlockStorage(String name, OrePrefixes prefix) {
        super(name, ItemStorage.class, net.minecraft.block.material.Material.IRON);
        this.prefix = prefix;

        for (int i = 0; i < getMaterials().length; i++) {
            if (Material.MATERIAL_REGISTRY.containsKey(getMaterials()[i].toString())) {
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + i + ".name", "Block of " + getMaterials()[i].defaultLocalName);
//                GT_OreDictUnificator.registerOre(prefix, getMaterials()[i], new ItemStack(this, 1, i));
            }
        }
        setHardness(5.0F); //Blocks.IRON_BLOCK
        setResistance(10.0F); //Blocks.IRON_BLOCK
        setSoundType(SoundType.METAL);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
    }

    public abstract Material[] getMaterials();

    public PropertyMaterial getMaterialProperty() {
        if (this.MATERIAL == null) {
            this.MATERIAL = PropertyMaterial.create("material", getMaterials());
        }
        return this.MATERIAL;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, getMaterialProperty());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(getMaterialProperty(), getMaterials()[meta & 0b1111]);
    }

    /**
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        Material material = state.getValue(getMaterialProperty());

        int meta = 0;
        for (int i = 0; i < getMaterials().length; i++) {
            if (material == getMaterials()[i]) {
                meta |= i;
            }
        }
        return meta;
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 1;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList(new ItemStack(this, 1, getMetaFromState(state)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < 16; i++) {
            if (!(new ItemStack(item, 1, i).getDisplayName().contains(".name"))) list.add(new ItemStack(item, 1, i));
        }
    }
}
