package gregtech.loaders.oreprocessing;

import gregtech.api.GregTech_API;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingCompressed implements IOreRegistrationHandler {
    public ProcessingCompressed() {
        OrePrefix.compressed.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.removeRecipeByOutput(aStack);
        GregTech_API.registerCover(aStack, new GT_RenderedTexture(aMaterial.mIconSet.mTextures[72], aMaterial.mRGBa), null);
        GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, aStack), null, false);
    }
}
