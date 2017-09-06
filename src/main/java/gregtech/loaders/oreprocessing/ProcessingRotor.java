package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingRotor implements IOreRegistrationHandler {

    private ProcessingRotor() {}

	public static void register() {
		OrePrefix.rotor.addProcessingHandler(new ProcessingRotor());
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if (entry.material instanceof SolidMaterial && !entry.material.hasFlag(NO_UNIFICATION | NO_WORKING)) {
            SolidMaterial material = (SolidMaterial) entry.material;
            ItemStack stack = simpleStack.asItemStack();
		    ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
		    ItemStack ringStack = OreDictUnifier.get(OrePrefix.ring, material);

            ModHandler.addShapedRecipe(stack, "PhP", "SRf", "PdP",
                'P', plateStack, 'R', ringStack, 'S', OreDictUnifier.get(OrePrefix.screw, material));

            RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(4, plateStack), ringStack)
                .outputs(stack)
                .fluidInputs(Materials.SolderingAlloy.getFluid(32))
                .duration(240)
                .EUt(24)
                .buildAndRegister();
        }
	}

}
