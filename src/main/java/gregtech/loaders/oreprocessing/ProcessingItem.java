package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingItem implements IOreRegistrationHandler {
    public ProcessingItem() {
        OrePrefixes.item.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (OreDictionaryUnifier.getItemData(aStack) == null && !aOreDictName.equals("itemCertusQuartz") && !aOreDictName.equals("itemNetherQuartz")) {
            switch (aOreDictName) {
                case "itemSilicon":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Silicon, 3628800L, new MaterialStack[0]));
                    GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 0L, 19), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 20), 200, 16);
                case "itemWheat":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Wheat, 3628800L, new MaterialStack[0]));
                case "itemManganese":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Manganese, 3628800L, new MaterialStack[0]));
                case "itemSalt":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Salt, 3628800L, new MaterialStack[0]));
                case "itemMagnesium":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Magnesium, 3628800L, new MaterialStack[0]));
                case "itemPhosphorite":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Phosphorus, 3628800L, new MaterialStack[0]));
                case "itemSulfur":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Sulfur, 3628800L, new MaterialStack[0]));
                case "itemAluminum":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Aluminium, 3628800L, new MaterialStack[0]));
                case "itemSaltpeter":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Saltpeter, 3628800L, new MaterialStack[0]));
                case "itemUranium":
                    OreDictionaryUnifier.addItemData(aStack, new ItemMaterialInfo(Materials.Uranium, 3628800L, new MaterialStack[0]));
            }
        }
    }
}
