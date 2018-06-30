package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolWrenchLV extends ToolWrench {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }
}
