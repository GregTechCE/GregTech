package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingCraftingLens implements IOreRegistrationHandler {

	public void register() {
		OrePrefix.craftingLens.addProcessingHandler(this);
	}

	@Override
	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		ItemStack stack = simpleStack.asItemStack();

		if (entry.material == MarkerMaterials.Color.Red) {
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.foil, Materials.Copper))
					.notConsumable(stack)
					.outputs(MetaItems.CIRCUIT_PARTS_WIRING_BASIC.getStackForm())
					.duration(64)
					.EUt(30)
					.buildAndRegister();
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.foil, Materials.AnnealedCopper))
					.notConsumable(stack)
					.outputs(MetaItems.CIRCUIT_PARTS_WIRING_BASIC.getStackForm())
					.duration(64)
					.EUt(30)
					.buildAndRegister();
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.foil, Materials.Gold))
					.notConsumable(stack)
					.outputs(MetaItems.CIRCUIT_PARTS_WIRING_ADVANCED.getStackForm())
					.duration(64)
					.EUt(120)
					.buildAndRegister();
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.foil, Materials.Electrum))
					.notConsumable(stack)
					.outputs(MetaItems.CIRCUIT_PARTS_WIRING_ADVANCED.getStackForm())
					.duration(64)
					.EUt(120)
					.buildAndRegister();
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.foil, Materials.Platinum))
					.notConsumable(stack)
					.outputs(MetaItems.CIRCUIT_PARTS_WIRING_ELITE.getStackForm())
					.duration(64)
					.EUt(480)
					.buildAndRegister();
		} else if (entry.material == MarkerMaterials.Color.Green) {
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Olivine))
					.notConsumable(stack)
					.outputs(MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm())
					.duration(256)
					.EUt(480)
					.buildAndRegister();
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Emerald))
					.notConsumable(stack)
					.outputs(MetaItems.CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE.getStackForm())
					.duration(256)
					.EUt(480)
					.buildAndRegister();
		} else if (entry.material == MarkerMaterials.Color.White) {
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(new ItemStack(Blocks.SANDSTONE, 1, 2))
					.notConsumable(stack)
					.outputs(new ItemStack(Blocks.SANDSTONE, 1, 1))
					.duration(20)
					.EUt(16)
					.buildAndRegister();
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(new ItemStack(Blocks.STONE))
					.notConsumable(stack)
					.outputs(new ItemStack(Blocks.STONEBRICK, 1, 3))
					.duration(50)
					.EUt(16)
					.buildAndRegister();
			RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
					.inputs(new ItemStack(Blocks.QUARTZ_BLOCK))
					.notConsumable(stack)
					.outputs(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1))
					.duration(50)
					.EUt(16)
					.buildAndRegister();
		}
	}
}
