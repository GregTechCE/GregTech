package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;

public class ProcessingSlab implements IOreRegistrationHandler {
    public ProcessingSlab() {
        OrePrefix.slab.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        if (aOreDictName.startsWith("slabWood")) {
            GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(3L, new Object[]{aStack}), Materials.Creosote.getFluid(1000L), ItemList.RC_Tie_Wood.get(1L, new Object[0]), null, null, null, 200, 4);
        }
    }
}
