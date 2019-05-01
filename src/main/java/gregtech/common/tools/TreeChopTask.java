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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

import java.util.Set;
import java.util.Stack;

public class TreeChopTask implements Task {

    private final Stack<EnumFacing> moveStack = new Stack<>();
    private final MutableBlockPos currentPos = new MutableBlockPos();
    private final World world;
    private final EntityPlayerMP player;
    private final ItemStack itemStack;

    public TreeChopTask(BlockPos startPos, World world, EntityPlayerMP player, ItemStack toolStack) {
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
        if (isPlayerConnected && !itemInMainHand.isEmpty() && isItemEqual(itemInMainHand) && toolMetaItem.isUsable(itemInMainHand, damagePerBlockBreak) && tryBreakAny()) {
            toolMetaItem.damageItem(itemInMainHand, damagePerBlockBreak, false);
            this.player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        return false;
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
        while (true) {
            //try to iterate neighbour blocks
            for (EnumFacing facing : EnumFacing.VALUES) {
                //do not move back
                if (!moveStack.isEmpty() && facing == moveStack.peek().getOpposite())
                    continue;
                //move at facing
                currentPos.move(facing);
                IBlockState blockState = this.world.getBlockState(currentPos);
                if (isLogOrLeavesBlock(blockState)) {
                    blockState.getBlock().dropBlockAsItemWithChance(this.world, currentPos, blockState, 1.0F, 0);
                    this.world.setBlockToAir(currentPos);
                    //write our move to stack
                    moveStack.add(facing);
                    return true;
                }
                //move back if it wasn't a tree block
                currentPos.move(facing.getOpposite());
            }
            //we didn't found any matching block in neighbours - move back
            if (!moveStack.isEmpty()) {
                currentPos.move(moveStack.pop());
            } else return false;
        }
    }

    public static boolean isLogOrLeavesBlock(IBlockState blockState) {
        if(blockState.getBlock() instanceof BlockLog ||
            blockState.getBlock() instanceof BlockLeaves) {
            return true;
        }
        Item itemBlock = Item.getItemFromBlock(blockState.getBlock());
        ItemStack blockStack = new ItemStack(itemBlock, 1, blockState.getBlock().damageDropped(blockState));
        Set<String> blocks = OreDictUnifier.getOreDictionaryNames(blockStack);
        return blocks.contains("treeLeaves") || blocks.contains("logWood");
    }

}
