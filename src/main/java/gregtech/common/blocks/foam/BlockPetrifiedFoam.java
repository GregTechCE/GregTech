package gregtech.common.blocks.foam;

import gregtech.api.GregTechAPI;
import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPetrifiedFoam extends BlockColored {

    public BlockPetrifiedFoam(boolean isReinforced) {
        super(Material.ROCK);
        setSoundType(SoundType.SNOW);
        setResistance(isReinforced ? 1.0f : 5.0f);
        setHardness(isReinforced ? 1.0f : 3.0f);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setHarvestLevel("pickaxe", isReinforced ? 2 : 1);
    }

}
