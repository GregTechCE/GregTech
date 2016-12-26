package gregtech.loaders.oreprocessing;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingCrate implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingCrate() {
        OrePrefixes.crateGtDust.add(this);
        OrePrefixes.crateGtIngot.add(this);
        OrePrefixes.crateGtGem.add(this);
        OrePrefixes.crateGtPlate.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        boolean aSpecialRecipeReq2 = aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_WORKING);
        switch (aPrefix) {
            case crateGtDust:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 16L)), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtDust, aMaterial, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtDust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
                if (aSpecialRecipeReq2) GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 16L), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefixes.crateGtDust.get(aMaterial)});
                break;
            case crateGtIngot:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 16L)), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, aMaterial, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
                if (aSpecialRecipeReq2) GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 16L), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefixes.crateGtIngot.get(aMaterial)});
                break;
            case crateGtGem:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 16L)), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtGem, aMaterial, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtGem, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
                if (aSpecialRecipeReq2) GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 16L), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefixes.crateGtGem.get(aMaterial)});
                break;
            case crateGtPlate:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 16L)), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtPlate, aMaterial, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtPlate, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
                if (aSpecialRecipeReq2) GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 16L), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefixes.crateGtPlate.get(aMaterial)});
                break;
        }
    }
}
