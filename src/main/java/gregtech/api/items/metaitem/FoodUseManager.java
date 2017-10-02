package gregtech.api.items.metaitem;

import gregtech.api.items.metaitem.stats.IFoodStats;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.metaitem.stats.IItemUseManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class FoodUseManager implements IItemBehaviour, IItemUseManager {

    private final IFoodStats foodStats;

    public FoodUseManager(IFoodStats foodStats) {
        this.foodStats = foodStats;
    }

    public IFoodStats getFoodStats() {
        return foodStats;
    }

    @Override
    public void onItemUseStart(ItemStack stack, EntityPlayer player) {}

    @Override
    public boolean canStartUsing(ItemStack stack, EntityPlayer player) {
        return player.getFoodStats().needFood() || foodStats.alwaysEdible(stack, player);
    }

    @Override
    public EnumAction getUseAction(ItemStack itemStack) {
        return foodStats.getFoodAction(itemStack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemStack) {
        return 3000;
    }

    @Override
    public void onItemUsingTick(ItemStack stack, EntityPlayer player, int count) {}

    @Override
    public void onPlayerStoppedItemUsing(ItemStack stack, EntityPlayer player, int timeLeft) {}

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, EntityPlayer player) {
        stack.shrink(1);
        player.getFoodStats().addStats(foodStats.getFoodLevel(stack, player), foodStats.getSaturation(stack, player));
        foodStats.onEaten(stack, player);
        return stack;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer player, Entity entity) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        foodStats.addInformation(itemStack, lines);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity player, int timer, boolean isInHand) {}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

}
