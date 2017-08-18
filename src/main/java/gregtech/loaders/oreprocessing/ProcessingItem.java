package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingItem implements IOreRegistrationHandler {
    public ProcessingItem() {
        OrePrefix.item.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (OreDictionaryUnifier.getItemData(stack) == null && !aOreDictName.equals("itemCertusQuartz") && !aOreDictName.equals("itemNetherQuartz")) {
            switch (aOreDictName) {
                case "itemSilicon":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Silicon, 3628800L, new MaterialStack[0]));
                    GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(1, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 0, 19), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 20), 200, 16);
                case "itemWheat":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Wheat, 3628800L, new MaterialStack[0]));
                case "itemManganese":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Manganese, 3628800L, new MaterialStack[0]));
                case "itemSalt":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Salt, 3628800L, new MaterialStack[0]));
                case "itemMagnesium":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Magnesium, 3628800L, new MaterialStack[0]));
                case "itemPhosphorite":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Phosphor, 3628800L, new MaterialStack[0]));
                case "itemSulfur":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Sulfur, 3628800L, new MaterialStack[0]));
                case "itemAluminum":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Aluminium, 3628800L, new MaterialStack[0]));
                case "itemSaltpeter":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Saltpeter, 3628800L, new MaterialStack[0]));
                case "itemUranium":
                    OreDictionaryUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Uranium, 3628800L, new MaterialStack[0]));
            }
        }
    }
}
