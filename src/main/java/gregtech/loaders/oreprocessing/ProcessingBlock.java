package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;

public class ProcessingBlock implements IOreRegistrationHandler {

	public ProcessingBlock() {
		OrePrefix.block.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		ItemStack blockStack = simpleStack.asItemStack();
		RecipeMap.CUTTER_RECIPES.recipeBuilder()
				.inputs(GTUtility.copyAmount(1, blockStack))
				.outputs(OreDictionaryUnifier.get(OrePrefix.plate, entry.material, 9))
				.duration((int) Math.max(entry.material.getMass() * 10L, 1L))
				.EUt(30)
				.buildAndRegister();

		ItemStack ingotStack = OreDictionaryUnifier.get(OrePrefix.ingot, entry.material);
		ItemStack gemStack = OreDictionaryUnifier.get(OrePrefix.gem, entry.material);
		ItemStack dustStack = OreDictionaryUnifier.get(OrePrefix.dust, entry.material);
		ModHandler.removeRecipe(blockStack);

		if (ingotStack != null)
			ModHandler.removeRecipe(ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack);
		if (gemStack != null)
			ModHandler.removeRecipe(gemStack, gemStack, gemStack, gemStack, gemStack, gemStack, gemStack, gemStack, gemStack);
		if (dustStack != null) {
			ModHandler.removeRecipe(dustStack, dustStack, dustStack, dustStack, dustStack, dustStack, dustStack, dustStack, dustStack);
		}
		if (entry.material instanceof FluidMaterial) {
			FluidMaterial fluidMaterial = (FluidMaterial) entry.material;
			RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
					.notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
					.fluidInputs(fluidMaterial.getFluid(L * 9))
					.outputs(OreDictionaryUnifier.get(OrePrefix.block, entry.material))
					.duration(288)
					.EUt(8)
					.buildAndRegister();
		}
		if (false) { //TODO CONFIG GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.storageblockcrafting, OreDictionaryUnifier.get(OrePrefix.block, entry.material).toString(), false)
			if (ingotStack == null && gemStack == null && dustStack != null)
				ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.block, entry.material), "XXX", "XXX", "XXX", 'X', OreDictionaryUnifier.get(OrePrefix.dust, entry.material));
			if (gemStack != null)
				ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.block, entry.material), "XXX", "XXX", "XXX", 'X', OreDictionaryUnifier.get(OrePrefix.gem, entry.material));
			if (ingotStack != null) {
				ModHandler.addShapedRecipe(OreDictionaryUnifier.get(OrePrefix.block, entry.material), "XXX", "XXX", "XXX", 'X', OreDictionaryUnifier.get(OrePrefix.ingot, entry.material));
			}
		}
		if (ingotStack != null) ingotStack.stackSize = 9;
		if (gemStack != null) gemStack.stackSize = 9;
		if (dustStack != null) dustStack.stackSize = 9;
		RecipeMap.HAMMER_RECIPES.recipeBuilder()
				.inputs(blockStack)
				.outputs(gemStack)
				.duration(100)
				.EUt(24)
				.buildAndRegister();

		if (gemStack != null) { //TODO CONFIG GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.storageblockdecrafting, OreDictionaryUnifier.get(OrePrefix.block, entry.material).toString(), gemStack != null)
			if (dustStack != null)
				ModHandler.addShapelessRecipe(dustStack, OreDictionaryUnifier.get(OrePrefix.block, entry.material));
			if (gemStack != null)
				ModHandler.addShapelessRecipe(gemStack, OreDictionaryUnifier.get(OrePrefix.block, entry.material));
			if (ingotStack != null) {
				ModHandler.addShapelessRecipe(ingotStack, OreDictionaryUnifier.get(OrePrefix.block, entry.material));
			}
		}
		if (!OrePrefix.block.isIgnored(entry.material)) {
			RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
					.inputs(OreDictionaryUnifier.get(OrePrefix.ingot, entry.material, 9))
					.outputs(OreDictionaryUnifier.get(OrePrefix.block, entry.material))
					.buildAndRegister();
		}

		if (entry.material == Materials.Steel) {
			RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, blockStack))
					.notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
					.outputs(OreDictionaryUnifier.get(OrePrefix.stick, entry.material))
					.duration(1280)
					.EUt(120)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.coal_ball, 8), GTUtility.copyAmount(1, blockStack))
					.outputs(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.coal_chunk, 1))
					.duration(400)
					.EUt(4)
					.buildAndRegister();
		} else if (entry.material == Materials.WroughtIron || entry.material == Materials.Iron) {
			RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
					.inputs(GTUtility.copyAmount(1, blockStack))
					.notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
					.outputs(OreDictionaryUnifier.get(OrePrefix.stick, entry.material))
					.duration(640)
					.EUt(120)
					.buildAndRegister();

			RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
					.inputs(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.coal_ball, 8), GTUtility.copyAmount(1, blockStack))
					.outputs(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.coal_chunk, 1))
					.duration(400)
					.EUt(4)
					.buildAndRegister();
		} else if (entry.material == Materials.Mercury) {
			System.err.println("'blockQuickSilver'?, In which Ice Desert can you actually place this as a solid Block?");
		}
	}
}
