package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;

public class ProcessingRound implements IOreRegistrationHandler {
    public ProcessingRound() {
        OrePrefix.round.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        if (!uEntry.material.contains(SubTag.NO_WORKING)) {
            GT_Values.RA.addLatheRecipe(OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material, 1L), GT_Utility.copyAmount(1L, aStack), null, (int) Math.max(uEntry.material.getMass() / 4L, 1L), 8);
            if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material))
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.round, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefix.nugget.get(uEntry.material)});
        }
//        TODO
//        if (GT_Mod.gregtechproxy.mAE2Integration) {
//            Api.INSTANCE.registries().matterCannon().registerAmmo(OreDictionaryUnifier.get(OrePrefix.round, uEntry.material, 1L), uEntry.material.getMass());
//        }
    }
}
