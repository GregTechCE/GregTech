package gregtech.common.tools;

import gregtech.api.items.toolitem.IToolStats;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.items.toolitem.ToolMetaItem.MetaToolValueItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.util.function.Task;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

import java.util.*;

public class TreeChopTask implements Task {

    private static final int MAX_BLOCKS_SEARCH_PER_TICK = 1024;
    private static final int MAX_BLOCKS_TO_SEARCH = 8192;
    private final Stack<EnumFacing> moveStack = new Stack<>();
    private final MutableBlockPos currentPos = new MutableBlockPos();
    private final BlockPos startBlockPos;
    private final Set<BlockPos> visitedBlockPos = new HashSet<>();
    private final List<BlockPos> woodBlockPos = new ArrayList<>();

    private boolean finishedSearchingBlocks = false;
    private int currentWoodBlockIndex = 0;
    private final World world;
    private final EntityPlayerMP player;
    private final ItemStack itemStack;

    public TreeChopTask(BlockPos startPos, World world, EntityPlayerMP player, ItemStack toolStack) {
        this.startBlockPos = startPos.toImmutable();
        this.currentPos.setPos(startPos);
        this.world = world;
        this.itemStack = toolStack;
        this.player = player;
    }

    @Override
    public boolean run() {
        ItemStack itemInMainHand = this.player.getHeldItemMainhand();
        ToolMetaItem<?> toolMetaItem = (ToolMetaItem<?>) itemStack.getItem();
        MetaToolValueItem toolValueItem = toolMetaItem.getItem(itemStack);
        if (toolValueItem == null) {
            return false;
        }
        IToolStats toolStats = toolValueItem.getToolStats();
        int damagePerBlockBreak = toolStats.getToolDamagePerBlockBreak(itemStack);
        boolean isPlayerNear = player.world == world && currentPos.distanceSq(player.posX, currentPos.getY(), player.posZ) <= 1024;
        boolean isPlayerConnected = player.connection.netManager.isChannelOpen() && isPlayerNear;
        if (!isPlayerConnected || itemInMainHand.isEmpty() || !isItemEqual(itemInMainHand)) {
            return false;
        }

        if(!finishedSearchingBlocks) {
            this.finishedSearchingBlocks = !attemptSearchWoodBlocks() ||
                this.visitedBlockPos.size() >= MAX_BLOCKS_TO_SEARCH;
            if(finishedSearchingBlocks) {
                this.woodBlockPos.sort(new WoodBlockComparator());
            }
            return true;
        }
        if(toolMetaItem.isUsable(itemInMainHand, damagePerBlockBreak) && tryBreakAny()) {
            toolMetaItem.damageItem(itemInMainHand, damagePerBlockBreak, false);
            return true;
        }
        return false;
    }

    private class WoodBlockComparator implements Comparator<BlockPos> {

        @Override
        public int compare(BlockPos o1, BlockPos o2) {
            int a = -Integer.compare(o1.getY(), o2.getY());
            if(a != 0) {
                return a;
            }
            return Integer.compare(distance(o1), distance(o2));
        }

        private int distance(BlockPos pos) {
            int diffX = pos.getX() - startBlockPos.getX();
            int diffZ = pos.getZ() - startBlockPos.getZ();
            return diffX * diffX + diffZ * diffZ;
        }
    }

    private boolean isItemEqual(ItemStack heldItem) {
        if (heldItem.getItem() != itemStack.getItem() ||
            heldItem.getItemDamage() != itemStack.getItemDamage()) {
            return false;
        }
        SolidMaterial heldToolMaterial = ToolMetaItem.getToolMaterial(heldItem);
        SolidMaterial toolMaterial = ToolMetaItem.getToolMaterial(itemStack);
        return toolMaterial == heldToolMaterial;
    }

    private boolean tryBreakAny() {
        if(woodBlockPos.size() > currentWoodBlockIndex) {
            BlockPos woodPos = woodBlockPos.get(currentWoodBlockIndex++);
            IBlockState blockState = this.world.getBlockState(woodPos);
            if(isLogOrLeavesBlock(blockState) == 1) {
                blockState.getBlock().dropBlockAsItemWithChance(world, woodPos, blockState, 1.0f, 0);
                this.world.setBlockToAir(woodPos);
                return true;
            }
            return true;
        }
        return false;
    }

    private boolean attemptSearchWoodBlocks() {
        int blocksSearchedNow = 0;
        int validWoodBlocksFound = 0;
        main: while (blocksSearchedNow <= MAX_BLOCKS_SEARCH_PER_TICK) {
            //try to iterate neighbour blocks
            blocksSearchedNow++;
            for (EnumFacing facing : EnumFacing.VALUES) {
                //move at facing
                currentPos.move(facing);

                if(!visitedBlockPos.contains(currentPos)) {
                    IBlockState blockState = this.world.getBlockState(currentPos);
                    int blockType;
                    if ((blockType = isLogOrLeavesBlock(blockState)) > 0) {
                        BlockPos immutablePos = currentPos.toImmutable();
                        this.visitedBlockPos.add(immutablePos);
                        if(blockType == 1) {
                            this.woodBlockPos.add(immutablePos);
                        }
                        validWoodBlocksFound++;
                        moveStack.add(facing.getOpposite());
                        continue main;
                    }
                }

                //move back if it wasn't a tree block
                currentPos.move(facing.getOpposite());
            }
            //we didn't found any matching block in neighbours - move back
            if (!moveStack.isEmpty()) {
                currentPos.move(moveStack.pop());
            } else break;
        }
        return validWoodBlocksFound > 0;
    }

    public static int isLogOrLeavesBlock(IBlockState blockState) {
        if(blockState.getBlock() instanceof BlockLog) {
            return 1;
        } else if(blockState.getBlock() instanceof BlockLeaves) {
            return 2;
        }
        Item itemBlock = Item.getItemFromBlock(blockState.getBlock());
        ItemStack blockStack = new ItemStack(itemBlock, 1, blockState.getBlock().damageDropped(blockState));
        Set<String> blocks = OreDictUnifier.getOreDictionaryNames(blockStack);
        if(blocks.contains("logWood")) {
            return 1;
        } else if(blocks.contains("treeLeaves")) {
            return 2;
        }
        return 0;
    }

}
