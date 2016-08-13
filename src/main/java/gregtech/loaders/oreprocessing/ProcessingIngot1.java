package gregtech.loaders.oreprocessing;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingIngot1 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingIngot1() {
        OrePrefixes.ingot.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        Object[] Ogem1 = new Object[]{aStack};
        if (aMaterial.mFuelPower > 0) {
            GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, Ogem1), null, aMaterial.mFuelPower, aMaterial.mFuelType);
        }
        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, Ogem1), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, aMaterial, 1L), 100, 8);
        GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);

        if (aMaterial.mStandardMoltenFluid != null) {
            GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), aMaterial.getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), 32, 8);
        }
        GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
        GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
        if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
            GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, Ogem1), aMaterial, aPrefix.mMaterialAmount, null, null, null);
        }
        boolean BmatNSs1 = aMaterial.contains(SubTag.NO_SMASHING);
        if (!BmatNSs1) {
            long matgM1 = aMaterial.getMass();
            GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, Ogem1), GT_Utility.copy(new Object[]{GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 2L), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 8L)}), 100, 4);
            GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int) Math.max(matgM1, 1L), 16);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int) Math.max(matgM1 * 1L, 1L), 24);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int) Math.max(matgM1 * 2L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int) Math.max(matgM1 * 3L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int) Math.max(matgM1 * 4L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int) Math.max(matgM1 * 5L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int) Math.max(matgM1 * 9L, 1L), 96);
        }


        if (!OrePrefixes.block.isIgnored(aMaterial)) {
            GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L));}
        if (!aMaterial.contains(SubTag.NO_WORKING)) {
            long matgM1 = aMaterial.getMass();
            GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, Ogem1), GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial.mMacerateInto, 2L), (int) Math.max(matgM1 * 5L, 1L), 16);}
        if (!aMaterial.contains(SubTag.NO_SMELTING)) {
            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, Ogem1), ItemList.Shape_Mold_Nugget.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial, 9L), 100, 1);
            if ((GT_ModHandler.getSmeltingOutput(aStack, false, null) == null) && (GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 1L) != null) && (!GT_ModHandler.addSmeltingRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 9L)))) {
                GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 9L), new Object[]{aOreDictName});}
        }
        ItemStack tStack;
        if ((null != (tStack = GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L))) && (
                (aMaterial.mBlastFurnaceRequired) || (aMaterial.contains(SubTag.NO_SMELTING)))) {
            GT_ModHandler.removeFurnaceSmelting(tStack);
        }

        GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, Ogem1), OrePrefixes.plate.get(aMaterial).toString(), !BmatNSs1);

        if (aMaterial == Materials.Mercury) {
            System.err.println("Quicksilver Ingots?, Don't tell me there is an Armor made of that highly toxic and very likely to be melting Material!");
        }
    }
}
