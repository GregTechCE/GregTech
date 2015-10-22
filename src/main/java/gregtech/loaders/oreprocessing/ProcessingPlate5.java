package gregtech.loaders.oreprocessing;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.ToolDictNames;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingPlate5 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingPlate5() {
        OrePrefixes.plateQuintuple.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.removeRecipeByOutput(aStack);
        GregTech_API.registerCover(aStack, new gregtech.api.objects.GT_RenderedTexture(aMaterial.mIconSet.mTextures[75], aMaterial.mRGBa, false), null);
        if ((!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) && (GregTech_API.sRecipeFile.get(gregtech.api.enums.ConfigCategories.Tools.hammerquintupleplate, OrePrefixes.plate.get(aMaterial).toString(), true))) {
            GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefixes.plateQuadruple.get(aMaterial), Character.valueOf('B'), OrePrefixes.plate.get(aMaterial)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{ToolDictNames.craftingToolForgeHammer, OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial)});
        } else {
            gregtech.api.enums.GT_Values.RA.addAssemblerRecipe(gregtech.api.util.GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 5L), ItemList.Circuit_Integrated.getWithDamage(0L, 5L, new Object[0]), Materials.Glue.getFluid(40L), GT_Utility.copyAmount(1L, new Object[]{aStack}), 160, 8);
        }
    }
}
