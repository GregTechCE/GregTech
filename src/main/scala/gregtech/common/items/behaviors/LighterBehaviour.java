package gregtech.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class LighterBehaviour implements IItemBehaviour {

    private final ItemStack emptyLighter;
    private final ItemStack usedLighter;
    private final ItemStack fullLighter;
    private final long fuelAmount;

    public LighterBehaviour(ItemStack emptyLighter, ItemStack usedLighter, ItemStack fullLighter, long fuelAmount) {
        this.fullLighter = fullLighter;
        this.usedLighter = usedLighter;
        this.emptyLighter = emptyLighter;
        this.fuelAmount = fuelAmount;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (player.world.isRemote || stack.getCount() != 1) {
            return false;
        }
        boolean output = false;
        if (entity instanceof EntityCreeper) {
            stack = prepare(stack);
            long fuelAmount = getLighterFuel(stack);
            if (ItemStack.areItemStacksEqual(stack, this.usedLighter)) {
                ((EntityCreeper) entity).ignite();
                if (!player.capabilities.isCreativeMode) {
                    fuelAmount -= 1L;
                }
                output = true;
            }
            setLighterFuel(stack, fuelAmount);
            if (fuelAmount <= 0L) {
                stack = useUp(stack);
            }
            player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
        }
        return output;
    }

    @Override
    public ActionResult<ItemStack> onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote || stack.getCount() != 1) {
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        }

        BlockPos clickedBlock = pos.offset(facing);
        if(!player.canPlayerEdit(clickedBlock, facing, stack)) {
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        }

        if (world.isAirBlock(clickedBlock))
        {
            stack = prepare(stack);
            long fuelAmount = getLighterFuel(stack);

//            GTUtility.sendSoundToPlayers(world, GregTechAPI.sSoundList.get(6), 1.0F, 1.0F, clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ());
            world.setBlockState(clickedBlock, Blocks.FIRE.getDefaultState(), 11);

            if (!player.capabilities.isCreativeMode) {
                fuelAmount -= 1L;
            }
            setLighterFuel(stack, fuelAmount);
            if(fuelAmount <= 0L) {
                stack = useUp(stack);
            }
            player.setHeldItem(hand, stack);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    private ItemStack prepare(ItemStack stack) {
        if (ItemStack.areItemStacksEqual(stack, this.fullLighter)) {
            stack = this.usedLighter.copy();
            setLighterFuel(stack, this.fuelAmount);
        }
        return stack;
    }

    private ItemStack useUp(ItemStack stack) {
        if (this.emptyLighter.isEmpty()) {
            stack.shrink(1);
            return stack;
        }
        return this.emptyLighter.copy();
    }

    public static void setLighterFuel(ItemStack stack, long aFuel) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        compound.setLong("GT.LighterFuel", aFuel);
        stack.setTagCompound(compound);
    }

    public static long getLighterFuel(ItemStack stack) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        return compound.getLong("GT.LighterFuel");
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("behaviour.lighter.tooltip"));
        NBTTagCompound nbt = itemStack.getTagCompound();
        long fuelAmount = nbt == null ? this.fuelAmount : nbt.getLong("GT.LighterFuel");
        lines.add(I18n.format("behaviour.lighter.uses", fuelAmount));
        lines.add(I18n.format("behaviour.unstackable"));
    }
}
