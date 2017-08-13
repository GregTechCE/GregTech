package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingRound implements IOreRegistrationHandler {
    public ProcessingRound() {
        OrePrefix.round.add(this);
    }

    @Override
    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aMaterial.contains(SubTag.NO_WORKING)) {
            GT_Values.RA.addLatheRecipe(OreDictionaryUnifier.get(OrePrefix.nugget, aMaterial, 1L), GT_Utility.copyAmount(1L, aStack), null, (int) Math.max(aMaterial.getMass() / 4L, 1L), 8);
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial))
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.round, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
        }
//        TODO
//        if (GT_Mod.gregtechproxy.mAE2Integration) {
//            Api.INSTANCE.registries().matterCannon().registerAmmo(OreDictionaryUnifier.get(OrePrefix.round, aMaterial, 1L), aMaterial.getMass());
//        }
    }
}
