package gregtech.common.blocks;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.render.blocks.IBlockTextureProvider;
import gregtech.jei.JEI_Compat;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

public abstract class GT_Block_Ores_Abstract extends GT_Generic_Block implements ITileEntityProvider, IBlockTextureProvider {

    protected GT_Block_Ores_Abstract(String aUnlocalizedName, Material aMaterial) {
        super(GT_Item_Ores.class, aUnlocalizedName, aMaterial);

        this.isBlockContainer = true;
        setSoundType(SoundType.STONE);
        setCreativeTab(GregTech_API.TAB_GREGTECH_ORES);

        for (int i = 0; i < 16; i++) {
            GT_ModHandler.addValuableOre(this, i, 1);
        }

        boolean hideOres = Loader.isModLoaded("JustEnoughItems") && GT_Mod.gregtechproxy.mHideUnusedOres;

        for (int i = 1; i < GregTech_API.sGeneratedMaterials.length; i++) {

            if (GregTech_API.sGeneratedMaterials[i] != null) {
                for(int x = 0; x < 16; x++) {
                    if (isValidForCreativeTab(x)) {
                        String localizedName = getLocalizedName(GregTech_API.sGeneratedMaterials[i]);
                        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 1000 * x) + ".name", localizedName);
                        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 16000 + 1000 * x) + ".name", "Small " + localizedName);
                    }
                }

                if ((GregTech_API.sGeneratedMaterials[i].mTypes & 0x8) != 0) {
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[0] != null ? this.getProcessingPrefix()[0].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[1] != null ? this.getProcessingPrefix()[1].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 1000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[2] != null ? this.getProcessingPrefix()[2].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 2000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[3] != null ? this.getProcessingPrefix()[3].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 3000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[4] != null ? this.getProcessingPrefix()[4].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 4000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[5] != null ? this.getProcessingPrefix()[5].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 5000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[6] != null ? this.getProcessingPrefix()[6].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 6000));
                    GT_OreDictUnificator.registerOre(this.getProcessingPrefix()[7] != null ? this.getProcessingPrefix()[7].get(GregTech_API.sGeneratedMaterials[i]) : "", new ItemStack(this, 1, i + 7000));

                    if (hideOres) {
                        Item aItem = Item.getItemFromBlock(this);
                        for(int x = 0; x < 16; x++) {
                            if(isValidForCreativeTab(x)) {
                                JEI_Compat.hideItem(new ItemStack(aItem, 1, 1000 * x + i));
                                JEI_Compat.hideItem(new ItemStack(aItem, 1, 16000 + 1000 * x + i));
                            }
                        }
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

    //public abstract String getUnlocalizedName();

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
        return new GT_TileEntity_Ores();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ITexture[] getTexture(World world, BlockPos blockPos, IExtendedBlockState blockState, EnumFacing side) {
        GT_TileEntity_Ores oreTile = (GT_TileEntity_Ores) world.getTileEntity(blockPos);
        return oreTile.getTexture(this, (byte) side.getIndex());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ITexture[] getItemblockTexture(EntityPlayer player, ItemStack itemStack, EnumFacing side) {
        int mMetaData = itemStack.getItemDamage();
        Materials aMaterial = GregTech_API.sGeneratedMaterials[(mMetaData % 1000)];
        if (aMaterial != null && mMetaData < 32000) {
            return new ITexture[]{
                    getTextureSet()[(mMetaData / 1000) % 16],
                    aMaterial.mOreTextureSet[mMetaData / 16000 == 0 ? 0 : 1]};
        }
        return new ITexture[]{
                GT_Block_Ores.TEXTURES[0],
                Materials._NULL.mOreTextureSet[0]};
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tTileEntity = world.getTileEntity(pos);
        if ((tTileEntity instanceof GT_TileEntity_Ores)) {
            return ((GT_TileEntity_Ores) tTileEntity).getDrops(getDroppedBlock(), fortune);
        }
        return Collections.EMPTY_LIST;
    }

    public abstract OrePrefixes[] getProcessingPrefix(); //Must have 17 entries; an entry can be null to disable automatic recipes.

    public abstract Block getDroppedBlock();

    public abstract Materials[] getDroppedDusts(); //Must have 16 entries; can be null.

    public abstract ITexture[] getTextureSet(); //Must have 16 entries.

    @Override
    public void getSubBlocks(Item aItem, CreativeTabs aTab, List<ItemStack> aList) {
        for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
            Materials tMaterial = GregTech_API.sGeneratedMaterials[i];
            if (tMaterial != null && (tMaterial.mTypes & 0x8) != 0) {
                for(byte x = 0; x < 16; x++) {
                    if(isValidForCreativeTab(x)) {
                        aList.add(new ItemStack(aItem, 1, 1000 * x + i));
                        aList.add(new ItemStack(aItem, 1, 16000 + 1000 * x + i));
                    }
                }
            }
        }
    }

    public boolean isValidForCreativeTab(int baseBlockType) {
        return baseBlockType <= 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    //for multilayer blocks
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
