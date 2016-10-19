package gregtech.loaders.oreprocessing;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingLog implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingLog() {
        OrePrefixes.log.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.equals("logRubber")) {
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, null, Materials.Methane.getGas(60L), ItemList.IC2_Resin.get(1L, new Object[0]), GT_ModHandler.getIC2Item("plantBall", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), null, null, new int[]{5000, 3750, 2500, 2500}, 200, 20);
            GT_ModHandler.addSawmillRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.IC2_Resin.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 16L));
            GT_ModHandler.addExtractionRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.RawRubber, 1L));
            GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 6L), ItemList.IC2_Resin.get(1L, new Object[0]), 33, false);
        } else {
            GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 6L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 80, false);
        }

        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 2L), gregtech.api.util.GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | gregtech.api.util.GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"sLf", 'L', GT_Utility.copyAmount(1L, new Object[]{aStack})});
        GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 160, 8);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), Materials.SeedOil.getFluid(50L), ItemList.FR_Stick.get(1L, new Object[0]), 16, 8);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), Materials.SeedOil.getFluid(250L), ItemList.FR_Casing_Impregnated.get(1L, new Object[0]), 64, 16);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Creosote.getFluid(1000L), GT_ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1L, 8), null, null, null, 16, 16);

        short aMeta = (short) aStack.getItemDamage();

        if (aMeta == Short.MAX_VALUE) {
            if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[]{aStack}), false, null), new ItemStack(Items.coal, 1, 1)))) {
                GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), null, 1, new ItemStack(Items.coal, 20, 1), Materials.Creosote.getFluid(4000), 640, 64);
                GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), Materials.Nitrogen.getGas(1000), 2, new ItemStack(Items.coal, 20, 1), Materials.Creosote.getFluid(4000), 320, 96);
                GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), null, 3, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 4), Materials.OilHeavy.getFluid(200), 320, 192);
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", true)) {
                    GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[]{aStack}));
                }
            }
            for (int i = 0; i < 32767; i++) {
                if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(new ItemStack(aStack.getItem(), 1, i), false, null), new ItemStack(Items.coal, 1, 1)))) {
                    GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), null, 1, new ItemStack(Items.coal, 20, 1), Materials.Creosote.getFluid(4000), 640, 64);
                    GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), Materials.Nitrogen.getGas(1000), 2, new ItemStack(Items.coal, 20, 1), Materials.Creosote.getFluid(4000), 320, 96);
                    GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), null, 3, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 4), Materials.OilHeavy.getFluid(200), 320, 192);
                    if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", true)) {
                        GT_ModHandler.removeFurnaceSmelting(new ItemStack(aStack.getItem(), 1, i));
                    }
                }
                ItemStack tStack = GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(aStack.getItem(), 1, i)});
                if (tStack == null) {
                	if (i >= 16) {
                        break;
                      }
                    }
                    else
                    {
                	
                    ItemStack tPlanks = GT_Utility.copy(new Object[]{tStack});
                    tPlanks.stackSize = (tPlanks.stackSize * 3 / 2);
                    GT_Values.RA.addCutterRecipe(new ItemStack(aStack.getItem(), 1, i), Materials.Lubricant.getFluid(1L), GT_Utility.copy(new Object[]{tPlanks}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 200, 8);
                    GT_Values.RA.addCutterRecipe(new ItemStack(aStack.getItem(), 1, i), GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[]{tStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 200, 8);
                    GT_ModHandler.addSawmillRecipe(new ItemStack(aStack.getItem(), 1, i), tPlanks, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L));
                    GT_ModHandler.removeRecipe(new ItemStack[]{new ItemStack(aStack.getItem(), 1, i)});
                    GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[]{tStack}), new Object[]{"s", "L", 'L', new ItemStack(aStack.getItem(), 1, i)});
                    GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(tStack.stackSize / (GT_Mod.gregtechproxy.mNerfedWoodPlank ? 2 : 1), new Object[]{tStack}), new Object[]{new ItemStack(aStack.getItem(), 1, i)});
                }
            }
        } else {
            if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[]{aStack}), false, null), new ItemStack(Items.coal, 1, 1)))) {
                GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), null, 1, new ItemStack(Items.coal, 20, 1), Materials.Creosote.getFluid(4000), 640, 64);
                GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), Materials.Nitrogen.getGas(1000), 2, new ItemStack(Items.coal, 20, 1), Materials.Creosote.getFluid(4000), 320, 96);
                GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), null, 3, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 4), Materials.OilHeavy.getFluid(200), 320, 192);
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", true)) {
                    GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[]{aStack}));
                }
            }
            ItemStack tStack = GT_ModHandler.getRecipeOutput(new ItemStack[]{GT_Utility.copyAmount(1L, new Object[]{aStack})});
            if (tStack != null) {
                ItemStack tPlanks = GT_Utility.copy(new Object[]{tStack});
                tPlanks.stackSize = (tPlanks.stackSize * 3 / 2);
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Lubricant.getFluid(1L), GT_Utility.copy(new Object[]{tPlanks}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 200, 8);
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[]{tStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 200, 8);
                GT_ModHandler.addSawmillRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), tPlanks, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L));
                GT_ModHandler.removeRecipe(new ItemStack[]{GT_Utility.copyAmount(1L, new Object[]{aStack})});
                GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[]{tStack}), new Object[]{"s", "L", 'L', GT_Utility.copyAmount(1L, new Object[]{aStack})});
                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(tStack.stackSize / (GT_Mod.gregtechproxy.mNerfedWoodPlank ? 2 : 1), new Object[]{tStack}), new Object[]{GT_Utility.copyAmount(1L, new Object[]{aStack})});
            }
        }

        if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[]{aStack}), false, null), new ItemStack(Items.coal, 1, 1)))) {
            GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), null, 1, new ItemStack(Items.coal, 20, 1), Materials.Creosote.getFluid(4000), 640, 64);
            GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), Materials.Nitrogen.getGas(1000), 2, new ItemStack(Items.coal, 20, 1), Materials.Creosote.getFluid(4000), 320, 96);
            GT_Values.RA.addPyrolyseRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), null, 3, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 4), Materials.OilHeavy.getFluid(200), 320, 192);
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", true))
                GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[]{aStack}));
        }
    }
}
