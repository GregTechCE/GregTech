package gregtech.common.tools;

import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import net.minecraft.item.ItemStack;

public class ToolPlunger extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 4;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public void onStatsAddedToTool(MetaValueItem item, int ID) {
        super.onStatsAddedToTool(item, ID);
    }
}
