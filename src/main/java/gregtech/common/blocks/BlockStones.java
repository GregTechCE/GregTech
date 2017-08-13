package gregtech.common.blocks;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GT_LanguageManager;
//import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.common.blocks.itemblocks.ItemGranites;

public class BlockStones extends BlockStonesAbstract {
    public BlockStones() {
        super("blockstones", ItemGranites.class);
        setResistance(60.0F);
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Marble");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Marble Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Mossy Marble Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Marble Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Cracked Marble Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Mossy Marble Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Chiseled Marble");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Smooth Marble");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Basalt");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Basalt Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Mossy Basalt Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Basalt Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cracked Basalt Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Mossy Basalt Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Chiseled Basalt");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Smooth Basalt");
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, new ItemStack(this, 1, 0));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, new ItemStack(this, 1, 1));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, new ItemStack(this, 1, 2));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, new ItemStack(this, 1, 3));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, new ItemStack(this, 1, 4));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, new ItemStack(this, 1, 5));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, new ItemStack(this, 1, 6));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, new ItemStack(this, 1, 7));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, new ItemStack(this, 1, 8));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, new ItemStack(this, 1, 9));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, new ItemStack(this, 1, 10));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, new ItemStack(this, 1, 11));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, new ItemStack(this, 1, 12));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, new ItemStack(this, 1, 13));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, new ItemStack(this, 1, 14));
//        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, new ItemStack(this, 1, 15));
    }

    @Override
    public Material[] getMaterials() {
        if (materials == null) {
            materials = new Material[]{Materials.Marble, Materials.Basalt};
        }
        return materials;
    }
}
