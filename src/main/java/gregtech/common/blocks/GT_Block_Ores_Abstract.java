package gregtech.common.blocks;

import gregtech.api.enums.TextureSet;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.common.render.newblocks.ITextureBlockIconProvider;
import mezz.jei.Internal;
import mezz.jei.api.IItemBlacklist;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class GT_Block_Ores_Abstract extends GT_Generic_Block implements ITileEntityProvider, ITextureBlockIconProvider {
    public static ThreadLocal<GT_TileEntity_Ores> mTemporaryTileEntity = new ThreadLocal<>();
    public static boolean tHideOres;

    protected GT_Block_Ores_Abstract(String aUnlocalizedName, boolean aHideFirstMeta, Material aMaterial) {
        super(GT_Item_Ores.class, aUnlocalizedName, aMaterial);
        this.isBlockContainer = true;
        setSoundType(SoundType.STONE);
        setCreativeTab(GregTech_API.TAB_GREGTECH_ORES);
        tHideOres = Loader.isModLoaded("JustEnoughItems") && GT_Mod.gregtechproxy.mHideUnusedOres;
        for (int i = 0; i < 16; i++) {
            GT_ModHandler.addValuableOre(this, i, 1);
        }
        for (int i = 1; i < GregTech_API.sGeneratedMaterials.length; i++) {
            if (GregTech_API.sGeneratedMaterials[i] != null) {
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + i + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 1000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 2000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 3000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 4000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 5000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 6000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 7000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 16000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 17000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 18000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 19000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 20000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 21000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 22000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 23000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                if ((GregTech_API.sGeneratedMaterials[i].mTypes & 0x8) != 0) {
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[0] != null ? this.getProcessingPrefix()[0].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[1] != null ? this.getProcessingPrefix()[1].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 1000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[2] != null ? this.getProcessingPrefix()[2].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 2000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[3] != null ? this.getProcessingPrefix()[3].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 3000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[4] != null ? this.getProcessingPrefix()[4].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 4000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[5] != null ? this.getProcessingPrefix()[5].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 5000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[6] != null ? this.getProcessingPrefix()[6].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 6000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[7] != null ? this.getProcessingPrefix()[7].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 7000));
                    if (tHideOres) {
                        IItemBlacklist blacklist = Internal.getHelpers().getItemBlacklist();
                        if (aHideFirstMeta) blacklist.addItemToBlacklist(new ItemStack(this, 1, i));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 1000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 2000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 3000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 4000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 5000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 6000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 7000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 16000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 17000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 18000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 19000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 20000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 21000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 22000));
                        blacklist.addItemToBlacklist(new ItemStack(this, 1, i + 23000));
                    }
                }
            }
        }
    }


    public String getLocalizedName(Materials aMaterial) {
        switch (aMaterial) {
            case InfusedAir:
            case InfusedDull:
            case InfusedEarth:
            case InfusedEntropy:
            case InfusedFire:
            case InfusedOrder:
            case InfusedVis:
            case InfusedWater:
                return aMaterial.mDefaultLocalName + " Infused Stone";
            case Vermiculite:
            case Bentonite:
            case Kaolinite:
            case Talc:
            case BasalticMineralSand:
            case GraniticMineralSand:
            case GlauconiteSand:
            case CassiteriteSand:
            case GarnetSand:
            case QuartzSand:
            case Pitchblende:
            case FullersEarth:
                return aMaterial.mDefaultLocalName;
            default:
                return aMaterial.mDefaultLocalName + OrePrefixes.ore.mLocalizedMaterialPost;
        }
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        return tileEntity != null && tileEntity.receiveClientEvent(id, param);
    }

    @Override
    public String getHarvestTool(IBlockState aMeta) {
        return aMeta.getValue(METADATA) < 8 ? "pickaxe" : "shovel";
    }

    @Override
    public int getHarvestLevel(IBlockState aMeta) {
        return 2;
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return 1.0F + getHarvestLevel(blockState);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return 1.0F + getHarvestLevel(world.getBlockState(pos));
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    public abstract String getUnlocalizedName();

    public String getLocalizedName() {
        return GT_LanguageManager.getTranslation(getUnlocalizedName() + ".name");
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return true;
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World aWorld, int aMeta) {
        return createTileEntity(aWorld, aMeta);
    }

    @Override
    public ITexture[] getTexture(World world, BlockPos blockPos, IExtendedBlockState blockState, EnumFacing side) {
        GT_TileEntity_Ores oreTile = (GT_TileEntity_Ores) world.getTileEntity(blockPos);
        return oreTile.getTexture(this, (byte) side.getIndex());
    }

    @Override
    public ITexture[] getItemblockTexture(EntityPlayer player, ItemStack itemStack, EnumFacing side) {
        int mMetaData = itemStack.getItemDamage();
        Materials aMaterial = GregTech_API.sGeneratedMaterials[(mMetaData % 1000)];
        if ((aMaterial != null) && (mMetaData < 32000)) {
            GT_RenderedTexture aIconSet = new GT_RenderedTexture(
                    aMaterial.mIconSet.mTextures[mMetaData / 16000 == 0 ?
                    OrePrefixes.ore.mTextureIndex :
                    OrePrefixes.oreSmall.mTextureIndex], aMaterial.mRGBa);
            return new ITexture[]{getTextureSet()[(mMetaData / 1000) % 16], aIconSet};
        }
        return new ITexture[]{
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture(TextureSet.SET_NONE.mTextures[OrePrefixes.ore.mTextureIndex])};
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tTileEntity = worldIn.getTileEntity(pos);
        if ((tTileEntity instanceof GT_TileEntity_Ores)) {
            mTemporaryTileEntity.set((GT_TileEntity_Ores) tTileEntity);
        }
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tTileEntity = world.getTileEntity(pos);
        if ((tTileEntity instanceof GT_TileEntity_Ores)) {
            return ((GT_TileEntity_Ores) tTileEntity).getDrops(getDroppedBlock(), fortune);
        }
        return mTemporaryTileEntity.get() == null ? new ArrayList<>() : mTemporaryTileEntity.get().getDrops(getDroppedBlock(), fortune);
    }


    public abstract OrePrefixes[] getProcessingPrefix(); //Must have 8 entries; an entry can be null to disable automatic recipes.

    public abstract Block getDroppedBlock();

    public abstract Materials[] getDroppedDusts(); //Must have 8 entries; can be null.

    public TileEntity createTileEntity(World aWorld, int aMeta) {
        return new GT_TileEntity_Ores();
    }

    public abstract ITexture[] getTextureSet(); //Must have 16 entries.

    @Override
    public void getSubBlocks(Item aItem, CreativeTabs aTab, List<ItemStack> aList) {
        for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
            Materials tMaterial = GregTech_API.sGeneratedMaterials[i];
            if ((tMaterial != null) && ((tMaterial.mTypes & 0x8) != 0)) {
                aList.add(new ItemStack(aItem, 1, i));
                aList.add(new ItemStack(aItem, 1, i + 1000));
                aList.add(new ItemStack(aItem, 1, i + 2000));
                aList.add(new ItemStack(aItem, 1, i + 3000));
                aList.add(new ItemStack(aItem, 1, i + 4000));
                aList.add(new ItemStack(aItem, 1, i + 5000));
                aList.add(new ItemStack(aItem, 1, i + 6000));
                aList.add(new ItemStack(aItem, 1, i + 7000));
                aList.add(new ItemStack(aItem, 1, i + 16000));
                aList.add(new ItemStack(aItem, 1, i + 17000));
                aList.add(new ItemStack(aItem, 1, i + 18000));
                aList.add(new ItemStack(aItem, 1, i + 19000));
                aList.add(new ItemStack(aItem, 1, i + 20000));
                aList.add(new ItemStack(aItem, 1, i + 21000));
                aList.add(new ItemStack(aItem, 1, i + 22000));
                aList.add(new ItemStack(aItem, 1, i + 23000));
            }
        }
    }

}
