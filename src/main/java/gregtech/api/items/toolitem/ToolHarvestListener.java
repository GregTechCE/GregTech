package gregtech.api.items.toolitem;

import gregtech.api.items.toolitem.ToolMetaItem.MetaToolValueItem;
import gregtech.api.util.GTUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ToolHarvestListener {

    private static final Object DUMMY_OBJECT = new Object();
    private final ThreadLocal<Object> harvesting = new ThreadLocal<>();

    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer harvester = event.getHarvester();
        List<ItemStack> drops = event.getDrops();
        if(harvester == null)
            return;
        ItemStack stackInHand = harvester.getHeldItem(EnumHand.MAIN_HAND);
        if(stackInHand.isEmpty() || !(stackInHand.getItem() instanceof ToolMetaItem<?>))
            return;
        ToolMetaItem<? extends MetaToolValueItem> toolMetaItem = (ToolMetaItem<?>) stackInHand.getItem();
        MetaToolValueItem valueItem = toolMetaItem.getItem(stackInHand);
        IToolStats toolStats = valueItem == null ? null : valueItem.getToolStats();
        if(toolStats == null || !toolStats.isMinableBlock(event.getState(), stackInHand))
            return;
        boolean isRecursiveCall = harvesting.get() == DUMMY_OBJECT;
        if(isRecursiveCall && !toolStats.allowRecursiveConversion())
            return; //do not call recursive if not allowed by tool stats explicitly
        if(!isRecursiveCall) {
            harvesting.set(DUMMY_OBJECT);
        }
        try {
            int damageDealt = toolStats.convertBlockDrops(event.getWorld(), event.getPos(), event.getState(), harvester, drops, isRecursiveCall);
            if(damageDealt > 0) {
                event.setDropChance(1.0f);
                boolean damagedTool = GTUtility.doDamageItem(stackInHand, damageDealt *
                    toolStats.getToolDamagePerDropConversion(stackInHand), false);
                if(!damagedTool) {
                    //if we can't apply entire damage to tool, just break it
                    stackInHand.shrink(1);
                }
            }
        } finally {
            if(!isRecursiveCall) {
                //restore state only if non-recursive
                harvesting.set(null);
            }
        }
    }

}
