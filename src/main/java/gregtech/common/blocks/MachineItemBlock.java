package gregtech.common.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
        this.setHasSubtypes(true);
    }

    public IMetaTileEntityFactory getFactory(ItemStack stack) {
        IMetaTileEntityFactory factory = GregTechAPI.METATILEENTITY_REGISTRY.getObjectById(stack.getItemDamage());
        if(factory == null) factory = GregTechAPI.METATILEENTITY_REGISTRY.iterator().next();
        return factory;
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getFactory(stack).getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(getUnlocalizedName(stack));
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
            IGregTechTileEntity tileEntity = (IGregTechTileEntity) world.getTileEntity(pos);
            if (tileEntity == null) {
                return true;
            }
            IMetaTileEntity mte = tileEntity.getMetaTileEntity();
            if (mte == null) {
                return true;
            }
            mte.setFrontFacing(player.getHorizontalFacing().getOpposite());
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag tooltipFlag) {
        tooltip.addAll(Arrays.asList(getFactory(stack).getDescription(stack)));
    }
}
