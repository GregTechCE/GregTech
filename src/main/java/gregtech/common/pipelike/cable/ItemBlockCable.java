package gregtech.common.pipelike.cable;

import gregtech.api.GTValues;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.ItemBlockPipe;
import gregtech.api.util.GTUtility;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockCable extends ItemBlockPipe<Insulation, WireProperties> {

    public ItemBlockCable(BlockPipe<Insulation, WireProperties, ?> block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        Insulation insulation = blockPipe.getPipeType(stack);
        WireProperties wireProperties = blockPipe.getProperties(insulation);
        String voltageName = GTValues.VN[GTUtility.getTierByVoltage(wireProperties.voltage)];
        tooltip.add(I18n.translateToLocalFormatted("gregtech.cable.voltage", wireProperties.voltage, voltageName));
        tooltip.add(I18n.translateToLocalFormatted("gregtech.cable.amperage", wireProperties.amperage));
        tooltip.add(I18n.translateToLocalFormatted("gregtech.cable.loss_per_block", wireProperties.lossPerBlock));
    }
}
