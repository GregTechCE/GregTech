package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;

public class ProcessingSaplings implements IOreRegistrationHandler {

    private ProcessingSaplings() {}

    public static void register() {
        OrePrefix.treeSapling.addProcessingHandler(new ProcessingSaplings());
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if(entry.material instanceof DustMaterial) {
            DustMaterial material = (DustMaterial) entry.material;
            ItemStack stack = simpleStack.asItemStack();

            RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(OreDictUnifier.get(OrePrefix.dustSmall, material, 2))
                .buildAndRegister();

            RecipeMap.LATHE_RECIPES.recipeBuilder()
                .inputs(stack)
                .outputs(OreDictUnifier.get(OrePrefix.stick, material), //can be null, though
                    OreDictUnifier.get(OrePrefix.dustTiny, material))
                .duration(16)
                .EUt(8)
                .buildAndRegister();

        }
    }

}
