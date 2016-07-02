package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushedPurified implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingCrushedPurified() {
        OrePrefixes.crushedPurified.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), (int) Math.min(5000L, Math.abs(aMaterial.getMass() * 20L)), new Object[]{GT_OreDictUnificator.get(OrePrefixes.crushedCentrifuged, aMaterial.mMacerateInto, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L)});
        ItemStack tGem = GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);
        if (tGem != null)
        	if(aMaterial == Materials.Tanzanite||
        	aMaterial == Materials.Sapphire||
        	aMaterial == Materials.Olivine||
        	aMaterial == Materials.GreenSapphire||
        	aMaterial == Materials.Opal||
        	aMaterial == Materials.Amethyst||
        	aMaterial == Materials.Emerald||
        	aMaterial == Materials.Ruby||
        	aMaterial == Materials.Amber||
        	aMaterial == Materials.Diamond||
        	aMaterial == Materials.FoolsRuby||
        	aMaterial == Materials.BlueTopaz||
        	aMaterial == Materials.GarnetRed||
        	aMaterial == Materials.Topaz||
        	aMaterial == Materials.Jasper||
        	aMaterial == Materials.GarnetYellow){
        		GT_Values.RA.addSifterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gemExquisite, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, tGem, 1L), tGem, GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, tGem, 1L)}, new int[]{300, 1200, 4500, 1400, 2800, 3500}, 800, 16);
            		
        	}else{
        		GT_Values.RA.addSifterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gemExquisite, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, tGem, 1L), tGem, GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, tGem, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, tGem, 1L)}, new int[]{100, 400, 1500, 2000, 4000, 5000}, 800, 16);
        	}
       }
}
