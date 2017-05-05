package gregtech.common.blocks;

import com.google.common.collect.Lists;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.blocks.itemblocks.GT_Item_Storage;
import gregtech.common.blocks.properties.PropertyMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class GT_Block_Storage extends GT_Generic_Block {

    public final PropertyMaterial MATERIAL;

    public Materials[] mMats;
    public OrePrefixes mPrefix;
    public Textures.BlockIcons[] mBlockIcons;

    public GT_Block_Storage(String aName, Materials[] aMats, OrePrefixes aPrefix, Textures.BlockIcons[] aBlockIcons) {
        super(aName, GT_Item_Storage.class, Material.IRON);
        mMats = aMats;
        mPrefix = aPrefix;
        mBlockIcons = aBlockIcons;

        MATERIAL = PropertyMaterial.create("material", aMats);

        for (int i = 0; i < aMats.length; i++) {
            if (aMats[i].mMetaItemSubID > 0 && aMats[i].mHasParentMod) {
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + i + ".name", "Block of " + aMats[i].mDefaultLocalName);
                GT_OreDictUnificator.registerOre(aPrefix, aMats[i], new ItemStack(this, 1, i));
            }
        }
        setHardness(5.0F); //Blocks.IRON_BLOCK
        setResistance(10.0F); //Blocks.IRON_BLOCK
        setSoundType(SoundType.METAL);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MATERIAL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(MATERIAL, mMats[meta & 15]);
    }

    /**
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        Materials material = state.getValue(MATERIAL);

        int meta = 0;
        for (int i = 0; i < mMats.length; i++) {
            if (material == mMats[i]) {
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

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List<ItemStack> aList) {
        for (int i = 0; i < 16; i++) {
            if (!(new ItemStack(aItem, 1, i).getDisplayName().contains(".name"))) aList.add(new ItemStack(aItem, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(EnumFacing aSide, int aDamage) {
        if ((aDamage >= 0) && (aDamage < 16) && aDamage < mMats.length) {
            return mBlockIcons[aDamage].getIcon();
        }
        return mBlockIcons[0].getIcon();
    }
}
