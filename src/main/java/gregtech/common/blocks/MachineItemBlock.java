package gregtech.common.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.GregtechTileEntity;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class MachineItemBlock extends ItemBlock {

    private BlockMachine block;

    public MachineItemBlock(BlockMachine block) {
        super(block);
        this.block = block;
    }

    public IMetaTileEntityFactory getFactory(ItemStack stack) {
        return GregTechAPI.METATILEENTITY_REGISTRY.getObjectById(stack.getItemDamage());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getFactory(stack).getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.addAll(Arrays.asList(getFactory(stack).getDescription(stack)));
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (!world.setBlockState(pos, newState, 3))
            return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == this.block) {
            IMetaTileEntity tileEntity = getFactory(stack).constructMetaTileEntity();
            GregtechTileEntity realTileEntity = new GregtechTileEntity();
            realTileEntity.setMetaTileEntity(tileEntity);
            world.setTileEntity(pos, realTileEntity);
            if(stack.hasTagCompound()) {
                tileEntity.initFromItemStackData(stack.getTagCompound());
            }
            this.block.onBlockPlacedBy(world, pos, state, player, stack);
        }
        return true;
    }

}
