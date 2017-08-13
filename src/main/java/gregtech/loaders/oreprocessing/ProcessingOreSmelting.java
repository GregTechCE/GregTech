package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingOreSmelting implements IOreRegistrationHandler {
    private final OrePrefix[] mSmeltingPrefixes = {OrePrefix.crushed, OrePrefix.crushedPurified, OrePrefix.crushedCentrifuged, OrePrefix.dustImpure, OrePrefix.dustPure, OrePrefix.dustRefined};

    public ProcessingOreSmelting() {
        for (OrePrefix tPrefix : this.mSmeltingPrefixes) tPrefix.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.removeFurnaceSmelting(aStack);
        if (!aMaterial.contains(SubTag.NO_SMELTING)) {
            if ((aMaterial.mBlastFurnaceRequired) || (aMaterial.mDirectSmelting.mBlastFurnaceRequired)) {
                GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, aMaterial, OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), 1L) : OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), null, (int) Math.max(aMaterial.getMass() / 4L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
                if (aMaterial.mBlastFurnaceTemp <= 1000)
                    GT_ModHandler.addRCBlastFurnaceRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), aMaterial.mBlastFurnaceTemp * 2);
            } else {
                switch (aPrefix) {
                    case crushed:
                    case crushedPurified:
                    case crushedCentrifuged:
                        ItemStack tStack = OreDictionaryUnifier.get(OrePrefix.nugget, aMaterial.mDirectSmelting, aMaterial.mDirectSmelting == aMaterial ? 10L : 3L);
                        if (tStack == null)
                            tStack = OreDictionaryUnifier.get(aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OrePrefix.gem : OrePrefix.ingot, aMaterial.mDirectSmelting, 1L);
                        if ((tStack == null) && (!aMaterial.contains(SubTag.SMELTING_TO_GEM)))
                            tStack = OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial.mDirectSmelting, 1L);
                        GT_ModHandler.addSmeltingRecipe(aStack, tStack);
                        break;
                    default:
                        GT_ModHandler.addSmeltingRecipe(aStack, OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial.mDirectSmelting, 1L));
                }
            }
        }
    }
}
