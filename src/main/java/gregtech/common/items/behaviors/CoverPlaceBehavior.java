package gregtech.common.items.behaviors;

import gregtech.api.block.machines.BlockMachine;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoverPlaceBehavior implements IItemBehaviour {

    public final CoverDefinition coverDefinition;

    public CoverPlaceBehavior(CoverDefinition coverDefinition) {
        this.coverDefinition = coverDefinition;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        MetaTileEntity metaTileEntity = BlockMachine.getMetaTileEntity(world, pos);
        if(metaTileEntity == null) {
            return EnumActionResult.PASS;
        }
        if(metaTileEntity.getCoverAtSide(side) != null || !metaTileEntity.canPlaceCoverOnSide(side)) {
            return EnumActionResult.PASS;
        }
        if(!world.isRemote) {
            ItemStack itemStack = player.getHeldItem(hand);
            boolean result = metaTileEntity.placeCoverOnSide(side, itemStack, coverDefinition);
            if (result && !player.capabilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            return result ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
        }
        return EnumActionResult.SUCCESS;
    }
}
