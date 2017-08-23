package gregtech.loaders.oreprocessing;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;

public class ProcessingLens implements IOreRegistrationHandler {
    public ProcessingLens() {
        OrePrefix.lens.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        RecipeMap.LATHE_RECIPES.recipeBuilder()
                .inputs(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1))
                .outputs(OreDictionaryUnifier.get(OrePrefix.lens, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, uEntry.material, 1))
                .duration((int) Math.max(uEntry.material.getMass() / 2L, 1L))
                .EUt(16)
                .buildAndRegister();
        GregTechAPI.registerCover(stack, new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_LENS, uEntry.material.materialRGB)), new gregtech.common.covers.GT_Cover_Lens(uEntry.material.mColor.mIndex));
    }
}
