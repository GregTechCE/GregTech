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

public class ProcessingStick implements IOreRegistrationHandler {
    public ProcessingStick() {
        OrePrefix.stick.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_WORKING)) {
            GT_Values.RA.addLatheRecipe(aMaterial.contains(SubTag.CRYSTAL) ? OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 1L) : OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.stick, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial.mMacerateInto, 2L), (int) Math.max(aMaterial.getMass() * 5L, 1L), 16);
            GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.bolt, aMaterial, 4L), null, (int) Math.max(aMaterial.getMass() * 2L, 1L), 4);
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial)) {
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stick, aMaterial, 2L), GT_Proxy.tBits, new Object[]{"s", "X", Character.valueOf('X'), OrePrefix.stickLong.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stick, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"f ", " X", Character.valueOf('X'), OrePrefix.ingot.get(aMaterial)});
            }
        }
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
            GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.stickLong, aMaterial, 1L), (int) Math.max(aMaterial.getMass(), 1L), 16);
        }
    }
}
