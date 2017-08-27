package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;

public class ProcessingLens implements IOreRegistrationHandler {

    public ProcessingLens() {
        OrePrefix.lens.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {

        RecipeMap.LATHE_RECIPES.recipeBuilder()
                .inputs(OreDictionaryUnifier.get(OrePrefix.plate, entry.material))
                .outputs(OreDictionaryUnifier.get(OrePrefix.lens, entry.material), OreDictionaryUnifier.get(OrePrefix.dustSmall, entry.material))
                .duration((int) Math.max(entry.material.getMass() / 2L, 1L))
                .EUt(16)
                .buildAndRegister();
    }
}
