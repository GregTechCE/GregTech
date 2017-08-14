package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;

public class ProcessingCrate implements IOreRegistrationHandler {
    public ProcessingCrate() {
        OrePrefix.crateGtDust.add(this);
        OrePrefix.crateGtIngot.add(this);
        OrePrefix.crateGtGem.add(this);
        OrePrefix.crateGtPlate.add(this);
    }

    @Override
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        boolean aSpecialRecipeReq2 = uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING);
        switch (uEntry.orePrefix) {
            case crateGtDust:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 16)), ItemList.Crate_Empty.get(1), OreDictionaryUnifier.get(OrePrefix.crateGtDust, uEntry.material, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.crateGtDust, uEntry.material, 1L), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 16), ItemList.Crate_Empty.get(1), 800, 1);
                if (aSpecialRecipeReq2) ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 16), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefix.crateGtDust.get(uEntry.material)});
                break;
            case crateGtIngot:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16, OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 16)), ItemList.Crate_Empty.get(1), OreDictionaryUnifier.get(OrePrefix.crateGtIngot, uEntry.material, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.crateGtIngot, uEntry.material, 1L), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 16), ItemList.Crate_Empty.get(1), 800, 1);
                if (aSpecialRecipeReq2) ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 16), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefix.crateGtIngot.get(uEntry.material)});
                break;
            case crateGtGem:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16, OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 16)), ItemList.Crate_Empty.get(1), OreDictionaryUnifier.get(OrePrefix.crateGtGem, uEntry.material, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.crateGtGem, uEntry.material, 1L), OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 16), ItemList.Crate_Empty.get(1), 800, 1);
                if (aSpecialRecipeReq2) ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 16), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefix.crateGtGem.get(uEntry.material)});
                break;
            case crateGtPlate:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16, OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 16)), ItemList.Crate_Empty.get(1), OreDictionaryUnifier.get(OrePrefix.crateGtPlate, uEntry.material, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.crateGtPlate, uEntry.material, 1L), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 16), ItemList.Crate_Empty.get(1), 800, 1);
                if (aSpecialRecipeReq2) ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 16), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefix.crateGtPlate.get(uEntry.material)});
                break;
        }
    }
}
