package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;

public class ProcessingDirty implements IOreRegistrationHandler {
    public ProcessingDirty() {
        OrePrefix.clump.add(this);
        OrePrefix.shard.add(this);
        OrePrefix.crushed.add(this);
        OrePrefix.dirtyGravel.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Material aMaterial, String aOreDictName, String aModName, net.minecraft.item.ItemStack aStack) {
        GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1L), 10, 16);
        GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1L), 1L), OreDictionaryUnifier.get(OrePrefix.dust, GT_Utility.selectItemInList(0, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), 10, false);
        GT_ModHandler.addOreWasherRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), 1000, new Object[]{OreDictionaryUnifier.get(aPrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dustTiny, GT_Utility.selectItemInList(0, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1L)});
        GT_ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), (int) Math.min(5000L, Math.abs(aMaterial.getMass() * 20L)), new Object[]{OreDictionaryUnifier.get(aPrefix == OrePrefix.crushed ? OrePrefix.crushedCentrifuged : OrePrefix.dust, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dustTiny, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1L)});

        if (aMaterial.contains(SubTag.WASHING_MERCURY))
            GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Mercury.getFluid(1000L), OreDictionaryUnifier.get(aPrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1L), new int[]{10000, 7000, 4000}, 800, 8);
        if (aMaterial.contains(SubTag.WASHING_SODIUMPERSULFATE))
            GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SodiumPersulfate.getFluid(1000L), OreDictionaryUnifier.get(aPrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1L), new int[]{10000, 7000, 4000}, 800, 8);
        for (Materials tMaterial : aMaterial.mOreByProducts) {
            if (tMaterial.contains(SubTag.WASHING_MERCURY))
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Mercury.getFluid(1000L), OreDictionaryUnifier.get(aPrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dust, tMaterial.mMacerateInto, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1L), new int[]{10000, 7000, 4000}, 800, 8);
            if (tMaterial.contains(SubTag.WASHING_SODIUMPERSULFATE))
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.SodiumPersulfate.getFluid(1000L), OreDictionaryUnifier.get(aPrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dust, tMaterial.mMacerateInto, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1L), new int[]{10000, 7000, 4000}, 800, 8);
        }
    }
}
