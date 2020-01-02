package gregtech.common.tools;

import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.common.items.behaviors.MagnifyingGlassBehaviour;
import net.minecraft.item.ItemStack;

public class ToolMagnifyingGlass extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        return 0.1F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 8.0F;
    }

    @Override
    public void onStatsAddedToTool(MetaValueItem item) {
        item.addComponents(new MagnifyingGlassBehaviour(DamageValues.DAMAGE_FOR_MAGNIFYING_GLASS));
    }
}
