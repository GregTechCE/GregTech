package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingFoil implements IOreRegistrationHandler {
    public ProcessingFoil() {
        OrePrefix.foil.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (!uEntry.material.contains(SubTag.NO_SMASHING)) {
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1, OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4)), OreDictionaryUnifier.get(OrePrefix.foil, uEntry.material, 4), (int) Math.max(uEntry.material.getMass(), 1L), 24);
        }
        GregTech_API.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[70], uEntry.material.materialRGB), null);
    }
}
