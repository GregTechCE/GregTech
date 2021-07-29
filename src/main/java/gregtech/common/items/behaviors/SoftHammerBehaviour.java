package gregtech.common.items.behaviors;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;
import gregtech.api.util.LocalisationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SoftHammerBehaviour implements IItemBehaviour {

    private final int cost;

    public SoftHammerBehaviour(int cost) {
        this.cost = cost;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (world.isRemote || world.isAirBlock(pos)) {
            return EnumActionResult.PASS;
        }
        ItemStack stack = player.getHeldItem(hand);

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null) {
            IControllable controllable = tileEntity.getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, side);
            if (controllable != null) {
                if (controllable.isWorkingEnabled()) {
                    controllable.setWorkingEnabled(false);
                } else {
                    controllable.setWorkingEnabled(true);
                }
                GTUtility.doDamageItem(stack, cost, false);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(LocalisationUtils.format("behaviour.soft_hammer"));
    }
}
