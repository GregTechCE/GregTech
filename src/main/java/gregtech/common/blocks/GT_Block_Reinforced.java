package gregtech.common.blocks;

import com.google.common.collect.Lists;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.blocks.itemblocks.GT_Item_Storage;
import gregtech.common.blocks.materials.GT_Material_Reinforced;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class GT_Block_Reinforced extends GT_Generic_Block {

    public static final PropertyEnum<EnumReinforcedVariant> VARIANT = PropertyEnum.<EnumReinforcedVariant>create("variant", EnumReinforcedVariant.class);

    private TextureAtlasSprite COAL_BLOCK_ICON_DATA;

    public GT_Block_Reinforced(String aName) {
        super(aName, GT_Item_Storage.class, GT_Material_Reinforced.INSTANCE);
        setSoundType(SoundType.STONE);
        setCreativeTab(GregTech_API.TAB_GREGTECH);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(VARIANT, EnumReinforcedVariant.BPLATE_REINF));

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Bronzeplate Reinforced Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Iridium-Tungstensteel Reinforced Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Plascrete Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Tungstensteel Reinforced Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Brittle Charcoal");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Powderbarrel");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Solid Super Fuel");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Magic Solid Super Fuel");
        ItemList.Block_BronzePlate.set(new ItemStack(this, 1, 0));
        ItemList.Block_IridiumTungstensteel.set(new ItemStack(this, 1, 1));
        ItemList.Block_Plascrete.set(new ItemStack(this, 1, 2));
        ItemList.Block_TungstenSteelReinforced.set(new ItemStack(this, 1, 3));
        ItemList.Block_BrittleCharcoal.set(new ItemStack(this, 1, 4));
        ItemList.Block_Powderbarrel.set(new ItemStack(this, 1, 5));
        ItemList.Block_SSFUEL.set(new ItemStack(this, 1, 6));
        ItemList.Block_MSSFUEL.set(new ItemStack(this, 1, 7));
        GT_ModHandler.addCraftingRecipe(ItemList.Block_BronzePlate.get(1L),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hP ", "PBP", " P ", 'P', OrePrefixes.plate.get(Materials.Bronze), 'B', OrePrefixes.stone.get(Materials.GraniteBlack)});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_BronzePlate.get(1L),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hP ", "PBP", " P ", 'P', OrePrefixes.plate.get(Materials.Bronze), 'B', OrePrefixes.stone.get(Materials.GraniteRed)});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_IridiumTungstensteel.get(1L),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hBP", 'P', OrePrefixes.plate.get(Materials.Iridium), 'B', ItemList.Block_TungstenSteelReinforced.get(1L)});
        GT_OreDictUnificator.setItemData(ItemList.Block_IridiumTungstensteel.get(1), new ItemData(new MaterialStack(Materials.Iridium, OrePrefixes.plate.mMaterialAmount), new MaterialStack(Materials.TungstenSteel, 2*OrePrefixes.plate.mMaterialAmount),new MaterialStack(Materials.Concrete, OrePrefixes.dust.mMaterialAmount)));
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.COAL, 1, 1), new Object[]{ItemList.Block_BrittleCharcoal.get(1)});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_Powderbarrel.get(1L),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WSW","GGG","WGW", 'W', OrePrefixes.plank.get(Materials.Wood), 'G', new ItemStack(Items.GUNPOWDER,1),'S',new ItemStack(Items.STRING ,1)});
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(VARIANT, EnumReinforcedVariant.byMetadata(meta & 15));
    }

    /**
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        meta |= state.getValue(VARIANT).getMetadata();
        return meta;
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        EnumReinforcedVariant variant = state.getValue(VARIANT);
        switch (variant) {
            case BRITTLE_CHARCOAL:
            case POWDERBARREL:
            case SSFUEL:
            case MSSFUEL:
                return "axe";
        }
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState blockState) {
        EnumReinforcedVariant variant = blockState.getValue(VARIANT);
        switch (variant) {
            case BRITTLE_CHARCOAL:
            case POWDERBARREL:
            case SSFUEL:
            case MSSFUEL:
                return 1;
            case PLASCRETE:
                return 2;
        }
        return 4;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(EnumFacing aSide, int aMeta) {
        if ((aMeta >= 0) && (aMeta < 16)) {
            switch (aMeta) {
                case 0:
                    return Textures.BlockIcons.BLOCK_BRONZEPREIN.getIcon();
                case 1:
                    return Textures.BlockIcons.BLOCK_IRREIN.getIcon();
                case 2:
                    return Textures.BlockIcons.BLOCK_PLASCRETE.getIcon();
                case 3:
                    return Textures.BlockIcons.BLOCK_TSREIN.getIcon();
                case 4:
                    return COAL_BLOCK_ICON_DATA;
                case 5:
                	return Textures.BlockIcons.COVER_WOOD_PLATE.getIcon();
                case 6:
                case 7:
                	return COAL_BLOCK_ICON_DATA;
            }
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
    }

    @Override
    public void registerIcons(TextureMap map) {
        COAL_BLOCK_ICON_DATA = map.registerSprite(new ResourceLocation("minecraft:blocks/coal_block"));
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        EnumReinforcedVariant variant = blockState.getValue(VARIANT);

        switch (variant) {
            case BPLATE_REINF:
                return 60.0F;
            case ITS_REINF:
                return 200.0F;
            case PLASCRETE:
                return 80.0F;
            case TS_REINF:
                return 100.0F;
            case BRITTLE_CHARCOAL:
                return 0.5F;
            case POWDERBARREL:
            case SSFUEL:
            case MSSFUEL:
                return 2.5F;
        }
        return 5F;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        EnumReinforcedVariant variant = world.getBlockState(pos).getValue(VARIANT);

        switch (variant) {
            case BPLATE_REINF:
                return 150.0F;
            case ITS_REINF:
                return 600.0F;
            case PLASCRETE:
                return 350.0F;
            case TS_REINF:
                return 400.0F;
            case BRITTLE_CHARCOAL:
            case SSFUEL:
            case MSSFUEL:
                return 8.0F;
            case POWDERBARREL:
                return 1.0F;
        }
        return 10F;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList(new ItemStack(this, 1, getMetaFromState(state)));
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getValue(VARIANT) == EnumReinforcedVariant.BRITTLE_CHARCOAL) {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.COAL, worldIn.rand.nextInt(2) + 1, 1));
        } else  {
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (!world.isRemote && state.getValue(VARIANT) == EnumReinforcedVariant.POWDERBARREL) {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, player);
            world.spawnEntityInWorld(entitytntprimed);
            world.playSound((EntityPlayer)null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockToAir(pos);
            return false;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if (worldIn.isBlockPowered(pos) && state.getValue(VARIANT) == EnumReinforcedVariant.POWDERBARREL) {
            this.removedByPlayer(state, worldIn, pos, null, false);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
        if (worldIn.isBlockPowered(pos) && state.getValue(VARIANT) == EnumReinforcedVariant.POWDERBARREL) {
            this.removedByPlayer(state, worldIn, pos, null, false);
        }
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        if (!world.isRemote && world.getBlockState(pos).getValue(VARIANT) == EnumReinforcedVariant.POWDERBARREL) {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosion.getExplosivePlacedBy());
            entitytntprimed.setFuse(world.rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8);
            world.spawnEntityInWorld(entitytntprimed);
        }
        super.onBlockExploded(world, pos, explosion);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (heldItem != null && (heldItem.getItem() == Items.FLINT_AND_STEEL || heldItem.getItem() == Items.FIRE_CHARGE) && state.getValue(VARIANT) == EnumReinforcedVariant.POWDERBARREL) {
            removedByPlayer(state, worldIn, pos, playerIn, true);

            if (heldItem.getItem() == Items.FLINT_AND_STEEL)
            {
                heldItem.damageItem(1, playerIn);
            }
            else if (!playerIn.capabilities.isCreativeMode)
            {
                --heldItem.stackSize;
            }
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List<ItemStack> aList) {
        for (int i = 0; i < 16; i++) {
            ItemStack aStack = new ItemStack(aItem, 1, i);
            if (!aStack.getDisplayName().contains(".name")) aList.add(aStack);
        }
    }

    public static enum EnumReinforcedVariant implements IStringSerializable {
        BPLATE_REINF("bplate_reinf"),
        ITS_REINF("its_reinf"),
        PLASCRETE("plascrete"),
        TS_REINF("ts_reinf"),
        BRITTLE_CHARCOAL("brittle_charcoal"),
        POWDERBARREL("powderbarrel"),
        SSFUEL("ssfuel"),
        MSSFUEL("mssfuel");

        private final int meta = ordinal();
        private final String name;

        EnumReinforcedVariant(String name) {
            this.name = name;
        }

        public int getMetadata() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static EnumReinforcedVariant byMetadata(int meta) {
            if (meta < 0 || meta >= values().length) {
                meta = 0;
            }

            return values()[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}