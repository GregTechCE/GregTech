package gregtech.common.blocks;

import com.google.common.collect.Lists;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.properties.PropertyMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gregtech.api.enums.GT_Values.B;

public abstract class GT_Block_Stones_Abstract extends GT_Generic_Block implements IOreRecipeRegistrator {

    // We have only 1 bit to store material
    public static final int MATERIALS_PER_BLOCK = 2;

    public static final PropertyEnum<EnumStoneVariant> STONE_VARIANT = PropertyEnum.create("stone_variant", EnumStoneVariant.class);
    private PropertyMaterial MATERIAL;

    protected Materials[] mMaterials;

    /**
     *
     * @param name
     * @param itemClass
     */
    public GT_Block_Stones_Abstract(String name, Class<? extends ItemBlock> itemClass) {
        super(name, itemClass, Material.ROCK);

        if (getMaterials().length > MATERIALS_PER_BLOCK)
            throw new IllegalArgumentException("Materials.length must not be > MATERIALS_PER_BLOCK");

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(STONE_VARIANT, EnumStoneVariant.NORMAL)
                .withProperty(getMaterialProperty(), getMaterialProperty().getFirstType()));

        OrePrefixes.crafting.add(this);
        setSoundType(SoundType.STONE);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
        setHardness(1.5F);

        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 7));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 2), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 3), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 4), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 5), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 6), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 7), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 8), new ItemStack(this, 1, 15));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 9), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 10), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 11), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 12), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 13), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 14), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 15), new ItemStack(this, 1, 8));
        GT_Values.RA.addAssemblerRecipe(new ItemStack(this, 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new ItemStack(this, 1, 3), 50, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(this, 1, 8), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new ItemStack(this, 1, 11), 50, 4);
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(this, 1, 7)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 14), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(this, 1, 15)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 4), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(this, 1, 3)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 12), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(this, 1, 11)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(this, 1, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 9), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(this, 1, 8)});
        GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 3), new ItemStack(this, 1, 4), 16, 10);
        GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 11), new ItemStack(this, 1, 12), 16, 10);
        GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), 16, 10);
        GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 8), new ItemStack(this, 1, 9), 16, 10);
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XX", "XX", 'X', new ItemStack(this, 4, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 11), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XX", "XX", 'X', new ItemStack(this, 4, 8)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XX", "XX", 'X', new ItemStack(this, 4, 7)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 11), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XX", "XX", 'X', new ItemStack(this, 4, 15)});
    }

    public void registerOre(OrePrefixes prefix, Materials material, String oreDictName, String modName, ItemStack stack) {
        if (oreDictName.equals(OreDictNames.craftingLensWhite.toString())) {
            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(this, 1, 7), GT_Utility.copyAmount(0L, stack), new ItemStack(this, 1, 6), 50, 16);
            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(this, 1, 15), GT_Utility.copyAmount(0L, stack), new ItemStack(this, 1, 14), 50, 16);
        }
    }

    public abstract Materials[] getMaterials();

    public PropertyMaterial getMaterialProperty() {
        if (this.MATERIAL == null) {
            this.MATERIAL = PropertyMaterial.create("material", getMaterials());
        }
        return this.MATERIAL;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STONE_VARIANT, getMaterialProperty());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(STONE_VARIANT, EnumStoneVariant.byMetadata(meta & 0b0111))
                .withProperty(getMaterialProperty(), getMaterials()[(meta & 0b1000) >> 3]);
    }

    /**
     * 0b0111 - STONE_VARIANT mask
     * 0b1000 - MATERIAL mask
     *
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        meta |= state.getValue(STONE_VARIANT).getMetadata();

        Materials material = state.getValue(getMaterialProperty());
        for (int i = 0; i < getMaterials().length; i++) {
            if (material == getMaterials()[i]){
                meta |= i << 3;
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
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(EnumFacing side, int meta) {
        if ((meta >= 0) && (meta < 16)) {
            return gregtech.api.enums.Textures.BlockIcons.GRANITES[meta].getIcon();
        }
        return null;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return state.getValue(STONE_VARIANT).getMetadata() < 3;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        EnumStoneVariant stoneVariant = state.getValue(STONE_VARIANT);
        if (stoneVariant == EnumStoneVariant.NORMAL) {
            return Lists.newArrayList(createStackedBlock(state.cycleProperty(STONE_VARIANT)));
        }
        return Lists.newArrayList(createStackedBlock(state));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < 16; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public enum EnumStoneVariant implements IStringSerializable {
        NORMAL("normal"),
        COBBLESTONE("cobblestone"),
        MOSSY_COBBLESTONE("mossy_cobblestone"),
        BRICKS("bricks"),
        CRACKED_BRICKS("cracked_bricks"),
        MOSSY_BRICKS("mossy_bricks"),
        CHISELED("chiseled"),
        SMOOTH("smooth");

        private static final EnumStoneVariant[] META_LOOKUP = new EnumStoneVariant[values().length];

        private final int meta = ordinal();
        private final String name;

        EnumStoneVariant(String name) {
            this.name = name;
        }

        public int getMetadata() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static EnumStoneVariant byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }

        static {
            for (EnumStoneVariant stoneVariant : values()) {
                META_LOOKUP[stoneVariant.getMetadata()] = stoneVariant;
            }
        }
    }
}