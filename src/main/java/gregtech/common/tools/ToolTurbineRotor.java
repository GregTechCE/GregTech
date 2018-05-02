package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.material.type.SolidMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ToolTurbineRotor extends ToolBase implements ITurbineToolStats {

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        return false;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0f;
    }

    @Override
    public double getRotorEfficiency(ItemStack itemStack) {
        SolidMaterial primaryMaterial = ToolMetaItem.getPrimaryMaterial(itemStack);
        return primaryMaterial.toolSpeed / 24.0f;
    }
}
