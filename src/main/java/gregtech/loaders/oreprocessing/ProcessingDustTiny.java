package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingDustTiny implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingDustTiny() {
        OrePrefixes.dustTiny.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), ItemList.Schematic_Dust.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 100, 4);
        if (!aMaterial.mBlastFurnaceRequired) {
            GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
            if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
                GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[]{aStack}), aMaterial, aPrefix.mMaterialAmount, null, null, null);
            }
        }
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMELTING)) {
            if (aMaterial.mBlastFurnaceRequired) {
                GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.mSmeltInto, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), null, (int) Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
                GT_ModHandler.removeFurnaceSmelting(aStack);
            } else {
                GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 1L));
                GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 130, 3, true);
            }
        }
    }
}
