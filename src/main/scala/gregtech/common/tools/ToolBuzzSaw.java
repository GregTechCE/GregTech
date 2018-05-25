package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolBuzzSaw extends ToolSaw {

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 3;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.0F;
    }

}
