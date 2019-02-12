package gregtech.common.pipelike.cable;

import gregtech.api.GTValues;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.ItemBlockPipe;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockCable extends ItemBlockPipe<Insulation, WireProperties> {

    public ItemBlockCable(BlockPipe<Insulation, WireProperties, ?> block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        WireProperties wireProperties = blockPipe.createItemProperties(stack);
        String voltageName = GTValues.VN[GTUtility.getTierByVoltage(wireProperties.voltage)];
        tooltip.add(I18n.format("gregtech.cable.voltage", wireProperties.voltage, voltageName));
        tooltip.add(I18n.format("gregtech.cable.amperage", wireProperties.amperage));
        tooltip.add(I18n.format("gregtech.cable.loss_per_block", wireProperties.lossPerBlock));
    }
}
