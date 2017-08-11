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

    default ActionResult<ItemStack> onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    void addInformation(ItemStack itemStack, List<String> lines);

    void onUpdate(ItemStack itemStack, World world, Entity player, int timer, boolean isInHand);

    default ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        return ActionResult.newResult(EnumActionResult.PASS, itemStack);
    }

}