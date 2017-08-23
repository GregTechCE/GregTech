package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingPure implements IOreRegistrationHandler {
    public ProcessingPure() {
        OrePrefix.crushedPurified.add(this);
        OrePrefix.cleanGravel.add(this);
        OrePrefix.reduced.add(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        GTValues.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustPure, uEntry.material.mMacerateInto, 1L), 10, 16);
        ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustPure, uEntry.material.mMacerateInto, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material.mMacerateInto, 1L), 1L), OreDictionaryUnifier.get(OrePrefix.dust, GT_Utility.selectItemInList(1, uEntry.material.mMacerateInto, uEntry.material.mOreByProducts), 1L), 10, false);
    }
}
