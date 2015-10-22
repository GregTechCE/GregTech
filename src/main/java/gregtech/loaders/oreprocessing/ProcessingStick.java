package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingStick implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingStick() {
        OrePrefixes.stick.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_WORKING))
            GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 4L), null, (int) Math.max(aMaterial.getMass() * 2L, 1L), 4);
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
            GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1L), (int) Math.max(aMaterial.getMass(), 1L), 16);
            GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_Utility.copy(new Object[]{GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 4L)}), 50, 4);
        }
    }
}
