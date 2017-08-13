package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingRotor implements IOreRegistrationHandler {
    public ProcessingRotor() {
        OrePrefix.rotor.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_WORKING)) {
            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.rotor, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"PhP", "SRf", "PdP", Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefix.plank.get(aMaterial) : OrePrefix.plate.get(aMaterial), Character.valueOf('R'), OrePrefix.ring.get(aMaterial), Character.valueOf('S'), OrePrefix.screw.get(aMaterial)});
            GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictionaryUnifier.get(OrePrefix.ring, aMaterial, 1L), Materials.Tin.getMolten(32), OreDictionaryUnifier.get(OrePrefix.rotor, aMaterial, 1L), 240, 24);
            GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictionaryUnifier.get(OrePrefix.ring, aMaterial, 1L), Materials.Lead.getMolten(48), OreDictionaryUnifier.get(OrePrefix.rotor, aMaterial, 1L), 240, 24);
            GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictionaryUnifier.get(OrePrefix.ring, aMaterial, 1L), Materials.SolderingAlloy.getMolten(16), OreDictionaryUnifier.get(OrePrefix.rotor, aMaterial, 1L), 240, 24);
        }
    }
}
