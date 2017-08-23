package gregtech.loaders.oreprocessing;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeRegistrator;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingCompressed implements IOreRegistrationHandler {
    public ProcessingCompressed() {
        OrePrefix.compressed.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        ModHandler.removeRecipeByOutput(stack);
        GregTechAPI.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[72], uEntry.material.materialRGB), null);
        RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1, stack), null, false);
    }
}
