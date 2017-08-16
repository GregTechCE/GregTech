package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushedOre implements IOreRegistrationHandler {
    public ProcessingCrushedOre() {
		OrePrefix.crushedCentrifuged.addProcessingHandler(this);
        OrePrefix.crushedPurified.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
		ItemStack stack = simpleStack.asItemStack();
		switch (uEntry.orePrefix) {
			case crushedCentrifuged:
				if (uEntry.material instanceof SolidMaterial) {
					SolidMaterial solidMaterial = (SolidMaterial) uEntry.material;
					RecipeMap.HAMMER_RECIPES.recipeBuilder()
							.inputs(GT_Utility.copyAmount(1, stack))
							.outputs(OreDictionaryUnifier.get(OrePrefix.dust, solidMaterial.macerateInto))
							.duration(10)
							.EUt(16)
							.buildAndRegister();
					ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, solidMaterial.macerateInto, 1), OreDictionaryUnifier.get(OrePrefix.dust, GT_Utility.selectItemInList(2, solidMaterial.macerateInto, solidMaterial.oreByProducts), 1), 10, false);
				}
				break;
			case crushedPurified:
				if (uEntry.material instanceof SolidMaterial) {
					SolidMaterial solidMaterial = (SolidMaterial) uEntry.material;
					ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1, stack), (int) Math.min(5000L, Math.abs(uEntry.material.getMass() * 20L)), OreDictionaryUnifier.get(OrePrefix.crushedCentrifuged, solidMaterial.macerateInto, OreDictionaryUnifier.get(OrePrefix.dust, solidMaterial.macerateInto, 1), 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, GT_Utility.selectItemInList(1, solidMaterial.macerateInto, solidMaterial.oreByProducts), 1));
				}

				ItemStack tGem = OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1);
				if (tGem!=null){
					switch (uEntry.material.toString()) {
						case "Tanzanite": case "Sapphire": case "Olivine": case "GreenSapphire": case "Opal": case "Amethyst": case "Emerald": case "Ruby":
						case "Amber": case "Diamond": case "FoolsRuby": case "BlueTopaz": case "GarnetRed": case "Topaz": case "Jasper": case "GarnetYellow":
							RecipeMap.SIFTER_RECIPES.recipeBuilder()
									.inputs(GT_Utility.copyAmount(1, stack))
									.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemExquisite, uEntry.material), 300)
									.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemFlawless, uEntry.material), 1200)
									.chancedOutput(tGem, 4500).chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemFlawed, uEntry.material), 1400)
									.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemChipped, uEntry.material), 2800)
									.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material), 3500)
									.duration(800)
									.EUt(16)
									.buildAndRegister();
							//TODO Magneticraft
							//if(GT_Mod.gregtechproxy.mMagneticraftRecipes && GregTech_API.mMagneticraft){
								//com.cout970.magneticraft.api.access.MgRecipeRegister.registerSifterRecipe(OreDictionaryUnifier.get(OrePrefixes.crushedPurified, uEntry.material, tGem, 1), OreDictionaryUnifier.get(OrePrefixes.gem, uEntry.material, tGem, 1), OreDictionaryUnifier.get(OrePrefixes.dust, uEntry.material, tGem, 1), 0.2f);
							//}
							break;
						default:
							RecipeMap.SIFTER_RECIPES.recipeBuilder()
									.inputs(GT_Utility.copyAmount(1, stack))
									.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemExquisite, uEntry.material), 100)
									.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemFlawless, uEntry.material), 400)
									.chancedOutput(tGem, 1500).chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemFlawed, uEntry.material), 2000)
									.chancedOutput(OreDictionaryUnifier.get(OrePrefix.gemChipped, uEntry.material), 4000)
									.chancedOutput(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material), 5000)
									.duration(800)
									.EUt(16)
									.buildAndRegister();
							//TODO Magneticraft
							//if(GT_Mod.gregtechproxy.mMagneticraftRecipes && GregTech_API.mMagneticraft){
								//com.cout970.magneticraft.api.access.MgRecipeRegister.registerSifterRecipe(OreDictionaryUnifier.get(OrePrefixes.crushedPurified, uEntry.material, tGem, 1), OreDictionaryUnifier.get(OrePrefixes.gem, uEntry.material, tGem, 1), OreDictionaryUnifier.get(OrePrefixes.dust, uEntry.material, tGem, 1), 0.2f);
							//}
					}
				}
				break;
		}
	}
}
