package gregtech.common.blocks;

import com.google.common.collect.ImmutableList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.render.blocks.IBlockIconProvider;
import gregtech.common.render.data.IIconData;
import gregtech.common.render.data.IIconRegister;
import gregtech.common.render.data.IconDataGetter;
import gregtech.jei.JEI_Compat;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

public abstract class GT_Block_Ores_Abstract extends GT_Generic_Block implements ITileEntityProvider, IBlockIconProvider, IIconRegister {

    protected TIntObjectMap<IIconData> mGeneratedIconData = new TIntObjectHashMap<>();

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
            Materials materials = GregTech_API.sGeneratedMaterials[i];
            if (materials != null && (materials.mTypes & 0x8) != 0) {
                OrePrefixes[] processingPrefixes = getProcessingPrefix();
                for (int x = 0; x < processingPrefixes.length; x++) {
                    ItemStack oreStack = new ItemStack(this, 1, i + 1000 * x);
                    ItemStack smallOreStack = new ItemStack(this, 1, i + 1000 * x + 16000);
                    if (processingPrefixes[x] != null) {
                        GT_OreDictUnificator.registerOre(processingPrefixes[x].get(materials), oreStack);
                    }
                    String localizedName = getLocalizedName(materials);
                    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (oreStack.getItemDamage()) + ".name", localizedName);
                    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (smallOreStack.getItemDamage()) + ".name", "Small " + localizedName);
                    if (hideOres) {
                        JEI_Compat.hideItem(oreStack);
                        JEI_Compat.hideItem(smallOreStack);
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
    public String getHarvestTool(IBlockState aMeta) {
        return aMeta.getValue(METADATA) < 8 ? "pickaxe" : "shovel";
    }

    @Override
    public int getHarvestLevel(IBlockState aMeta) {
        return aMeta.getValue(METADATA) % 8;
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

    public String getLocalizedName() {
        return GT_LanguageManager.getTranslation(getUnlocalizedName() + ".name");
    }

    @Override
    public TileEntity createNewTileEntity(World aWorld, int aMeta) {
        return new GT_TileEntity_Ores();
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn) {
        GT_TileEntity_Ores tileEntity_ores = (GT_TileEntity_Ores) world.getTileEntity(pos);
        if (tileEntity_ores != null) {
            tileEntity_ores.broadcastPacketIfNeeded();
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public ImmutableList<BakedQuad> getIcon(EnumFacing aSide, int aDamage) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ImmutableList<BakedQuad> getIcon(IBlockAccess world, BlockPos pos, EnumFacing aSide, int metadata) {
        try {
            GT_TileEntity_Ores tileEntity_ores = (GT_TileEntity_Ores) world.getTileEntity(pos);
            if (tileEntity_ores != null) {
                IIconData iconData = mGeneratedIconData.get(tileEntity_ores.mMetaData);
                if (iconData == null) {
                    return ImmutableList.of();
                }
                return iconData.getQuads(aSide);
            }
            //Dont know how and when it happens
        } catch (ConcurrentModificationException dontknowhowandwhenithappens) {}
        return ImmutableList.of();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ImmutableList<BakedQuad> getIcon(EntityPlayer player, ItemStack itemStack, EnumFacing aSide) {
        IIconData iconData = mGeneratedIconData.get(itemStack.getItemDamage());
        if(iconData == null) {
            return ImmutableList.of();
        }
        return iconData.getQuads(aSide);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconDataGetter quadGetter) {
        System.out.println("Starting ore texture generation");
        for(int i = 1; i < GregTech_API.sGeneratedMaterials.length; i++) {
            Materials materials = GregTech_API.sGeneratedMaterials[i];
            if (materials != null && (materials.mTypes & 0x8) != 0) {
                IIconContainer[] materialTextures = materials.mIconSet.mTextures;
                IIconContainer[] textureSet = getTextureSet();
                for(int j = 0; j < textureSet.length; j++) {
                    IIconData normalIconData = quadGetter.makeIconData(
                            textureSet[j],
                            materialTextures[68],
                            materials.getColorInt());

                    IIconData smallIconData = quadGetter.makeIconData(
                            textureSet[j],
                            materialTextures[67],
                            materials.getColorInt());

                    mGeneratedIconData.put((i + 1000 * j), normalIconData);
                    mGeneratedIconData.put((i + 1000 * j + 16000), smallIconData);
                }
            }
        }
        System.out.println("Finished ore texture generation");
    }

    private static ThreadLocal<GT_TileEntity_Ores> STUPID_MOJANGS = new ThreadLocal<>();

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        STUPID_MOJANGS.set((GT_TileEntity_Ores) worldIn.getTileEntity(pos));
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        GT_TileEntity_Ores tTileEntity = (GT_TileEntity_Ores) world.getTileEntity(pos);
        if(tTileEntity == null && STUPID_MOJANGS.get() != null) {
            tTileEntity = STUPID_MOJANGS.get();
            STUPID_MOJANGS.remove();
        }
        if (tTileEntity != null) {
            return tTileEntity.getDrops(getDroppedBlock(), fortune);
        }
        return Collections.EMPTY_LIST;
    }

    public abstract OrePrefixes[] getProcessingPrefix(); //Must have 16 entries; an entry can be null to disable automatic recipes.

    public abstract Block getDroppedBlock();

    public abstract Materials[] getDroppedDusts(); //Must have 16 entries; can be null.

    public abstract IIconContainer[] getTextureSet(); //Must have 16 entries.

    public abstract boolean isGravelAlike(int aOreMeta);

    public boolean isValidForCreativeTab(int aOreMeta) {
        return aOreMeta < 12000; //|| (metadata > 16000 && metadata < 25000);
    }

    @Override
    public void getSubBlocks(Item aItem, CreativeTabs aTab, List<ItemStack> aList) {
        int validMats = 0;
        for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
            Materials tMaterial = GregTech_API.sGeneratedMaterials[i];
            if (tMaterial != null && (tMaterial.mTypes & 0x8) != 0) {
                validMats++;
                for(byte x = 0; x < 16; x++) {
                    int metadata = 1000 * x + i;
                    if(isValidForCreativeTab(metadata)) {
                        aList.add(new ItemStack(aItem, 1, metadata));
                    }
                    metadata = 16000 + 1000 * x + i;
                    if(isValidForCreativeTab(metadata)) {
                        aList.add(new ItemStack(aItem, 1, metadata));
                    }
                }
            }
        }
        System.out.println(validMats + " valid ores");
    }


    @Override
    @SideOnly(Side.CLIENT)
    //for multilayer blocks
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
