package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class ProcessingDirty implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingDirty() {
        OrePrefixes.clump.add(this);
        OrePrefixes.shard.add(this);
        OrePrefixes.crushed.add(this);
        OrePrefixes.dirtyGravel.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, net.minecraft.item.ItemStack aStack) {
        GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1L), 10, 16);
        GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, GT_Utility.selectItemInList(0, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), 10, false);
        GT_ModHandler.addOreWasherRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), 1000, new Object[]{GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(0, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L)});
        GT_ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), (int) Math.min(5000L, Math.abs(aMaterial.getMass() * 20L)), new Object[]{GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedCentrifuged : OrePrefixes.dust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L)});

        if (aMaterial.contains(SubTag.WASHING_MERCURY))
            GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Mercury.getFluid(1000L), GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new int[]{10000, 7000, 4000}, 800, 8);
        if (aMaterial.contains(SubTag.WASHING_SODIUMPERSULFATE))
            GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SodiumPersulfate.getFluid(1000L), GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new int[]{10000, 7000, 4000}, 800, 8);
        for (Materials tMaterial : aMaterial.mOreByProducts) {
            if (tMaterial.contains(SubTag.WASHING_MERCURY))
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Mercury.getFluid(1000L), GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new int[]{10000, 7000, 4000}, 800, 8);
            if (tMaterial.contains(SubTag.WASHING_SODIUMPERSULFATE))
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SodiumPersulfate.getFluid(1000L), GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new int[]{10000, 7000, 4000}, 800, 8);
        }
    }
}
