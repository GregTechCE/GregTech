package gregtech.common.blocks.modelfactories;

import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.BlockMetalCasing;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockMetalCasingFactory extends AbstractBlockModelFactory {

    public static void init() {
        BlockMetalCasingFactory factory = new BlockMetalCasingFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockMetalCasingFactory() {
        super("metal_casing_block", "metal_casing_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        Material material = ((BlockMetalCasing) block).getMetalCasingMaterial();
        return blockStateSample.replace("$MATERIAL$", material.toString())
            .replace("$TEXTURE$", MaterialIconType.metalCasing.getBlockPath(material.materialIconSet).toString());
    }
}
