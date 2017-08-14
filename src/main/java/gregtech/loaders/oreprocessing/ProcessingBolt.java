package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;

public class ProcessingBolt implements IOreRegistrationHandler {
    public ProcessingBolt() {
        OrePrefix.bolt.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
            ModHandler.addCraftingRecipe(GT_Utility.copyAmount(2, simpleStack.asItemStack()), GT_Proxy.tBits, "s ", " X", Character.valueOf('X'), OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material));
        }
    }
}
