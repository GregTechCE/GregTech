package gregtech.loaders.oreprocessing;

import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.items.ItemList;
import gregtech.api.unification.material.Materials;
import gregtech.api.items.OreDictNames;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_ModHandler;
import ic2.core.item.type.CasingResourceType;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

public class ProcessingCircuit implements IOreRegistrationHandler {
    public ProcessingCircuit() {
        OrePrefix.circuit.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        switch (aMaterial.mName) {
            case "Good":
            case "Advanced":
            case "Data":
            case "Elite":
            case "Master":
            case "Ultimate":
                if (!OreDictionaryUnifier.isBlacklisted(aStack))
                    GT_ModHandler.removeRecipeByOutput(aStack);
                break;
            case "Primitive":
                GT_ModHandler.removeRecipeByOutput(aStack);
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Primitive.get(1L), new Object[]{GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.steel, 1), OrePrefix.wireGt01.get(Materials.RedAlloy), OrePrefix.wireGt01.get(Materials.RedAlloy), OrePrefix.wireGt01.get(Materials.Tin)});
                break;
            case "Basic":
                GT_ModHandler.removeRecipeByOutput(aStack);
                GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{"WWW", "CPC", "WWW", 'C', OrePrefix.circuit.get(Materials.Primitive), 'W', OreDictNames.craftingWireCopper, 'P', OrePrefix.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{"WCW", "WPW", "WCW", 'C', OrePrefix.circuit.get(Materials.Primitive), 'W', OreDictNames.craftingWireCopper, 'P', OrePrefix.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{"WWW", "CPC", "WWW", 'C', OrePrefix.circuit.get(Materials.Primitive), 'W', OrePrefix.cableGt01.get(Materials.RedAlloy), 'P', OrePrefix.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{"WCW", "WPW", "WCW", 'C', OrePrefix.circuit.get(Materials.Primitive), 'W', OrePrefix.cableGt01.get(Materials.RedAlloy), 'P', OrePrefix.plate.get(Materials.Steel)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{ItemList.Circuit_Integrated.getWildcard(1L)});
        }
    }
}
