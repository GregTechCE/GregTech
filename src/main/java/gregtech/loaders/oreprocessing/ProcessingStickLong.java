package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingStickLong implements IOreRegistrationHandler {
    public ProcessingStickLong() {
        OrePrefix.stickLong.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (!uEntry.material.contains(SubTag.NO_WORKING)) {
            GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 2), null, (int) Math.max(uEntry.material.getMass(), 1L), 4);
            if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefix.gemFlawless.get(uEntry.material)});
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefix.gemExquisite.get(uEntry.material)});
            }
        }
        if (!uEntry.material.contains(SubTag.NO_SMASHING)) {
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.spring, uEntry.material, 1), 200, 16);
            if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material))
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"ShS", Character.valueOf('S'), OrePrefix.stick.get(uEntry.material)});
        }
    }
}
