package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolWrenchLV extends ToolWrench {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 2.0F;
    }
}
