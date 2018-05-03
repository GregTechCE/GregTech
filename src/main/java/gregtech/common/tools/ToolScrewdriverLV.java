package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolScrewdriverLV extends ToolScrewdriver {

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 2;
    }
}
