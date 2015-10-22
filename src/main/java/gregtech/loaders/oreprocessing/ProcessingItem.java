package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingItem implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingItem() {
        OrePrefixes.item.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (GT_OreDictUnificator.getItemData(aStack) == null) {

            if (!aOreDictName.equals("itemCertusQuartz")) {

                if (!aOreDictName.equals("itemNetherQuartz")) {

                    if (aOreDictName.equals("itemSilicon")) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Silicon, 3628800L, new MaterialStack[0]));
                        GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 0L, 19), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 20), 200, 16);
                    } else if (aOreDictName.equals("itemWheat")) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Wheat, 3628800L, new MaterialStack[0]));
                    } else if (aOreDictName.equals("itemManganese")) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Manganese, 3628800L, new MaterialStack[0]));
                    } else if (aOreDictName.equals("itemSalt")) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Salt, 3628800L, new MaterialStack[0]));
                    } else if (aOreDictName.equals("itemMagnesium")) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Magnesium, 3628800L, new MaterialStack[0]));
                    } else if ((aOreDictName.equals("itemPhosphorite")) || (aOreDictName.equals("itemPhosphorus"))) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Phosphorus, 3628800L, new MaterialStack[0]));
                    } else if (aOreDictName.equals("itemSulfur")) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Sulfur, 3628800L, new MaterialStack[0]));
                    } else if ((aOreDictName.equals("itemAluminum")) || (aOreDictName.equals("itemAluminium"))) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Aluminium, 3628800L, new MaterialStack[0]));
                    } else if (aOreDictName.equals("itemSaltpeter")) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Saltpeter, 3628800L, new MaterialStack[0]));
                    } else if (aOreDictName.equals("itemUranium")) {
                        GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Uranium, 3628800L, new MaterialStack[0]));
                    } else {
                        //System.out.println("Item Name: " + aOreDictName + " !!!Unknown Item detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
                    }
                }
            }
        }
    }
}
