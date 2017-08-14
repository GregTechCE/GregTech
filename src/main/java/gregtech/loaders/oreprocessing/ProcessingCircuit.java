package gregtech.loaders.oreprocessing;

import com.enderio.core.common.OreDict;
import gregtech.api.items.ItemList;
import gregtech.api.items.OreDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import ic2.core.item.type.CasingResourceType;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

public class ProcessingCircuit implements IOreRegistrationHandler {
    public ProcessingCircuit() {
        OrePrefix.circuit.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.material.toString()) {
            case "Good":
            case "Advanced":
            case "Data":
            case "Elite":
            case "Master":
            case "Ultimate":
                if (!OreDictionaryUnifier.isBlacklisted(stack)) {
                    ModHandler.removeRecipeByOutput(stack);
                }
                break;
            case "Primitive":
                ModHandler.removeRecipeByOutput(stack);
                ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Primitive.get(1), ModHandler.getIC2Item(ItemName.casing, CasingResourceType.steel, 1), OreDictionaryUnifier.get(OrePrefix.wireGt01, Materials.RedAlloy), OreDictionaryUnifier.get(OrePrefix.wireGt01, Materials.RedAlloy), OreDictionaryUnifier.get(OrePrefix.wireGt01, Materials.Tin));
                break;
            case "Basic":
                ModHandler.removeRecipeByOutput(stack);
                ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1), "WWW", "CPC", "WWW", 'C', OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Primitive), 'W', OreDictNames.craftingWireCopper, 'P', OreDictionaryUnifier.get(OrePrefix.plate, Materials.Steel));
                ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1), "WCW", "WPW", "WCW", 'C', OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Primitive), 'W', OreDictNames.craftingWireCopper, 'P', OreDictionaryUnifier.get(OrePrefix.plate, Materials.Steel));
                ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1), "WWW", "CPC", "WWW", 'C', OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Primitive), 'W', OreDictionaryUnifier.get(OrePrefix.cableGt01, Materials.RedAlloy), 'P', OreDictionaryUnifier.get(OrePrefix.plate, Materials.Steel));
                ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1), "WCW", "WPW", "WCW", 'C', OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Primitive), 'W', OreDictionaryUnifier.get(OrePrefix.cableGt01, Materials.RedAlloy), 'P', OreDictionaryUnifier.get(OrePrefix.plate, Materials.Steel));
                ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Basic.get(1), ItemList.Circuit_Integrated.getWildcard(1));
        }
    }
}
