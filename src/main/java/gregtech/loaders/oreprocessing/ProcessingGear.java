package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.W;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingGear implements IOreRegistrationHandler {

	public void register() {
		OrePrefix.gear.addProcessingHandler(this);
		OrePrefix.gearSmall.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if(entry.material instanceof SolidMaterial) {
			SolidMaterial material = (SolidMaterial) entry.material;
			ItemStack stack = simpleStack.asItemStack();
			boolean isSmall = entry.orePrefix == OrePrefix.gearSmall;

			if(material.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID)) {
                RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(isSmall ? MetaItems.SHAPE_MOLD_GEAR_SMALL : MetaItems.SHAPE_MOLD_GEAR)
                        .fluidInputs(material.getFluid(L * (isSmall ? 1 : 4)))
                        .outputs(stack)
                        .duration(isSmall ? 20 : 100)
                        .EUt(8)
                        .buildAndRegister();
            }

            if(isSmall) {
			    if(material instanceof MetalMaterial && !material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    ModHandler.addShapedRecipe(stack, "h##", "#P#",
                            'P', OreDictUnifier.get(OrePrefix.plate, material));
                }
            } else {
			    ModHandler.addShapedRecipe(stack, "RPR", "PdP", "RPR",
                        'P', OreDictUnifier.get(OrePrefix.plate, material),
                        'R', OreDictUnifier.get(OrePrefix.stick, material));
            }

		}
	}

}
