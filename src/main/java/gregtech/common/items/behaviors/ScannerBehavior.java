package gregtech.common.items.behaviors;

import codechicken.lib.raytracer.RayTracer;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.metaitem.stats.IItemUseManager;
import gregtech.api.items.toolitem.IScannableBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ScannerBehavior implements IItemBehaviour, IItemUseManager {

    private final int costPerUseTick;

    public ScannerBehavior(int costPerUseTick) {
        this.costPerUseTick = costPerUseTick;
    }

    @Override
    public boolean canStartUsing(ItemStack stack, EntityPlayer player) {
        return checkCanUseScanner(stack, player, true).getLeft() == null;
    }

    private Pair<BlockPos, IBlockState> getHitBlock(EntityPlayer entityPlayer) {
        RayTraceResult result = RayTracer.retrace(entityPlayer);
        if (result.typeOfHit == Type.BLOCK) {
            BlockPos blockPos = result.getBlockPos();
            IBlockState blockState = entityPlayer.world.getBlockState(blockPos);
            if (blockState.getBlock() instanceof IScannableBlock) {
                return Pair.of(blockPos, blockState);
            }
        }
        return null;
    }

    private boolean dischargeItem(ItemStack itemStack, long amount, boolean simulate) {
        IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (electricItem == null) {
            return false;
        }
        long dischargeAmount = amount * costPerUseTick;
        return electricItem.discharge(dischargeAmount, Integer.MAX_VALUE, true, false, simulate) >= dischargeAmount;
    }

    private Pair<String, Integer> checkCanUseScanner(ItemStack itemStack, EntityPlayer player, boolean simulate) {
        Pair<BlockPos, IBlockState> hitBlock = getHitBlock(player);
        if (hitBlock == null) {
            return Pair.of("behavior.scanner.analyzing_failed", 0);
        }
        IBlockState state = hitBlock.getRight();
        long amount = ((IScannableBlock) state.getBlock()).getScanDuration(player.world, hitBlock.getLeft(), state, player);
        if (!dischargeItem(itemStack, amount, simulate)) {
            return Pair.of("metaitem.scanner.not_enough_energy", 0);
        }
        int maxHitUse = ((IScannableBlock) state.getBlock()).getScanDuration(player.world, hitBlock.getLeft(), state, player);
        return Pair.of(null, maxHitUse);
    }

    @Override
    public void onItemUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (!player.world.isRemote) {
            Pair<String, Integer> pair = checkCanUseScanner(stack, player, true);
            String failureReason = pair.getLeft();
            if (failureReason == null) {
                ITextComponent component = new TextComponentTranslation("behavior.scanner.analyzing");
                component.getStyle().setColor(TextFormatting.GREEN);
                player.sendStatusMessage(component, true);
                int maxUseCount = pair.getRight();
                if ((getMaxItemUseDuration(stack) - count) >= maxUseCount) {
                    player.activeItemStackUseCount = 0;
                }
            } else {
                ITextComponent component = new TextComponentTranslation(failureReason);
                component.getStyle().setColor(TextFormatting.RED);
                player.sendStatusMessage(component, true);
                player.stopActiveHand();
                player.getCooldownTracker().setCooldown(stack.getItem(), 20);
            }
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, EntityPlayer player) {
        if (!player.world.isRemote) {
            Pair<BlockPos, IBlockState> hitBlock = getHitBlock(player);
            if (hitBlock != null && checkCanUseScanner(stack, player, false).getLeft() == null) {
                ITextComponent component = new TextComponentTranslation("behavior.scanner.analyzing_complete");
                component.getStyle().setColor(TextFormatting.GOLD);
                player.sendStatusMessage(component, true);
                IScannableBlock magnifiableBlock = ((IScannableBlock) hitBlock.getRight().getBlock());
                List<ITextComponent> text = magnifiableBlock.getMagnifyResults(player.world, hitBlock.getLeft(), hitBlock.getRight(), player);
                text.forEach(player::sendMessage);
            }
        }
        return stack;
    }

    @Override
    public EnumAction getUseAction(ItemStack stack) {
        return EnumAction.NONE;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 200_000;
    }
}
