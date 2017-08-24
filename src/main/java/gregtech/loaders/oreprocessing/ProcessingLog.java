package gregtech.loaders.oreprocessing;

import gregtech.GregTechMod;
import gregtech.api.ConfigCategories;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingLog implements IOreRegistrationHandler {
    public ProcessingLog() {
        OrePrefix.log.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (aOreDictName.equals("logRubber")) {
            GTValues.RA.addCentrifugeRecipe(GTUtility.copyAmount(1, stack), null, null, Materials.Methane.getGas(60L), ItemList.IC2_Resin.get(1), ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.plant_ball, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Carbon, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 1L), null, null, new int[]{5000, 3750, 2500, 2500}, 200, 20);
            ModHandler.addSawmillRecipe(GTUtility.copyAmount(1, stack), ItemList.IC2_Resin.get(1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 16));
            ModHandler.addExtractionRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.RawRubber, 1));
            ModHandler.addPulverisationRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 6), ItemList.IC2_Resin.get(1), 33, false);
        } else {
            ModHandler.addPulverisationRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 6), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 1L), 80, false);
        }

        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, Materials.Wood, 2), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | gregtech.api.util.ModHandler.RecipeBits.BUFFERED, new Object[]{"sLf", 'L', GTUtility.copyAmount(1L, stack)});
        GTValues.RA.addLatheRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.stickLong, Materials.Wood, 4), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2L), 160, 8);
        GTValues.RA.addAssemblerRecipe(GTUtility.copyAmount(1, stack), ItemList.Circuit_Integrated.getWithDamage(0, 2), Materials.SeedOil.getFluid(50L), ItemList.FR_Stick.get(1L), 16, 8);
        GTValues.RA.addAssemblerRecipe(GTUtility.copyAmount(8, stack), ItemList.Circuit_Integrated.getWithDamage(0, 8), Materials.SeedOil.getFluid(250L), ItemList.FR_Casing_Impregnated.get(1L), 64, 16);
        GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(1, stack), Materials.Creosote.getFluid(1000), ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1, 8), null, null, null, 16, 16);

        short aMeta = (short) stack.getItemDamage();

        if (aMeta == Short.MAX_VALUE) {
            if ((GTUtility.areStacksEqual(ModHandler.getSmeltingOutput(GTUtility.copyAmount(1, stack), false, null), new ItemStack(Items.COAL, 1, 1)))) {
                GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), null, 1, new ItemStack(Items.COAL, 20, 1), Materials.Creosote.getFluid(4000), 640, 64);
                GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), Materials.Nitrogen.getFluid(1000), 2, new ItemStack(Items.COAL, 20, 1), Materials.Creosote.getFluid(4000), 320, 96);
                GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), null, 3, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Ash, 4), Materials.OilHeavy.getFluid(200), 320, 192);
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", true)) {
                    ModHandler.removeFurnaceSmelting(GTUtility.copyAmount(1, stack));
                }
            }
            for (int i = 0; i < 32767; i++) {
                if ((GTUtility.areStacksEqual(ModHandler.getSmeltingOutput(new ItemStack(stack.getItem(), 1, i), false, null), new ItemStack(Items.COAL, 1, 1)))) {
                    GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), null, 1, new ItemStack(Items.COAL, 20, 1), Materials.Creosote.getFluid(4000), 640, 64);
                    GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), Materials.Nitrogen.getFluid(1000), 2, new ItemStack(Items.COAL, 20, 1), Materials.Creosote.getFluid(4000), 320, 96);
                    GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), null, 3, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Ash, 4), Materials.OilHeavy.getFluid(200), 320, 192);
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", true)) {
                        ModHandler.removeFurnaceSmelting(new ItemStack(stack.getItem(), 1, i));
                    }
                }
                ItemStack tStack = ModHandler.getRecipeOutput(new ItemStack(stack.getItem(), 1, i));
                if (tStack == null) {
                	if (i >= 16) {
                        break;
                      }
                    }
                    else
                    {

                    ItemStack tPlanks = GTUtility.copy(tStack);
                    tPlanks.stackSize = (tPlanks.stackSize * 3 / 2);
                    GTValues.RA.addCutterRecipe(new ItemStack(stack.getItem(), 1, i), Materials.Lubricant.getFluid(1L), GTUtility.copy(tPlanks), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 1L), 200, 8);
                    GTValues.RA.addCutterRecipe(new ItemStack(stack.getItem(), 1, i), GTUtility.copyAmount(GregTechMod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, tStack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2L), 200, 8);
                    ModHandler.addSawmillRecipe(new ItemStack(stack.getItem(), 1, i), tPlanks, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 1L));
                    ModHandler.removeRecipe(new ItemStack(stack.getItem(), 1, i));
                    ModHandler.addCraftingRecipe(GTUtility.copyAmount(GregTechMod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, tStack), new Object[]{"s", "L", Character.valueOf('L'), new ItemStack(stack.getItem(), 1, i)});
                    ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(tStack.stackSize / (GregTechMod.gregtechproxy.mNerfedWoodPlank ? 2 : 1), tStack), new Object[]{new ItemStack(stack.getItem(), 1, i)});
                }
            }
        } else {
            if ((GTUtility.areStacksEqual(ModHandler.getSmeltingOutput(GTUtility.copyAmount(1, stack), false, null), new ItemStack(Items.COAL, 1, 1)))) {
                GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), null, 1, new ItemStack(Items.COAL, 20, 1), Materials.Creosote.getFluid(4000), 640, 64);
                GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), Materials.Nitrogen.getGas(1000), 2, new ItemStack(Items.COAL, 20, 1), Materials.Creosote.getFluid(4000), 320, 96);
                GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), null, 3, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Ash, 4), Materials.OilHeavy.getFluid(200), 320, 192);
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", true)) {
                    ModHandler.removeFurnaceSmelting(GTUtility.copyAmount(1, stack));
                }
            }
            ItemStack tStack = ModHandler.getRecipeOutput(GTUtility.copyAmount(1, stack));
            if (tStack != null) {
                ItemStack tPlanks = GTUtility.copy(tStack);
                tPlanks.stackSize = (tPlanks.stackSize * 3 / 2);
                GTValues.RA.addCutterRecipe(GTUtility.copyAmount(1L, stack), Materials.Lubricant.getFluid(1L), GTUtility.copy(tPlanks), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 1L), 200, 8);
                GTValues.RA.addCutterRecipe(GTUtility.copyAmount(1L, stack), GTUtility.copyAmount(GregTechMod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, tStack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2L), 200, 8);
                ModHandler.addSawmillRecipe(GTUtility.copyAmount(1, stack), tPlanks, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 1L));
                ModHandler.removeRecipe(GTUtility.copyAmount(1, stack));
                ModHandler.addCraftingRecipe(GTUtility.copyAmount(GregTechMod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, tStack), "s", "L", 'L', GTUtility.copyAmount(1, stack));
                ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(tStack.stackSize / (GregTechMod.gregtechproxy.mNerfedWoodPlank ? 2 : 1), tStack), GTUtility.copyAmount(1, stack));
            }
        }

        if ((GTUtility.areStacksEqual(ModHandler.getSmeltingOutput(GTUtility.copyAmount(1, stack), false, null), new ItemStack(Items.COAL, 1, 1)))) {
            GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), null, 1, new ItemStack(Items.COAL, 20, 1), Materials.Creosote.getFluid(4000), 640, 64);
            GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), Materials.Nitrogen.getFluid(1000), 2, new ItemStack(Items.COAL, 20, 1), Materials.Creosote.getFluid(4000), 320, 96);
            GTValues.RA.addPyrolyseRecipe(GTUtility.copyAmount(16L, stack), null, 3, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Ash, 4), Materials.OilHeavy.getFluid(200), 320, 192);
            if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", true)) {
                ModHandler.removeFurnaceSmelting(GTUtility.copyAmount(1, stack));
            }
        }
    }
}
