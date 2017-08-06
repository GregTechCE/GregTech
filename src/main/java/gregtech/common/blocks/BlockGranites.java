package gregtech.common.blocks;

import gregtech.api.material.Materials;
import gregtech.api.material.type.Material;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.ItemGranites;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

//import gregtech.api.util.GT_OreDictUnificator;


public class BlockGranites extends BlockStonesAbstract {

    public BlockGranites() {
        super("blockgranites", ItemGranites.class);
        setResistance(60.0F);
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Black Granite");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Black Granite Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Mossy Black Granite Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Black Granite Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Cracked Black Granite Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Mossy Black Granite Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Chiseled Black Granite");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Smooth Black Granite");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Red Granite");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Red Granite Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Mossy Red Granite Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Red Granite Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cracked Red Granite Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Mossy Red Granite Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Chiseled Red Granite");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Smooth Red Granite");
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 0));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 1));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 2));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 3));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 4));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 5));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 6));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 7));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 8));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 9));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 10));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 11));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 12));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 13));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 14));
//        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 15));
    }

    @Override
    public Material[] getMaterials() {
        if (materials == null) {
            materials = new Material[]{Materials.GraniteBlack, Materials.GraniteRed};
        }
        return materials;
    }

    @Override
    public int getHarvestLevel(IBlockState blockState) {
        return 3;
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return !(entity instanceof EntityWither);
    }
}
