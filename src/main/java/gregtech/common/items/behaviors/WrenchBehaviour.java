package gregtech.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WrenchBehaviour implements IItemBehaviour {

    private final int cost;

    public WrenchBehaviour(int cost) {
        this.cost = cost;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote && !world.isAirBlock(pos)) {
            ItemStack stack = player.getHeldItem(hand);
            TileEntity tileEntity = world.getTileEntity(pos);
//            if (tileEntity instanceof IWrenchable) {
//                IWrenchable wrenchable = (IWrenchable) tileEntity;
//                if (player.isSneaking()) {
//                    if (wrenchable.wrenchCanRemove(world, pos, player)) {
//                        List<ItemStack> wrenchDrops = wrenchable.getWrenchDrops(world, pos, world.getBlockState(pos), tileEntity, player, 0);
//                        for (ItemStack wrenchDrop : wrenchDrops) {
//                            if (!player.inventory.addItemStackToInventory(wrenchDrop)) {
//                                Block.spawnAsEntity(world, pos, wrenchDrop);
//                            }
//                        }
//                        world.setBlockToAir(pos);
//                        world.removeTileEntity(pos);
//                        GTUtility.doDamageItem(stack, this.cost, false);
//                        GTUtility.sendSoundToPlayers(world, GregTechAPI.sSoundList.get(100), 1.0F, -1.0F, pos);
//                        return true;
//                    }
//                } else {
//                    if (wrenchable.getFacing(world, pos) != side) {
//                        if (wrenchable.setFacing(world, pos, side, player)) {
//                            GTUtility.doDamageItem(stack, this.cost, false);
//                            GTUtility.sendSoundToPlayers(world, GregTechAPI.sSoundList.get(100), 1.0F, -1.0F, pos);
//                            return true;
//                        }
//                    }
//                }
//            }

            if (world.getBlockState(pos).getBlock().rotateBlock(world, pos, side)) {
                GTUtility.doDamageItem(stack, this.cost, false);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("behaviour.wrench"));
    }
}
