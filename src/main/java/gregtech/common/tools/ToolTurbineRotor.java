package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.material.type.SolidMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ToolTurbineRotor extends ToolBase implements ITurbineToolStats {

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        return false;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 250.0f;
    }

    @Override
    public double getRotorEfficiency(ItemStack itemStack) {
        SolidMaterial primaryMaterial = ToolMetaItem.getToolMaterial(itemStack);
        return primaryMaterial == null ? 0.1 : primaryMaterial.toolSpeed / 24.0f;
    }

    @Override
    public void addInformation(ItemStack stack, List<String> lines, boolean isAdvanced) {
        lines.add(I18n.format("metaitem.tool.tooltip.rotor.efficiency",
            (int) (getRotorEfficiency(stack) * 100)));
    }
}
