package gregtech.loaders.postload;

import gregtech.GT_Mod;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;

import java.util.Set;

public class GT_BlockResistanceLoader
        implements Runnable {
    public void run() {
        Blocks.STONE.setResistance(10.0F);
        Blocks.COBBLESTONE.setResistance(10.0F);
        Blocks.STONEBRICK.setResistance(10.0F);
        Blocks.BRICK_BLOCK.setResistance(20.0F);
        Blocks.HARDENED_CLAY.setResistance(15.0F);
        Blocks.STAINED_HARDENED_CLAY.setResistance(15.0F);

        if (GT_Mod.gregtechproxy.mHardRock) {
            Blocks.STONE.setHardness(16.0F);
            Blocks.BRICK_BLOCK.setHardness(32.0F);
            Blocks.HARDENED_CLAY.setHardness(32.0F);
            Blocks.STAINED_HARDENED_CLAY.setHardness(32.0F);
            Blocks.COBBLESTONE.setHardness(12.0F);
            Blocks.STONEBRICK.setHardness(24.0F);
        }

        Blocks.BED.setHarvestLevel("axe", 0);
        Blocks.HAY_BLOCK.setHarvestLevel("axe", 0);
        Blocks.TNT.setHarvestLevel("pickaxe", 0);
        Blocks.SPONGE.setHarvestLevel("axe", 0);
        Blocks.MONSTER_EGG.setHarvestLevel("pickaxe", 0);

    }
}
