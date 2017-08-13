package gregtech.loaders.oreprocessing;

import gregtech.api.GregTech_API;
import gregtech.api.GT_Values;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_MultiTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.unification.OreDictionaryUnifier;
import net.minecraft.item.ItemStack;

public class ProcessingLens implements IOreRegistrationHandler {
    public ProcessingLens() {
        OrePrefix.lens.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_Values.RA.addLatheRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.lens, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial, 1L), (int) Math.max(aMaterial.getMass() / 2L, 1L), 16);
        GregTech_API.registerCover(aStack, new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_LENS, aMaterial.mRGBa)), new gregtech.common.covers.GT_Cover_Lens(aMaterial.mColor.mIndex));
    }
}
