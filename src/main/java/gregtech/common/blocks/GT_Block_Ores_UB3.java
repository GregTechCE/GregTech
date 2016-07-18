package gregtech.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GT_Block_Ores_UB3 extends GT_Block_Ores_Abstract {
    public GT_Block_Ores_UB3() {
        super("gt.blockores.ub3", Material.rock);
        for (int i = 0; i < 16; i++) {
            GT_ModHandler.addValuableOre(this, i, 1);
        }
        for (int i = 1; i < GregTech_API.sGeneratedMaterials.length; i++) {
            if (GregTech_API.sGeneratedMaterials[i] != null) {
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + i + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 1000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 2000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 3000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 4000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 5000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 6000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 7000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 8000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 9000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 10000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 11000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 12000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 13000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 14000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 15000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                if ((GregTech_API.sGeneratedMaterials[i].mTypes & 0x8) != 0) {
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 1000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 2000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 3000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 4000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 5000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 6000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 7000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 8000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 9000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 10000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 11000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 12000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 13000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 14000));
                    GT_OreDictUnificator.registerOre("ore", new ItemStack(this, 1, i + 15000));
                    if (tHideOres) {
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 1000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 2000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 3000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 4000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 5000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 6000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 7000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 8000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 9000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 10000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 11000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 12000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 13000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 14000));
                        codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i + 15000));
                    }
                }
            }
        }
    }

    @Override
    public String getUnlocalizedName() {
        return "gt.blockores.ub3";
    }

    @Override
    public ITexture[] getStoneTexture(byte aSide, int aMetaData, Materials aMaterial) {
        ITexture[] mStoneTextures = {new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 0), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 1), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 2), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 3), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 4), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 5), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 6), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 7), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 0), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 1), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 2), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 3), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 4), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 5), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 6), new GT_CopiedBlockTexture(UndergroundBiomes.sedimentaryStone, 0, 7)};
        return new ITexture[]{mStoneTextures[(aMetaData / 1000 % 16)], new GT_RenderedTexture(aMaterial.mIconSet.mTextures[aMetaData < 8000 ? OrePrefixes.ore.mTextureIndex : OrePrefixes.oreSmall.mTextureIndex], aMaterial.mRGBa)};
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs aTab, List aList) {
        for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
            Materials tMaterial = GregTech_API.sGeneratedMaterials[i];
            if ((tMaterial != null) && ((tMaterial.mTypes & 0x8) != 0)) {
                aList.add(new ItemStack(aItem, 1, i));
                aList.add(new ItemStack(aItem, 1, i + 1000));
                aList.add(new ItemStack(aItem, 1, i + 2000));
                aList.add(new ItemStack(aItem, 1, i + 3000));
                aList.add(new ItemStack(aItem, 1, i + 4000));
                aList.add(new ItemStack(aItem, 1, i + 5000));
                aList.add(new ItemStack(aItem, 1, i + 6000));
                aList.add(new ItemStack(aItem, 1, i + 7000));
                aList.add(new ItemStack(aItem, 1, i + 8000));
                aList.add(new ItemStack(aItem, 1, i + 9000));
                aList.add(new ItemStack(aItem, 1, i + 10000));
                aList.add(new ItemStack(aItem, 1, i + 11000));
                aList.add(new ItemStack(aItem, 1, i + 12000));
                aList.add(new ItemStack(aItem, 1, i + 13000));
                aList.add(new ItemStack(aItem, 1, i + 14000));
                aList.add(new ItemStack(aItem, 1, i + 15000));
            }
        }
    }
}
