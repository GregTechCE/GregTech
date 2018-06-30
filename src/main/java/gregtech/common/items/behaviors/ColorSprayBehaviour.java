package gregtech.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ColorSprayBehaviour implements IItemBehaviour {

    private final ItemStack empty;
    private final ItemStack used;
    private final ItemStack full;
    private final long totalUses;
    private final EnumDyeColor color;

    public ColorSprayBehaviour(ItemStack empty, ItemStack used, ItemStack full, long totalUses, int color) {
        this.empty = empty;
        this.used = used;
        this.full = full;
        this.totalUses = totalUses;
        this.color = EnumDyeColor.values()[color];
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote || stack.getCount() != 1) {
            return EnumActionResult.PASS;
        }
        EnumActionResult output = EnumActionResult.FAIL;
        if (!player.canPlayerEdit(pos, side, stack)) {
            return EnumActionResult.FAIL;
        }
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            compound = new NBTTagCompound();
        }
        long uses = compound.getLong("GT.RemainingPaint");
        if (ItemStack.areItemStacksEqual(stack, this.full)) {
            player.setHeldItem(hand, used);
            uses = this.totalUses;
        }
        if (ItemStack.areItemStacksEqual(stack, this.used) && colorize(world, pos, side)) {
//            GTUtility.sendSoundToPlayers(world, GregTechAPI.sSoundList.get(102), 1.0F, 1.0F, pos);
            if (!player.capabilities.isCreativeMode) {
                uses -= 1L;
            }
            output = EnumActionResult.SUCCESS;
        }
        compound.removeTag("GT.RemainingPaint");
        if (uses > 0L) {
            compound.setLong("GT.RemainingPaint", uses);
        }
        if (compound.hasNoTags()) {
            stack.setTagCompound(null);
        } else {
            stack.setTagCompound(compound);
        }
        if (uses <= 0L) {
            if (this.empty == null) {
                stack.shrink(1);
            } else {
                player.setHeldItem(hand, empty);
            }
        }
        return output;
    }

    private boolean colorize(World world, BlockPos pos, EnumFacing side) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof BlockColored) {
            world.setBlockState(pos, blockState.withProperty(BlockColored.COLOR, color));
            return true;
        }
        return block.recolorBlock(world, pos, side, this.color);
    }


    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("behaviour.paintspray." + this.color.getUnlocalizedName() + ".tooltip"));
        NBTTagCompound compound = itemStack.getTagCompound();
        long remainingPaint = compound == null ? 0L : ItemStack.areItemStacksEqual(itemStack, this.full) ? this.totalUses : compound.getLong("GT.RemainingPaint");
        lines.add(I18n.format("behaviour.paintspray.uses", remainingPaint));
        lines.add(I18n.format("behaviour.unstackable"));
    }
}
