package gregtech.common.items.behaviors;

import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.Arrays;
import java.util.List;

public class PlungerBehaviour implements IItemBehaviour {

    public final int cost;

    public PlungerBehaviour(int cost) {
        this.cost = cost;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity == null) {
            return EnumActionResult.PASS;
        }
        IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
        if (fluidHandler == null) {
            return EnumActionResult.PASS;
        }
        ItemStack toolStack = player.getHeldItem(hand);
        boolean isShiftClick = player.isSneaking();
        IFluidHandler handlerToRemoveFrom = isShiftClick ?
            (fluidHandler instanceof FluidHandlerProxy ? ((FluidHandlerProxy) fluidHandler).input : null) :
            (fluidHandler instanceof FluidHandlerProxy ? ((FluidHandlerProxy) fluidHandler).output : fluidHandler);

        if (handlerToRemoveFrom != null && GTUtility.doDamageItem(toolStack, cost, false)) {
            if (!world.isRemote) {
                FluidStack drainStack = handlerToRemoveFrom.drain(1000, true);
                int amountOfFluid = drainStack == null ? 0 : drainStack.amount;
                if (amountOfFluid > 0) {
                    player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0f, amountOfFluid / 1000.0f);
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.addAll(Arrays.asList(I18n.format("behavior.plunger.description").split("/n")));
    }
}
