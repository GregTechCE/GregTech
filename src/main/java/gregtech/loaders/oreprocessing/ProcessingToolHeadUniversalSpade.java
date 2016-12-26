package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.ToolDictNames;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import net.minecraft.item.ItemStack;

public class ProcessingToolHeadUniversalSpade implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingToolHeadUniversalSpade() {
        OrePrefixes.toolHeadUniversalSpade.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.UNIVERSALSPADE, 1, aMaterial, aMaterial, null), new Object[]{aOreDictName, OrePrefixes.stick.get(aMaterial), OrePrefixes.screw.get(aMaterial), ToolDictNames.craftingToolScrewdriver});
    }
}
