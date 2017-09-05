package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;

public class ProcessingBlock implements IOreRegistrationHandler {

	public void register() {
		OrePrefix.block.addProcessingHandler(this);
		OrePrefix.stone.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if(entry.material instanceof DustMaterial) {
            ItemStack blockStack = simpleStack.asItemStack();

			if (entry.material.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID)) {
				FluidMaterial fluidMaterial = (FluidMaterial) entry.material;
				RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
						.notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
						.fluidInputs(fluidMaterial.getFluid(L * 9))
						.outputs(blockStack)
						.duration(180)
						.EUt(8)
						.buildAndRegister();
			}

			if(entry.material.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE)) {
			    ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, entry.material);
			    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(blockStack)
                        .outputs(GTUtility.copyAmount(9, plateStack))
                        .duration((int) (entry.material.getMass() * 10L))
                        .EUt(30)
                        .buildAndRegister();
            }

			ItemStack blockComponent;
			if(entry.material instanceof GemMaterial) {
				blockComponent = OreDictUnifier.get(OrePrefix.gem, entry.material);
			} else if(entry.material instanceof MetalMaterial) {
				blockComponent = OreDictUnifier.get(OrePrefix.ingot, entry.material);
			} else {
				blockComponent = OreDictUnifier.get(OrePrefix.dust, entry.material);
			}
			ModHandler.addShapedRecipe(blockStack,
					"XXX",
					"XXX",
					"XXX",
					'X', blockComponent);
			ModHandler.addShapelessRecipe(GTUtility.copyAmount(9, blockComponent), blockStack);
		}
	}

}
