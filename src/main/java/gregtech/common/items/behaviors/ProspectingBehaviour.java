package gregtech.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.List;

public class ProspectingBehaviour implements IItemBehaviour {

    private final int cost;

    public ProspectingBehaviour(int cost) {
        this.cost = cost;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

        if (world.isRemote || !world.isAirBlock(pos)) {
            return EnumActionResult.PASS;
        }

        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        ItemStack stack = player.getHeldItem(hand);
        UnificationEntry entry = OreDictUnifier.getUnificationEntry(new ItemStack(block));

        if (entry != null && entry.orePrefix.toString().startsWith("ore")) {
//            GTUtility.sendChatToPlayer(player, "This is " + entry.mMaterial.mMaterial.mDefaultLocalName + " Ore.");
//            GTUtility.sendSoundToPlayers(world, GregTechAPI.sSoundList.get(1), 1.0F, -1.0F, pos);
            return EnumActionResult.SUCCESS;
        }

        if (block.isReplaceableOreGen(blockState, world, pos, state ->
                state.getBlock() == Blocks.STONE ||
                state.getBlock() == Blocks.END_STONE ||
                state.getBlock() == Blocks.NETHERRACK)) {

            if (GTUtility.doDamageItem(stack, cost, false)) {

//                GTUtility.sendSoundToPlayers(world, GregTechAPI.sSoundList.get(1), 1.0F, -1.0F, pos);
                // TODO rewrite it to make more sense
                int quality = stack.getItem().getHarvestLevel(stack, "", player, blockState);
                int scanRadius = 6 + quality;
                boolean breakIt = false;
                for(int x = -scanRadius; x < scanRadius; x++) {
                    if(breakIt) break;
                    for(int z = -scanRadius; z < scanRadius; z++) {
                        if(breakIt) break;
                        for(int y = 0; y < scanRadius; y++) {
                            if(breakIt) break;
                            BlockPos scanPos = pos.add(x, -(y + 1), z);
                            IBlockState scanState = world.getBlockState(scanPos);
                            if(scanState.getBlock() == Blocks.LAVA || scanState.getBlock() == Blocks.FLOWING_LAVA) {
//                                GTUtility.sendChatToPlayer(player, "There is Lava behind this Rock");
                                breakIt = true;
                            } else if(scanState.getBlock() == Blocks.WATER || scanState.getBlock() == Blocks.FLOWING_WATER) {
//                                GTUtility.sendChatToPlayer(player, "There is Water behind this Rock");
                                breakIt = true;
                            } else if(scanState.getBlock() instanceof IFluidBlock) {
//                                GTUtility.sendChatToPlayer(player, "There is Fluid behind this Rock");
                                breakIt = true;
                            } else if(scanState.getBlock() == Blocks.AIR ||
                                    scanState.getBlock() == Blocks.MONSTER_EGG ||
                                    scanState.getCollisionBoundingBox(world, pos) == Block.NULL_AABB) {
//                                GTUtility.sendChatToPlayer(player, "There is an Air Pocket behind this Rock.");
                                breakIt = true;
                            } else if(scanState.getBlock() != block) {
//                                GTUtility.sendChatToPlayer(player, "Material is changing behind this Rock.");
                                breakIt = true;
                            }
                        }
                    }
                }

//                GTUtility.sendChatToPlayer(player, "No Ores found.");
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("behaviour.prospecting"));
    }
}
