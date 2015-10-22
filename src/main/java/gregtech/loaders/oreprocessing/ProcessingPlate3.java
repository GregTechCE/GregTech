package gregtech.loaders.oreprocessing;

import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingPlate3 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingPlate3() {
        OrePrefixes.plateTriple.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.removeRecipeByOutput(aStack);

        GregTech_API.registerCover(aStack, new gregtech.api.objects.GT_RenderedTexture(aMaterial.mIconSet.mTextures[73], aMaterial.mRGBa, false), null);

        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int) Math.max(aMaterial.getMass() * 3L, 1L), 96);
        }
        if ((!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) && (GregTech_API.sRecipeFile.get(gregtech.api.enums.ConfigCategories.Tools.hammertripleplate, OrePrefixes.plate.get(aMaterial).toString(), true))) {
            GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), gregtech.api.util.GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | gregtech.api.util.GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefixes.plateDouble.get(aMaterial), Character.valueOf('B'), OrePrefixes.plate.get(aMaterial)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{gregtech.api.enums.ToolDictNames.craftingToolForgeHammer, OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial)});
        } else {
            GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 3L), gregtech.api.enums.ItemList.Circuit_Integrated.getWithDamage(0L, 3L, new Object[0]), Materials.Glue.getFluid(20L), GT_Utility.copyAmount(1L, new Object[]{aStack}), 96, 8);
        }
    }
}
