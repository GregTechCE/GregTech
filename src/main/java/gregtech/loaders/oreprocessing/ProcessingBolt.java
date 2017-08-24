package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.CommonProxy;

public class ProcessingBolt implements IOreRegistrationHandler {
    public ProcessingBolt() {
        OrePrefix.bolt.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
            ModHandler.addCraftingRecipe(GTUtility.copyAmount(2, simpleStack.asItemStack()), CommonProxy.tBits, "s ", " X", Character.valueOf('X'), OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material));
        }
    }
}
