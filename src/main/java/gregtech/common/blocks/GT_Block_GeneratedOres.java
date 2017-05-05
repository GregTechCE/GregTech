package gregtech.common.blocks;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.StoneTypes;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.itemblocks.GT_Item_GeneratedOres;
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
import java.util.Random;

import static gregtech.api.enums.GT_Values.B;

public class GT_Block_GeneratedOres extends GT_Generic_Block {

    // We have only 1 bit to store material
    public static final int MATERIALS_PER_BLOCK = 2;

    public static final PropertyEnum<StoneTypes> STONE_TYPE = PropertyEnum.create("stone_type", StoneTypes.class);
    public final PropertyMaterial MATERIAL;

    private static int sNextId;

    public static GT_Block_GeneratedOres[] sGeneratedBlocks;
    public static GT_Block_GeneratedOres[] sGeneratedSmallBlocks;

    public static void registerOreBlocks() {
        System.out.println("REGISTERING ORE BLOCKS...");

        sGeneratedBlocks = new GT_Block_GeneratedOres[GregTech_API.sGeneratedMaterials.length];
        sGeneratedSmallBlocks = new GT_Block_GeneratedOres[GregTech_API.sGeneratedMaterials.length];

        Materials[] lastMats = new Materials[MATERIALS_PER_BLOCK];
        int length = 0;
        for(Materials aMaterial : GregTech_API.sGeneratedMaterials) {
            if(aMaterial != null && (aMaterial.mTypes & 0x08) != 0) {
                lastMats[length++] = aMaterial;
                if(length == MATERIALS_PER_BLOCK) {
                    new GT_Block_GeneratedOres(lastMats, false);
                    new GT_Block_GeneratedOres(lastMats, true);
                    lastMats = new Materials[MATERIALS_PER_BLOCK];
                    length = 0;
                }
            }
        }
        if(length != 0) {
            Arrays.stream(lastMats).filter(material -> material != null).toArray(Materials[]::new);
            new GT_Block_GeneratedOres(lastMats, false);
            new GT_Block_GeneratedOres(lastMats, true);
        }
        System.out.println("ORE BLOCKS REGISTERED: " + sNextId);
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

        GT_Block_GeneratedOres oreBlock = (small ? sGeneratedSmallBlocks : sGeneratedBlocks)[materialSubId];

        IBlockState blockState = oreBlock.getDefaultState()
                .withProperty(STONE_TYPE, StoneTypes.mTypes[variantId])
                .withProperty(oreBlock.MATERIAL, GregTech_API.sGeneratedMaterials[materialSubId]);

        return world.setBlockState(pos, blockState);
    }

    public final Materials[] mMaterials;
    public final boolean mSmall;
    public int mId;

    /**
     * @param materials Materials for which ore will be created. Works for materials.length <= MATERIALS_PER_BLOCK
     * @param flag
     */
    protected GT_Block_GeneratedOres(Materials[] materials, boolean small) {
        super("blockores." + sNextId, GT_Item_GeneratedOres.class, Material.ROCK);

        this.mId = sNextId;
        this.mMaterials = materials;
        this.mSmall = small;

        if (materials.length > MATERIALS_PER_BLOCK)
            throw new IllegalArgumentException("Materials.length must not be > MATERIALS_PER_BLOCK");

        for (int i = 0; i < MATERIALS_PER_BLOCK; i++) {
            (small ? sGeneratedSmallBlocks : sGeneratedBlocks)[materials[i].mMetaItemSubID] = this;
        }

        MATERIAL = PropertyMaterial.create("material", materials);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(STONE_TYPE, StoneTypes.STONE)
                .withProperty(MATERIAL, MATERIAL.getFirstType()));

        for (int i = 0, j = 0; i < StoneTypes.mTypes.length && j < materials.length; i++, j++) {
            Materials aMaterial = materials[j];

            IBlockState blockState = this.getDefaultState()
                    .withProperty(STONE_TYPE, StoneTypes.mTypes[0])
                    .withProperty(MATERIAL, aMaterial);

            ItemStack itemStack = createStackedBlock(blockState);
            GT_OreDictUnificator.registerOre(StoneTypes.mTypes[i].processingPrefix.get(this.mMaterials[j]), itemStack);
            GT_LanguageManager.addStringLocalization(itemStack.getUnlocalizedName() + ".name", (small ? "Small " : "") + getLocalizedName(aMaterial));
        }

        setSoundType(SoundType.STONE);
        setCreativeTab(GregTech_API.TAB_GREGTECH_ORES);
        sNextId++;
    }

    public Materials getMaterialSafe(IBlockState state) {
        Materials material = state.getValue(MATERIAL);
        for (int i = 0; i < mMaterials.length; i++) {
            if (material == mMaterials[i]) {
                return material;
            }
        }
        return Materials.Air;
    }

    public StoneTypes getStoneTypeSafe(IBlockState state) {
        return (StoneTypes) state.getValue(STONE_TYPE);
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
        return new BlockStateContainer(this, STONE_TYPE, MATERIAL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(STONE_TYPE, StoneTypes.mTypes[(meta & 15) >> 1])
                .withProperty(MATERIAL, mMaterials[meta & B[0]]);
    }

    /**
     * First bit for MATERIAL
     * rest - STONE_TYPE
     *
     * One more stone type can be added
     *
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        Materials material = state.getValue(MATERIAL);
        for (int i = 0; i < mMaterials.length; i++) {
            if (material.equals(mMaterials[i])){
                meta |= i;
            }
        }
        meta |= state.getValue(STONE_TYPE).mId << 1;
        return meta;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for(int i = 0; i < mMaterials.length; i++) {
            for(int j = 0; j < StoneTypes.mTypes.length; j++) {
                list.add(new ItemStack(this, 1, i * 7 + j));
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

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleSprite(IBlockAccess worldObj, BlockPos aPos, EnumFacing side) {
        return getStoneTypeSafe(worldObj.getBlockState(aPos)).mIconContainer.getIcon();
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> rList = new ArrayList<>();
        if (!mSmall) {
            rList.add(createStackedBlock(state));
            return rList;
        }
        Materials aMaterial = getMaterialSafe(state);
        Materials aBaseMaterial = getStoneTypeSafe(state).stoneMaterial;
        if (aMaterial != null) {
            Random tRandom = new Random(pos.hashCode());
            List<ItemStack> tSelector = new ArrayList<>();

            ItemStack tStack = GT_OreDictUnificator.get(OrePrefixes.gemExquisite, aMaterial, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 1L);
            if (tStack != null) {
                for (int i = 0; i < 1; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 1L);
            if (tStack != null) {
                for (int i = 0; i < 2; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);
            if (tStack != null) {
                for (int i = 0; i < 12; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, GT_OreDictUnificator.get(OrePrefixes.crushed, aMaterial, 1L), 1L);
            if (tStack != null) {
                for (int i = 0; i < 5; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.crushed, aMaterial, 1L);
            if (tStack != null) {
                for (int i = 0; i < 10; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), 1L);
            if (tStack != null) {
                for (int i = 0; i < 5; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L);
            if (tStack != null) {
                for (int i = 0; i < 10; i++) {
                    tSelector.add(tStack);
                }
            }

            tStack = GT_OreDictUnificator.get(tRandom.nextInt(3) > 0 ? OrePrefixes.dustImpure : OrePrefixes.dust, aBaseMaterial, 1L);
            if(tStack != null && tRandom.nextInt(3 + fortune) > 1) {
                rList.add(tStack);
            }

            if(tSelector.size() > 0) {
                for (int i = 0, j = Math.max(1, aMaterial.mOreMultiplier + (fortune > 0 ? tRandom.nextInt(1 + fortune * aMaterial.mOreMultiplier) : 0) / 2); i < j; i++) {
                    rList.add(GT_Utility.copyAmount(1L, tSelector.get(tRandom.nextInt(tSelector.size()))));
                }
            }
        }
        return rList;
    }

    public String getLocalizedName(Materials aMaterial) {
        switch (aMaterial.mName) {

            case "InfusedAir":
            case "InfusedDull":
            case "InfusedEarth":
            case "InfusedEntropy":
            case "InfusedFire":
            case "InfusedOrder":
            case "InfusedVis":
            case "InfusedWater":
                return aMaterial.mDefaultLocalName + " Infused Stone";

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
                return aMaterial.mDefaultLocalName;

            default:
                return aMaterial.mDefaultLocalName + OrePrefixes.ore.mLocalizedMaterialPost;
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderGeneratedOres.INSTANCE.renderType;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}