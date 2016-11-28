package gregtech.common.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_CopiedBlockTexture;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class GT_Block_Ores_UB1 extends GT_Block_Ores_Abstract {
    Block aUBBlock = GameRegistry.findBlock("UndergroundBiomes", "igneousStone");

    public GT_Block_Ores_UB1() {
        super("gt.blockores.ub1", 8, true, Material.rock);
        if (aUBBlock == null) aUBBlock = Blocks.stone;
    }

    @Override
    public String getUnlocalizedName() {
        return "gt.blockores.ub1";
    }

    @Override
    public OrePrefixes[] getProcessingPrefix() { //Must have 8 entries.
        return new OrePrefixes[]{OrePrefixes.oreRedgranite, OrePrefixes.oreBlackgranite, OrePrefixes.ore, OrePrefixes.ore, OrePrefixes.ore, OrePrefixes.oreBasalt, OrePrefixes.ore, OrePrefixes.ore};
    }

    @Override
    public int getBaseBlockHarvestLevel(int aMeta) {
        return aUBBlock.getHarvestLevel(aMeta);
    }

    @Override
    public Block getDroppedBlock() {
        return GregTech_API.sBlockOresUb1;
    }

    @Override
    public Materials[] getDroppedDusts() { //Must have 8 entries; can be null.
        return new Materials[]{Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone};
    }

    @Override
    public ITexture[] getTextureSet() { //Must have 16 entries.
        return new ITexture[]{new GT_CopiedBlockTexture(aUBBlock, 0, 0), new GT_CopiedBlockTexture(aUBBlock, 0, 1), new GT_CopiedBlockTexture(aUBBlock, 0, 2), new GT_CopiedBlockTexture(aUBBlock, 0, 3), new GT_CopiedBlockTexture(aUBBlock, 0, 4), new GT_CopiedBlockTexture(aUBBlock, 0, 5), new GT_CopiedBlockTexture(aUBBlock, 0, 6), new GT_CopiedBlockTexture(aUBBlock, 0, 7), new GT_CopiedBlockTexture(aUBBlock, 0, 0), new GT_CopiedBlockTexture(aUBBlock, 0, 1), new GT_CopiedBlockTexture(aUBBlock, 0, 2), new GT_CopiedBlockTexture(aUBBlock, 0, 3), new GT_CopiedBlockTexture(aUBBlock, 0, 4), new GT_CopiedBlockTexture(aUBBlock, 0, 5), new GT_CopiedBlockTexture(aUBBlock, 0, 6), new GT_CopiedBlockTexture(aUBBlock, 0, 7)};
    }
}
