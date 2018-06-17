package gregtech.common.blocks.modelfactories;

import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.model.ResourcePackHook;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//why the fuck should i have this shit...
@SideOnly(Side.CLIENT)
public class BlockFluidFactory extends AbstractBlockModelFactory {

    public static void init() {
        BlockFluidFactory factory = new BlockFluidFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockFluidFactory() {
        super("fluid_block", "fluid.");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        BlockFluidBase fluidBlock = (BlockFluidBase) block;
        Fluid fluid = ObfuscationReflectionHelper.getPrivateValue(BlockFluidBase.class, fluidBlock, "definedFluid");
        return blockStateSample.replace("$FLUID_NAME$", fluid.getName());
    }

}