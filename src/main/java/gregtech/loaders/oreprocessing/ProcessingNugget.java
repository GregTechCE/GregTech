package gregtech.loaders.oreprocessing;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingNugget implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingNugget() {
        OrePrefixes.nugget.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial == Materials.Iron)
            GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.WroughtIron, 1L));
        GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), aMaterial.contains(SubTag.SMELTING_TO_GEM) ? ItemList.Shape_Mold_Ball.get(0L, new Object[0]) : ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OrePrefixes.gem : OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 200, 2);
        if (aMaterial.mStandardMoltenFluid != null)
            GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Nugget.get(0L, new Object[0]), aMaterial.getMolten(16L), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial, 1L), 16, 4);
        GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
        GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
        if (!aMaterial.contains(SubTag.NO_SMELTING)) {
            GT_Values.RA.addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), ItemList.Shape_Mold_Nugget.get(0L, new Object[0]), GT_Utility.copyAmount(9L, new Object[]{aStack}), 100, 1);
            if ((GT_ModHandler.getSmeltingOutput(GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), false, null) == null) && (GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 1L) != null) && (!GT_ModHandler.addSmeltingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), GT_Utility.copyAmount(9L, new Object[]{aStack})))) {
//                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), new Object[]{aOreDictName});
            }
        }
    }
}
