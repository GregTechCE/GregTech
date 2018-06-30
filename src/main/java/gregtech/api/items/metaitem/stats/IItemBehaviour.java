package gregtech.api.items.metaitem.stats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IItemBehaviour extends IMetaItemStats {

    default boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer player, Entity entity) {
        return false;
    }

    default EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return EnumActionResult.PASS;
    }

    default ActionResult<ItemStack> onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    default void addInformation(ItemStack itemStack, List<String> lines) {
    }

    default void onUpdate(ItemStack itemStack, World world, Entity player, int timer, boolean isInHand) {
    }

    default ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}