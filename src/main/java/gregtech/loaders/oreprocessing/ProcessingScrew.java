package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingScrew implements IOreRegistrationHandler {

    private ProcessingScrew() {}

    public static void register() {
        OrePrefix.screw.addProcessingHandler(new ProcessingScrew());
    }

    @Override
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if (entry.material instanceof MetalMaterial && !entry.material.hasFlag(NO_WORKING)) {
            MetalMaterial material = (MetalMaterial) entry.material;
            ItemStack stack = simpleStack.asItemStack();
            ItemStack boltStack = OreDictUnifier.get(OrePrefix.bolt, material);

            RecipeMap.LATHE_RECIPES.recipeBuilder()
                .inputs(boltStack)
                .outputs(stack)
                .duration((int) (entry.material.getMass() / 8L))
                .EUt(4)
                .buildAndRegister();

            ModHandler.addShapedRecipe("screw_" + material, stack, "fX", "X#", 'X', boltStack);
        }
    }
}
