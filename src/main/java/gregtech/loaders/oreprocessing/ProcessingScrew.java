package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingScrew implements IOreRegistrationHandler {

    public ProcessingScrew() {
        OrePrefix.screw.addProcessingHandler(this);
    }

    @Override
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        if (!entry.material.hasFlag(NO_WORKING)) {

            RecipeMap.LATHE_RECIPES.recipeBuilder()
                    .inputs(OreDictionaryUnifier.get(OrePrefix.bolt, entry.material, 1))
                    .outputs(GTUtility.copyAmount(1, stack))
                    .duration((int) Math.max(entry.material.getMass() / 8L, 1L))
                    .EUt(4)
                    .buildAndRegister();

            if (!entry.material.hasFlag(NO_UNIFICATION)) {
                ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.screw, entry.material),
                        "fX",
                        "X ",
                        'X', OreDictionaryUnifier.get(OrePrefix.bolt, entry.material));
            }
        }
    }
}
