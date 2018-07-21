package gregtech.common.tools;

import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.common.items.behaviors.HoeBehaviour;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ToolHoe extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.75F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack, String tool) {
        return (tool != null && tool.equals("hoe")) ||
            block.getMaterial() == Material.GROUND;
    }

    @Override
    public void onStatsAddedToTool(MetaValueItem item) {
        item.addStats(new HoeBehaviour(100));
    }

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }
}
