package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushedOre implements IOreRegistrationHandler {

	public ProcessingCrushedOre() {
		OrePrefix.crushedCentrifuged.addProcessingHandler(this);
		OrePrefix.crushedPurified.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		ItemStack stack = simpleStack.asItemStack();
		switch (entry.orePrefix) {
			case crushedCentrifuged:
				if (entry.material instanceof SolidMaterial) {
					SolidMaterial solidMaterial = (SolidMaterial) entry.material;
					RecipeMap.HAMMER_RECIPES.recipeBuilder()
							.inputs(GTUtility.copyAmount(1, stack))
							.outputs(OreDictionaryUnifier.get(OrePrefix.dust, solidMaterial.macerateInto))
							.duration(10)
							.EUt(16)
							.buildAndRegister();

					RecipeMap.MACERATOR_RECIPES.recipeBuilder()
							.inputs(GTUtility.copyAmount(1, stack))
							.outputs(OreDictionaryUnifier.get(OrePrefix.dust, solidMaterial.macerateInto, 1))
							.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, GTUtility.selectItemInList(2, solidMaterial.macerateInto, solidMaterial.oreByProducts), 1), 1000);
				}
				break;
			case crushedPurified:
				if (entry.material instanceof SolidMaterial) {
					SolidMaterial solidMaterial = (SolidMaterial) entry.material;

					ItemStack crushAndCentOre = OreDictionaryUnifier.get(OrePrefix.crushedCentrifuged, solidMaterial.macerateInto);
					if (crushAndCentOre != null) {
						ModHandler.addThermalCentrifugeRecipe(GTUtility.copyAmount(1, stack),
								(int) Math.min(5000L, Math.abs(entry.material.getMass() * 20L)),
								crushAndCentOre,
								OreDictionaryUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, solidMaterial.macerateInto, solidMaterial.oreByProducts), 1));

					} else {
						ModHandler.addThermalCentrifugeRecipe(GTUtility.copyAmount(1, stack),
								(int) Math.min(5000L, Math.abs(entry.material.getMass() * 20L)),
								OreDictionaryUnifier.get(OrePrefix.dust, solidMaterial.macerateInto),
								OreDictionaryUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, solidMaterial.macerateInto, solidMaterial.oreByProducts), 1));

					}
				}

				ItemStack gemStack = OreDictionaryUnifier.get(OrePrefix.gem, entry.material);
				if (gemStack != null) {
					if (entry.material == Materials.Tanzanite
							|| entry.material == Materials.Sapphire
							|| entry.material == Materials.Olivine
							|| entry.material == Materials.GreenSapphire
							|| entry.material == Materials.Opal
							|| entry.material == Materials.Amethyst
							|| entry.material == Materials.Emerald
							|| entry.material == Materials.Ruby
							|| entry.material == Materials.Diamond
							|| entry.material == Materials.BlueTopaz
							|| entry.material == Materials.GarnetRed
							|| entry.material == Materials.Topaz
							|| entry.material == Materials.Jasper
							|| entry.material == Materials.GarnetYellow) {

						RecipeMap.SIFTER_RECIPES.recipeBuilder()
								.inputs(GTUtility.copyAmount(1, stack))
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemExquisite, entry.material), 300)
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemFlawless, entry.material), 1200)
								.chancedOutput(gemStack, 4500).chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemFlawed, entry.material), 1400)
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemChipped, entry.material), 2800)
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, entry.material), 3500)
								.duration(800)
								.EUt(16)
								.buildAndRegister();
					} else {
						RecipeMap.SIFTER_RECIPES.recipeBuilder()
								.inputs(GTUtility.copyAmount(1, stack))
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemExquisite, entry.material), 100)
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemFlawless, entry.material), 400)
								.chancedOutput(gemStack, 1500).chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemFlawed, entry.material), 2000)
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemChipped, entry.material), 4000)
								.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, entry.material), 5000)
								.duration(800)
								.EUt(16)
								.buildAndRegister();
					}
				}
				break;
		}
	}
}
