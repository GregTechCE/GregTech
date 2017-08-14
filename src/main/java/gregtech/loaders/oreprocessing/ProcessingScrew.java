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

public class ProcessingScrew implements IOreRegistrationHandler {
    public ProcessingScrew() {
        OrePrefix.screw.addProcessingHandler(this);
    }

    @Override
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (!uEntry.material.contains(SubTag.NO_WORKING)) {
            GT_Values.RA.addLatheRecipe(OreDictionaryUnifier.get(OrePrefix.bolt, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), null, (int) Math.max(uEntry.material.getMass() / 8L, 1L), 4);
            if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material))
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.screw, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefix.bolt.get(uEntry.material)});
        }
    }
}
