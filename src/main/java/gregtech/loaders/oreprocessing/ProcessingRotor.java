package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.GT_Proxy;

public class ProcessingRotor implements IOreRegistrationHandler {
    public ProcessingRotor() {
        OrePrefix.rotor.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.rotor, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"PhP", "SRf", "PdP", Character.valueOf('P'), uEntry.material == Materials.Wood ? OrePrefix.plank.get(uEntry.material) : OrePrefix.plate.get(uEntry.material), Character.valueOf('R'), OrePrefix.ring.get(uEntry.material), Character.valueOf('S'), OrePrefix.screw.get(uEntry.material)});
            GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4L), OreDictionaryUnifier.get(OrePrefix.ring, uEntry.material, 1L), Materials.Tin.getMolten(32), OreDictionaryUnifier.get(OrePrefix.rotor, uEntry.material, 1L), 240, 24);
            GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4L), OreDictionaryUnifier.get(OrePrefix.ring, uEntry.material, 1L), Materials.Lead.getMolten(48), OreDictionaryUnifier.get(OrePrefix.rotor, uEntry.material, 1L), 240, 24);
            GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4L), OreDictionaryUnifier.get(OrePrefix.ring, uEntry.material, 1L), Materials.SolderingAlloy.getMolten(16), OreDictionaryUnifier.get(OrePrefix.rotor, uEntry.material, 1L), 240, 24);
        }
    }
}
