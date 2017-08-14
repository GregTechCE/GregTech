package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingDirty implements IOreRegistrationHandler {
    public ProcessingDirty() {
        OrePrefix.clump.addProcessingHandler(this);
        OrePrefix.shard.addProcessingHandler(this);
        OrePrefix.crushed.addProcessingHandler(this);
        OrePrefix.dirtyGravel.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material.mMacerateInto, 1), 10, 16);
        ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material.mMacerateInto, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto, 1), 1), OreDictionaryUnifier.get(OrePrefix.dust, GT_Utility.selectItemInList(0, uEntry.material.mMacerateInto, uEntry.material.mOreByProducts), 1L), 10, false);
        ModHandler.addOreWasherRecipe(GT_Utility.copyAmount(1, stack), 1000, OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, GT_Utility.selectItemInList(0, uEntry.material.mMacerateInto, uEntry.material.mOreByProducts), 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1));
        ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1, stack), (int) Math.min(5000, Math.abs(uEntry.material.getMass() * 20)), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedCentrifuged : OrePrefix.dust, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, GT_Utility.selectItemInList(1, uEntry.material.mMacerateInto, uEntry.material.mOreByProducts), 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1));
        
        if (uEntry.material.contains(SubTag.WASHING_MERCURY))
            GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.Mercury.getFluid(1000), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1), new int[]{10000, 7000, 4000}, 800, 8);
        if (uEntry.material.contains(SubTag.WASHING_SODIUMPERSULFATE))
            GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.SodiumPersulfate.getFluid(1000), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1), new int[]{10000, 7000, 4000}, 800, 8);
        for (Materials tMaterial : uEntry.material.mOreByProducts) {
            if (tMaterial.contains(SubTag.WASHING_MERCURY))
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.Mercury.getFluid(1000), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, tMaterial.mMacerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1), new int[]{10000, 7000, 4000}, 800, 8);
            if (tMaterial.contains(SubTag.WASHING_SODIUMPERSULFATE))
                GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1, stack), Materials.SodiumPersulfate.getFluid(1000), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, tMaterial.mMacerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1), new int[]{10000, 7000, 4000}, 800, 8);
        }
    }
}
