package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingFineWire implements IOreRegistrationHandler {
    public ProcessingFineWire() {
        OrePrefix.wireFine.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
            GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), GT_Utility.copy(new Object[]{OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 2L), GT_Utility.copyAmount(1L, new Object[]{aStack})}), 100, 4);
            GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.stick, aMaterial, 1L), GT_Utility.copy(new Object[]{OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack})}), 50, 4);
        }
        if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_WORKING)) {
            GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_Proxy.tBits, new Object[]{"Xx", Character.valueOf('X'), OrePrefix.foil.get(aMaterial)});
        }
    }
}