package gregtech.common.blocks.modelfactories;

import gregtech.api.model.AbstractBlockModelFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.common.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockOreFactory extends AbstractBlockModelFactory {

    public static void init() {
        BlockOreFactory factory = new BlockOreFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockOreFactory() {
        super("ore_block", "ore_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        MaterialIconSet iconSet = ((BlockOre) block).material.materialIconSet;
        return blockStateSample
                .replace("$MATERIAL_TEXTURE_NORMAL$", MaterialIconType.ore.getBlockPath(iconSet).toString())
                .replace("$MATERIAL_TEXTURE_SMALL$", MaterialIconType.oreSmall.getBlockPath(iconSet).toString());
    }

}
