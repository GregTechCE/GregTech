package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeRegistrator;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingNugget implements IOreRegistrationHandler {
    public ProcessingNugget() {
        OrePrefix.nugget.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (uEntry.material == Materials.Iron)
            ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.WroughtIron, 1L));
        GTValues.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(9, stack), uEntry.material.contains(SubTag.SMELTING_TO_GEM) ? ItemList.Shape_Mold_Ball.get(0L) : ItemList.Shape_Mold_Ingot.get(0L), OreDictionaryUnifier.get(uEntry.material.contains(SubTag.SMELTING_TO_GEM) ? OrePrefix.gem : OrePrefix.ingot, uEntry.material.mSmeltInto, 1L), 200, 2);
        if (uEntry.material.mStandardMoltenFluid != null)
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Nugget.get(0L), uEntry.material.getMolten(16L), OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material, 1L), 16, 4);
        RecipeRegistrator.registerReverseFluidSmelting(stack, uEntry.material, uEntry.orePrefix.materialAmount, null);
        RecipeRegistrator.registerReverseMacerating(stack, uEntry.material, uEntry.orePrefix.materialAmount, null, null, null, false);
        if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING)) {
            GTValues.RA.addAlloySmelterRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1L), ItemList.Shape_Mold_Nugget.get(0L), GT_Utility.copyAmount(9L, stack), 100, 1);
            if ((ModHandler.getSmeltingOutput(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1), false, null) == null) && (OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material.mSmeltInto, 1L) != null) && (!ModHandler.addSmeltingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1), GT_Utility.copyAmount(9, stack)))) {
//                ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(9L, stack), new Object[]{aOreDictName});
            }
        }
    }
}
