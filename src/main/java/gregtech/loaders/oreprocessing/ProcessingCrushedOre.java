package gregtech.loaders.oreprocessing;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushedOre implements IOreRegistrationHandler {
    public ProcessingCrushedOre() {
		OrePrefix.crushedCentrifuged.add(this);
        OrePrefix.crushedPurified.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
		switch (aPrefix) {
			case crushedCentrifuged:
				GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1L), 10, 16);
				GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1L), OreDictionaryUnifier.get(OrePrefix.dust, GT_Utility.selectItemInList(2, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), 10, false);
				break;
			case crushedPurified:
				GT_ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), (int) Math.min(5000L, Math.abs(aMaterial.getMass() * 20L)), new Object[]{OreDictionaryUnifier.get(OrePrefix.crushedCentrifuged, aMaterial.mMacerateInto, OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1L), 1L), OreDictionaryUnifier.get(OrePrefix.dustTiny, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L)});

				ItemStack tGem = OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 1L);
				if(tGem!=null){
				switch (aMaterial.mName) {
					case "Tanzanite": case "Sapphire": case "Olivine": case "GreenSapphire": case "Opal": case "Amethyst": case "Emerald": case "Ruby":
					case "Amber": case "Diamond": case "FoolsRuby": case "BlueTopaz": case "GarnetRed": case "Topaz": case "Jasper": case "GarnetYellow":
						GT_Values.RA.addSifterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack[]{OreDictionaryUnifier.get(OrePrefix.gemExquisite, aMaterial, tGem, 1L), OreDictionaryUnifier.get(OrePrefix.gemFlawless, aMaterial, tGem, 1L), tGem, OreDictionaryUnifier.get(OrePrefix.gemFlawed, aMaterial, tGem, 1L), OreDictionaryUnifier.get(OrePrefix.gemChipped, aMaterial, tGem, 1L), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, tGem, 1L)}, new int[]{300, 1200, 4500, 1400, 2800, 3500}, 800, 16);
				        if(GT_Mod.gregtechproxy.mMagneticraftRecipes && GregTech_API.mMagneticraft){
				        	//TODO com.cout970.magneticraft.api.access.MgRecipeRegister.registerSifterRecipe(OreDictionaryUnifier.get(OrePrefix.crushedPurified, aMaterial, tGem, 1), OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, tGem, 1), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, tGem, 1), 0.2f);
				        }
						break;
					default:
						GT_Values.RA.addSifterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack[]{OreDictionaryUnifier.get(OrePrefix.gemExquisite, aMaterial, tGem, 1L), OreDictionaryUnifier.get(OrePrefix.gemFlawless, aMaterial, tGem, 1L), tGem, OreDictionaryUnifier.get(OrePrefix.gemFlawed, aMaterial, tGem, 1L), OreDictionaryUnifier.get(OrePrefix.gemChipped, aMaterial, tGem, 1L), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, tGem, 1L)}, new int[]{100, 400, 1500, 2000, 4000, 5000}, 800, 16);
						if(GT_Mod.gregtechproxy.mMagneticraftRecipes && GregTech_API.mMagneticraft){
							//TODO com.cout970.magneticraft.api.access.MgRecipeRegister.registerSifterRecipe(OreDictionaryUnifier.get(OrePrefix.crushedPurified, aMaterial, tGem, 1), OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, tGem, 1), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, tGem, 1), 0.2f);
				        }
				}}
				break;
		}
	}
}
