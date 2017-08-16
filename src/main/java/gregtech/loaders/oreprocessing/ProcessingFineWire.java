package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingFineWire implements IOreRegistrationHandler {
    public ProcessingFineWire() {
        OrePrefix.wireFine.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
            RecipeMap.WIREMILL_RECIPES.recipeBuilder()
                    .inputs(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1))
                    .outputs(GT_Utility.copy(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 2), GT_Utility.copyAmount(1, stack)))
                    .duration(100)
                    .EUt(4)
                    .buildAndRegister();
            RecipeMap.WIREMILL_RECIPES.recipeBuilder()
                    .inputs(OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 1))
                    .outputs(GT_Utility.copy(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 1), GT_Utility.copyAmount(1, stack)))
                    .duration(50)
                    .EUt(4)
                    .buildAndRegister();
        }
        if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
            ModHandler.addCraftingRecipe(GT_Utility.copyAmount(1, stack), GT_Proxy.tBits, "Xx", Character.valueOf('X'), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material));
        }
    }
}