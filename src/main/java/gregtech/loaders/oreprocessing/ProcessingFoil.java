package gregtech.loaders.oreprocessing;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingFoil implements IOreRegistrationHandler {
    public ProcessingFoil() {
        OrePrefix.foil.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
            RecipeMap.BENDER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(1, OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4)))
                    .outputs(OreDictionaryUnifier.get(OrePrefix.foil, uEntry.material, 4))
                    .duration((int) Math.max(uEntry.material.getMass(), 1L))
                    .EUt(24)
                    .buildAndRegister();
        }
        GregTechAPI.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[70], uEntry.material.materialRGB), null);
    }
}
