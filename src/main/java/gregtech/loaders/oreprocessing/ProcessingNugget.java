package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingNugget implements IOreRegistrationHandler {
    public ProcessingNugget() {
        OrePrefix.nugget.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial == Materials.Iron)
            GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.WroughtIron, 1L));
        GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, aStack), aMaterial.contains(SubTag.SMELTING_TO_GEM) ? ItemList.Shape_Mold_Ball.get(0L) : ItemList.Shape_Mold_Ingot.get(0L), OreDictionaryUnifier.get(aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OrePrefix.gem : OrePrefix.ingot, aMaterial.mSmeltInto, 1L), 200, 2);
        if (aMaterial.mStandardMoltenFluid != null)
            GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Nugget.get(0L), aMaterial.getMolten(16L), OreDictionaryUnifier.get(OrePrefix.nugget, aMaterial, 1L), 16, 4);
        GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
        GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
        if (!aMaterial.contains(SubTag.NO_SMELTING)) {
            GT_Values.RA.addAlloySmelterRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), ItemList.Shape_Mold_Nugget.get(0L), GT_Utility.copyAmount(9L, aStack), 100, 1);
            if ((GT_ModHandler.getSmeltingOutput(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), false, null) == null) && (OreDictionaryUnifier.get(OrePrefix.nugget, aMaterial.mSmeltInto, 1L) != null) && (!GT_ModHandler.addSmeltingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), GT_Utility.copyAmount(9L, aStack)))) {
//                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(9L, aStack), new Object[]{aOreDictName});
            }
        }
    }
}
