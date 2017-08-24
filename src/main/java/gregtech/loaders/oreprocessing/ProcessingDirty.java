package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
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
        if (uEntry.material instanceof SolidMaterial) {
            Material macerateInto = ((SolidMaterial) uEntry.material).macerateInto;
            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, stack))
                    .outputs(OreDictionaryUnifier.get(OrePrefix.dustImpure, macerateInto, 1))
                    .duration(10)
                    .EUt(16)
                    .buildAndRegister();
            ModHandler.addPulverisationRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, macerateInto, OreDictionaryUnifier.get(OrePrefix.dust, macerateInto, 1), 1), OreDictionaryUnifier.get(OrePrefix.dust, GTUtility.selectItemInList(0, macerateInto, uEntry.material.mOreByProducts), 1L), 10, false);
            ModHandler.addOreWasherRecipe(GTUtility.copyAmount(1, stack), 1000, OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(0, macerateInto, macerateInto), 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1));
            ModHandler.addThermalCentrifugeRecipe(GTUtility.copyAmount(1, stack), (int) Math.min(5000, Math.abs(uEntry.material.getMass() * 20)), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedCentrifuged : OrePrefix.dust, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, macerateInto, macerateInto), 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1));

            if (uEntry.material.contains(SubTag.WASHING_MERCURY))
                GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.Mercury.getFluid(1000), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, macerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1), new int[]{10000, 7000, 4000}, 800, 8);
            if (uEntry.material.contains(SubTag.WASHING_SODIUMPERSULFATE))
                GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.SodiumPersulfate.getFluid(1000), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, macerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1), new int[]{10000, 7000, 4000}, 800, 8);
            for (Materials tMaterial : uEntry.material.mOreByProducts) {
                if (tMaterial.contains(SubTag.WASHING_MERCURY))
                    GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.Mercury.getFluid(1000), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, tMaterial.mMacerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1), new int[]{10000, 7000, 4000}, 800, 8);
                if (tMaterial.contains(SubTag.WASHING_SODIUMPERSULFATE))
                    GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.SodiumPersulfate.getFluid(1000), OreDictionaryUnifier.get(uEntry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, tMaterial.mMacerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Stone, 1), new int[]{10000, 7000, 4000}, 800, 8);
            }
        }
    }
}
