package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingCrate implements IOreRegistrationHandler {
    public ProcessingCrate() {
        OrePrefix.crateGtDust.add(this);
        OrePrefix.crateGtIngot.add(this);
        OrePrefix.crateGtGem.add(this);
        OrePrefix.crateGtPlate.add(this);
    }

    @Override
    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        boolean aSpecialRecipeReq2 = aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_WORKING);
        switch (aPrefix) {
            case crateGtDust:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 16L)), ItemList.Crate_Empty.get(1L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.crateGtDust, aMaterial, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.crateGtDust, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
                if (aSpecialRecipeReq2) GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 16L), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefix.crateGtDust.get(aMaterial)});
                break;
            case crateGtIngot:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 16L)), ItemList.Crate_Empty.get(1L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.crateGtIngot, aMaterial, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.crateGtIngot, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
                if (aSpecialRecipeReq2) GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 16L), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefix.crateGtIngot.get(aMaterial)});
                break;
            case crateGtGem:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 16L)), ItemList.Crate_Empty.get(1L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.crateGtGem, aMaterial, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.crateGtGem, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
                if (aSpecialRecipeReq2) GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 16L), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefix.crateGtGem.get(aMaterial)});
                break;
            case crateGtPlate:
                GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 16L)), ItemList.Crate_Empty.get(1L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.crateGtPlate, aMaterial, 1L), 100, 8);
                GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.crateGtPlate, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
                if (aSpecialRecipeReq2) GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 16L), GT_Proxy.tBits, new Object[]{"Xc", Character.valueOf('X'), OrePrefix.crateGtPlate.get(aMaterial)});
                break;
        }
    }
}
