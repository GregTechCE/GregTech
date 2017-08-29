package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;

public class ProcessingLens implements IOreRegistrationHandler {

    public void init() {
        OrePrefix.lens.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if(entry.material instanceof GemMaterial) {
            RecipeMap.LATHE_RECIPES.recipeBuilder()
                    .inputs(OreDictUnifier.get(OrePrefix.plate, entry.material))
                    .outputs(OreDictUnifier.get(OrePrefix.lens, entry.material), OreDictUnifier.get(OrePrefix.dustSmall, entry.material))
                    .duration((int) (entry.material.getMass() / 2L))
                    .EUt(16)
                    .buildAndRegister();
        }
    }

}
