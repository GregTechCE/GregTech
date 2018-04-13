package gregtech.common.cable;

import gregtech.api.GTValues;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockCable extends ItemBlock {

    private BlockCable blockCable;

    public ItemBlockCable(BlockCable block) {
        super(block);
        this.blockCable = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Insulation insulation = BlockCable.getInsulation(stack);
        MetalMaterial material = blockCable.baseProps.material;
        return insulation.orePrefix.getLocalNameForItem(material);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        Insulation insulation = BlockCable.getInsulation(stack);
        WireProperties wireProperties = blockCable.getProperties(insulation);
        String voltageName = GTValues.VN[GTUtility.getTierByVoltage(wireProperties.voltage)];
        tooltip.add(I18n.format("gregtech.cable.voltage", wireProperties.voltage, voltageName));
        tooltip.add(I18n.format("gregtech.cable.amperage", wireProperties.amperage));
        tooltip.add(I18n.format("gregtech.cable.loss_per_block", wireProperties.lossPerBlock));
    }
}
