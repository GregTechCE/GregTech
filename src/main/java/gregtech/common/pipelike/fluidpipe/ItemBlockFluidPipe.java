package gregtech.common.pipelike.fluidpipe;

import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.ItemBlockPipe;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockFluidPipe extends ItemBlockPipe<FluidPipeType, FluidPipeProperties> {

    public ItemBlockFluidPipe(BlockPipe<FluidPipeType, FluidPipeProperties, ?> block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        FluidPipeProperties pipeProperties = blockPipe.createItemProperties(stack);
        tooltip.add(I18n.format("gregtech.fluid_pipe.throughput", GTUtility.format(pipeProperties.throughput)));
        tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature", GTUtility.format(pipeProperties.maxFluidTemperature)));
        if (!pipeProperties.gasProof) tooltip.add(I18n.format("gregtech.fluid_pipe.non_gas_proof"));
    }
}
