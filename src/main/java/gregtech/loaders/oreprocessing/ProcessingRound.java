package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingRound implements IOreRegistrationHandler {

    public ProcessingRound() {
        OrePrefix.round.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {

            RecipeMap.LATHE_RECIPES.recipeBuilder()
                    .inputs(OreDictUnifier.get(OrePrefix.nugget, entry.material, 1))
                    .outputs(GTUtility.copyAmount(1, stack))
                    .duration((int) Math.max(entry.material.getMass() / 4L, 1L))
                    .EUt(8)
                    .buildAndRegister();

            if (!entry.material.hasFlag(NO_UNIFICATION)) {
                ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.round, entry.material),
                        "fX",
                        "X ",
                        'X', OreDictUnifier.get(OrePrefix.nugget, entry.material));
            }
        }
    }
}
