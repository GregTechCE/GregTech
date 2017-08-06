package gregtech.common.blocks;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.material.OrePrefixes;
import gregtech.api.material.StoneTypes;
import gregtech.api.material.Materials;
import gregtech.api.material.type.DustMaterial;
import gregtech.api.material.type.Material;
import gregtech.api.items.GenericBlock;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.ItemGeneratedOres;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockGeneratedOres extends GenericBlock {

    public static final PropertyEnum<StoneTypes> STONE_TYPE = PropertyEnum.create("stone_type", StoneTypes.class);
    public static final PropertyBool SMALL = PropertyBool.create("small");

    public static final Map<Material, BlockGeneratedOres> GENERATED_ORES = new HashMap<>();

    public static void registerOreBlocks() {
        System.out.println("REGISTERING ORE BLOCKS...");

        int oreAmount = 0;
        for(Material material : Material.MATERIAL_REGISTRY) {
            if(material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
                new BlockGeneratedOres(material);
                oreAmount++;
            }
        }

        System.out.println("ORE BLOCKS REGISTERED: " + oreAmount);
    }

    public static boolean setOreBlock(World world, BlockPos pos, Material material, boolean small) {
        IBlockState prevState = world.getBlockState(pos);
        Block block = prevState.getBlock();
        int metadata = block.getMetaFromState(prevState);

        int variantId = 0;

        if(block == Blocks.STONE) {
            variantId = StoneTypes.STONE.id;
        } else if(block == GregTech_API.sBlockGranites) {
            if(metadata == 0)
                variantId = StoneTypes.BLACK_GRANITE.id;
            else if(metadata == 8) variantId = StoneTypes.RED_GRANITE.id;
        } else if(block == GregTech_API.sBlockStones) {
            if(metadata == 0)
                variantId = StoneTypes.MARBLE.id;
            else if(metadata == 8) variantId = StoneTypes.BASALT.id;
        } else if(block == Blocks.NETHERRACK) {
            variantId = StoneTypes.NETHERRACK.id;
        } else if(block == Blocks.END_STONE) {
            variantId = StoneTypes.ENDSTONE.id;
        } else {
            return false;
        }

        IBlockState blockState = GENERATED_ORES.get(material)
                .getDefaultState()
                .withProperty(STONE_TYPE, StoneTypes.TYPES[variantId])
                .withProperty(SMALL, small);

        return world.setBlockState(pos, blockState);
    }

    public final Material material;

    protected BlockGeneratedOres(Material material) {
        super("blockores." + material, ItemGeneratedOres.class, net.minecraft.block.material.Material.ROCK, false);

        this.material = material;

        GENERATED_ORES.put(material, this);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(STONE_TYPE, StoneTypes.STONE)
                .withProperty(SMALL, false));

        for (int i = 0; i < StoneTypes.TYPES.length; i++) {
            for (boolean small : new boolean[]{true, false}) {

                IBlockState blockState = this.getDefaultState()
                        .withProperty(STONE_TYPE, StoneTypes.TYPES[i])
                        .withProperty(SMALL, small);

                ItemStack itemStack = createStackedBlock(blockState);
//                GT_OreDictUnificator.registerOre(StoneTypes.mTypes[i].processingPrefix.get(this.getMaterials()[j]), itemStack);
                GT_LanguageManager.addStringLocalization(itemStack.getUnlocalizedName() + ".name", (small ? "Small " : "") + getLocalizedName(material));
            }
        }

        setSoundType(SoundType.STONE);
        setCreativeTab(GregTech_API.TAB_GREGTECH_ORES);
    }

    public Material getMaterialSafe(IBlockState state) {
        Block block = state.getBlock();
        if (block instanceof BlockGeneratedOres) {
            return ((BlockGeneratedOres) block).material;
        }

        return Materials.Air;
    }

    public StoneTypes getStoneTypeSafe(IBlockState state) {
        return state.getValue(STONE_TYPE);
    }

    public Material getMaterialSafe(ItemStack stack) {
        IBlockState state = getStateFromMeta(stack.getMetadata());
        return getMaterialSafe(state);
    }

    public StoneTypes getStoneTypeSafe(ItemStack stack) {
        IBlockState state = getStateFromMeta(stack.getMetadata());
        return getStoneTypeSafe(state);
    }

    public IBlockState overrideStoneType(IBlockState state, StoneTypes stoneType) {
        return state.withProperty(STONE_TYPE, stoneType);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STONE_TYPE, SMALL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(STONE_TYPE, StoneTypes.TYPES[meta & 0b0111])
                .withProperty(SMALL, (meta & 0b1000) != 0);
    }

    /**
     * 0b0111 - STONE_TYPE mask
     * 0b1000 - SMALL mask
     *
     * One more stone type can be added
     *
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        meta |= state.getValue(STONE_TYPE).id;
        if (state.getValue(SMALL)) {
            meta |= 1 << 3;
        }
        return meta;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (boolean small : new boolean[]{true, false}) {
            for (StoneTypes type : StoneTypes.TYPES) {
                IBlockState blockState = this.getDefaultState()
                        .withProperty(STONE_TYPE, type)
                        .withProperty(SMALL, small);
                list.add(createStackedBlock(blockState));
            }
        }
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return GT_Mod.gregtechproxy.mHarvestLevel[getMaterialSafe(state).mMetaItemSubID];
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return 1.25F * (getHarvestLevel(blockState) + 1);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return 1.75F * (getHarvestLevel(world.getBlockState(pos)) + 1);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false; //never allow silk touch
    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public TextureAtlasSprite getParticleSprite(IBlockAccess worldObj, BlockPos pos, EnumFacing side) {
//        return getStoneTypeSafe(worldObj.getBlockState(pos)).mIconContainer.getIcon();
//    }
//
//    @Override
//    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
//        List<ItemStack> dropList = new ArrayList<>();
//        if (!small) {
//            dropList.add(createStackedBlock(state));
//            return dropList;
//        }
//        Materials material = getMaterialSafe(state);
//        Materials baseMaterial = getStoneTypeSafe(state).stoneMaterial;
//        if (material != null) {
//            Random random = new Random(pos.hashCode());
//            List<ItemStack> selector = new ArrayList<>();
//
//            ItemStack stack = GT_OreDictUnificator.get(OrePrefixes.gemExquisite, material, GT_OreDictUnificator.get(OrePrefixes.gem, material, 1L), 1L);
//            if (stack != null) {
//                for (int i = 0; i < 1; i++) {
//                    selector.add(stack);
//                }
//            }
//            stack = GT_OreDictUnificator.get(OrePrefixes.gemFlawless, material, GT_OreDictUnificator.get(OrePrefixes.gem, material, 1L), 1L);
//            if (stack != null) {
//                for (int i = 0; i < 2; i++) {
//                    selector.add(stack);
//                }
//            }
//            stack = GT_OreDictUnificator.get(OrePrefixes.gem, material, 1L);
//            if (stack != null) {
//                for (int i = 0; i < 12; i++) {
//                    selector.add(stack);
//                }
//            }
//            stack = GT_OreDictUnificator.get(OrePrefixes.gemFlawed, material, GT_OreDictUnificator.get(OrePrefixes.crushed, material, 1L), 1L);
//            if (stack != null) {
//                for (int i = 0; i < 5; i++) {
//                    selector.add(stack);
//                }
//            }
//            stack = GT_OreDictUnificator.get(OrePrefixes.crushed, material, 1L);
//            if (stack != null) {
//                for (int i = 0; i < 10; i++) {
//                    selector.add(stack);
//                }
//            }
//            stack = GT_OreDictUnificator.get(OrePrefixes.gemChipped, material, GT_OreDictUnificator.get(OrePrefixes.dustImpure, material, 1L), 1L);
//            if (stack != null) {
//                for (int i = 0; i < 5; i++) {
//                    selector.add(stack);
//                }
//            }
//            stack = GT_OreDictUnificator.get(OrePrefixes.dustImpure, material, 1L);
//            if (stack != null) {
//                for (int i = 0; i < 10; i++) {
//                    selector.add(stack);
//                }
//            }
//
//            stack = GT_OreDictUnificator.get(random.nextInt(3) > 0 ? OrePrefixes.dustImpure : OrePrefixes.dust, baseMaterial, 1L);
//            if(stack != null && random.nextInt(3 + fortune) > 1) {
//                dropList.add(stack);
//            }
//
//            if(selector.size() > 0) {
//                for (int i = 0, j = Math.max(1, material.mOreMultiplier + (fortune > 0 ? random.nextInt(1 + fortune * material.mOreMultiplier) : 0) / 2); i < j; i++) {
//                    dropList.add(GT_Utility.copyAmount(1L, selector.get(random.nextInt(selector.size()))));
//                }
//            }
//        }
//        return dropList;
//    }

    public String getLocalizedName(Material material) {
        switch (material.defaultLocalName) {
            case "Vermiculite":
            case "Bentonite":
            case "Kaolinite":
            case "Talc":
            case "BasalticMineralSand":
            case "GraniticMineralSand":
            case "GlauconiteSand":
            case "CassiteriteSand":
            case "GarnetSand":
            case "QuartzSand":
            case "Pitchblende":
            case "FullersEarth":
                return material.defaultLocalName;

            default:
                return material.defaultLocalName + OrePrefixes.ore.mLocalizedMaterialPost;
        }
    }

//    @Override
//    public EnumBlockRenderType getRenderType(IBlockState state) {
//        return RenderGeneratedOres.INSTANCE.renderType;
//    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}