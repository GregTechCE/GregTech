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

public class GT_Block_Ores_GC extends GT_Block_Ores_Abstract {
    Block aMoonBlock = GameRegistry.findBlock("GalacticraftCore", "tile.moonBlock");
    Block aMarsBlock = GameRegistry.findBlock("GalacticraftMars", "tile.mars");

    public GT_Block_Ores_GC() {
        super("gt.blockores.gc", 4, true, Material.rock);
        if (aMoonBlock == null) aMoonBlock = Blocks.stone;
        if (aMarsBlock == null) aMarsBlock = Blocks.stone;
    }

    @Override
    public String getUnlocalizedName() {
        return "gt.blockores.gc";
    }

    @Override
    public OrePrefixes[] getProcessingPrefix() { //Must have 8 entries.
        return new OrePrefixes[]{OrePrefixes.ore, OrePrefixes.ore, OrePrefixes.ore, OrePrefixes.ore, OrePrefixes.ore, OrePrefixes.ore, OrePrefixes.ore, OrePrefixes.ore};
    }

    @Override
    public Block getDroppedBlock() {
        return GregTech_API.sBlockOresGC;
    }

    @Override
    public Materials[] getDroppedDusts() { //Must have 8 entries; can be null.
        return new Materials[]{Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone, Materials.Stone};
    }

    @Override
    public ITexture[] getTextureSet() { //Must have 16 entries.
        return new ITexture[]{new GT_CopiedBlockTexture(aMoonBlock, 0, 3), new GT_CopiedBlockTexture(aMoonBlock, 0, 4), new GT_CopiedBlockTexture(aMoonBlock, 0, 6), new GT_CopiedBlockTexture(aMarsBlock, 0, 9), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0),new GT_CopiedBlockTexture(aMoonBlock, 0, 3), new GT_CopiedBlockTexture(aMoonBlock, 0, 4), new GT_CopiedBlockTexture(aMoonBlock, 0, 6), new GT_CopiedBlockTexture(aMarsBlock, 0, 9), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0)};
    }
}
