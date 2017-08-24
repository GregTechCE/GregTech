package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.CommonProxy;

public class ProcessingRotor implements IOreRegistrationHandler {
    public ProcessingRotor() {
        OrePrefix.rotor.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.rotor, uEntry.material, 1), CommonProxy.tBits, new Object[]{"PhP", "SRf", "PdP", Character.valueOf('P'), uEntry.material == Materials.Wood ? OrePrefix.plank.get(uEntry.material) : OrePrefix.plate.get(uEntry.material), Character.valueOf('R'), OrePrefix.ring.get(uEntry.material), Character.valueOf('S'), OrePrefix.screw.get(uEntry.material)});
            GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4L), OreDictionaryUnifier.get(OrePrefix.ring, uEntry.material, 1L), Materials.Tin.getMolten(32), OreDictionaryUnifier.get(OrePrefix.rotor, uEntry.material, 1L), 240, 24);
            GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4L), OreDictionaryUnifier.get(OrePrefix.ring, uEntry.material, 1L), Materials.Lead.getMolten(48), OreDictionaryUnifier.get(OrePrefix.rotor, uEntry.material, 1L), 240, 24);
            GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4L), OreDictionaryUnifier.get(OrePrefix.ring, uEntry.material, 1L), Materials.SolderingAlloy.getMolten(16), OreDictionaryUnifier.get(OrePrefix.rotor, uEntry.material, 1L), 240, 24);
        }
    }
}
