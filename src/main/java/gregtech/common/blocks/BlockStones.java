package gregtech.common.blocks;

import gregtech.api.material.Materials;
import gregtech.api.material.type.Material;
import gregtech.api.util.GT_LanguageManager;
//import gregtech.api.util.GT_OreDictUnificator;
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
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(this, 1, 0));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(this, 1, 1));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(this, 1, 2));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(this, 1, 3));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(this, 1, 4));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(this, 1, 5));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(this, 1, 6));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(this, 1, 7));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(this, 1, 8));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(this, 1, 9));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(this, 1, 10));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(this, 1, 11));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(this, 1, 12));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(this, 1, 13));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(this, 1, 14));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(this, 1, 15));
    }

    @Override
    public Material[] getMaterials() {
        if (materials == null) {
            materials = new Material[]{Materials.Marble, Materials.Basalt};
        }
        return materials;
    }
}
