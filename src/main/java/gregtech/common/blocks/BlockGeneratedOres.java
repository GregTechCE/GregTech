package gregtech.common.blocks;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.StoneTypes;
import gregtech.api.items.GenericBlock;
import gregtech.api.util.GT_LanguageManager;
//import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.itemblocks.ItemGeneratedOres;
import gregtech.common.blocks.properties.PropertyMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class BlockGeneratedOres extends GenericBlock {

    // We have only 1 bit to store material
    public static final int MATERIALS_PER_BLOCK = 2;

    public static final PropertyEnum<StoneTypes> STONE_TYPE = PropertyEnum.create("stone_type", StoneTypes.class);
    private PropertyMaterial MATERIAL;

    private static int nextId;

    public static BlockGeneratedOres[] generatedOres;
    public static BlockGeneratedOres[] generatedOresSmall;

    public static void registerOreBlocks() {
        System.out.println("REGISTERING ORE BLOCKS...");

        generatedOres = new BlockGeneratedOres[GregTech_API.sGeneratedMaterials.length];
        generatedOresSmall = new BlockGeneratedOres[GregTech_API.sGeneratedMaterials.length];

        Materials[] lastMats = new Materials[MATERIALS_PER_BLOCK];
        int length = 0;
        for(Materials material : GregTech_API.sGeneratedMaterials) {
            if(material != null && (material.mTypes & 0x08) != 0) {
                lastMats[length++] = material;
                if(length == MATERIALS_PER_BLOCK) {
                    createOreBlock(lastMats);
                    lastMats = new Materials[MATERIALS_PER_BLOCK];
                    length = 0;
                }
            }
        }
        if(length != 0) {
            Materials[] materials = Arrays.stream(lastMats).filter(Objects::nonNull).toArray(Materials[]::new);
            createOreBlock(materials);
        }
        System.out.println("ORE BLOCKS REGISTERED: " + nextId);
    }

    private static void createOreBlock(Materials[] materials){
        new BlockGeneratedOres(false) {
            @Override
            public Materials[] getMaterials() {
                return materials;
            }
        };
        new BlockGeneratedOres(true) {
            @Override
            public Materials[] getMaterials() {
                return materials;
            }
        };
    }

    public static boolean setOreBlock(World world, BlockPos pos, int materialSubId, boolean small) {
        IBlockState prevState = world.getBlockState(pos);
        Block block = prevState.getBlock();
        int metadata = block.getMetaFromState(prevState);

        int variantId = 0;

        if(block == Blocks.STONE) {
            variantId = StoneTypes.STONE.mId;
        } else if(block == GregTech_API.sBlockGranites) {
            if(metadata == 0)
                variantId = StoneTypes.BLACK_GRANITE.mId;
            else if(metadata == 8) variantId = StoneTypes.RED_GRANITE.mId;
        } else if(block == GregTech_API.sBlockStones) {
            if(metadata == 0)
                variantId = StoneTypes.MARBLE.mId;
            else if(metadata == 8) variantId = StoneTypes.BASALT.mId;
        } else if(block == Blocks.NETHERRACK) {
            variantId = StoneTypes.NETHERRACK.mId;
        } else if(block == Blocks.END_STONE) {
            variantId = StoneTypes.ENDSTONE.mId;
        } else {
            return false;
        }

        BlockGeneratedOres oreBlock = (small ? generatedOresSmall : generatedOres)[materialSubId];

        IBlockState blockState = oreBlock.getDefaultState()
                .withProperty(STONE_TYPE, StoneTypes.mTypes[variantId])
                .withProperty(oreBlock.getMaterialProperty(), GregTech_API.sGeneratedMaterials[materialSubId]);

        return world.setBlockState(pos, blockState);
    }

    public final boolean small;
    public int id;

    protected BlockGeneratedOres(boolean small) {
        super("blockores." + nextId, ItemGeneratedOres.class, Material.ROCK, false);

        this.id = nextId;
        this.small = small;

        if (getMaterials().length > MATERIALS_PER_BLOCK)
            throw new IllegalArgumentException("Materials.length must not be > MATERIALS_PER_BLOCK");

        for (int i = 0; i < getMaterials().length; i++) {
            (small ? generatedOresSmall : generatedOres)[getMaterials()[i].mMetaItemSubID] = this;
        }

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(STONE_TYPE, StoneTypes.STONE)
                .withProperty(getMaterialProperty(), getMaterialProperty().getFirstType()));

        for (int i = 0; i < StoneTypes.mTypes.length; i++) {
            for (int j = 0; j < getMaterials().length; j++) {
                Materials material = getMaterials()[j];

                IBlockState blockState = this.getDefaultState()
                        .withProperty(STONE_TYPE, StoneTypes.mTypes[i])
                        .withProperty(getMaterialProperty(), material);

                ItemStack itemStack = createStackedBlock(blockState);
//                GT_OreDictUnificator.registerOre(StoneTypes.mTypes[i].processingPrefix.get(this.getMaterials()[j]), itemStack);
                GT_LanguageManager.addStringLocalization(itemStack.getUnlocalizedName() + ".name", (small ? "Small " : "") + getLocalizedName(material));
            }
        }

        setSoundType(SoundType.STONE);
        setCreativeTab(GregTech_API.TAB_GREGTECH_ORES);
        nextId++;
    }

    public abstract Materials[] getMaterials();

    public PropertyMaterial getMaterialProperty() {
        if (this.MATERIAL == null) {
            this.MATERIAL = PropertyMaterial.create("material", getMaterials());
        }
        return this.MATERIAL;
    }

    public Materials getMaterialSafe(IBlockState state) {
        Materials material = state.getValue(getMaterialProperty());
        for (int i = 0; i < getMaterials().length; i++) {
            if (material == getMaterials()[i]) {
                return material;
            }
        }
        return Materials.Air;
    }

    public StoneTypes getStoneTypeSafe(IBlockState state) {
        return state.getValue(STONE_TYPE);
    }

    public Materials getMaterialSafe(ItemStack stack) {
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
        return new BlockStateContainer(this, STONE_TYPE, getMaterialProperty());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(STONE_TYPE, StoneTypes.mTypes[meta & 0b0111])
                .withProperty(getMaterialProperty(), getMaterials()[(meta & 0b1000) >> 3]);
    }

    /**
     * 0b0111 - STONE_TYPE mask
     * 0b1000 - MATERIAL mask
     *
     * One more stone type can be added
     *
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        meta |= state.getValue(STONE_TYPE).mId;

        Materials material = state.getValue(getMaterialProperty());
        for (int i = 0; i < getMaterials().length; i++) {
            if (material == getMaterials()[i]){
                meta |= i << 3;
            }
        }
        return meta;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (Materials material : getMaterials()) {
            for (StoneTypes type : StoneTypes.mTypes) {
                IBlockState blockState = this.getDefaultState()
                        .withProperty(getMaterialProperty(), material)
                        .withProperty(STONE_TYPE, type);
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

    public String getLocalizedName(Materials material) {
        switch (material.mName) {

            case "InfusedAir":
            case "InfusedDull":
            case "InfusedEarth":
            case "InfusedEntropy":
            case "InfusedFire":
            case "InfusedOrder":
            case "InfusedVis":
            case "InfusedWater":
                return material.mDefaultLocalName + " Infused Stone";

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
                return material.mDefaultLocalName;

            default:
                return material.mDefaultLocalName + OrePrefixes.ore.mLocalizedMaterialPost;
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