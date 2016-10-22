package gregtech.loaders.oreprocessing;

import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushedOre implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingCrushedOre() {
		OrePrefixes.crushedCentrifuged.add(this);
        OrePrefixes.crushedPurified.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
		switch (aPrefix) {
			case crushedCentrifuged:
				GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), 10, 16);
				GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, GT_Utility.selectItemInList(2, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), 10, false);
				break;
			case crushedPurified:
				GT_ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), (int) Math.min(5000L, Math.abs(aMaterial.getMass() * 20L)), new Object[]{GT_OreDictUnificator.get(OrePrefixes.crushedCentrifuged, aMaterial.mMacerateInto, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L)});

				ItemStack tGem = GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);
				if(tGem!=null){
				switch (aMaterial.mName) {
					case "Tanzanite": case "Sapphire": case "Olivine": case "GreenSapphire": case "Opal": case "Amethyst": case "Emerald": case "Ruby":
					case "Amber": case "Diamond": case "FoolsRuby": case "BlueTopaz": case "GarnetRed": case "Topaz": case "Jasper": case "GarnetYellow":
						GT_Values.RA.addSifterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gemExquisite, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, tGem, 1L), tGem, GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, tGem, 1L)}, new int[]{300, 1200, 4500, 1400, 2800, 3500}, 800, 16);
				        if(GT_Mod.gregtechproxy.mMagneticraftRecipes && GregTech_API.mMagneticraft){
				        	com.cout970.magneticraft.api.access.MgRecipeRegister.registerSifterRecipe(GT_OreDictUnificator.get(OrePrefixes.crushedPurified, aMaterial, tGem, 1), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, tGem, 1), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, tGem, 1), 0.2f);
				        }
						break;
					default:
						GT_Values.RA.addSifterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gemExquisite, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, tGem, 1L), tGem, GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, tGem, 1L)}, new int[]{100, 400, 1500, 2000, 4000, 5000}, 800, 16);
						if(GT_Mod.gregtechproxy.mMagneticraftRecipes && GregTech_API.mMagneticraft){
							com.cout970.magneticraft.api.access.MgRecipeRegister.registerSifterRecipe(GT_OreDictUnificator.get(OrePrefixes.crushedPurified, aMaterial, tGem, 1), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, tGem, 1), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, tGem, 1), 0.2f);
				        }
				}}
				break;
		}
	}
}
