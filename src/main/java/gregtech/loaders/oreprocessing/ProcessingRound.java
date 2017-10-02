package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingRound implements IOreRegistrationHandler {

    private ProcessingRound() {}

    public static void register() {
        OrePrefix.round.addProcessingHandler(new ProcessingRound());
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if (entry.material instanceof MetalMaterial && !entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
            ItemStack stack = simpleStack.asItemStack();
            ItemStack nuggetStack = OreDictUnifier.get(OrePrefix.nugget, entry.material);

            RecipeMap.LATHE_RECIPES.recipeBuilder()
                    .inputs(nuggetStack)
                    .outputs(stack)
                    .duration((int) (entry.material.getMass() / 4L))
                    .EUt(8)
                    .buildAndRegister();

            ModHandler.addShapedRecipe("round_" + entry.material, stack, "fX#", "X##", 'X', nuggetStack);
        }
    }

}
