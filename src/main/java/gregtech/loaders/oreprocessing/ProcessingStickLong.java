package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingStickLong implements IOreRegistrationHandler {
    public ProcessingStickLong() {
        OrePrefixes.stickLong.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aMaterial.contains(SubTag.NO_WORKING)) {
            GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefixes.stick, aMaterial, 2L), null, (int) Math.max(aMaterial.getMass(), 1L), 4);
            if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial)) {
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefixes.stickLong, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefixes.gemFlawless.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefixes.stickLong, aMaterial, 2L), GT_Proxy.tBits, new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefixes.gemExquisite.get(aMaterial)});
            }
        }
        if (!aMaterial.contains(SubTag.NO_SMASHING)) {
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefixes.spring, aMaterial, 1L), 200, 16);
            if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial))
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefixes.stickLong, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"ShS", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial)});
        }
    }
}
