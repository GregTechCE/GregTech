package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolKnife extends ToolSword {

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return 3.0f; //3x faster than sword attack
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

}
