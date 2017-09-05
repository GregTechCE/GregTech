package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.M;

public class ProcessingPolarizing implements IOreRegistrationHandler {

    public void register() {
        for (OrePrefix prefix : OrePrefix.values()) {
            if (prefix.materialAmount > 0L && prefix.generationCondition != null) {
                prefix.addProcessingHandler(this);
            }
        }
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        if(entry.material instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) entry.material;

            if(material.magneticMaterial != null) {
                ItemStack magneticStack = OreDictUnifier.get(entry.orePrefix, material.magneticMaterial);

                RecipeMap.POLARIZER_RECIPES.recipeBuilder() //polarizing
                        .inputs(stack)
                        .outputs(magneticStack)
                        .duration(16)
                        .EUt(16)
                        .buildAndRegister();

                ModHandler.addSmeltingRecipe(magneticStack, stack); //de-magnetizing
            }
        }
    }

}
