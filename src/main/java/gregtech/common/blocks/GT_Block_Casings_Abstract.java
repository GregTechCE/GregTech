package gregtech.common.blocks;

import com.google.common.collect.Lists;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.render.IIconRegister;
import gregtech.common.render.newblocks.IBlockIconProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class GT_Block_Casings_Abstract extends GT_Generic_Block implements IBlockIconProvider, IBlockColor {


    public GT_Block_Casings_Abstract(Class<? extends ItemBlock> aItemClass, String aName, Material aMaterial) {
        super(aItemClass, aName, aMaterial);
        setSoundType(SoundType.METAL);
        setCreativeTab(GregTech_API.TAB_GREGTECH);
        GregTech_API.registerMachineBlock(this, -1);
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + 32767 + ".name", "Any Sub Block of this");
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return "wrench";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 2;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return super.createBlockState();
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return Blocks.IRON_BLOCK.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return Blocks.IRON_BLOCK.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if(GregTech_API.isMachineBlock(this, state.getValue(METADATA))) {
            GregTech_API.causeMachineUpdate(worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if(GregTech_API.isMachineBlock(this, state.getValue(METADATA))) {
            GregTech_API.causeMachineUpdate(worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Override
    public String getUnlocalizedName() {
        return this.mUnlocalizedName;
    }

    @Override
    public String getLocalizedName() {
        return GT_LanguageManager.getTranslation(this.mUnlocalizedName + ".name");
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList(new ItemStack(this, 1, state.getValue(METADATA)));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List<ItemStack> aList) {
        for (int i = 0; i < 16; i++) {
            aList.add(new ItemStack(aItem, 1, i));
        }
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return Dyes._NULL.getRGBAInt();
    }

}
