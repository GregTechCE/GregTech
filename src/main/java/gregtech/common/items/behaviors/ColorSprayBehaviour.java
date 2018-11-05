package gregtech.common.items.behaviors;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ColorSprayBehaviour extends AbstractUsableBehaviour {

    private final ItemStack empty;
    private final EnumDyeColor color;

    public ColorSprayBehaviour(ItemStack empty, int totalUses, int color) {
        super(totalUses);
        this.empty = empty;
        this.color = EnumDyeColor.values()[color];
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.canPlayerEdit(pos, side, stack)) {
            return EnumActionResult.FAIL;
        }
        if(!tryPaintBlock(world, pos, side)) {
            return EnumActionResult.PASS;
        }
        useItemDurability(player, hand, stack, empty.copy());
        return EnumActionResult.SUCCESS;
    }

    private boolean tryPaintBlock(World world, BlockPos pos, EnumFacing side) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        return block.recolorBlock(world, pos, side, this.color);
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        int remainingUses = getUsesLeft(itemStack);
        lines.add(I18n.format("behaviour.paintspray." + this.color.getUnlocalizedName() + ".tooltip"));
        lines.add(I18n.format("behaviour.paintspray.uses", remainingUses));
    }
}
