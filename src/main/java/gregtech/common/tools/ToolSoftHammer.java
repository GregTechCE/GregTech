package gregtech.common.tools;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.behaviors.SoftHammerBehaviour;
import net.minecraft.item.ItemStack;

public class ToolSoftHammer extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.1F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 8.0F;
    }

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
        item.addStats(new SoftHammerBehaviour(100));
    }

}
