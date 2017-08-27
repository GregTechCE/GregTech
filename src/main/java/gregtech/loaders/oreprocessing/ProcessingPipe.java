package gregtech.loaders.oreprocessing;

import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingPipe implements IOreRegistrationHandler {

	public ProcessingPipe() {
		OrePrefix.pipeLarge.addProcessingHandler(this);
		OrePrefix.pipeMedium.addProcessingHandler(this);
		OrePrefix.pipeSmall.addProcessingHandler(this);
		OrePrefix.pipeRestrictiveHuge.addProcessingHandler(this);
		OrePrefix.pipeRestrictiveLarge.addProcessingHandler(this);
		OrePrefix.pipeRestrictiveMedium.addProcessingHandler(this);
		OrePrefix.pipeRestrictiveSmall.addProcessingHandler(this);
		OrePrefix.pipeRestrictiveTiny.addProcessingHandler(this);
	}

	@Override
	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		ItemStack stack = simpleStack.asItemStack();
		switch (entry.orePrefix) {
			case pipeLarge:
			case pipeMedium:
			case pipeSmall:
				if ((!entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) && ((entry.material.contains(SubTag.WOOD)) || (!entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)))) {

					int amount = 6;
					if (entry.orePrefix == OrePrefix.pipeLarge) amount = 1;
					else if (entry.orePrefix == OrePrefix.pipeMedium) amount = 2;

					String row1 = "PWP";
					if (entry.orePrefix == OrePrefix.pipeLarge) row1 = "PHP";
					else if (entry.orePrefix == OrePrefix.pipeMedium) row1 = "PPP";

					String row2 = "W H";
					if (entry.orePrefix == OrePrefix.pipeLarge
							|| entry.orePrefix == OrePrefix.pipeSmall) row2 = "P P";

					String row3 = "PHP";
					if (entry.orePrefix == OrePrefix.pipeLarge) row3 = "PWP";
					else if (entry.orePrefix == OrePrefix.pipeMedium) row3 = "PPP";

					ModHandler.addMirroredShapedRecipe(GTUtility.copyAmount(amount, stack),
							row1,
							row2,
							row3,
							'P', entry.material == Materials.Wood ? new UnificationEntry(OrePrefix.plank, entry.material) : new UnificationEntry(OrePrefix.plate, entry.material),
							'H', entry.material.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSoftHammer : ToolDictNames.craftingToolHardHammer,
							'W', entry.material.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSaw : ToolDictNames.craftingToolWrench);
				}
				break;
			case pipeRestrictiveHuge:
			case pipeRestrictiveLarge:
			case pipeRestrictiveMedium:
			case pipeRestrictiveSmall:
			case pipeRestrictiveTiny:

				RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
						.inputs(OreDictionaryUnifier.get(OrePrefix.getPrefix(entry.orePrefix.name().replaceFirst("Restrictive", "")), entry.material),
								OreDictionaryUnifier.get(OrePrefix.ring, Materials.Steel, (int) (entry.orePrefix.secondaryMaterial.amount / OrePrefix.ring.materialAmount)))
						.outputs(GTUtility.copyAmount(1, stack))
						.duration((int) (entry.orePrefix.secondaryMaterial.amount * 400L / OrePrefix.ring.materialAmount))
						.EUt(4)
						.buildAndRegister();
				break;
		}
	}
}