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
import net.minecraft.item.ItemStack;

public class ProcessingFineWire implements IOreRegistrationHandler {
    public ProcessingFineWire() {
        OrePrefix.wireFine.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (!uEntry.material.contains(SubTag.NO_SMASHING)) {
            GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1), GT_Utility.copy(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 2), GT_Utility.copyAmount(1, stack)), 100, 4);
            GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 1), GT_Utility.copy(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 1), GT_Utility.copyAmount(1, stack)), 50, 4);
        }
        if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
            ModHandler.addCraftingRecipe(GT_Utility.copyAmount(1, stack), GT_Proxy.tBits, new Object[]{"Xx", Character.valueOf('X'), OrePrefix.foil.get(uEntry.material)});
        }
    }
}