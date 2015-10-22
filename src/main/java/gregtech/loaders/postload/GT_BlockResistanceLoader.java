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
        if (GT_Mod.gregtechproxy.mHardRock) {
            Blocks.stone.setHardness(16.0F);
            Blocks.brick_block.setHardness(32.0F);
            Blocks.hardened_clay.setHardness(32.0F);
            Blocks.stained_hardened_clay.setHardness(32.0F);
            Blocks.cobblestone.setHardness(12.0F);
            Blocks.stonebrick.setHardness(24.0F);
        }
        Blocks.stone.setResistance(10.0F);
        Blocks.cobblestone.setResistance(10.0F);
        Blocks.stonebrick.setResistance(10.0F);
        Blocks.brick_block.setResistance(20.0F);
        Blocks.hardened_clay.setResistance(15.0F);
        Blocks.stained_hardened_clay.setResistance(15.0F);


        Blocks.bed.setHarvestLevel("axe", 0);
        Blocks.hay_block.setHarvestLevel("axe", 0);
        Blocks.tnt.setHarvestLevel("pickaxe", 0);
        Blocks.sponge.setHarvestLevel("axe", 0);
        Blocks.monster_egg.setHarvestLevel("pickaxe", 0);

        GT_Utility.callMethod(Material.tnt, "func_85158_p", true, false, false, new Object[0]);
        GT_Utility.callMethod(Material.tnt, "setAdventureModeExempt", true, false, false, new Object[0]);

        Set tSet = (Set) GT_Utility.getFieldContent(ItemAxe.class, "field_150917_c", true, true);
        tSet.add(Blocks.bed);
        tSet.add(Blocks.hay_block);
        tSet.add(Blocks.sponge);

        tSet = (Set) GT_Utility.getFieldContent(ItemPickaxe.class, "field_150915_c", true, true);
        tSet.add(Blocks.monster_egg);
        tSet.add(Blocks.tnt);
    }
}
