package gregtech.api.items.toolitem;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ToolCraftingListener {

    @SubscribeEvent
    public void onToolCrafted(PlayerEvent.ItemCraftedEvent event) {
        IInventory craftingMatrix = event.craftMatrix;
        ItemStack resultStack = event.crafting;
        if(resultStack.getItem() instanceof ToolMetaItem<?>) {
            ToolMetaItem<?> toolMetaItem = (ToolMetaItem<?>) resultStack.getItem();
            toolMetaItem.onToolCreated(event.player, resultStack, craftingMatrix);
        }
    }

}
