package gregtech.common.blocks;

import com.google.common.collect.Lists;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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

import java.util.List;

public abstract class GT_Block_Casings_Abstract extends GT_Generic_Block {

    public GT_Block_Casings_Abstract(String aName, Class<? extends ItemBlock> aItemClass, Material aMaterial) {
        super(aName, aItemClass, aMaterial);
        setSoundType(SoundType.METAL);
        setCreativeTab(GregTech_API.TAB_GREGTECH);
        setHardness(5.0F); //Blocks.IRON_BLOCK
        setResistance(10.0F); //Blocks.IRON_BLOCK
        GregTech_API.registerMachineBlock(this, -1);
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + GT_Values.W + ".name", "Any Sub Block of this");
    }

    @Override
    public int damageDropped(IBlockState state) {
        return super.damageDropped(state); //TODO
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
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if(GregTech_API.isMachineBlock(state)) {
            GregTech_API.causeMachineUpdate(worldIn, pos);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if(GregTech_API.isMachineBlock(state)) {
            GregTech_API.causeMachineUpdate(worldIn, pos);
        }
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList(new ItemStack(this, 1, this.damageDropped(state)));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List<ItemStack> aList) {
        for (int i = 0; i < 16; i++) {
            ItemStack aStack = new ItemStack(aItem, 1, i);
            if (!aStack.getDisplayName().contains(".name")) aList.add(aStack);
        }
    }

}
