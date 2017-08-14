package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingSaplings implements IOreRegistrationHandler {
    public ProcessingSaplings() {
        OrePrefix.treeSapling.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2), null, 0, false);
        GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood, 1L), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Wood, 1L), 16, 8);
    }
}
