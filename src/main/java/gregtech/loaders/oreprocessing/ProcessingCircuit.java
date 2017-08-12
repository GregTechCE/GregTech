package gregtech.loaders.oreprocessing;

import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.items.ItemList;
import gregtech.api.unification.material.Materials;
import gregtech.api.items.OreDictNames;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import ic2.core.item.type.CasingResourceType;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

public class ProcessingCircuit implements IOreRegistrationHandler {
    public ProcessingCircuit() {
        OrePrefixes.circuit.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
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
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Primitive.get(1L), new Object[]{GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.steel, 1), OrePrefixes.wireGt01.get(Materials.RedAlloy), OrePrefixes.wireGt01.get(Materials.RedAlloy), OrePrefixes.wireGt01.get(Materials.Tin)});
                break;
            case "Basic":
                GT_ModHandler.removeRecipeByOutput(aStack);
                GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{"WWW", "CPC", "WWW", 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OreDictNames.craftingWireCopper, 'P', OrePrefixes.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{"WCW", "WPW", "WCW", 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OreDictNames.craftingWireCopper, 'P', OrePrefixes.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{"WWW", "CPC", "WWW", 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OrePrefixes.cableGt01.get(Materials.RedAlloy), 'P', OrePrefixes.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{"WCW", "WPW", "WCW", 'C', OrePrefixes.circuit.get(Materials.Primitive), 'W', OrePrefixes.cableGt01.get(Materials.RedAlloy), 'P', OrePrefixes.plate.get(Materials.Steel)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Basic.get(1L), new Object[]{ItemList.Circuit_Integrated.getWildcard(1L)});
        }
    }
}
