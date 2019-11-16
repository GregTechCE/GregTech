package gregtech.common.tools;

import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.common.items.behaviors.ScoopBehaviour;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ToolScoop extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 2;
    }

    @Override
    public boolean canMineBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && tool.equals("scoop");
    }

    @Override
    public void onStatsAddedToTool(MetaValueItem item) {
        item.addComponents(new ScoopBehaviour(DamageValues.DAMAGE_FOR_SCOOP));
    }

}
